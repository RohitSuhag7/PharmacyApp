package com.example.pharmacyapp.presentation.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.pharmacyapp.R
import com.example.pharmacyapp.utils.Constants

sealed class PharmacyNavScreens(
    val route: String,
    @DrawableRes val icons: Int,
    @StringRes val label: Int
) {
    data object AppointmentsDetailsScreen : PharmacyNavScreens(
        Constants.APPOINTMENTS_DETAILS_SCREEN,
        R.drawable.ic_heart,
        R.string.home
    )

    data object AppointmentsScreen : PharmacyNavScreens(
        Constants.APPOINTMENTS_SCREEN,
        R.drawable.ic_add,
        R.string.appointments
    )

    data object ProfileScreen : PharmacyNavScreens(
        Constants.PROFILE_SCREEN, R.drawable.ic_person,
        R.string.profile
    )

    data object ChartScreen : PharmacyNavScreens(
        Constants.CHART_SCREEN,
        R.drawable.ic_chart,
        R.string.chart
    )

    data object MoreScreen : PharmacyNavScreens(
        Constants.MORE_SCREEN,
        R.drawable.ic_more_horiz,
        R.string.more
    )
}
