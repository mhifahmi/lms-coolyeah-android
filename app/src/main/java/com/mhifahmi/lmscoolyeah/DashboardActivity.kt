package com.mhifahmi.lmscoolyeah

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val userRole = intent.getStringExtra("USER_ROLE") ?: "KARYAWAN"
        val userName = intent.getStringExtra("USER_NAME") ?: "User"

        val tvWelcome = findViewById<TextView>(R.id.tvWelcome)
        val tvRoleBadge = findViewById<TextView>(R.id.tvRoleBadge)
        val btnLogout = findViewById<MaterialButton>(R.id.btnLogout)

        val layoutKaryawan = findViewById<LinearLayout>(R.id.layoutKaryawan)
        val layoutAdmin = findViewById<LinearLayout>(R.id.layoutAdmin)

        val btnAjukanCuti = findViewById<MaterialButton>(R.id.btnAjukanCuti)

        tvWelcome.text = "Halo, $userName!"
        tvRoleBadge.text = "Role: $userRole"

        btnLogout.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            // clear history for back button
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        btnAjukanCuti.setOnClickListener {
            val intent = Intent(this, FormCutiActivity::class.java)
            startActivity(intent)
        }

        if (userRole == "ADMIN") {
            layoutKaryawan.visibility = View.GONE
            layoutAdmin.visibility = View.VISIBLE
        } else {
            layoutKaryawan.visibility = View.VISIBLE
            layoutAdmin.visibility = View.GONE
        }
    }
}