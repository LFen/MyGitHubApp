package com.lifen.mygithubapp.model

import com.google.gson.annotations.SerializedName

data class GitHubOwner(
    val login: String,
    @SerializedName("avatar_url") val avatarUrl: String
)
