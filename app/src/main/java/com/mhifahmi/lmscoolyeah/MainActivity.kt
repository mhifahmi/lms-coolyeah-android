package com.mhifahmi.lmscoolyeah

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import kotlin.jvm.java
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

import com.mhifahmi.lmscoolyeah.data.repository.AuthRepository
import com.mhifahmi.lmscoolyeah.utils.SessionManager

class MainActivity : AppCompatActivity() {

    private lateinit var sessionManager: SessionManager
    private lateinit var authRepository: AuthRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sessionManager = SessionManager(this)
        authRepository = AuthRepository(this)

        if (sessionManager.isLoggedIn()) {

            startActivity(
                Intent(this, DashboardActivity::class.java)
            )

            finish()

            return

        }

        val etUsername = findViewById<TextInputEditText>(R.id.etUsername)
        val etPassword = findViewById<TextInputEditText>(R.id.etPassword)
        val btnLogin = findViewById<MaterialButton>(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (username.isEmpty()) {
                etUsername.error = "Username tidak boleh kosong"
                etUsername.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                etPassword.error = "Password tidak boleh kosong"
                etPassword.requestFocus()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                btnLogin.isEnabled = false
                btnLogin.text = "Memproses..."

                val result = authRepository.login(
                    username,
                    password
                )

                result.onSuccess { loginData ->

                    sessionManager.saveSession(
                        token = loginData.token,
                        userId = loginData.userId,
                        username = loginData.username,
                        fullName = loginData.fullName,
                        role = loginData.role
                    )

                    Toast.makeText(
                        this@MainActivity,
                        "Login berhasil",
                        Toast.LENGTH_SHORT
                    ).show()

                    btnLogin.text = "Masuk"
                    btnLogin.isEnabled = true

                    startActivity(
                        Intent(
                            this@MainActivity,
                            DashboardActivity::class.java
                        )
                    )

                    finish()

                }

                result.onFailure {

                    Toast.makeText(
                        this@MainActivity,
                        it.message,
                        Toast.LENGTH_LONG
                    ).show()

                }

                btnLogin.isEnabled = true

            }

//            if (username == "admin" && password == "admin123") {
//                val intent = Intent(this, DashboardActivity::class.java)
//                intent.putExtra("USER_ROLE", "ADMIN")
//                intent.putExtra("USER_NAME", "Administrator")
//                startActivity(intent)
//                finish()
//            } else if (username == "hilman" && password == "user123") {
//                val intent = Intent(this, DashboardActivity::class.java)
//                intent.putExtra("USER_ROLE", "KARYAWAN")
//                intent.putExtra("USER_NAME", "Hilman")
//                startActivity(intent)
//                finish()
//            } else {
//                Toast.makeText(this, "Username atau Password salah!", Toast.LENGTH_SHORT).show()
//            }
        }
    }
}