package pl.dawidfendler.onboarding.util

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState

@OptIn(ExperimentalFoundationApi::class)
internal val PagerState.pageOffset: Float
    get() = this.currentPage + this.currentPageOffsetFraction

@OptIn(ExperimentalFoundationApi::class)
internal fun PagerState.calculateCurrentOffsetForPage(page: Int): Float {
    return (currentPage - page) + currentPageOffsetFraction
}
