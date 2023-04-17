package com.example.firstkmmapp.domain.util

import com.example.firstkmmapp.util.Resource
import kotlinx.coroutines.flow.Flow

abstract class Paginator<ItemType, PageIndexType> {

    abstract val itemsFlow: Flow<Resource<List<ItemType>, PageIndexType>>
    abstract val isFirstFetch: Boolean
    abstract fun getMoreItems()
    abstract fun reset()
    abstract fun setArguments(args: Any?)
}