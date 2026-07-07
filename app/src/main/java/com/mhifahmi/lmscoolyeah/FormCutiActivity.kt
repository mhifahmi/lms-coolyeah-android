package com.mhifahmi.lmscoolyeah

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.mhifahmi.lmscoolyeah.data.remote.request.CreateLeaveRequest
import com.mhifahmi.lmscoolyeah.data.remote.response.LeaveType
import com.mhifahmi.lmscoolyeah.data.repository.AuthRepository
import com.mhifahmi.lmscoolyeah.data.repository.LeaveRepository
import com.mhifahmi.lmscoolyeah.utils.SessionManager
import kotlinx.coroutines.launch
import java.util.Calendar

class FormCutiActivity : AppCompatActivity() {

    private lateinit var repository: LeaveRepository

    private lateinit var sessionManager: SessionManager

    private var leaveTypes = listOf<LeaveType>()

    private var selectedLeaveType: LeaveType? = null

    private lateinit var actvJenisCuti: AutoCompleteTextView
    private lateinit var etTanggalMulai: TextInputEditText
    private lateinit var etTanggalSelesai: TextInputEditText
    private lateinit var etAlasan: TextInputEditText
    private lateinit var btnKirim: MaterialButton

    private var tanggalMulaiMillis = 0L

    // format yyyy-MM-dd
    private var startDateApi = ""

    private var endDateApi = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_cuti)

        repository = LeaveRepository(this)

        actvJenisCuti = findViewById<AutoCompleteTextView>(R.id.actvJenisCuti)
        etTanggalMulai = findViewById<TextInputEditText>(R.id.etTanggalMulai)
        etTanggalSelesai = findViewById<TextInputEditText>(R.id.etTanggalSelesai)
        etTanggalSelesai.isEnabled = false
        etAlasan = findViewById<TextInputEditText>(R.id.etAlasan)
        btnKirim = findViewById<MaterialButton>(R.id.btnKirimPengajuan)

        sessionManager = SessionManager(this)
        actvJenisCuti.keyListener = null
        loadLeaveTypes()

        etTanggalMulai.setOnClickListener {
            val calendar = Calendar.getInstance()
            val dialog = DatePickerDialog(this, { _, year, month, day ->
                    val selected = Calendar.getInstance()
                    selected.set(year, month, day)
                    tanggalMulaiMillis = selected.timeInMillis

                    val monthValue = month + 1
                    startDateApi = String.format("%04d-%02d-%02d",
                        year,
                        monthValue,
                        day
                    )

                    val displayDate = "$day/$monthValue/$year"
                    etTanggalMulai.setText(displayDate)
                    etTanggalSelesai.isEnabled = true
                    endDateApi = startDateApi
                    etTanggalSelesai.setText(displayDate)
                },

                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )

            dialog.datePicker.minDate = System.currentTimeMillis()
            dialog.show()
        }

        etTanggalSelesai.setOnClickListener {
            val calendar = Calendar.getInstance()
            val dialog = DatePickerDialog(this, { _, year, month, day ->
                    val monthValue = month + 1
                    endDateApi = String.format("%04d-%02d-%02d",
                        year,
                        monthValue,
                        day
                    )

                    val displayDate = "$day/$monthValue/$year"
                    etTanggalSelesai.setText(displayDate)
                },

                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )

            dialog.datePicker.minDate = tanggalMulaiMillis
            dialog.show()
        }

        btnKirim.setOnClickListener {
            if (selectedLeaveType == null) {
                Toast.makeText(
                    this,
                    "Silakan pilih jenis cuti",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val alasan = etAlasan.text.toString().trim()

            if (startDateApi.isBlank() || endDateApi.isBlank() || alasan.isBlank()) {
                Toast.makeText(
                    this,
                    "Harap lengkapi semua data",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            val request = CreateLeaveRequest(

                leaveTypeId = selectedLeaveType!!.id,

                startDate = startDateApi,

                endDate = endDateApi,

                reason = alasan

            )

            lifecycleScope.launch {

                repository
                    .createLeave(request)
                    .onSuccess {

                        Toast.makeText(

                            this@FormCutiActivity,

                            "Pengajuan cuti berhasil",

                            Toast.LENGTH_LONG

                        ).show()

                        setResult(RESULT_OK)

                        finish()

                    }
                    .onFailure {

                        Toast.makeText(

                            this@FormCutiActivity,

                            it.message,

                            Toast.LENGTH_LONG

                        ).show()

                    }

            }
        }

        actvJenisCuti.setOnItemClickListener {_, _, position, _ ->
            selectedLeaveType = leaveTypes[position]
        }
    }

    private fun loadLeaveTypes() {
        btnKirim.isEnabled = false
        lifecycleScope.launch {
            repository.getLeaveTypes()
                .onSuccess {
                    leaveTypes = it
                    val adapter = ArrayAdapter(
                        this@FormCutiActivity,
                        android.R.layout.simple_dropdown_item_1line,
                        leaveTypes.map { leave ->
                            leave.name
                        }
                    )
                    actvJenisCuti.setAdapter(adapter)
                    btnKirim.isEnabled = true
                }

                .onFailure {
                    Toast.makeText(
                        this@FormCutiActivity,
                        it.message,
                        Toast.LENGTH_LONG
                    ).show()
                    btnKirim.isEnabled = true
                }
        }
    }
}