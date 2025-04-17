package com.example.pharmacyapp.presentation.screens.appointments

import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.pharmacyapp.R
import com.example.pharmacyapp.data.model.Appointments
import com.example.pharmacyapp.presentation.navigation.PharmacyNavScreens
import com.example.pharmacyapp.presentation.theme.LightBlue
import com.example.pharmacyapp.presentation.viewmodel.AppointmentsViewModel
import com.example.pharmacyapp.presentation.viewmodel.PharmacyViewModel
import com.example.pharmacyapp.utils.DropDownMenuEventHandler
import com.example.pharmacyapp.utils.reminder.scheduleAlarm
import com.example.pharmacyapp.utils.timeFormatter
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BookAppointmentsScreen(navController: NavController) {

    // viewmodel
    val appointmentsViewModel: AppointmentsViewModel = hiltViewModel()
    val pharmacyViewModel: PharmacyViewModel = hiltViewModel()

    val doctorsData by pharmacyViewModel.doctorsData.collectAsState()
    val medicalReasons by pharmacyViewModel.medicalReasons.collectAsState()

    val selectedDoctor = remember { mutableStateOf<String?>(null) }
    val selectedMedicalReason = remember { mutableStateOf<String?>(null) }

    val mContext = LocalContext.current

    val today = LocalDate.now()
    val currentMonth = remember { mutableStateOf(YearMonth.now()) }
    val selectedDate = remember { mutableStateOf(today) }

    // function to generate days for the current month
    fun generateDaysForMonthWithOffset(month: YearMonth): List<LocalDate?> {
        val firstDayOfMonth = LocalDate.of(month.year, month.month, 1)
        val startDayOfWeek = firstDayOfMonth.dayOfWeek.value % 7
        val totalDaysInMonth = month.lengthOfMonth()

        val daysWithOffset = MutableList<LocalDate?>(startDayOfWeek) { null }
        daysWithOffset.addAll((1..totalDaysInMonth).map {
            LocalDate.of(month.year, month.month, it)
        })

        return daysWithOffset
    }

    val daysInMonth = generateDaysForMonthWithOffset(currentMonth.value)

    val openTimeDialog = remember { mutableStateOf(false) }
    val currentTime = Calendar.getInstance()
    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true
    )
    val selectedTime = remember { mutableStateOf("") }

    val listOfWeeks = listOf("SU", "MO", "TU", "WE", "TH", "FR", "SA")

    val snackBarHostState = remember { SnackbarHostState() }
    val showSnackBar = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        pharmacyViewModel.fetchDoctors("")
        pharmacyViewModel.fetchMedicalReasons()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            elevation = CardDefaults.elevatedCardElevation(4.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                val showMonth =
                    currentMonth.value.month.getDisplayName(TextStyle.FULL, Locale.getDefault())
                CalendarHeader(
                    headerText = showMonth,
                    onPreviousMonthClick = {
                        currentMonth.value = currentMonth.value.minusMonths(1)
                    },
                    onNextMonthClick = {
                        currentMonth.value = currentMonth.value.plusMonths(1)
                    },
                    isPreviousDisabled = currentMonth.value == YearMonth.from(today)
                )

                Spacer(modifier = Modifier.height(8.dp))

                CalendarContent(
                    days = daysInMonth,
                    selectedDate = selectedDate,
                    today = today,
                    listOfWeeks = listOfWeeks
                )
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = CardDefaults.elevatedCardElevation(4.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            TextField(
                readOnly = true,
                value = if (selectedTime.value != "")
                    selectedTime.value
                else
                    stringResource(R.string.select_appointment_time),
                onValueChange = {
                    // Blank because of read only
                },
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_alarm),
                        contentDescription = null,
                        modifier = Modifier
                            .size(50.dp)
                            .clickable { openTimeDialog.value = true },
                        tint = Color.Unspecified,
                    )
                },
                textStyle = androidx.compose.ui.text.TextStyle(
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                ),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }

        // Open time picker dialog
        if (openTimeDialog.value) {
            DatePickerDialog(
                onDismissRequest = {
                    openTimeDialog.value = false
                },
                confirmButton = {
                    TextButton(onClick = {
                        openTimeDialog.value = false
                        val calendar = Calendar.getInstance()
                        calendar.set(Calendar.HOUR_OF_DAY, timePickerState.hour)
                        calendar.set(Calendar.MINUTE, timePickerState.minute)

                        selectedTime.value = timeFormatter(calendar.time.time)
                    }) {
                        Text(text = stringResource(R.string.okay))
                    }
                }) {
                TimePicker(
                    state = timePickerState,
                    modifier = Modifier.padding(16.dp),
                )
            }
        }

        DropDownMenuEventHandler(
            selectedValue = selectedMedicalReason.value ?: stringResource(R.string.select_a_reason),
            options = medicalReasons.map {
                it.reason
            },
            onValueChangedEvent = {
                selectedMedicalReason.value = it
            },
            getOptionLabel = {
                it
            }
        )

        DropDownMenuEventHandler(
            selectedValue = selectedDoctor.value ?: stringResource(R.string.select_a_doctor),
            options = doctorsData.map {
                "${it.firstName} ${it.lastName}"
            },
            onValueChangedEvent = {
                selectedDoctor.value = it
            },
            getOptionLabel = {
                it
            }
        )

        ElevatedButton(
            onClick = {

                // Formatting selectedDate using DateTimeFormatter
                val dateFormatter = DateTimeFormatter.ofPattern("dd MMM, yyyy")
                val formattedDate = selectedDate.value.format(dateFormatter)

                if (formattedDate.isNotEmpty() && selectedTime.value.isNotEmpty() && selectedMedicalReason.value != null && selectedDoctor.value != null) {
                    appointmentsViewModel.addAppointments(
                        Appointments(
                            date = formattedDate,
                            time = if (selectedTime.value != "")
                                selectedTime.value
                            else
                                mContext.getString(R.string._08_00),
                            reason = if (selectedMedicalReason.value != null)
                                selectedMedicalReason.value.toString()
                            else mContext.getString(R.string.select_a_reason),
                            doctor = if (selectedDoctor.value != null)
                                selectedDoctor.value.toString()
                            else mContext.getString(R.string.select_a_doctor)
                        )
                    ) { insertedId ->
                        scheduleExactAlarmWithPermission(
                            mContext = mContext,
                            formattedDate = formattedDate,
                            selectedTime = selectedTime.value,
                            insertedId = insertedId.toInt(),
                            selectedDoctor = selectedDoctor.value.toString()
                        )
                    }
                    navController.navigate(PharmacyNavScreens.AppointmentsDetailsScreen.route)
                } else {
                    showSnackBar.value = true
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(20.dp),
            colors = ButtonDefaults.buttonColors(containerColor = LightBlue)
        ) {
            Text(
                text = stringResource(R.string.save),
                fontSize = 18.sp
            )
        }

        // SnackbarHost for displaying the Snackbar
        SnackbarHost(
            hostState = snackBarHostState,
            modifier = Modifier.padding(bottom = 50.dp)
        )

        if (showSnackBar.value) {
            LaunchedEffect(snackBarHostState) {
                snackBarHostState.showSnackbar(mContext.getString(R.string.please_select_or_enter_all_the_field))
                showSnackBar.value = false
            }
        }
    }
}

fun scheduleExactAlarmWithPermission(
    mContext: Context,
    formattedDate: String,
    selectedTime: String,
    insertedId: Int,
    selectedDoctor: String
) {
    // Reminder Notification
    val date = formattedDate.split(" ").map { it.trim(',') }
    val monthMap = mapOf(
        "Jan" to "01",
        "Feb" to "02",
        "Mar" to "03",
        "Apr" to "04",
        "May" to "05",
        "Jun" to "06",
        "Jul" to "07",
        "Aug" to "08",
        "Sep" to "09",
        "Oct" to "10",
        "Nov" to "11",
        "Dec" to "12"
    )
    val time = selectedTime.split(":")
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val alarmManager = mContext.getSystemService(AlarmManager::class.java)
        if (!alarmManager.canScheduleExactAlarms()) {
            val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
            mContext.startActivity(intent)
        } else {
            scheduleAlarm(
                context = mContext,
                id = insertedId,
                title = "Appointment with $selectedDoctor",
                year = date[2].toInt(),
                month = monthMap[date[1]]?.toInt() ?: 3,
                day = date[0].toInt(),
                hour = time[0].toInt(),
                minute = time[1].toInt()
            )
        }
    } else {
        scheduleAlarm(
            context = mContext,
            id = insertedId,
            title = "Appointment with $selectedDoctor",
            year = date[2].toInt(),
            month = monthMap[date[1]]?.toInt() ?: 3,
            day = date[0].toInt(),
            hour = time[0].toInt(),
            minute = time[1].toInt()
        )
    }
}
