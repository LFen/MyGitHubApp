package com.lifen.mygithubapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lifen.mygithubapp.auth.GitHubAuthManager
import com.lifen.mygithubapp.model.AuthState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.lifecycle.ViewModelProvider

class GitHubAuthViewModel(private val gitHubAuthManager: GitHubAuthManager) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState as StateFlow<LoginUiState>

    // 处理 GitHub 的 OAuth 回调
    fun handleOauthCallback(code: String) {
        viewModelScope.launch {
            try {
                // 发起网络请求获取访问令牌
                gitHubAuthManager.fetchAccessToken(code)
                _uiState.value = _uiState.value.copy(isLoading = false, authState = AuthState(true))
            } catch (e: Exception) {
                _uiState.value =
                    _uiState.value.copy(isLoading = false, error = "授权失败: ${e.message}")
            }
        }
    }

    // 退出登录
    fun logout() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                gitHubAuthManager.logout()
            }
            _uiState.value = _uiState.value.copy(isLoading = false, authState = AuthState(false))
        }
    }
}

data class LoginUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val authState: AuthState? = null
)

class GitHubAuthViewModelFactory(
    private val gitHubAuthManager: GitHubAuthManager
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GitHubAuthViewModel(gitHubAuthManager) as T
    }
}