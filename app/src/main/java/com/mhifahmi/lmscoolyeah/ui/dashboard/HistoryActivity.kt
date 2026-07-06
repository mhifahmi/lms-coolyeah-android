package com.mhifahmi.lmscoolyeah.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mhifahmi.lmscoolyeah.R
import com.mhifahmi.lmscoolyeah.adapter.LeaveListAdapter
import com.mhifahmi.lmscoolyeah.data.repository.LeaveRepository
import com.mhifahmi.lmscoolyeah.ui.leave.LeaveDetailActivity
import kotlinx.coroutines.launch

class HistoryActivity
    : AppCompatActivity() {

    private lateinit var repository: LeaveRepository

    private lateinit var adapter: LeaveListAdapter

    private lateinit var rvLeave: RecyclerView

    private lateinit var tvEmpty: TextView

    private lateinit var role: String

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {

        super.onCreate(savedInstanceState)

        setContentView(
            R.layout.activity_history_list
        )

        repository =
            LeaveRepository(this)

        rvLeave =
            findViewById(R.id.rvApproval)

        tvEmpty =
            findViewById(R.id.tvEmpty)

        adapter =
            LeaveListAdapter(
                mutableListOf()
            ){ leave ->

                openDetail(leave.id)

            }

        rvLeave.layoutManager =
            LinearLayoutManager(this)

        rvLeave.adapter =
            adapter

        role =
            intent.getStringExtra("ROLE")
                ?: "EMPLOYEE"

        loadData()

    }

    private fun loadData() {

        lifecycleScope.launch {

            val result =

                if (role == "ADMIN") {

                    repository.getLeaveList(

                        listOf(
                            "APPROVED",
                            "REJECTED",
                            "CANCELLED"
                        )

                    )

                } else {

                    repository.getLeaveList(null)

                }

            result
                .onSuccess {

                    if (it.isEmpty()) {

                        tvEmpty.visibility =
                            View.VISIBLE

                        rvLeave.visibility =
                            View.GONE

                    } else {

                        tvEmpty.visibility =
                            View.GONE

                        rvLeave.visibility =
                            View.VISIBLE

                        adapter.submitList(it)

                    }

                }
                .onFailure {

                    Toast.makeText(

                        this@HistoryActivity,

                        it.message,

                        Toast.LENGTH_LONG

                    ).show()

                }

        }

    }

    private val detailLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){ result ->

            if(result.resultCode == RESULT_OK){

                loadData()

                setResult(RESULT_OK)

            }

        }

    private fun openDetail(
        leaveId: Long
    ){

        detailLauncher.launch(

            Intent(

                this,

                LeaveDetailActivity::class.java

            ).apply {

                putExtra(
                    "LEAVE_ID",
                    leaveId
                )

                putExtra(
                    "ROLE",
                    role
                )

            }

        )

    }
}