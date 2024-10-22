package com.example.dearfutureme.Activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.dearfutureme.API.RetrofitInstance
import com.example.dearfutureme.API.RetrofitInstance.tokenManager
import com.example.dearfutureme.API.TokenManager
import com.example.dearfutureme.Model.User
import com.example.dearfutureme.ViewModel.UsernameViewModel
import com.example.dearfutureme.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val usernameViewModel: UsernameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        RetrofitInstance.init(this)
        tokenManager = TokenManager(this)

        // Observe the username LiveData
        usernameViewModel.username.observe(this) { userName ->
            userName?.let {
                // Navigate to MyCapsuleList with the username
                val intent = Intent(this, MyCapsuleList::class.java).apply {
                    putExtra("USERNAME", it) // Pass the username
                }
                startActivity(intent)
            } ?: run {
                binding.tvInvalid.text = "Invalid credentials!"
                hideMessageAfterDelay()
            }
        }

        loginAcc()
        createAcc()
    }

    private fun loginAcc() {
        binding.loginBtn.setOnClickListener {
            val email = binding.etEmailAddress.text.toString()
            val password = binding.etPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                val request = User(null, email, password)
                usernameViewModel.login(email, password) // Trigger login
            } else {
                binding.tvInvalid.text = "Please fill in all fields!"
                hideMessageAfterDelay()
            }
        }
    }

    private fun createAcc() {
        binding.createAccount.setOnClickListener {
            startActivity(Intent(this@MainActivity, SignupUi::class.java))
        }
    }

    private fun hideMessageAfterDelay() {
        Handler(Looper.getMainLooper()).postDelayed({
            binding.tvInvalid.text = ""
        }, 3000)
    }
}
