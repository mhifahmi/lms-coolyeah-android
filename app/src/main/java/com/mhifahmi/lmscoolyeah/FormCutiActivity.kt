package com.mhifahmi.lmscoolyeah

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import java.util.Calendar

class FormCutiActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_cuti)

        val actvJenisCuti = findViewById<AutoCompleteTextView>(R.id.actvJenisCuti)
        val etTanggalMulai = findViewById<TextInputEditText>(R.id.etTanggalMulai)
        val etTanggalSelesai = findViewById<TextInputEditText>(R.id.etTanggalSelesai)
        etTanggalSelesai.isEnabled = false
        val etAlasan = findViewById<TextInputEditText>(R.id.etAlasan)
        val btnKirim = findViewById<MaterialButton>(R.id.btnKirimPengajuan)

        val pilihanCuti = arrayOf("Cuti Tahunan", "Cuti Sakit", "Cuti Melahirkan", "Cuti Penting")
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, pilihanCuti)
        actvJenisCuti.setAdapter(adapter)

        var tanggalMulaiMillis: Long = 0

        etTanggalMulai.setOnClickListener {
            val calendar = Calendar.getInstance()
            val datePickerDialog = DatePickerDialog(this, { _, year, month, day ->
                val selectedCalendar = Calendar.getInstance()
                selectedCalendar.set(year, month, day)

                tanggalMulaiMillis = selectedCalendar.timeInMillis

                val formattedDate = "$day/${month + 1}/$year"
                etTanggalMulai.setText(formattedDate)

                etTanggalSelesai.isEnabled = true
                etTanggalSelesai.setText("")
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))

            datePickerDialog.datePicker.minDate = System.currentTimeMillis()
            datePickerDialog.show()
        }

        etTanggalSelesai.setOnClickListener {
            val calendar = Calendar.getInstance()
            val datePickerDialog = DatePickerDialog(this, { _, year, month, day ->
                val formattedDate = "$day/${month + 1}/$year"
                etTanggalSelesai.setText(formattedDate)
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))

            datePickerDialog.datePicker.minDate = tanggalMulaiMillis
            datePickerDialog.show()
        }

        btnKirim.setOnClickListener {
            val jenis = actvJenisCuti.text.toString()
            val mulai = etTanggalMulai.text.toString()
            val selesai = etTanggalSelesai.text.toString()
            val alasan = etAlasan.text.toString().trim()

            if (jenis.isEmpty() || mulai.isEmpty() || selesai.isEmpty() || alasan.isEmpty()) {
                Toast.makeText(this, "Harap lengkapi semua data!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Toast.makeText(this, "Pengajuan $jenis berhasil dikirim!", Toast.LENGTH_LONG).show()

            finish()
        }
    }

    private fun showDatePicker(editText: TextInputEditText) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            val formattedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
            editText.setText(formattedDate)
        }, year, month, day)

        datePickerDialog.show()
    }
}