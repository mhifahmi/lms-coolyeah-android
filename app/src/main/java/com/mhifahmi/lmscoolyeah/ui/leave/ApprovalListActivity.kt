package com.mhifahmi.lmscoolyeah.ui.leave

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
import kotlinx.coroutines.launch

class ApprovalListActivity
    : AppCompatActivity() {

    private lateinit var repository: LeaveRepository

    private lateinit var adapter: LeaveListAdapter

    private lateinit var rvApproval: RecyclerView

    private lateinit var tvEmpty: TextView

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {

        super.onCreate(savedInstanceState)

        setContentView(
            R.layout.activity_approval_list
        )

        repository =
            LeaveRepository(this)

        rvApproval =
            findViewById(R.id.rvApproval)

        tvEmpty =
            findViewById(R.id.tvEmpty)

        adapter =
            LeaveListAdapter(
                mutableListOf()
            ){ approval ->

                openDetail(approval.id)

            }

        rvApproval.layoutManager =
            LinearLayoutManager(this)

        rvApproval.adapter =
            adapter

        loadData()

    }

    private fun loadData() {

        lifecycleScope.launch {

            repository
                .getLeaveList(listOf("PENDING"))
                .onSuccess {

                    if(it.isEmpty()){

                        tvEmpty.visibility =
                            View.VISIBLE

                        rvApproval.visibility =
                            View.GONE

                    }else{

                        tvEmpty.visibility =
                            View.GONE

                        rvApproval.visibility =
                            View.VISIBLE

                        adapter.submitList(it)

                    }

                }
                .onFailure {

                    Toast.makeText(

                        this@ApprovalListActivity,

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
                    "ADMIN"
                )

            }

        )

    }
}