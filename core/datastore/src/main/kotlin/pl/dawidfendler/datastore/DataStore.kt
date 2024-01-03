package pl.dawidfendler.datastore

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

interface DataStore {

    suspend fun <T> getPreferences(key: Preferences.Key<T>, defaultValue: T): Flow<T>
    suspend fun <T> putPreference(key: Preferences.Key<T>, value: T)
    suspend fun clearAllPreference()
}
