package pl.dawidfendler.data.mapper

import pl.dawidfendler.data.model.categories.CategoryEntity
import pl.dawidfendler.domain.model.categories.CategoryIcon
import pl.dawidfendler.domain.model.categories.CategoryType
import pl.dawidfendler.domain.model.categories.FinanceCategory

internal fun CategoryEntity.toFinanceCategory() = FinanceCategory(
    name = name,
    type = if (type == "income") {
        CategoryType.INCOME
    } else {
        CategoryType.EXPENSE
    },
    icon = iconType.toCategoryIcon()
)

internal fun String.toCategoryIcon() = when (this) {
    "FOOD" -> CategoryIcon.FOOD
    "TRANSPORT" -> CategoryIcon.TRANSPORT
    "HOME" -> CategoryIcon.HOME
    "SUBSCRIPTIONS" -> CategoryIcon.SUBSCRIPTIONS
    "HEALTH" -> CategoryIcon.HEALTH
    "BEAUTY" -> CategoryIcon.BEAUTY
    "ENTERTAINMENT" -> CategoryIcon.ENTERTAINMENT
    "SHOPPING" -> CategoryIcon.SHOPPING
    "EDUCATION" -> CategoryIcon.EDUCATION
    "TRAVEL" -> CategoryIcon.TRAVEL
    "GIFTS" -> CategoryIcon.GIFTS
    "OTHER_EXPENSE" -> CategoryIcon.OTHER_EXPENSE
    "SALARY" -> CategoryIcon.SALARY
    "BONUS" -> CategoryIcon.BONUS
    "INVESTMENT" -> CategoryIcon.INVESTMENT
    "REFUND" -> CategoryIcon.REFUND
    "SALE" -> CategoryIcon.SALE
    "OTHER_INCOME" -> CategoryIcon.OTHER_INCOME
    else -> CategoryIcon.OTHER_EXPENSE
}

internal fun FinanceCategory.toCategoryEntity() = CategoryEntity(
    name = name,
    type = if (type == CategoryType.INCOME) {
        "income"
    } else {
        "expense"
    },
    iconType = icon.toString()
)
