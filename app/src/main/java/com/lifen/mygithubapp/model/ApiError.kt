package com.lifen.mygithubapp.model

import retrofit2.HttpException

sealed class ApiError {
    data class HttpError(val code: Int, val msg: String) : ApiError()
    object NetworkError : ApiError()
    object UnknownError : ApiError()

    companion object {
        fun fromException(e: HttpException): ApiError {
            return HttpError(e.code(), e.message())
        }
    }
}