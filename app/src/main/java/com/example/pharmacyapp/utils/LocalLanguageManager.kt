package com.example.pharmacyapp.utils

import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.LocaleList

object LocalLanguageManager {

    fun updateLocalLanguage(context: Context, languageCode: String) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.getSystemService(LocaleManager::class.java).applicationLocales = LocaleList.forLanguageTags(languageCode)
        }
//        else {
//         AppCompatDelegate.setApplicationLocals(LocaleListCompat.forLanguageTags(languageCode))
//        }
    }
}
