package com.example.verodigitalsolutionandroidtask.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.verodigitalsolutionandroidtask.domain.model.FetchType
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth_prefs")

@Singleton
class AppDataStore  @Inject constructor(
    @ApplicationContext private val context: Context
){

    private val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")
    private val LAST_FETCH_TIME_KEY = longPreferencesKey("last_fetch_time")
    private val LAST_FETCH_TYPE_KEY = stringPreferencesKey("last_fetch_type")

    suspend fun saveAccessToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN_KEY] = token
        }
        Timber.d("Access token saved: $token")
    }

    suspend fun removeAccessToken(){
        context.dataStore.edit { preferences ->
            preferences.remove(ACCESS_TOKEN_KEY)
        }
        Timber.d("Access token removed")
    }

    suspend fun saveLastFetchTime(fetchType: FetchType) {
        context.dataStore.edit { preferences ->
            preferences[LAST_FETCH_TIME_KEY] = System.currentTimeMillis()
            preferences[LAST_FETCH_TYPE_KEY] = fetchType.toString()

        }
    }

    val lastFetchTimeFlow: Flow<Long?> = context.dataStore.data.map {
        it[LAST_FETCH_TIME_KEY]
    }

    val lastFetchTypeFlow: Flow<String?> = context.dataStore.data.map {
        it[LAST_FETCH_TYPE_KEY]
    }

    val accessTokenFlow: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[ACCESS_TOKEN_KEY]
        }
}