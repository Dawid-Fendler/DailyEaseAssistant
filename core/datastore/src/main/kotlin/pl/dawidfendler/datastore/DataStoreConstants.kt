package pl.dawidfendler.datastore

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object DataStoreConstants {
    val ONBOARDING_DISPLAYED = booleanPreferencesKey("onboarding_displayed")
    val DISPLAY_HOME = booleanPreferencesKey("display_home")
    val SELECTED_CURRENCY = stringPreferencesKey("selected_currency")
}
