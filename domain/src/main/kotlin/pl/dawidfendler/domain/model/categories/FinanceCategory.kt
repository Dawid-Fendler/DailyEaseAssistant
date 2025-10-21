package pl.dawidfendler.domain.model.categories

data class FinanceCategory(
    val name: String,
    val icon: CategoryIcon,
    val type: CategoryType,
    val isUserCategory: Boolean = false
)
