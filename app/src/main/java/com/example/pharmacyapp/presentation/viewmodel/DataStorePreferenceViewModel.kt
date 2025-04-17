package com.example.pharmacyapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pharmacyapp.utils.DataStorePreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DataStorePreferenceViewModel @Inject constructor(private val dataStorePreference: DataStorePreference) :
    ViewModel() {

    private val _language = MutableStateFlow<String?>(null)
    val language: StateFlow<String?> get() = _language

    private val _theme = MutableStateFlow<Boolean>(false)
    val theme: StateFlow<Boolean> get() = _theme

    private val _pdfPageIndex = MutableStateFlow<Int>(0)
    val pdfPageIndex: StateFlow<Int> get() = _pdfPageIndex

    fun loadPDFPageCount() {
        viewModelScope.launch {
            dataStorePreference.getPDFPageIndex.collect { value ->
                _pdfPageIndex.value = value
            }
        }
    }

    fun savePDFPageCount(pageIndex: Int) {
        viewModelScope.launch {
            dataStorePreference.savePDFPageIndex(pageIndex)
            _pdfPageIndex.value = pageIndex
        }
    }

    fun loadLanguage() {
        viewModelScope.launch {
            _language.value = dataStorePreference.getLanguage ?: "en"
//            dataStorePreference.getLanguage.collect { lang ->
//                _language.value = lang ?: "en"
//            }
        }
    }

    fun setLanguage(languageCode: String) {
        viewModelScope.launch {
            dataStorePreference.saveLanguage(languageCode)
            _language.value = languageCode
        }
    }

    fun loadTheme() {
        viewModelScope.launch {
            dataStorePreference.getTheme.collect { theme ->
                _theme.value = theme ?: false
            }
        }
    }

    fun setTheme(theme: Boolean) {
        viewModelScope.launch {
            dataStorePreference.saveTheme(theme)
            _theme.value = theme
        }
    }
}
