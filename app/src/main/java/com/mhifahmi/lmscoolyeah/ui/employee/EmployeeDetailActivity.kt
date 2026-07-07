package com.mhifahmi.lmscoolyeah.ui.employee

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mhifahmi.lmscoolyeah.R
import com.mhifahmi.lmscoolyeah.data.remote.response.EmployeeDetail
import com.mhifahmi.lmscoolyeah.data.repository.EmployeeRepository
import kotlinx.coroutines.launch

class EmployeeDetailActivity : AppCompatActivity() {

    private lateinit var toolbar: MaterialToolbar

    private lateinit var tvEmployeeCode: TextView
    private lateinit var tvUsername: TextView
    private lateinit var tvFullName: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvPhone: TextView
    private lateinit var tvBirthDate: TextView
    private lateinit var tvHireDate: TextView
    private lateinit var tvDepartment: TextView
    private lateinit var tvPosition: TextView
    private lateinit var tvStatus: TextView

    private lateinit var btnEdit: MaterialButton
    private lateinit var btnStatus: MaterialButton

    private lateinit var repository: EmployeeRepository

    private var employeeId: Long = 0

    private val editLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->

            if (result.resultCode == RESULT_OK) {

                loadDetail()

                setResult(
                    RESULT_OK
                )

            }

        }

    private lateinit var employee: EmployeeDetail

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {

        super.onCreate(savedInstanceState)

        setContentView(
            R.layout.activity_employee_detail
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

        loadDetail()

    }

    private fun loadDetail() {

        lifecycleScope.launch {

            repository
                .getEmployeeDetail(employeeId)
                .onSuccess {

                    bindEmployee(it)

                }
                .onFailure {

                    Toast.makeText(

                        this@EmployeeDetailActivity,

                        it.message,

                        Toast.LENGTH_LONG

                    ).show()

                }

        }

    }

    private fun bindEmployee(
        employee: EmployeeDetail
    ) {
        this.employee = employee

        tvEmployeeCode.text =
            employee.employeeCode

        tvUsername.text =
            employee.username

        tvFullName.text =
            employee.fullName

        tvEmail.text =
            employee.email

        tvPhone.text =
            employee.phone

        tvBirthDate.text =
            employee.birthDate ?: "-"

        tvHireDate.text =
            employee.hireDate

        tvDepartment.text =
            employee.departmentName

        tvPosition.text =
            employee.positionName

        tvStatus.text =
            employee.employmentStatus

        btnStatus.text =

            if (
                employee.employmentStatus == "ACTIVE"
            ) {

                "Nonaktifkan"

            } else {

                "Aktifkan"

            }
    }

    private fun initView() {

        toolbar = findViewById(R.id.toolbar)

        tvEmployeeCode = findViewById(R.id.tvEmployeeCode)
        tvUsername = findViewById(R.id.tvUsername)
        tvFullName = findViewById(R.id.tvFullName)
        tvEmail = findViewById(R.id.tvEmail)
        tvPhone = findViewById(R.id.tvPhone)
        tvBirthDate = findViewById(R.id.tvBirthDate)
        tvHireDate = findViewById(R.id.tvHireDate)
        tvDepartment = findViewById(R.id.tvDepartment)
        tvPosition = findViewById(R.id.tvPosition)
        tvStatus = findViewById(R.id.tvStatus)

        btnEdit = findViewById(R.id.btnEdit)
        btnStatus = findViewById(R.id.btnStatus)

    }

    private fun setupToolbar() {

        toolbar.setNavigationOnClickListener {
            finish()
        }

    }

    private fun setupClickListener() {

        btnEdit.setOnClickListener {

            editLauncher.launch(

                Intent(

                    this,

                    EditEmployeeActivity::class.java

                ).apply {

                    putExtra(

                        "EMPLOYEE_ID",

                        employeeId

                    )

                }

            )

        }

        btnStatus.setOnClickListener {

            showStatusConfirmation()

        }

    }

    private fun showStatusConfirmation() {

        val nextStatus =

            if (
                employee.employmentStatus == "ACTIVE"
            ) {

                "RESIGNED"

            } else {

                "ACTIVE"

            }

        val message =

            if (
                nextStatus == "RESIGNED"
            ) {

                "Yakin ingin menonaktifkan karyawan?"

            } else {

                "Yakin ingin mengaktifkan kembali karyawan?"

            }

        MaterialAlertDialogBuilder(this)

            .setTitle("Konfirmasi")

            .setMessage(message)

            .setNegativeButton("Batal", null)

            .setPositiveButton("Ya") { _, _ ->

                updateStatus(nextStatus)

            }

            .show()

    }

    private fun updateStatus(
        status: String
    ) {

        lifecycleScope.launch {

            repository
                .updateEmployeeStatus(
                    employeeId,
                    status
                )
                .onSuccess {

                    Toast.makeText(

                        this@EmployeeDetailActivity,

                        "Status berhasil diperbarui",

                        Toast.LENGTH_LONG

                    ).show()

                    loadDetail()

                    setResult(
                        RESULT_OK
                    )

                }
                .onFailure {

                    Toast.makeText(

                        this@EmployeeDetailActivity,

                        it.message,

                        Toast.LENGTH_LONG

                    ).show()

                }

        }

    }
}