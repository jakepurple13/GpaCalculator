package com.programmersbox.gpacalculator

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import org.jetbrains.skiko.wasm.onWasmReady

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    onWasmReady {
        val title = "Compose Counting Grid on plain JS"
        @OptIn(ExperimentalComposeUiApi::class)
        CanvasBasedWindow(title, canvasElementId = "ComposeTarget") {
            App()
        }
    }
}