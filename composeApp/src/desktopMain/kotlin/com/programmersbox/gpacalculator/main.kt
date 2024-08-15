package com.programmersbox.gpacalculator

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "GpaCalculator",
    ) {
        App()
    }
}
