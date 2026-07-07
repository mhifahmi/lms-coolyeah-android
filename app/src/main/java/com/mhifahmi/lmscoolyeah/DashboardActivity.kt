package com.mhifahmi.lmscoolyeah

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.mhifahmi.lmscoolyeah.utils.SessionManager
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mhifahmi.lmscoolyeah.adapter.LeaveListAdapter
import com.mhifahmi.lmscoolyeah.adapter.RecentLeaveAdapter
import com.mhifahmi.lmscoolyeah.data.remote.response.DashboardData
import com.mhifahmi.lmscoolyeah.data.repository.DashboardRepository
import com.mhifahmi.lmscoolyeah.ui.dashboard.HistoryActivity
import com.mhifahmi.lmscoolyeah.ui.employee.EmployeeListActivity
import com.mhifahmi.lmscoolyeah.ui.leave.ApprovalListActivity
import com.mhifahmi.lmscoolyeah.ui.leave.LeaveDetailActivity
import kotlinx.coroutines.launch

class DashboardActivity : AppCompatActivity() {

    private lateinit var dashboardRepository: DashboardRepository
    private lateinit var sessionManager: SessionManager

    private lateinit var recentLeaveAdapter: RecentLeaveAdapter
    private lateinit var leaveListAdapter: LeaveListAdapter

    private lateinit var tvWelcome: TextView
    private lateinit var tvRoleBadge: TextView

    private lateinit var layoutKaryawan: LinearLayout
    private lateinit var layoutAdmin: LinearLayout

    private lateinit var tvAnnualRemaining: TextView
    private lateinit var tvCarryOverRemaining: TextView

    private lateinit var tvTotalEmployee: TextView
    private lateinit var tvPendingRequest: TextView
    private lateinit var tvApprovedRequest: TextView
    private lateinit var tvRejectedRequest: TextView

    private lateinit var tvEmptyRecentLeave: TextView
    private lateinit var tvEmptyRecentApproval: TextView

    private lateinit var rvRecentLeave: RecyclerView
    private lateinit var rvRecentApproval: RecyclerView

    private lateinit var btnLogout: MaterialButton
    private lateinit var btnAjukanCuti: MaterialButton
    private lateinit var btnLihatSemuaApproval: MaterialButton
    private lateinit var btnHistory: MaterialButton
    private lateinit var btnLihatSemuaRiwayat: MaterialButton

    private lateinit var btnKelolaKaryawan: MaterialButton

    private val refreshLauncher  = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                loadDashboard()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_dashboard)
        initView()
        setupRecyclerView()
        setupClickListener()
        loadDashboard()
    }

    private fun initView() {
        sessionManager = SessionManager(this)
        dashboardRepository = DashboardRepository(this)

        tvWelcome = findViewById(R.id.tvWelcome)
        tvRoleBadge = findViewById(R.id.tvRoleBadge)

        layoutKaryawan = findViewById(R.id.layoutKaryawan)
        layoutAdmin = findViewById(R.id.layoutAdmin)

        tvAnnualRemaining =
            findViewById(R.id.tvAnnualRemaining)

        tvCarryOverRemaining =
            findViewById(R.id.tvCarryOverRemaining)

        tvTotalEmployee =
            findViewById(R.id.tvTotalEmployee)

        tvPendingRequest =
            findViewById(R.id.tvPendingRequest)

        tvApprovedRequest =
            findViewById(R.id.tvApprovedRequest)

        tvRejectedRequest =
            findViewById(R.id.tvRejectedRequest)

        tvEmptyRecentLeave =
            findViewById(R.id.tvEmptyRecentLeave)

        tvEmptyRecentApproval =
            findViewById(R.id.tvEmptyRecentApproval)

        rvRecentLeave =
            findViewById(R.id.rvRecentLeave)

        rvRecentApproval =
            findViewById(R.id.rvRecentApproval)

        btnLogout =
            findViewById(R.id.btnLogout)

        btnAjukanCuti =
            findViewById(R.id.btnAjukanCuti)

        btnLihatSemuaApproval =
            findViewById(R.id.btnLihatSemuaApproval)

        btnHistory =
            findViewById(R.id.btnHistory)

        btnLihatSemuaRiwayat =
            findViewById(R.id.btnLihatSemuaRiwayat)

        btnKelolaKaryawan = findViewById(R.id.btnKelolaKaryawan)
    }

    private fun setupRecyclerView() {

        recentLeaveAdapter =
            RecentLeaveAdapter(
                mutableListOf()
            ){ leave ->

                refreshLauncher.launch(
                    Intent(
                        this,
                        LeaveDetailActivity::class.java
                    ).apply {
                        putExtra("LEAVE_ID", leave.id)
                        putExtra("ROLE", "EMPLOYEE")
                    }
                )

            }

        rvRecentLeave.layoutManager =
            LinearLayoutManager(this)

        rvRecentLeave.adapter =
            recentLeaveAdapter

        leaveListAdapter =
            LeaveListAdapter(
                mutableListOf()
            ){ leave ->

                refreshLauncher.launch(
                    Intent(
                        this,
                        LeaveDetailActivity::class.java
                    ).apply {
                        putExtra("LEAVE_ID", leave.id)
                        putExtra("ROLE", "ADMIN")
                    }
                )

            }

        rvRecentApproval.layoutManager =
            LinearLayoutManager(this)

        rvRecentApproval.adapter =
            leaveListAdapter
    }

    private fun setupClickListener() {
        btnLogout.setOnClickListener {
            logout()
        }

        btnAjukanCuti.setOnClickListener {

            refreshLauncher.launch(

                Intent(
                    this,
                    FormCutiActivity::class.java
                )

            )

        }

        btnLihatSemuaApproval.setOnClickListener {

            refreshLauncher.launch(

                Intent(
                    this,
                    ApprovalListActivity::class.java
                ).apply {

                    putExtra(
                        "ROLE",
                        "ADMIN"
                    )

                }

            )

        }


        btnHistory.setOnClickListener {

            refreshLauncher.launch(

                Intent(

                    this,

                    HistoryActivity::class.java

                ).apply {

                    putExtra(
                        "ROLE",
                        "EMPLOYEE"
                    )

                }

            )

        }

        btnLihatSemuaRiwayat
           .setOnClickListener {

                refreshLauncher.launch(

                    Intent(

                        this,

                        HistoryActivity::class.java

                    ).apply {

                        putExtra(
                            "ROLE",
                            "ADMIN"
                        )

                    }

                )

            }

        btnKelolaKaryawan.setOnClickListener {

            startActivity(

                Intent(
                    this,
                    EmployeeListActivity::class.java
                )

            )

        }
    }

    private fun loadDashboard() {
        lifecycleScope.launch {
            if (!sessionManager.isLoggedIn()) {
                logout()
                return@launch
            }

            dashboardRepository
               .getDashboard()
               .onSuccess {
                    bindDashboard(it)
                }
               .onFailure {
                    Toast.makeText(
                        this@DashboardActivity,
                        it.message,
                        Toast.LENGTH_LONG
                    ).show()
                    logout()
                }
        }
    }

    private fun bindDashboard(
        dashboard: DashboardData
    ) {
        tvWelcome.text =
            "Halo, ${dashboard.fullName}"
        tvRoleBadge.text =
            "Role : ${dashboard.role}"
        if (dashboard.role == "ADMIN") {
            bindAdminDashboard(dashboard)
        } else {
            bindEmployeeDashboard(dashboard)
        }
    }

    private fun bindEmployeeDashboard(
        dashboard: DashboardData
    ) {
        layoutAdmin.visibility =
            View.GONE
        layoutKaryawan.visibility =
            View.VISIBLE
        dashboard.leaveBalance?.let {
            tvAnnualRemaining.text =
                it.annualRemaining.toString()
            tvCarryOverRemaining.text =
                it.carryOverRemaining.toString()
        }

        bindRecentLeave(
            dashboard
        )
    }

    private fun bindAdminDashboard(
        dashboard: DashboardData
    ) {
        layoutAdmin.visibility =
            View.VISIBLE
        layoutKaryawan.visibility =
            View.GONE
        dashboard.summary?.let {
            tvTotalEmployee.text =
                it.totalEmployee.toString()
            tvPendingRequest.text =
                it.pendingRequest.toString()
            tvApprovedRequest.text =
                it.approvedRequest.toString()
            tvRejectedRequest.text =
                it.rejectedRequest.toString()
        }
        bindRecentApproval(
            dashboard
        )
    }

    private fun bindRecentLeave(
        dashboard: DashboardData
    ) {
        if (dashboard.recentLeave.isEmpty()) {
            tvEmptyRecentLeave.visibility =
                View.VISIBLE
            rvRecentLeave.visibility =
                View.GONE
            return
        }

        tvEmptyRecentLeave.visibility =
            View.GONE
        rvRecentLeave.visibility =
            View.VISIBLE
        recentLeaveAdapter.submitList(
            dashboard.recentLeave
        )
    }

    private fun bindRecentApproval(
        dashboard: DashboardData
    ) {
        if (dashboard.recentApproval.isNullOrEmpty()) {

            tvEmptyRecentApproval.visibility = View.VISIBLE
            rvRecentApproval.visibility = View.GONE
            return

        }

        tvEmptyRecentApproval.visibility =
            View.GONE
        rvRecentApproval.visibility =
            View.VISIBLE
        leaveListAdapter.submitList(
            dashboard.recentApproval
        )
    }

    private fun logout() {

        sessionManager.logout()

        val intent = Intent(
            this,
            MainActivity::class.java
        )

        intent.flags =
            Intent.FLAG_ACTIVITY_NEW_TASK or
                    Intent.FLAG_ACTIVITY_CLEAR_TASK

        startActivity(intent)

        finish()

    }
}