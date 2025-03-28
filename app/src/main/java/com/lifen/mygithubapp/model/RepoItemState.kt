package com.lifen.mygithubapp.model

data class RepoItemState(
    val id: Long,
    val name: String,
    val description: String?,
    val stars: Int,
    val language: String?,
    val url: String,
    val owner: GitHubOwner
)
