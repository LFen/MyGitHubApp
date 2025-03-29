package com.lifen.mygithubapp.data

import com.lifen.mygithubapp.model.ApiError
import com.lifen.mygithubapp.model.ApiResult
import com.lifen.mygithubapp.model.GitHubRepo
import com.lifen.mygithubapp.network.RetrofitClient
import com.lifen.mygithubapp.network.api.ApiService
import com.lifen.mygithubapp.network.response.GithubRepoResponse
import retrofit2.HttpException

class GitHubRepoManager {

    private val apiService: ApiService by lazy {
        RetrofitClient.apiService
    }

    // 获取热门仓库列表
    suspend fun getPopularRepositories(token: String?): ApiResult<List<GitHubRepo>> {
        return try {
            val response = apiService.getPopularRepositories(token)
            ApiResult.Success(response)
        } catch (e: HttpException) {
            ApiResult.Error(ApiError.fromException(e))
        } catch (e: Exception) {
            ApiResult.Error(ApiError.NetworkError)
        }
    }

    // 搜索仓库
    suspend fun searchRepositories(token: String?, query: String, page: Int): ApiResult<GithubRepoResponse> {
        return try {
            val response = apiService.searchRepositories(token, query, page = page)
            ApiResult.Success(response)
        } catch (e: HttpException) {
            ApiResult.Error(ApiError.fromException(e))
        } catch (e: Exception) {
            ApiResult.Error(ApiError.NetworkError)
        }
    }

    // 获取用户仓库列表
    suspend fun getUserRepositories(token: String?): ApiResult<List<GitHubRepo>> {
        return try {
            val response = apiService.getUserRepos(token)
            ApiResult.Success(response)
        } catch (e: HttpException) {
            ApiResult.Error(ApiError.fromException(e))
        } catch (e: Exception) {
            ApiResult.Error(ApiError.NetworkError)
        }
    }

    // 获取仓库详情
    suspend fun getRepoDetails(token: String?, owner: String, repo: String): ApiResult<GitHubRepo> {
        return try {
            val response = apiService.getRepoDetail(token, owner, repo)
            ApiResult.Success(response)
        } catch (e: HttpException) {
            ApiResult.Error(ApiError.fromException(e))
        } catch (e: Exception) {
            ApiResult.Error(ApiError.NetworkError)
        }
    }
}