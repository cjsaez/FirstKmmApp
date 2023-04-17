package com.example.firstkmmapp.pagingBase

import com.example.firstkmmapp.domain.util.Paginator
import com.example.firstkmmapp.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

open class PagingListViewModel<T, K>(
    coroutineScope: CoroutineScope? = null,
    getItemsUseCase: PagingListGetItemsUseCase<T, K>
) {
    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)

    private val _pagingUiState = MutableStateFlow(PagingUiState<T>())
    val pagingUiState: StateFlow<PagingUiState<T>> = _pagingUiState

    var receivePagingListActions: (PagingUiActions) -> Unit

    private var paginator: Paginator<T, K>
    private var itemsJob: Job? = null

    init {
        receivePagingListActions = { action ->
            when (action) {
                is PagingUiActions.NewPage -> onNewPage()
                is PagingUiActions.Refresh -> onRefresh()
//                is PagingUiActions.ItemClicked -> onItemClicked( action.item as ItemType, action.holder )
            }
        }

        paginator = getItemsUseCase(viewModelScope)
        paginator.reset()

        itemsJob = viewModelScope.launch {
            paginator.itemsFlow.collect{
                println("erererererer  $it")
                when(it){
                    is Resource.Success -> {
                        _pagingUiState.value = pagingUiState.value.copy(
                            items = it.data ?: emptyList(),
                            emptyListText = if (it.data.isNullOrEmpty()) "Empty" else null,
                            isFirstTimeRequesting = false,
                            isRequestingNewPage = false,
                            isRefreshingBySwiped = false,
                            isFirstTimeRequestError = false,
                            requestedErrorText = null
                        )
                    }
                    is Resource.Error -> {
                        _pagingUiState.value = pagingUiState.value.copy(
                            items = it.data ?: emptyList(),
                            emptyListText = null,
                            isFirstTimeRequesting = false,
                            isRequestingNewPage = false,
                            isRefreshingBySwiped = false,
                            isFirstTimeRequestError = paginator.isFirstFetch,
                            requestedErrorText = it.message!!.message ?: "Some error!"
                        )
                    }
                    is Resource.Loading -> {
                        _pagingUiState.value = pagingUiState.value.copy(
                            items = it.data ?: emptyList(),
                            emptyListText = null,
                            isFirstTimeRequesting = paginator.isFirstFetch,
                            isRequestingNewPage = !paginator.isFirstFetch,
                            isRefreshingBySwiped = pagingUiState.value.isRefreshingBySwiped,
                            isFirstTimeRequestError = false,
                            requestedErrorText = null
                        )
                    }
                }
            }
        }
    }

    protected open fun onNewPage() {
        paginator.getMoreItems()
    }
    protected open fun onRefresh() {
        _pagingUiState.value = pagingUiState.value.copy(
            items = pagingUiState.value.items,
            emptyListText = pagingUiState.value.emptyListText,
            isFirstTimeRequesting = pagingUiState.value.isFirstTimeRequesting,
            isRequestingNewPage = pagingUiState.value.isRequestingNewPage,
            isRefreshingBySwiped = true,
            isFirstTimeRequestError = pagingUiState.value.isFirstTimeRequestError,
            requestedErrorText = pagingUiState.value.requestedErrorText
        )

        paginator.reset()
    }

    sealed class PagingUiActions {
        class Refresh() : PagingUiActions()
        class NewPage() : PagingUiActions()
//        class ItemClicked(val item: Any, val holder: RecyclerView.ViewHolder) : PagingUiActions()
    }
}