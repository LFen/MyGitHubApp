package com.lifen.mygithubapp.network.api

import com.lifen.mygithubapp.network.OAuthConfig
import com.lifen.mygithubapp.network.response.AccessTokenResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface OauthApiService {

    // 获取访问令牌
    @POST(OAuthConfig.TOKEN_URL)
    @Headers(
        "Accept: application/json"
    )
    @FormUrlEncoded
    suspend fun getAccessToken(
        @Field("client_id") clientId: String = OAuthConfig.CLIENT_ID,
        @Field("client_secret") clientSecret: String = OAuthConfig.CLIENT_SECRET,
        @Field("code") code: String,
        @Field("redirect_uri") redirectUri: String = OAuthConfig.REDIRECT_URI
    ): AccessTokenResponse
}