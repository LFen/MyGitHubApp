package com.lifen.mygithubapp.network.response

data class AccessTokenResponse(
    val access_token: String,
    val scope: String,
    val token_type: String
)
