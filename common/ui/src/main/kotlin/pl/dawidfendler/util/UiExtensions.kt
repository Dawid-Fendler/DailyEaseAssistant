package pl.dawidfendler.util

import android.os.Build
import android.view.Window
import android.view.WindowInsets

fun setStatusBarColor(window: Window, color: Int) {
    window.decorView.setOnApplyWindowInsetsListener { view, insets ->
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM) {
            val statusBarHeight = insets.getInsets(WindowInsets.Type.statusBars()).top
            view.setPadding(0, statusBarHeight, 0, 0)
            view.setBackgroundColor(color)
        } else {
            @Suppress("DEPRECATION")
            window.statusBarColor = color
        }
        insets
    }
}
