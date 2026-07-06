package com.mhifahmi.lmscoolyeah.network

import android.content.Context
import com.mhifahmi.lmscoolyeah.utils.SessionManager
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(context: Context) : Interceptor {
    private val sessionManager = SessionManager(context)

    override fun intercept(
        chain: Interceptor.Chain
    ): Response {
        val request = chain.request()
        val token = sessionManager.getToken()
        if (token.isNullOrEmpty()) {
            return chain.proceed(request)
        }

        val authenticatedRequest =
            request.newBuilder()
                .addHeader(
                    "Authorization",
                    "Bearer $token"
                )
                .build()

        return chain.proceed(
            authenticatedRequest
        )

    }

}