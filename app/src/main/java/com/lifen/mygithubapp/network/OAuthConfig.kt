package com.lifen.mygithubapp.network

object OAuthConfig {
    const val CLIENT_ID = "Ov23liGNoKR6t9w1JwFq"
    const val CLIENT_SECRET = "87c377788c90d5992f0596ff0e2b4b301b82bf4a"
    const val REDIRECT_URI = "mygithubapp://oauth-callback"
    const val AUTH_URL = "https://github.com/login/oauth/authorize"
    const val TOKEN_URL = "https://github.com/login/oauth/access_token"
    const val SCOPE = "repo,user"
}