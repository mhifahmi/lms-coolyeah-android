package com.mhifahmi.lmscoolyeah.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mhifahmi.lmscoolyeah.R
import com.mhifahmi.lmscoolyeah.data.remote.response.EmployeeItem

class EmployeeAdapter(

    private val items: MutableList<EmployeeItem>,

    private val onClick: (EmployeeItem) -> Unit

) : RecyclerView.Adapter<EmployeeAdapter.ViewHolder>() {

    inner class ViewHolder(
        view: View
    ) : RecyclerView.ViewHolder(view) {

        val tvName: TextView =
            view.findViewById(R.id.tvName)

        val tvEmployeeCode: TextView =
            view.findViewById(R.id.tvEmployeeCode)

        val tvDepartment: TextView =
            view.findViewById(R.id.tvDepartment)

        val tvPosition: TextView =
            view.findViewById(R.id.tvPosition)

        val tvStatus: TextView =
            view.findViewById(R.id.tvStatus)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val view = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.item_employee,
                parent,
                false
            )

        return ViewHolder(view)

    }

    override fun getItemCount(): Int {

        return items.size

    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {

        val employee = items[position]

        holder.tvName.text =
            employee.fullName

        holder.tvEmployeeCode.text =
            employee.employeeCode

        holder.tvDepartment.text =
            employee.department

        holder.tvPosition.text =
            employee.position

        holder.tvStatus.text =
            employee.status

        holder.itemView.setOnClickListener {

            onClick(employee)

        }

    }

    fun submitList(
        data: List<EmployeeItem>
    ) {

        items.clear()

        items.addAll(data)

        notifyDataSetChanged()

    }

}