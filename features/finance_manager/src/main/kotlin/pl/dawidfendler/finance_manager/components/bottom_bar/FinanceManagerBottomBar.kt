package pl.dawidfendler.finance_manager.components.bottom_bar

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import pl.dawidfendler.components.text_field.CustomText
import pl.dawidfendler.ui.theme.MD_THEME_LIGHT_PRIMARY
import pl.dawidfendler.ui.theme.dp_80
import pl.dawidfendler.ui.theme.sp_0
import pl.dawidfendler.ui.theme.sp_8

@Composable
fun FinanceManagerBottomBar(
    modifier: Modifier = Modifier,
    items: List<BottomNavItem>,
    currentSelectedItem: BottomNavItem?,
    onItemSelected: (BottomNavItem) -> Unit
) {
    NavigationBar(
        modifier = modifier,
        containerColor = Color.White,
        tonalElevation = dp_80
    ) {
        items.forEach { item ->
            val isSelected = item.destination == currentSelectedItem?.destination

            NavigationBarItem(
                selected = isSelected,
                onClick = { onItemSelected(item) },
                label = {
                    CustomText(
                        text = item.label,
                        letterSpacing = sp_0,
                        fontSize = sp_8,
                        color = if (isSelected) {
                            MD_THEME_LIGHT_PRIMARY
                        } else {
                            Color.Black
                        },
                    )
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = null
                    )
                },
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    unselectedIconColor = Color.Black,
                    indicatorColor = MD_THEME_LIGHT_PRIMARY
                )
            )
        }
    }
}
