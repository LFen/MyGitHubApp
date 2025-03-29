package com.lifen.mygithubapp.auth

import android.content.Context
import com.lifen.mygithubapp.network.RetrofitClient
import com.lifen.mygithubapp.network.api.OauthApiService

class GitHubAuthManager(private val context: Context) {

    private val oauthApiService: OauthApiService by lazy {
        RetrofitClient.oauthApiService
    }

    suspend fun fetchAccessToken(code: String) {
        val response = oauthApiService.getAccessToken(code = code)
        saveAuthToken(response.access_token)
    }

    private suspend fun saveAuthToken(token: String) {
        AuthDataManager.saveAuthToken(context, token)
    }

    suspend fun logout() {
        AuthDataManager.clearAuthData(context)
    }

    val authStatus = AuthDataManager.getAuthStatus(context)
}