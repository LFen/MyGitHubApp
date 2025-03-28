package com.lifen.mygithubapp.model

import com.google.gson.annotations.SerializedName

data class GitHubRepo(
    val id: Long,
    val name: String,
    @SerializedName("full_name") val fullName: String,
    val description: String?,
    @SerializedName("html_url") val url: String,
    @SerializedName("stargazers_count") val stars: Int,
    val language: String?,
    @SerializedName("owner") val owner: GitHubOwner
)
