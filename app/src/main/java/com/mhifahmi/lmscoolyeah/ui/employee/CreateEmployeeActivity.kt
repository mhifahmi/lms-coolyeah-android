package com.mhifahmi.lmscoolyeah.ui.employee

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.mhifahmi.lmscoolyeah.R

class CreateEmployeeActivity : AppCompatActivity() {

    private lateinit var toolbar: MaterialToolbar

    private lateinit var etUsername: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var etFullName: TextInputEditText
    private lateinit var etEmail: TextInputEditText
    private lateinit var etPhone: TextInputEditText

    private lateinit var etBirthDate: TextInputEditText
    private lateinit var etHireDate: TextInputEditText

    private lateinit var actDepartment: MaterialAutoCompleteTextView
    private lateinit var actPosition: MaterialAutoCompleteTextView

    private lateinit var btnSave: MaterialButton

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {

        super.onCreate(savedInstanceState)

        setContentView(
            R.layout.activity_create_employee
        )

        initView()

        setupToolbar()

        setupClickListener()

    }

    private fun initView() {

        toolbar = findViewById(R.id.toolbar)

        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)

        etFullName = findViewById(R.id.etFullName)
        etEmail = findViewById(R.id.etEmail)
        etPhone = findViewById(R.id.etPhone)

        etBirthDate = findViewById(R.id.etBirthDate)
        etHireDate = findViewById(R.id.etHireDate)

        actDepartment =
            findViewById(R.id.actDepartment)

        actPosition =
            findViewById(R.id.actPosition)

        btnSave =
            findViewById(R.id.btnSave)

    }

    private fun setupToolbar() {

        toolbar.setNavigationOnClickListener {

            finish()

        }

    }

    private fun setupClickListener() {

        etBirthDate.setOnClickListener {

            // DatePicker nanti

        }

        etHireDate.setOnClickListener {

            // DatePicker nanti

        }

        btnSave.setOnClickListener {

            saveEmployee()

        }

    }

    private fun saveEmployee() {

        // POST nanti

    }

}