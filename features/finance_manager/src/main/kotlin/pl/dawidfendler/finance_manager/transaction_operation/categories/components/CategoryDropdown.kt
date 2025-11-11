package pl.dawidfendler.finance_manager.transaction_operation.categories.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import pl.dawidfendler.ui.theme.SEED
import pl.dawidfendler.ui.theme.dp_1
import pl.dawidfendler.ui.theme.dp_12
import pl.dawidfendler.ui.theme.dp_20
import pl.dawidfendler.ui.theme.dp_24
import pl.dawidfendler.ui.theme.dp_8
import pl.finance_manager.transaction_operation.categories.model.CategoryUiModel

@Composable
fun CategoryDropdown(
    selected: CategoryUiModel,
    onCategorySelected: (CategoryUiModel) -> Unit,
    modifier: Modifier = Modifier,
    categories: List<CategoryUiModel> = emptyList()
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        OutlinedButton(
            onClick = { expanded = true },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(dp_12))
        ) {
            Icon(
                selected.iconId,
                contentDescription = null,
                tint = selected.colorId,
                modifier = Modifier.size(dp_20)
            )
            Spacer(modifier = Modifier.width(dp_8))
            Text(text = selected.name, color = selected.colorId)
            Spacer(Modifier.weight(1f))
            Icon(Icons.Default.ArrowDropDown, contentDescription = null)
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            border = BorderStroke(
                width = dp_1,
                color = SEED
            ),
            shape = RoundedCornerShape(dp_24)
        ) {
            categories.forEach { category ->
                DropdownMenuItem(
                    text = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = category.iconId,
                                contentDescription = null,
                                tint = category.colorId,
                                modifier = Modifier
                                    .size(dp_20)
                            )
                            Spacer(modifier = Modifier.width(dp_8))
                            Text(text = category.name)
                        }
                    },
                    onClick = {
                        onCategorySelected(category)
                        expanded = false
                    }
                )
            }
        }
    }
}