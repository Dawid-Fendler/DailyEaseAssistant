package pl.dawidfendler.finance_manager.components.bottom_bar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ManageSearch
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Insights
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val destination: FinanceManagerBottomNavigationType,
    val icon: ImageVector,
    val label: String
)

fun buildBottomNavItems(): List<BottomNavItem> {
    return listOf(
        BottomNavItem(
            destination = FinanceManagerBottomNavigationType.Dashboard,
            icon = Icons.Filled.Dashboard,
            label = "Dashboard"
        ),
        BottomNavItem(
            destination = FinanceManagerBottomNavigationType.Transactions,
            icon = Icons.AutoMirrored.Filled.ManageSearch,
            label = "Transactions"
        ),
        BottomNavItem(
            destination = FinanceManagerBottomNavigationType.Statistics,
            icon = Icons.Filled.Insights,
            label = "Statistics"
        ),
        BottomNavItem(
            destination = FinanceManagerBottomNavigationType.AiAssistant,
            icon = Icons.Filled.SmartToy,
            label = "AI Assistant"
        ),
        BottomNavItem(
            destination = FinanceManagerBottomNavigationType.Settings,
            icon = Icons.Filled.Settings,
            label = "Settings"
        )
    )
}
