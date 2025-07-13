package pl.dawidfendler.dailyeaseassistant.util

import androidx.compose.runtime.Composable
import pl.dawidfendler.coroutines.ObserveAsEvents
import pl.dawidfendler.util.controller.MainTopBarVisibilityController
import pl.dawidfendler.util.controller.MainTopBarVisibilityEvent

@Composable
internal fun ObserveMainTopBarVisibility(updateMainTopBarVisibility: (Boolean) -> Unit) {
    ObserveAsEvents(MainTopBarVisibilityController.mainTopBarVisibilityEvent) { events ->
        when (events) {
            is MainTopBarVisibilityEvent.ShowMainTopBar ->
                updateMainTopBarVisibility(true)


            is MainTopBarVisibilityEvent.HideMainTopBar ->
                updateMainTopBarVisibility(false)
        }
    }
}