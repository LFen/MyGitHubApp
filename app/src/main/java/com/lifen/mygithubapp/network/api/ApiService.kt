package com.lifen.mygithubapp.network.api

import com.lifen.mygithubapp.model.GitHubRepo
import com.lifen.mygithubapp.network.response.CreateIssueResponse
import com.lifen.mygithubapp.network.response.GithubRepoResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    // 搜索仓库
    @GET("/search/repositories")
    suspend fun searchRepositories(
        @Header("Authorization") token: String?,
        @Query("q") query: String,
        @Query("sort") sort: String = "stars",
        @Query("order") order: String = "desc",
        @Query("per_page") perPage: Int = 20,
        @Query("page") page: Int = 1
    ): GithubRepoResponse

    // 获取热门仓库
    @GET("/repositories")
    suspend fun getPopularRepositories(
        @Header("Authorization") token: String?,
        @Query("since") since: Long? = null,
    ): List<GitHubRepo>

    /**
     * 获取仓库详情
     * @param owner 仓库所属用户 GitHubOwner 对象的 login 属性
     * @param repo 仓库名称 GitHubRepo 对象的 name 属性
     * @return GitHubRepo
     */
    @GET("/repos/{owner}/{repo}")
    suspend fun getRepoDetail(
        @Header("Authorization") token: String?,
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): GitHubRepo

    // 获取用户仓库
    @GET("/user/repos")
    suspend fun getUserRepos(
        @Header("Authorization") token: String?
    ): List<GitHubRepo>

    @POST("/repos/{owner}/{repo}/issues")
    @Headers(
        "Accept: application/json"
    )
    suspend fun createIssue(
        @Header("Authorization") token: String?,
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Body issue: Map<String, String>
    ): CreateIssueResponse
}