package com.example.dearfutureme.API

import android.content.Context
import android.content.SharedPreferences

class TokenManager(context: Context) {
     val prefs: SharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    fun saveToken(context: Context, token: String) {
        prefs.edit().putString("auth_token", token).apply()
    }

    fun getToken(): String? {
        return prefs.getString("auth_token", null)
    }

    fun clearToken() {
        prefs.edit().remove("auth_token").apply()
    }
}