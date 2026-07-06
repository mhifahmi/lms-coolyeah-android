package com.mhifahmi.lmscoolyeah.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mhifahmi.lmscoolyeah.R
import com.mhifahmi.lmscoolyeah.data.remote.response.RecentLeave
import com.mhifahmi.lmscoolyeah.utils.DateFormatter
import com.mhifahmi.lmscoolyeah.utils.StatusColorHelper

class RecentLeaveAdapter(
    private val items: MutableList<RecentLeave>,
    private val onClick: (RecentLeave) -> Unit
) : RecyclerView.Adapter<RecentLeaveAdapter.ViewHolder>() {

    inner class ViewHolder(
        view: View
    ) : RecyclerView.ViewHolder(view) {

        val tvLeaveType =
            view.findViewById<TextView>(R.id.tvLeaveType)

        val tvDate =
            view.findViewById<TextView>(R.id.tvDate)

        val tvTotalDays =
            view.findViewById<TextView>(R.id.tvTotalDays)

        val tvStatus =
            view.findViewById<TextView>(R.id.tvStatus)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.item_recent_leave,
                parent,
                false
            )

        return ViewHolder(view)

    }

    override fun getItemCount() =
        items.size

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {

        val item = items[position]

        holder.tvLeaveType.text =
            item.leaveType

        holder.tvDate.text =
            "${DateFormatter.formatDate(item.startDate)} - ${
                DateFormatter.formatDate(item.endDate)
            }"

        holder.tvTotalDays.text =
            "${item.totalDays} Hari"

        holder.tvStatus.text =
            item.status

        holder.itemView.setOnClickListener {
            onClick(item)
        }

        holder.tvStatus.setTextColor(
            StatusColorHelper.getStatusColor(item.status)
        )
    }


    fun submitList(
        data: List<RecentLeave>
    ) {

        items.clear()

        items.addAll(data)

        notifyDataSetChanged()

    }

}