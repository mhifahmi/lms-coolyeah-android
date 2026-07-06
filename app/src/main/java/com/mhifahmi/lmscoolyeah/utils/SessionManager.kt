package com.mhifahmi.lmscoolyeah.utils

import android.content.Context
import android.content.SharedPreferences
import com.mhifahmi.lmscoolyeah.constant.Constants

class SessionManager(
    context: Context
) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences(
            Constants.PREF_NAME,
            Context.MODE_PRIVATE
        )

    fun saveSession(
        token: String,
        userId: Long,
        username: String,
        fullName: String,
        role: String,
    ) {

        prefs.edit()
            .putString(Constants.TOKEN_KEY, token)
            .putLong(Constants.USER_ID_KEY, userId)
            .putString(Constants.USERNAME_KEY, username)
            .putString(Constants.FULL_NAME_KEY, fullName)
            .putString(Constants.ROLE_KEY, role)
            .apply()

    }

    fun getToken(): String? {
        return prefs.getString(
            Constants.TOKEN_KEY,
            null
        )
    }

    fun getUserId(): Long {
        return prefs.getLong(
            Constants.USER_ID_KEY,
            0L
        )
    }

    fun getUsername(): String? {
        return prefs.getString(
            Constants.USERNAME_KEY,
            ""
        )
    }

    fun getFullName(): String? {
        return prefs.getString(
            Constants.FULL_NAME_KEY,
            ""
        )
    }

    fun getRole(): String? {
        return prefs.getString(
            Constants.ROLE_KEY,
            ""
        )
    }

    fun isLoggedIn(): Boolean {
        return !getToken().isNullOrEmpty()
    }

    fun logout() {
        prefs.edit().clear().apply()
    }

}