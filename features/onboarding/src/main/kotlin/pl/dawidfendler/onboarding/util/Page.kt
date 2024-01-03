package pl.dawidfendler.onboarding.util

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import pl.dawidfendler.onboarding.R

internal data class Page(
    @StringRes val titleId: Int,
    @StringRes val descriptionId: Int,
    @DrawableRes val image: Int
)

internal val pages = listOf(
    Page(
        titleId = R.string.onboarding_first_page_title,
        descriptionId = R.string.onboarding_first_page_description,
        image = R.drawable.projectmanagement
    ),
    Page(
        titleId = R.string.onboarding_second_page_title,
        descriptionId = R.string.onboarding_second_page_description,
        image = R.drawable.planning
    ),
    Page(
        titleId = R.string.onboarding_third_page_title,
        descriptionId = R.string.onboarding_third_page_description,
        image = R.drawable.graph
    )
)
