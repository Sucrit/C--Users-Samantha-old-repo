package com.example.dearfutureme.ViewModel

import com.example.dearfutureme.API.RetrofitInstance
import com.example.dearfutureme.Model.LoginResponse
import com.example.dearfutureme.Model.User
import retrofit2.Call
import retrofit2.Callback
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Response

class UsernameViewModel : ViewModel() {
    private val _username = MutableLiveData<String>()
    val username: LiveData<String> get() = _username

    fun login(email: String, password: String) {
        // Perform the login process (this could be a network call)
        val request = User(null, email, password)

        RetrofitInstance.instance.loginUser(request).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    // Extract the username
                    _username.value = response.body()?.user?.name // Set the username
                } else {
                    // Handle login failure
                    _username.value = null // or set an error message
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                // Handle error
                _username.value = null // or set an error message
            }
        })
    }
}
