package com.lifen.mygithubapp.auth

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.lifen.mygithubapp.model.AuthState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

object AuthDataManager {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("auth")
    private val TOKEN_KEY = stringPreferencesKey("token")

    suspend fun saveAuthToken(
        context: Context,
        token: String
    ) {
        context.dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
        }
    }

    suspend fun clearAuthData(context: Context) {
        context.dataStore.edit { it.clear() }
    }

    fun getAuthStatus(context: Context): Flow<AuthState> =
        context.dataStore.data.map { preferences ->
            AuthState(
                isLoggedIn = preferences[TOKEN_KEY] != null,
                accessToken = preferences[TOKEN_KEY],
            )
        }.onStart { emit(AuthState(isLoggedIn = false)) }
}