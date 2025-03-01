package pl.dawidfendler.onboarding.util

import androidx.compose.foundation.pager.PagerState

internal val PagerState.pageOffset: Float
    get() = this.currentPage + this.currentPageOffsetFraction

internal fun PagerState.calculateCurrentOffsetForPage(page: Int): Float {
    return (currentPage - page) + currentPageOffsetFraction
}
