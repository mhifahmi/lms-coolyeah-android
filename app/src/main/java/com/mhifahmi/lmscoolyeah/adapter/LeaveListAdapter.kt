package com.mhifahmi.lmscoolyeah.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mhifahmi.lmscoolyeah.R
import com.mhifahmi.lmscoolyeah.data.remote.response.RecentApproval
import com.mhifahmi.lmscoolyeah.utils.DateFormatter
import com.mhifahmi.lmscoolyeah.utils.StatusColorHelper

class LeaveListAdapter(

    private val items: MutableList<RecentApproval>,
    private val onClick: (RecentApproval) -> Unit

) : RecyclerView.Adapter<LeaveListAdapter.ViewHolder>() {

    inner class ViewHolder(
        view: View
    ) : RecyclerView.ViewHolder(view) {

        val tvEmployeeName =
            view.findViewById<TextView>(R.id.tvEmployeeName)

        val tvRequestNumber =
            view.findViewById<TextView>(R.id.tvRequestNumber)

        val tvLeaveType =
            view.findViewById<TextView>(R.id.tvLeaveType)

        val tvDate =
            view.findViewById<TextView>(R.id.tvDate)

        val tvStatus =
            view.findViewById<TextView>(R.id.tvStatus)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.item_recent_approval,
                parent,
                false
            )

        return ViewHolder(view)

    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {

        val item = items[position]

        holder.tvEmployeeName.text =
            item.fullName

        holder.tvRequestNumber.text =
            item.requestNumber

        holder.tvLeaveType.text =
            item.leaveType

        holder.tvDate.text =
            "${DateFormatter.formatDate(item.startDate)} - ${
                DateFormatter.formatDate(item.endDate)
            }"

        holder.tvStatus.text =
            item.status

        holder.tvStatus.setTextColor(
            StatusColorHelper.getStatusColor(
                holder.itemView.context,
                item.status
            )
        )

        holder.itemView.setOnClickListener {

            onClick(item)

        }

    }

    fun submitList(
        data: List<RecentApproval>
    ) {

        items.clear()

        items.addAll(data)

        notifyDataSetChanged()

    }

}