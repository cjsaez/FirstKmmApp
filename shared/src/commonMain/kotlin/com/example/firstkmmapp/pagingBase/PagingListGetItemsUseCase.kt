package com.example.firstkmmapp.pagingBase

import com.example.firstkmmapp.domain.util.Paginator
import kotlinx.coroutines.CoroutineScope

interface PagingListGetItemsUseCase<T, K> {

    operator fun invoke(scope: CoroutineScope): Paginator<T, K>
}