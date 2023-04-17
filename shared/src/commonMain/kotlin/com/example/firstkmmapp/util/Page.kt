package com.example.firstkmmapp.util

data class Page<ItemType, PageIndexType>(
    val items: List<ItemType>,
    val hasMore: Boolean,
    val nextPageIndex: PageIndexType?
) {
    inline fun <R> map(transform: (ItemType) -> R): Page<R, PageIndexType> {
        return Page(
            items = items.map(transform),
            hasMore = hasMore,
            nextPageIndex = nextPageIndex
        )
    }
}