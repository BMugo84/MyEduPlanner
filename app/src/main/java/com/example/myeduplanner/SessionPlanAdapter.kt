package com.example.myeduplanner

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myeduplanner.database.SessionPlanEntity
import java.text.SimpleDateFormat
import java.util.*

class SessionPlanAdapter(
    private var plans: List<SessionPlanEntity>,
    private val onView: (SessionPlanEntity) -> Unit,
    private val onEdit: (SessionPlanEntity) -> Unit,
    private val onDelete: (SessionPlanEntity) -> Unit
) : RecyclerView.Adapter<SessionPlanAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        val tvUnitCode: TextView = view.findViewById(R.id.tvUnitCode)
        val tvWeekOrDate: TextView = view.findViewById(R.id.tvWeekOrDate)
        val tvCreatedDate: TextView = view.findViewById(R.id.tvCreatedDate)
        val btnView: Button = view.findViewById(R.id.btnView)
        val btnEdit: Button = view.findViewById(R.id.btnEdit)
        val btnDelete: Button = view.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_plan, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val plan = plans[position]

        holder.tvTitle.text = plan.sessionTitle
        holder.tvUnitCode.text = "Unit: ${plan.unitCode}"
        holder.tvWeekOrDate.text = "${plan.date} • ${plan.time}"

        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        holder.tvCreatedDate.text = "Created: ${dateFormat.format(Date(plan.createdAt))}"

        holder.btnView.setOnClickListener { onView(plan) }
        holder.btnEdit.setOnClickListener { onEdit(plan) }
        holder.btnDelete.setOnClickListener { onDelete(plan) }
    }

    override fun getItemCount() = plans.size

    fun updatePlans(newPlans: List<SessionPlanEntity>) {
        plans = newPlans
        notifyDataSetChanged()
    }
}