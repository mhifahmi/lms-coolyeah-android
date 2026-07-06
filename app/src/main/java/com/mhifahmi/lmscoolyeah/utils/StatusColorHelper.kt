package com.mhifahmi.lmscoolyeah.utils

import android.content.Context
import android.graphics.Color
import androidx.core.content.ContextCompat
import com.mhifahmi.lmscoolyeah.R

object StatusColorHelper {

    fun getStatusColor(status: String): Int {

        return when (status.uppercase()) {
            "APPROVED" ->
                Color.parseColor("#2E7D32")
            "REJECTED" ->
                Color.parseColor("#D32F2F")
            "PENDING" ->
                Color.parseColor("#F9A825")
            "CANCELLED" ->
                Color.parseColor("#757575")
            else ->
                Color.BLACK
        }
    }

    fun getStatusColor(context: Context, status: String): Int {
        return when (status.uppercase()) {
            "APPROVED" ->
                ContextCompat.getColor(
                    context,
                    R.color.statusApproved
                )

            "REJECTED" ->
                ContextCompat.getColor(
                    context,
                    R.color.statusRejected
                )

            "PENDING" ->
                ContextCompat.getColor(
                    context,
                    R.color.statusPending
                )

            "CANCELLED" ->
                ContextCompat.getColor(
                    context,
                    R.color.statusCancelled
                )

            else ->
                ContextCompat.getColor(
                    context,
                    android.R.color.black
                )
        }
    }

}