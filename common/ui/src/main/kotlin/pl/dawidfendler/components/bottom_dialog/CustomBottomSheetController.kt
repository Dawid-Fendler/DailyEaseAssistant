package pl.dawidfendler.components.bottom_dialog

import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

object CustomBottomSheetController {

    private val _event = Channel<CustomBottomSheetEvent>()
    val event = _event.receiveAsFlow()

    suspend fun sendEvent(event: CustomBottomSheetEvent) {
        _event.send(event)
    }
}

sealed interface CustomBottomSheetEvent {
    data class SuccessBottomSheet(val title: String, val description: String) :
        CustomBottomSheetEvent

    data class ErrorBottomSheet(val title: String, val description: String) : CustomBottomSheetEvent
}

data class CustomBottomSheetModel(
    val showBottomSheet: Boolean = false,
    val isSuccess: Boolean = false,
    val title: String = "",
    val description: String = "",
    val icon: Int = 0,
    val iconTint: Color = Color.Black
)
