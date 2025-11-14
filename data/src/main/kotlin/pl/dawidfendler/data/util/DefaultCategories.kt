package pl.dawidfendler.data.util

import pl.dawidfendler.domain.model.categories.CategoryIcon
import pl.dawidfendler.domain.model.categories.CategoryType
import pl.dawidfendler.domain.model.categories.FinanceCategory

object DefaultCategories {

    val defaultExpenseCategories = listOf(
        FinanceCategory("Food & Drinks", CategoryIcon.FOOD, CategoryType.EXPENSE),
        FinanceCategory("Transport", CategoryIcon.TRANSPORT, CategoryType.EXPENSE),
        FinanceCategory("Housing", CategoryIcon.HOME, CategoryType.EXPENSE),
        FinanceCategory("Subscriptions", CategoryIcon.SUBSCRIPTIONS, CategoryType.EXPENSE),
        FinanceCategory("Health", CategoryIcon.HEALTH, CategoryType.EXPENSE),
        FinanceCategory("Beauty & Hygiene", CategoryIcon.BEAUTY, CategoryType.EXPENSE),
        FinanceCategory("Entertainment & Hobby", CategoryIcon.ENTERTAINMENT, CategoryType.EXPENSE),
        FinanceCategory("Shopping & Electronics", CategoryIcon.SHOPPING, CategoryType.EXPENSE),
        FinanceCategory("Education", CategoryIcon.EDUCATION, CategoryType.EXPENSE),
        FinanceCategory("Travel", CategoryIcon.TRAVEL, CategoryType.EXPENSE),
        FinanceCategory("Gifts & Donations", CategoryIcon.GIFTS, CategoryType.EXPENSE),
        FinanceCategory("Other Expenses", CategoryIcon.OTHER_EXPENSE, CategoryType.EXPENSE)
    )

    val defaultIncomeCategories = listOf(
        FinanceCategory("Salary", CategoryIcon.SALARY, CategoryType.INCOME),
        FinanceCategory("Bonus", CategoryIcon.BONUS, CategoryType.INCOME),
        FinanceCategory("Investment Profit", CategoryIcon.INVESTMENT, CategoryType.INCOME),
        FinanceCategory("Refunds", CategoryIcon.REFUND, CategoryType.INCOME),
        FinanceCategory("Item Sale", CategoryIcon.SALE, CategoryType.INCOME),
        FinanceCategory("Other Income", CategoryIcon.OTHER_INCOME, CategoryType.INCOME)
    )
}
