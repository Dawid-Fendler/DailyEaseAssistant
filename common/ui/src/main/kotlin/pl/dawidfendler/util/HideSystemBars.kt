package pl.dawidfendler.util

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

@Composable
fun HideSystemBars() {
    val view = LocalView.current
    val window = (view.context as? Activity)?.window

    LaunchedEffect(Unit) {
        window?.let {
            WindowCompat.setDecorFitsSystemWindows(it, false)
            val controller = WindowInsetsControllerCompat(it, view)
            controller.hide(WindowInsetsCompat.Type.statusBars())
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }
}

@Composable
fun ShowSystemBars() {
    val view = LocalView.current
    val window = (view.context as? Activity)?.window

    LaunchedEffect(Unit) {
        window?.let {
            WindowCompat.setDecorFitsSystemWindows(it, false)
            val controller = WindowInsetsControllerCompat(it, view)
            controller.show(WindowInsetsCompat.Type.statusBars())
        }
    }
}
