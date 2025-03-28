package com.lifen.mygithubapp.model

data class RepoUiState(
    val repos: List<RepoItemState> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
