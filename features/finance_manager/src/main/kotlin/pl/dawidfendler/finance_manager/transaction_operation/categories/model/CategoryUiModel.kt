package pl.dawidfendler.finance_manager.transaction_operation.categories.model

import androidx.annotation.ColorRes
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import pl.dawidfendler.domain.model.categories.CategoryType

data class CategoryUiModel(
    val name: String,
    val iconId: ImageVector,
    @ColorRes val colorId: Color,
    val type: CategoryType,
    val isUserCategory: Boolean = false
)
