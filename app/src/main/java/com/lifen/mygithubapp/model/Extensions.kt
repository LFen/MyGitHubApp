package com.lifen.mygithubapp.model

import com.lifen.mygithubapp.model.ApiError.HttpError
import com.lifen.mygithubapp.model.ApiError.NetworkError
import com.lifen.mygithubapp.model.ApiError.UnknownError

object Extensions {

    fun GitHubRepo.toItemState(): RepoItemState {
        return RepoItemState(
            id = id,
            name = name,
            description = description,
            stars = stars,
            language = language,
            url = url,
            owner = owner
        )
    }

    fun ApiError.getApiErrorMsg() = when (this) {
        is HttpError -> "HTTP Error: ${this.code}, ${this.msg}"
        is NetworkError -> "Network Error"
        is UnknownError -> "Unknown Error"
    }
}