package pl.dawidfendler.dailyeaseassistant.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import pl.dawidfendler.components.bottom_dialog.CustomBottomSheetController
import pl.dawidfendler.components.bottom_dialog.CustomBottomSheetEvent
import pl.dawidfendler.components.bottom_dialog.CustomBottomSheetModel
import pl.dawidfendler.coroutines.ObserveAsEvents

@Composable
internal fun ObserveBottomSheetEvent(updateCustomBottomModel: (CustomBottomSheetModel) -> Unit) {
    ObserveAsEvents(CustomBottomSheetController.event) { events ->
        when (events) {
            is CustomBottomSheetEvent.ErrorBottomSheet -> {
                updateCustomBottomModel(
                    CustomBottomSheetModel(
                        showBottomSheet = true,
                        isSuccess = false,
                        title = events.title,
                        description = events.description,
                        icon = pl.dawidfendler.ui.R.drawable.ic_error,
                        iconTint = Color.Red
                    )
                )
            }

            is CustomBottomSheetEvent.SuccessBottomSheet -> {
                updateCustomBottomModel(
                    CustomBottomSheetModel(
                        showBottomSheet = true,
                        isSuccess = true,
                        title = events.title,
                        description = events.description,
                        icon = pl.dawidfendler.ui.R.drawable.ic_success,
                        iconTint = Color.Green
                    )
                )
            }
        }
    }
}