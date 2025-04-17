package com.example.pharmacyapp.utils

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class DataStorePreference @Inject constructor(private val context: Context) {

    private val Context.dataStore by preferencesDataStore(name = Constants.PHARMACY_PREFS)

    private val LANGUAGE_KEY = stringPreferencesKey(Constants.LANGUAGE_KEY)

    private val THEME_KEY = booleanPreferencesKey(Constants.THEME_MODE)

    private val PDF_PAGE_INDEX_KEY = intPreferencesKey(Constants.PAGE_INDEX_KEY)

    val getPDFPageIndex: Flow<Int> =
        context.dataStore.data.map { pref ->
            Log.d("PageCount", "receive: ${pref[PDF_PAGE_INDEX_KEY]}")
            pref[PDF_PAGE_INDEX_KEY] ?: 0
        }

    suspend fun savePDFPageIndex(pageIndex: Int) {
        Log.d("PageCount", "Store: $pageIndex")
        context.dataStore.edit { pref ->
            pref[PDF_PAGE_INDEX_KEY] = pageIndex
            Log.d("PageCount", "Store: ${pref[PDF_PAGE_INDEX_KEY]}")
        }
    }

    val getLanguage: String = runBlocking {
        context.dataStore.data.map { pref ->
            pref[LANGUAGE_KEY]
        }.first() ?: "en"
    }

    suspend fun saveLanguage(languageCode: String) {
        context.dataStore.edit { pref ->
            pref[LANGUAGE_KEY] = languageCode
        }
    }

    //    val getTheme: Boolean = runBlocking {
//        context.dataStore.data.map { pref ->
//            pref[THEME_KEY]
//        }.first() ?: false
//    }
    val getTheme: Flow<Boolean> =
        context.dataStore.data.map { pref ->
            pref[THEME_KEY] ?: false
        }

    suspend fun saveTheme(theme: Boolean) {
        context.dataStore.edit { pref ->
            pref[THEME_KEY] = theme
        }
    }
}
