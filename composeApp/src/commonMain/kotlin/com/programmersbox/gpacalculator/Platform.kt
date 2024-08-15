package com.programmersbox.gpacalculator

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform