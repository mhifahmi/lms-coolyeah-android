package com.mhifahmi.lmscoolyeah.ui.employee

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.mhifahmi.lmscoolyeah.R
import com.mhifahmi.lmscoolyeah.data.remote.request.CreateEmployeeRequest
import com.mhifahmi.lmscoolyeah.data.remote.request.UpdateEmployeeRequest
import com.mhifahmi.lmscoolyeah.data.remote.response.DepartmentItem
import com.mhifahmi.lmscoolyeah.data.remote.response.EmployeeDetail
import com.mhifahmi.lmscoolyeah.data.remote.response.PositionItem
import com.mhifahmi.lmscoolyeah.data.repository.EmployeeRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class EditEmployeeActivity : AppCompatActivity() {

    private lateinit var repository: EmployeeRepository

    private var employeeId: Long = 0

    private lateinit var toolbar: MaterialToolbar

    private lateinit var etUsername: TextInputEditText
    private lateinit var etFullName: TextInputEditText
    private lateinit var etEmail: TextInputEditText
    private lateinit var etPhone: TextInputEditText

    private lateinit var etBirthDate: TextInputEditText
    private lateinit var etHireDate: TextInputEditText

    private lateinit var actDepartment: MaterialAutoCompleteTextView
    private lateinit var actPosition: MaterialAutoCompleteTextView

    private lateinit var btnSave: MaterialButton

    private var departments =
        emptyList<DepartmentItem>()

    private var positions =
        emptyList<PositionItem>()

    private var selectedDepartmentId: Long? = null

    private var selectedPositionId: Long? = null
    override fun onCreate(
        savedInstanceState: Bundle?
    ) {

        super.onCreate(savedInstanceState)

        setContentView(
            R.layout.activity_edit_employee
        )

        repository =
            EmployeeRepository(this)

        employeeId =
            intent.getLongExtra(
                "EMPLOYEE_ID",
                0
            )

        initView()

        setupToolbar()

        setupClickListener()

        loadDepartments()

        loadPositions()

    }

    private fun initView() {

        toolbar = findViewById(R.id.toolbar)

        etUsername = findViewById(R.id.etUsername)

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

            showDatePicker(etBirthDate)

        }

        etHireDate.setOnClickListener {

            showDatePicker(etHireDate)

        }

        actDepartment.setOnItemClickListener {

                _,
                _,
                position,
                _ ->

            selectedDepartmentId =
                departments[position].id

        }

        actPosition.setOnItemClickListener {

                _,
                _,
                position,
                _ ->

            selectedPositionId =
                positions[position].id

        }

        btnSave.setOnClickListener {

            updateEmployee()

        }

    }

    private fun updateEmployee() {

        if (!validateForm()) {
            return
        }

        val request = UpdateEmployeeRequest(

            username =
                etUsername.text.toString().trim(),

            full_name =
                etFullName.text.toString().trim(),

            email =
                etEmail.text.toString().trim(),

            phone =
                etPhone.text.toString().trim(),

            birth_date =
                etBirthDate.text
                    .toString()
                    .trim()
                    .ifBlank {
                        null
                    },

            hire_date =
                etHireDate.text
                    .toString()
                    .trim(),

            department_id =
                selectedDepartmentId!!,

            position_id =
                selectedPositionId!!

        )

        lifecycleScope.launch {

            repository
                .updateEmployee(
                    employeeId,
                    request
                )
                .onSuccess {

                    Toast.makeText(

                        this@EditEmployeeActivity,

                        "Data karyawan berhasil diperbarui",

                        Toast.LENGTH_LONG

                    ).show()

                    setResult(
                        RESULT_OK
                    )

                    finish()

                }
                .onFailure {

                    Toast.makeText(

                        this@EditEmployeeActivity,

                        it.message,

                        Toast.LENGTH_LONG

                    ).show()

                }

        }

    }

    private fun loadDepartments() {

        lifecycleScope.launch {

            repository
                .getDepartments()
                .onSuccess {

                    departments = it

                    val adapter =
                        ArrayAdapter(

                            this@EditEmployeeActivity,

                            android.R.layout.simple_dropdown_item_1line,

                            departments.map { item ->
                                item.name
                            }

                        )

                    actDepartment.setAdapter(
                        adapter
                    )

                    loadEmployee()

                }
                .onFailure {

                    Toast.makeText(

                        this@EditEmployeeActivity,

                        it.message,

                        Toast.LENGTH_LONG

                    ).show()

                }

        }

    }

    private fun loadPositions() {

        lifecycleScope.launch {

            repository
                .getPositions()
                .onSuccess {

                    positions = it

                    val adapter =
                        ArrayAdapter(

                            this@EditEmployeeActivity,

                            android.R.layout.simple_dropdown_item_1line,

                            positions.map { item ->
                                item.name
                            }

                        )

                    actPosition.setAdapter(
                        adapter
                    )

                }
                .onFailure {

                    Toast.makeText(

                        this@EditEmployeeActivity,

                        it.message,

                        Toast.LENGTH_LONG

                    ).show()

                }

        }

    }

    private fun showDatePicker(
        editText: TextInputEditText
    ) {

        val calendar =
            Calendar.getInstance()

        DatePickerDialog(

            this,

            { _,
              year,
              month,
              day ->

                calendar.set(
                    year,
                    month,
                    day
                )

                val formatter =
                    SimpleDateFormat(
                        "yyyy-MM-dd",
                        Locale.getDefault()
                    )

                editText.setText(
                    formatter.format(
                        calendar.time
                    )
                )

            },

            calendar.get(Calendar.YEAR),

            calendar.get(Calendar.MONTH),

            calendar.get(Calendar.DAY_OF_MONTH)

        ).show()

    }

    private fun loadEmployee() {

        lifecycleScope.launch {

            repository
                .getEmployeeDetail(employeeId)
                .onSuccess {

                    bindEmployee(it)

                }
                .onFailure {

                    Toast.makeText(

                        this@EditEmployeeActivity,

                        it.message,

                        Toast.LENGTH_LONG

                    ).show()

                }

        }

    }

    private fun bindEmployee(
        employee: EmployeeDetail
    ) {

        etUsername.setText(
            employee.username
        )

        etFullName.setText(
            employee.fullName
        )

        etEmail.setText(
            employee.email
        )

        etPhone.setText(
            employee.phone
        )

        etBirthDate.setText(
            employee.birthDate ?: ""
        )

        etHireDate.setText(
            employee.hireDate
        )

        //---------------------------------------
        // Department
        //---------------------------------------

        selectedDepartmentId =
            employee.departmentId

        actDepartment.setText(

            employee.departmentName,

            false

        )

        //---------------------------------------
        // Position
        //---------------------------------------

        selectedPositionId =
            employee.positionId

        actPosition.setText(

            employee.positionName,

            false

        )

    }

    private fun validateForm(): Boolean {

        if (
            etUsername.text
                .toString()
                .isBlank()
        ) {

            etUsername.error =
                "Username wajib diisi"

            return false

        }

        if (
            etFullName.text
                .toString()
                .isBlank()
        ) {

            etFullName.error =
                "Nama wajib diisi"

            return false

        }

        if (
            etEmail.text
                .toString()
                .isBlank()
        ) {

            etEmail.error =
                "Email wajib diisi"

            return false

        }

        if (
            etPhone.text
                .toString()
                .isBlank()
        ) {

            etPhone.error =
                "Nomor HP wajib diisi"

            return false

        }

        if (
            etHireDate.text
                .toString()
                .isBlank()
        ) {

            etHireDate.error =
                "Tanggal bergabung wajib diisi"

            return false

        }

        if (selectedDepartmentId == null) {

            Toast.makeText(

                this,

                "Pilih Department",

                Toast.LENGTH_LONG

            ).show()

            return false

        }

        if (selectedPositionId == null) {

            Toast.makeText(

                this,

                "Pilih Position",

                Toast.LENGTH_LONG

            ).show()

            return false

        }

        return true

    }

}