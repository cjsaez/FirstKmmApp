package com.example.firstkmmapp.pagingBase

import com.example.firstkmmapp.domain.util.Paginator
import com.example.firstkmmapp.util.Page
import com.example.firstkmmapp.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DefaultPaginator<ItemType, PageIndexType>(
    private val scope: CoroutineScope,
    var args: Any? = null,
    private val limit: Int = 40,
    private val initialPageIndex: PageIndexType,
    val query: (args: Any?) -> Flow<List<ItemType>>,
    val fetch: suspend (page: PageIndexType?, limit: Int, args: Any?) -> Result<Page<ItemType, PageIndexType>>,
    val saveFetchResult: suspend (List<ItemType>, Boolean) -> Unit,
) : Paginator<ItemType, PageIndexType>() {

    private var currentPage: PageIndexType? = initialPageIndex
    private val requestPageFlow: MutableSharedFlow<Resource<List<ItemType>, PageIndexType>> = MutableSharedFlow()
    private var job: Job? = null
    private var isMoreItems = true

    override val itemsFlow: Flow<Resource<List<ItemType>, PageIndexType>> = merge(
        query(args)
            .map { Resource.Success(it, currentPage) }
            .shareIn(
                scope = scope,
                started = SharingStarted.Eagerly
            ),
        requestPageFlow
    )

    private val flow = flow {
        val cache = query(args).first()
        emit(Resource.Loading(cache, currentPage))

        try{
            val request = fetch(currentPage, limit, args)
            if (request.isSuccess) {
                if(!request.getOrNull()!!.hasMore && request.getOrNull()!!.items.isEmpty()){
                    emit(Resource.Success(data = cache, page = null))
                } else {
                    saveFetchResult(request.getOrNull()!!.items, currentPage == initialPageIndex)
                }
                currentPage = request.getOrNull()?.nextPageIndex
                isMoreItems = request.getOrNull()?.hasMore != false
            }else {
                emit(Resource.Error(request.exceptionOrNull()!!, cache, currentPage))
            }
        }catch (e: Exception){
            emit(Resource.Error(e, cache, currentPage))
        }
        return@flow
    }

    override fun getMoreItems() {
        if (isMoreItems && (job == null || job?.isCancelled == true || job?.isCompleted == true)) {
            job = scope.launch {
                flow.collect {
                    requestPageFlow.emit(it as Resource<List<ItemType>, PageIndexType>)
                }
            }
        }
    }

    override fun reset() {
        if (job != null && job!!.isActive) job?.cancel()
        currentPage = initialPageIndex

        job = scope.launch {
            flow.collect {
                requestPageFlow.emit(it as Resource<List<ItemType>, PageIndexType>)
            }
        }
    }

    override fun setArguments(args: Any?) {
        this.args = args
    }

    override val isFirstFetch: Boolean
        get() = currentPage == initialPageIndex
}