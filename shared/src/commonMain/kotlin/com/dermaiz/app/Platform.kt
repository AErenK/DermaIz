package com.dermaiz.app

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform