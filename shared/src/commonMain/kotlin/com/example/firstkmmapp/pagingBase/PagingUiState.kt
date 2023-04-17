package com.example.firstkmmapp.pagingBase

data class PagingUiState<T>(
    val items: List<T> = emptyList(),
    val isFirstTimeRequestError: Boolean = false,
    val isFirstTimeRequesting: Boolean = false,
    val isRequestingNewPage: Boolean = false,
    val isRefreshingBySwiped: Boolean = false,
    val emptyListText: String? = null,
    val requestedErrorText: String? = null
)