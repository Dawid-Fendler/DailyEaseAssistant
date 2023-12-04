package pl.dawidfendler.datastore

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

private val Context.dataStore by preferencesDataStore(
    name = "user_preferences"
)

internal class DataStoreImpl(
    context: Context
) : DataStore {

    private val dataStore = context.dataStore

    override suspend fun <T> getPreferences(key: Preferences.Key<T>, defaultValue: T) =
        dataStore.data.catch { exception ->
            if (exception is IOException) {
                exception.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            preferences[key] ?: defaultValue
        }

    override suspend fun <T> putPreference(key: Preferences.Key<T>, value: T) {
        dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    override suspend fun clearAllPreference() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}
