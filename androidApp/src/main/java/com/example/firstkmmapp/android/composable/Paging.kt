package com.example.firstkmmapp.android.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.firstkmmapp.android.composable.*
import com.example.firstkmmapp.pagingBase.PagingListViewModel
import com.example.firstkmmapp.pagingBase.PagingUiState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun <T> PagingListView(
    modifier: Modifier = Modifier,
    uiState: PagingUiState<T>,
    receiveActions: (PagingListViewModel.PagingUiActions) -> Unit,
    pagingHeaderError: (listScope: LazyListScope, modifier: Modifier, errorText: String, retry: () -> Unit) -> Unit = ::PagingHeaderError,
    pagingFooterError: (listScope: LazyListScope, modifier: Modifier, errorText: String, retry: () -> Unit) -> Unit = ::PagingFooterError,
    loadingFooter: (listScope: LazyListScope) -> Unit = ::LoadingFooter,
    firstLoading: @Composable () -> Unit = { FirstLoading() },
    firstRequestError: @Composable (errorText: String, retry: () -> Unit) -> Unit = { errorText, retry -> FirstRequestError(errorText = errorText, retry = retry) },
    emptyView: @Composable () -> Unit = { EmptyView() },
    itemView: @Composable (T, Int, (T) -> Unit) -> Unit,
    onSelectItem: (T) -> Unit
){
    Box(modifier = modifier.fillMaxSize()){
        with(uiState) {
            if (isFirstTimeRequesting && items.isEmpty() && emptyListText == null){
                firstLoading.invoke()
            }else{
                if (items.isNotEmpty()){

                    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isRefreshingBySwiped)
                    SwipeRefresh(
                        state = swipeRefreshState,
                        onRefresh = {
                            receiveActions.invoke(PagingListViewModel.PagingUiActions.Refresh())
                        }) {

                        LazyColumn(modifier = Modifier
                            .fillMaxSize()
                            .padding(15.dp)){

                            if (isFirstTimeRequestError){
                                pagingHeaderError.invoke(this, Modifier, requestedErrorText!!) {
                                    receiveActions.invoke(PagingListViewModel.PagingUiActions.NewPage())
                                }
                            }

                            itemsIndexed(items){ index, item ->

                                if (index >= items.lastIndex - 1 && requestedErrorText == null){
                                    receiveActions.invoke(PagingListViewModel.PagingUiActions.NewPage())
                                }

                                itemView.invoke(item, index){
                                    onSelectItem.invoke(it)
                                }
                            }

                            if(isRequestingNewPage){
                                loadingFooter.invoke(this)
                            }

                            if (!isFirstTimeRequestError && requestedErrorText != null){
                                pagingFooterError.invoke(this, Modifier, requestedErrorText!!) {
                                    receiveActions.invoke(PagingListViewModel.PagingUiActions.NewPage())
                                }
                            }
                        }
                    }
                }else if (emptyListText != null) {
                    emptyView.invoke()
                }else if (requestedErrorText != null){
                    firstRequestError.invoke(errorText = requestedErrorText!!) {
                        receiveActions.invoke(PagingListViewModel.PagingUiActions.NewPage())
                    }
                }
            }
        }
    }
}