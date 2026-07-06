package com.mhifahmi.lmscoolyeah.ui.leave

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.mhifahmi.lmscoolyeah.R
import com.mhifahmi.lmscoolyeah.data.remote.response.LeaveDetail
import com.mhifahmi.lmscoolyeah.data.repository.LeaveRepository
import com.mhifahmi.lmscoolyeah.utils.DateFormatter
import com.mhifahmi.lmscoolyeah.utils.StatusColorHelper
import kotlinx.coroutines.launch

class LeaveDetailActivity
    : AppCompatActivity() {

    private lateinit var role: String
    private lateinit var repository: LeaveRepository

    private var leaveId: Long = 0

    private lateinit var cardEmployee: MaterialCardView

    private lateinit var tvRequestNumber: TextView
    private lateinit var tvEmployeeName: TextView
    private lateinit var tvLeaveType: TextView
    private lateinit var tvStartDate: TextView
    private lateinit var tvEndDate: TextView
    private lateinit var tvTotalDays: TextView
    private lateinit var tvDeductionType: TextView
    private lateinit var tvStatus: TextView
    private lateinit var tvReason: TextView

    private lateinit var btnApprove: MaterialButton
    private lateinit var btnReject: MaterialButton

    private lateinit var btnCancel: MaterialButton

    private fun initView(){

        cardEmployee = findViewById(R.id.cardEmployee)

        tvRequestNumber = findViewById(R.id.tvRequestNumber)
        tvEmployeeName = findViewById(R.id.tvEmployeeName)
        tvLeaveType = findViewById(R.id.tvLeaveType)
        tvStartDate = findViewById(R.id.tvStartDate)
        tvEndDate = findViewById(R.id.tvEndDate)
        tvTotalDays = findViewById(R.id.tvTotalDays)
        tvDeductionType = findViewById(R.id.tvDeductionType)
        tvStatus = findViewById(R.id.tvStatus)
        tvReason = findViewById(R.id.tvReason)

        btnApprove = findViewById(R.id.btnApprove)
        btnReject = findViewById(R.id.btnReject)
        btnCancel = findViewById(R.id.btnCancel)

    }

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {

        super.onCreate(savedInstanceState)

        role = intent.getStringExtra("ROLE") ?: "EMPLOYEE"

        setContentView(
            R.layout.activity_leave_detail
        )
        initView()

        repository =
            LeaveRepository(this)

        leaveId =
            intent.getLongExtra(
                "LEAVE_ID",
                0
            )

        if(leaveId == 0L){

            finish()

            return

        }

        loadDetail()
        setupClickListener()
    }

    private fun loadDetail(){

        lifecycleScope.launch {

            repository
                .getLeaveDetail(
                    leaveId
                )
                .onSuccess {

                    bindDetail(it)

                }
                .onFailure {

                    Toast.makeText(
                        this@LeaveDetailActivity,
                        it.message,
                        Toast.LENGTH_LONG
                    ).show()

                    finish()

                }

        }

    }

    private fun bindDetail(
        detail: LeaveDetail
    ){

        tvRequestNumber.text = detail.requestNumber

        tvLeaveType.text = detail.leaveType

        tvStartDate.text =
            DateFormatter.formatDate(detail.startDate)

        tvEndDate.text =
            DateFormatter.formatDate(detail.endDate)

        tvTotalDays.text =
            "${detail.totalDays} Hari"

        tvDeductionType.text =
            detail.deductionType

        tvReason.text =
            detail.reason

        tvStatus.text =
            detail.status

        tvStatus.setTextColor(
            StatusColorHelper.getStatusColor(
                this,
                detail.status
            )
        )

        if(detail.employeeName.isNullOrBlank()){

            cardEmployee.visibility = View.GONE

        }else{

            cardEmployee.visibility = View.VISIBLE
            tvEmployeeName.text = detail.employeeName

        }

        bindActionButton(detail)
    }

    private fun bindActionButton(
        detail: LeaveDetail
    ) {

        btnApprove.visibility = View.GONE
        btnReject.visibility = View.GONE
        btnCancel.visibility = View.GONE

        if (
            role == "ADMIN" &&
            detail.status == "PENDING"
        ) {

            btnApprove.visibility = View.VISIBLE
            btnReject.visibility = View.VISIBLE

        }

        if (
            role == "EMPLOYEE" &&
            detail.status == "PENDING"
        ) {

            btnCancel.visibility = View.VISIBLE

        }

    }

    private fun setupClickListener() {

        btnApprove.setOnClickListener {

            approveLeave()

        }

        btnReject.setOnClickListener {

            showRejectDialog()

        }

        btnCancel.setOnClickListener {

            showCancelDialog()

        }

    }

    private fun approveLeave() {

        lifecycleScope.launch {

            repository
                .approveLeave(
                    leaveId,
                    null
                )
                .onSuccess {

                    finishSuccess(it)

                }
                .onFailure {

                    Toast.makeText(
                        this@LeaveDetailActivity,
                        it.message,
                        Toast.LENGTH_LONG
                    ).show()

                }

        }

    }

    private fun showRejectDialog() {

        val inputLayout =
            TextInputLayout(this)

        val input =
            TextInputEditText(this)

        input.hint =
            "Reason"

        inputLayout.addView(input)

        MaterialAlertDialogBuilder(this)
            .setTitle("Reject Leave")
            .setView(inputLayout)
            .setNegativeButton(
                "Cancel",
                null
            )
            .setPositiveButton(
                "Reject",
                null
            )
            .show()
            .apply {

                getButton(
                    android.app.AlertDialog.BUTTON_POSITIVE
                ).setOnClickListener {

                    val reason =
                        input.text
                            ?.toString()
                            ?.trim()
                            .orEmpty()

                    if(reason.isBlank()){

                        input.error =
                            "Reason wajib diisi"

                        return@setOnClickListener

                    }

                    rejectLeave(
                        reason
                    )

                    dismiss()

                }

            }

    }

    private fun showCancelDialog() {

        MaterialAlertDialogBuilder(this)

            .setTitle("Batalkan Pengajuan")

            .setMessage(
                "Apakah Anda yakin ingin membatalkan pengajuan cuti ini?"
            )

            .setNegativeButton(
                "Tidak",
                null
            )

            .setPositiveButton(
                "Ya"
            ) { _, _ ->

                cancelLeave()

            }

            .show()

    }

    private fun rejectLeave(
        reason: String
    ) {

        lifecycleScope.launch {

            repository
                .rejectLeave(
                    leaveId,
                    reason
                )
                .onSuccess {

                    finishSuccess(it)

                }
                .onFailure {

                    Toast.makeText(
                        this@LeaveDetailActivity,
                        it.message,
                        Toast.LENGTH_LONG
                    ).show()

                }

        }

    }

    private fun cancelLeave() {

        lifecycleScope.launch {

            repository
                .cancelLeave(
                    leaveId
                )
                .onSuccess {

                    finishSuccess(it)

                }
                .onFailure {

                    Toast.makeText(

                        this@LeaveDetailActivity,

                        it.message,

                        Toast.LENGTH_LONG

                    ).show()

                }

        }

    }

    private fun finishSuccess(
        message: String
    ) {

        Toast.makeText(
            this,
            message,
            Toast.LENGTH_LONG
        ).show()

        setResult(
            RESULT_OK
        )

        finish()

    }
}