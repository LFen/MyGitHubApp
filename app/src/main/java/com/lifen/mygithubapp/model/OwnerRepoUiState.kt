package com.lifen.mygithubapp.model

data class OwnerRepoUiState(
    val repo: RepoItemState? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
