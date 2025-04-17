package com.example.pharmacyapp.presentation.viewmodel

import com.example.pharmacyapp.utils.DataStorePreference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class DataStorePreferenceViewModelTest {

    @get:Rule
    var mockitoRule = MockitoJUnit.rule()

    private lateinit var viewModel: DataStorePreferenceViewModel
    private val dataStorePreference: DataStorePreference = mock()

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(testDispatcher)  // Set the main dispatcher to the test dispatcher
        viewModel = DataStorePreferenceViewModel(dataStorePreference)
    }

    @Test
    fun `loadLanguage should update _language when dataStorePreference returns value`() = runTest {
        // Given
        val language = "en"
        whenever(dataStorePreference.getLanguage).thenReturn(language)

        // When
        viewModel.loadLanguage()
        advanceUntilIdle()

        // Then
        assert(viewModel.language.value == language)
        verify(dataStorePreference, times(1)).getLanguage
    }

    @Test
    fun `loadLanguage should set default language to en when null is returned from dataStorePreference`() =
        runTest {
            // Given
            whenever(dataStorePreference.getLanguage).thenReturn(null)

            // When
            viewModel.loadLanguage()
            advanceUntilIdle()

            // Then
            assert(viewModel.language.value == "en")
            verify(dataStorePreference, times(1)).getLanguage
        }

    @Test
    fun `setLanguage should save the new language and update _language`() = runTest {
        // Given
        val languageCode = "hi"
        whenever(dataStorePreference.saveLanguage(languageCode)).thenReturn(Unit)

        // When
        viewModel.setLanguage(languageCode)
        advanceUntilIdle()

        // Then
        assertEquals(languageCode, viewModel.language.value)
        verify(dataStorePreference, times(1)).saveLanguage(languageCode)
    }

    @Test
    fun `loadTheme should update _theme when dataStorePreference returns value`() = runTest {
        // Given
        val theme = true
        whenever(dataStorePreference.getTheme).thenReturn(flowOf(theme))

        // When
        viewModel.loadTheme()

        advanceUntilIdle()

        // Then
        assert(viewModel.theme.value == theme)
    }

    @Test
    fun `setTheme should save the new theme and update _theme`() = runTest {
        // Given
        val newTheme = true
        whenever(dataStorePreference.saveTheme(newTheme)).thenReturn(Unit)

        // When
        viewModel.setTheme(newTheme)
        advanceUntilIdle()

        // Then
        assert(viewModel.theme.value == newTheme)
        verify(dataStorePreference, times(1)).saveTheme(newTheme)
    }
}
