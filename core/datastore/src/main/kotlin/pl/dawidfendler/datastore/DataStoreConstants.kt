package pl.dawidfendler.datastore

import androidx.datastore.preferences.core.booleanPreferencesKey

object DataStoreConstants {
    val ONBOARDING_DISPLAYED = booleanPreferencesKey("onboarding_displayed")
    val DISPLAY_HOME = booleanPreferencesKey("display_home")
}
