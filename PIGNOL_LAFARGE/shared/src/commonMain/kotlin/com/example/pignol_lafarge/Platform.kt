package com.example.pignol_lafarge

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform