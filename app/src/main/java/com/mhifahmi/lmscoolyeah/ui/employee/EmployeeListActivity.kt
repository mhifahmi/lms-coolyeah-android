package com.mhifahmi.lmscoolyeah.ui.employee

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
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mhifahmi.lmscoolyeah.R
import com.mhifahmi.lmscoolyeah.adapter.EmployeeAdapter
import com.mhifahmi.lmscoolyeah.data.remote.response.EmployeeItem
import com.mhifahmi.lmscoolyeah.data.repository.EmployeeRepository
import kotlinx.coroutines.launch

class EmployeeListActivity : AppCompatActivity() {

    private lateinit var rvEmployee: RecyclerView
    private lateinit var fabAdd: FloatingActionButton

    private lateinit var adapter: EmployeeAdapter

    private lateinit var repository: EmployeeRepository

    private lateinit var tvEmpty: TextView

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {

        super.onCreate(savedInstanceState)

        repository = EmployeeRepository(this)
        tvEmpty = findViewById(R.id.tvEmpty)

        setContentView(
            R.layout.activity_employee_list
        )

        initView()

        setupRecyclerView()

        setupClickListener()

        findViewById<MaterialToolbar>(R.id.toolbar)
            .setNavigationOnClickListener {

                finish()

            }

        setupRecyclerView()

        setupClickListener()

        loadData()
    }

    private fun initView() {

        rvEmployee =
            findViewById(R.id.rvEmployee)

        fabAdd =
            findViewById(R.id.fabAddEmployee)

    }

    private fun setupRecyclerView() {

        adapter =
            EmployeeAdapter(
                mutableListOf()
            ) { employee ->

                // nanti menuju Detail Employee

            }

        rvEmployee.layoutManager =
            LinearLayoutManager(this)

        rvEmployee.adapter =
            adapter

    }

    private fun setupClickListener() {

        fabAdd.setOnClickListener {

            createEmployeeLauncher.launch(

                Intent(

                    this,

                    CreateEmployeeActivity::class.java

                )

            )

        }

    }

    private val createEmployeeLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->

            if (result.resultCode == RESULT_OK) {

                loadData()

            }

        }

    private fun loadData() {

        lifecycleScope.launch {

            repository
                .getEmployees()
                .onSuccess {

                    if (it.isEmpty()) {

                        tvEmpty.visibility =
                            View.VISIBLE

                        rvEmployee.visibility =
                            View.GONE

                    } else {

                        tvEmpty.visibility =
                            View.GONE

                        rvEmployee.visibility =
                            View.VISIBLE

                        adapter.submitList(it)

                    }

                }
                .onFailure {

                    Toast.makeText(

                        this@EmployeeListActivity,

                        it.message,

                        Toast.LENGTH_LONG

                    ).show()

                }

        }

    }

}