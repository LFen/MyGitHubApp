package com.lifen.mygithubapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.lifen.mygithubapp.data.GitHubRepoManager
import com.lifen.mygithubapp.model.ApiResult
import com.lifen.mygithubapp.model.AuthState
import com.lifen.mygithubapp.model.Extensions.getApiErrorMsg
import com.lifen.mygithubapp.model.Extensions.toItemState
import com.lifen.mygithubapp.model.OwnerRepoUiState
import com.lifen.mygithubapp.model.RepoUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GitHubRepoViewModel(private val repoManager: GitHubRepoManager) : ViewModel() {

    private val _uiState = MutableStateFlow(RepoUiState())
    val uiState: StateFlow<RepoUiState> = _uiState.asStateFlow()
    var page = 1

    private val _ownerRepoUiState = MutableStateFlow(OwnerRepoUiState())
    val ownerRepoUiState: StateFlow<OwnerRepoUiState> = _ownerRepoUiState.asStateFlow()

    private val _createIssueState = MutableLiveData(false)
    val createIssueState = _createIssueState as LiveData<Boolean>

    fun resetPage() {
        page = 1
        _uiState.value = _uiState.value.copy(repos = emptyList())
    }

    fun resetCreateIssueState() {
        _createIssueState.value = false
    }

    fun searchRepositories(authState: AuthState, query: String) {
        _uiState.value = _uiState.value.copy(isLoading = true)
        viewModelScope.launch {
            when (val response = repoManager.searchRepositories(getToken(authState), query, page)) {
                is ApiResult.Success -> {
                    page++
                    _uiState.value = _uiState.value.copy(
                        repos = _uiState.value.repos + response.data.items.map { it.toItemState() },
                        isLoading = false,
                        error = null
                    )
                }

                is ApiResult.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = response.error.getApiErrorMsg()
                    )
                }
            }
        }
    }

    fun getUserRepositories(authState: AuthState) {
        _uiState.value = _uiState.value.copy(isLoading = true)
        viewModelScope.launch {
            when (val response = repoManager.getUserRepositories(getToken(authState))) {
                is ApiResult.Success -> {
                    _uiState.value = _uiState.value.copy(
                        repos = response.data.map { it.toItemState() },
                        isLoading = false,
                        error = null
                    )
                }

                is ApiResult.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = response.error.getApiErrorMsg()
                    )
                }
            }
        }
    }

    fun getRepoDetails(authState: AuthState, owner: String, repo: String) {
        _ownerRepoUiState.value = _ownerRepoUiState.value.copy(isLoading = true)
        viewModelScope.launch {
            when (val response = repoManager.getRepoDetails(getToken(authState), owner, repo)) {
                is ApiResult.Success -> {
                    _ownerRepoUiState.value = _ownerRepoUiState.value.copy(
                        repo = response.data.toItemState(),
                        isLoading = false,
                        error = null
                    )
                }

                is ApiResult.Error -> {
                    _ownerRepoUiState.value = _ownerRepoUiState.value.copy(
                        isLoading = false,
                        error = response.error.getApiErrorMsg()
                    )
                }
            }
        }
    }

    fun createIssue(authState: AuthState, owner: String, repo: String, title: String) {
        viewModelScope.launch {
            val response = repoManager.createIssue(getToken(authState), owner, repo, title)
            when (response) {
                is ApiResult.Success -> {
                    _createIssueState.value = true
                }
                is ApiResult.Error -> {

                }
            }
        }
    }

    private fun getToken(authState: AuthState): String? =
        if (authState.isLoggedIn) "Bearer ${authState.accessToken}" else null
}

class GitHubRepoViewModelFactory(
    private val repository: GitHubRepoManager
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GitHubRepoViewModel(repository) as T
    }
}