package ru.itis.f1app.core.common.token

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth_prefs")

@Singleton
class TokenStorage @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")

    val accessTokenFlow: Flow<String?> = context.dataStore.data
        .map { prefs -> prefs[ACCESS_TOKEN_KEY] }

    suspend fun getAccessToken(): String? {
        return accessTokenFlow.firstOrNull()
    }

    suspend fun saveToken(token: String) {
        context.dataStore.edit { prefs ->
            prefs[ACCESS_TOKEN_KEY] = token
        }
    }

    suspend fun clearToken() {
        context.dataStore.edit { prefs ->
            prefs.remove(ACCESS_TOKEN_KEY)
        }
    }
}
