package com.lifen.mygithubapp.network.response

import com.lifen.mygithubapp.model.GitHubRepo

data class GithubRepoResponse(
    val items: List<GitHubRepo>
)
