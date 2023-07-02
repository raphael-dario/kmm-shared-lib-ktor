package com.labstyle.kmmsharedlibktor

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform