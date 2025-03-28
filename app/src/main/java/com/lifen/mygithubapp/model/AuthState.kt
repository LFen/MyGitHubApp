package com.lifen.mygithubapp.model

data class AuthState(
    val isLoggedIn: Boolean = false,
    val accessToken: String? = null,
    val userName: String? = null,
    val avatarUrl: String? = null
)
