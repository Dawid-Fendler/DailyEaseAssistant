package pl.dawidfendler.util.controller

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

object MainTopBarVisibilityController {

    private val _mainTopBarVisibilityEvent = Channel<MainTopBarVisibilityEvent>()
    val mainTopBarVisibilityEvent = _mainTopBarVisibilityEvent.receiveAsFlow()

    suspend fun sendMainTopBarEvent(event: MainTopBarVisibilityEvent) {
        _mainTopBarVisibilityEvent.send(event)
    }
}

sealed interface MainTopBarVisibilityEvent {
    data object ShowMainTopBar :
        MainTopBarVisibilityEvent

    data object HideMainTopBar : MainTopBarVisibilityEvent

}