package pl.finance_managerV2.transaction_operation.categories.mapper

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ShowChart
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.CreditScore
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.Subscriptions
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import pl.dawidfendler.domain.model.categories.CategoryIcon
import pl.dawidfendler.domain.model.categories.FinanceCategory
import pl.finance_managerV2.transaction_operation.categories.model.CategoryUiModel

fun FinanceCategory.toCategoryUiModel() = CategoryUiModel(
    name = this.name,
    iconId = mapCategoryIconToImageVectorIcon(icon),
    colorId = mapCategoryIconToColor(icon),
    type = type,
    isUserCategory = isUserCategory
)

fun mapCategoryIconToImageVectorIcon(category: CategoryIcon): ImageVector {
    return when (category) {
        CategoryIcon.FOOD -> Icons.Default.Restaurant
        CategoryIcon.TRANSPORT -> Icons.Default.DirectionsCar
        CategoryIcon.HOME -> Icons.Default.Home
        CategoryIcon.SUBSCRIPTIONS -> Icons.Default.Subscriptions
        CategoryIcon.HEALTH -> Icons.Default.MedicalServices
        CategoryIcon.BEAUTY -> Icons.Default.Face
        CategoryIcon.ENTERTAINMENT -> Icons.Default.SportsEsports
        CategoryIcon.SHOPPING -> Icons.Default.ShoppingCart
        CategoryIcon.EDUCATION -> Icons.Default.School
        CategoryIcon.TRAVEL -> Icons.Default.Flight
        CategoryIcon.GIFTS -> Icons.Default.CardGiftcard
        CategoryIcon.OTHER_EXPENSE -> Icons.Default.MoreHoriz
        CategoryIcon.SALARY -> Icons.Default.AttachMoney
        CategoryIcon.BONUS -> Icons.Default.EmojiEvents
        CategoryIcon.INVESTMENT -> Icons.AutoMirrored.Filled.ShowChart
        CategoryIcon.REFUND -> Icons.Default.CreditScore
        CategoryIcon.SALE -> Icons.Default.Money
        CategoryIcon.OTHER_INCOME -> Icons.Default.MoreHoriz
    }
}

fun mapCategoryIconToColor(category: CategoryIcon): Color {
    return when (category) {
        CategoryIcon.FOOD -> Color(0xFFFF7043)
        CategoryIcon.TRANSPORT -> Color(0xFF42A5F5)
        CategoryIcon.HOME -> Color(0xFFFFB74D)
        CategoryIcon.SUBSCRIPTIONS -> Color(0xFF66BB6A)
        CategoryIcon.HEALTH -> Color(0xFF9575CD)
        CategoryIcon.BEAUTY -> Color(0xFFFFC107)
        CategoryIcon.ENTERTAINMENT -> Color(0xFF3F51B5)
        CategoryIcon.SHOPPING -> Color(0xFFFF5722)
        CategoryIcon.EDUCATION -> Color(0xFF009688)
        CategoryIcon.TRAVEL -> Color(0xFF795548)
        CategoryIcon.GIFTS -> Color(0xFF607D8B)
        CategoryIcon.OTHER_EXPENSE -> Color(0xFF9E9E9E)
        CategoryIcon.SALARY -> Color(0xFF4CAF50)
        CategoryIcon.BONUS -> Color(0xFFFFC107)
        CategoryIcon.INVESTMENT -> Color(0xFF673AB7)
        CategoryIcon.REFUND -> Color(0xFF8BC34A)
        CategoryIcon.SALE -> Color(0xFFFF9800)
        CategoryIcon.OTHER_INCOME -> Color(0xFF9E9E9E)
    }
}