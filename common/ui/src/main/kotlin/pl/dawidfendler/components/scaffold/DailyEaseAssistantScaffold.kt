package pl.dawidfendler.components.scaffold

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import pl.dawidfendler.components.bottom_dialog.CustomBottomSheet
import pl.dawidfendler.components.bottom_dialog.CustomBottomSheetModel

@Composable
fun DailyEaseAssistantScaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    bottomSheetModel: CustomBottomSheetModel? = null,
    onDismissBottomSheet: () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {

    Box(modifier = modifier.fillMaxSize()) {
        Scaffold(
            topBar = topBar,
            bottomBar = bottomBar
        ) { innerPadding ->
            content(innerPadding)
        }

        if (bottomSheetModel?.showBottomSheet == true) {
            CustomBottomSheet(
                title = bottomSheetModel.title,
                description = bottomSheetModel.description,
                onDismissAction = onDismissBottomSheet,
                icon = bottomSheetModel.icon,
                iconTint = bottomSheetModel.iconTint,
            )
        }
    }
}