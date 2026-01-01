package com.example.myeduplanner

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myeduplanner.databinding.ActivityHistoryBinding
import com.example.myeduplanner.database.AppDatabase
import com.example.myeduplanner.database.LearningPlanEntity
import com.example.myeduplanner.database.PlanRepository
import com.example.myeduplanner.database.SessionPlanEntity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.File

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding
    private lateinit var repository: PlanRepository

    private lateinit var learningPlanAdapter: LearningPlanAdapter
    private lateinit var sessionPlanAdapter: SessionPlanAdapter

    private var currentTab = "learning" // "learning" or "session"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val database = AppDatabase.getDatabase(this)
        repository = PlanRepository(database)

        setupRecyclerView()
        setupTabs()
        setupSearch()

        loadLearningPlans()
    }

    private fun setupRecyclerView() {
        binding.rvPlans.layoutManager = LinearLayoutManager(this)

        // Learning Plan Adapter
        learningPlanAdapter = LearningPlanAdapter(
            plans = emptyList(),
            onView = { plan -> viewLearningPlan(plan) },
            onEdit = { plan -> editLearningPlan(plan) },
            onDelete = { plan -> deleteLearningPlan(plan) }
        )

        // Session Plan Adapter
        sessionPlanAdapter = SessionPlanAdapter(
            plans = emptyList(),
            onView = { plan -> viewSessionPlan(plan) },
            onEdit = { plan -> editSessionPlan(plan) },
            onDelete = { plan -> deleteSessionPlan(plan) }
        )
    }

    private fun setupTabs() {
        binding.btnLearningPlansTab.setOnClickListener {
            currentTab = "learning"
            binding.etSearch.text.clear()
            loadLearningPlans()
        }

        binding.btnSessionPlansTab.setOnClickListener {
            currentTab = "session"
            binding.etSearch.text.clear()
            loadSessionPlans()
        }
    }

    private fun setupSearch() {
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val query = s.toString()
                if (query.isEmpty()) {
                    if (currentTab == "learning") {
                        loadLearningPlans()
                    } else {
                        loadSessionPlans()
                    }
                } else {
                    searchPlans(query)
                }
            }
        })
    }

    private fun loadLearningPlans() {
        lifecycleScope.launch {
            repository.getAllLearningPlans().collectLatest { plans ->
                if (plans.isEmpty()) {
                    showEmptyState("No Learning Plans saved yet")
                } else {
                    hideEmptyState()
                    learningPlanAdapter.updatePlans(plans)
                    binding.rvPlans.adapter = learningPlanAdapter
                }
            }
        }
    }

    private fun loadSessionPlans() {
        lifecycleScope.launch {
            repository.getAllSessionPlans().collectLatest { plans ->
                if (plans.isEmpty()) {
                    showEmptyState("No Session Plans saved yet")
                } else {
                    hideEmptyState()
                    sessionPlanAdapter.updatePlans(plans)
                    binding.rvPlans.adapter = sessionPlanAdapter
                }
            }
        }
    }

    private fun searchPlans(query: String) {
        lifecycleScope.launch {
            if (currentTab == "learning") {
                repository.searchLearningPlans(query).collectLatest { plans ->
                    if (plans.isEmpty()) {
                        showEmptyState("No results found for '$query'")
                    } else {
                        hideEmptyState()
                        learningPlanAdapter.updatePlans(plans)
                    }
                }
            } else {
                repository.searchSessionPlans(query).collectLatest { plans ->
                    if (plans.isEmpty()) {
                        showEmptyState("No results found for '$query'")
                    } else {
                        hideEmptyState()
                        sessionPlanAdapter.updatePlans(plans)
                    }
                }
            }
        }
    }

    private fun viewLearningPlan(plan: LearningPlanEntity) {
        val file = File(plan.pdfFilePath)
        if (file.exists()) {
            try {
                // Use FileProvider to get content URI
                val uri = androidx.core.content.FileProvider.getUriForFile(
                    this,
                    "${applicationContext.packageName}.fileprovider",
                    file
                )

                val intent = Intent(Intent.ACTION_VIEW)
                intent.setDataAndType(uri, "text/html")
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

                startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this, "Error opening file: ${e.message}", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(this, "File not found at: ${plan.pdfFilePath}", Toast.LENGTH_LONG).show()
        }
    }

    private fun editLearningPlan(plan: LearningPlanEntity) {
        val intent = Intent(this, LearningPlanActivity::class.java)
        intent.putExtra("PLAN_ID", plan.id)
        startActivity(intent)
    }

    private fun deleteLearningPlan(plan: LearningPlanEntity) {
        AlertDialog.Builder(this)
            .setTitle("Delete Learning Plan")
            .setMessage("Are you sure you want to delete '${plan.sessionTitle}'?")
            .setPositiveButton("Delete") { _, _ ->
                lifecycleScope.launch {
                    repository.deleteLearningPlan(plan)

                    // Delete HTML file
                    val file = File(plan.pdfFilePath)
                    if (file.exists()) {
                        file.delete()
                    }

                    Toast.makeText(this@HistoryActivity, "Plan deleted", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun viewSessionPlan(plan: SessionPlanEntity) {
        val file = File(plan.pdfFilePath)
        if (file.exists()) {
            try {
                // Use FileProvider to get content URI
                val uri = androidx.core.content.FileProvider.getUriForFile(
                    this,
                    "${applicationContext.packageName}.fileprovider",
                    file
                )

                val intent = Intent(Intent.ACTION_VIEW)
                intent.setDataAndType(uri, "text/html")
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

                startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this, "Error opening file: ${e.message}", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(this, "File not found at: ${plan.pdfFilePath}", Toast.LENGTH_LONG).show()
        }
    }

    private fun editSessionPlan(plan: SessionPlanEntity) {
        val intent = Intent(this, SessionPlanActivity::class.java)
        intent.putExtra("PLAN_ID", plan.id)
        startActivity(intent)
    }

    private fun deleteSessionPlan(plan: SessionPlanEntity) {
        AlertDialog.Builder(this)
            .setTitle("Delete Session Plan")
            .setMessage("Are you sure you want to delete '${plan.sessionTitle}'?")
            .setPositiveButton("Delete") { _, _ ->
                lifecycleScope.launch {
                    repository.deleteSessionPlan(plan)

                    // Delete HTML file
                    val file = File(plan.pdfFilePath)
                    if (file.exists()) {
                        file.delete()
                    }

                    Toast.makeText(this@HistoryActivity, "Plan deleted", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showEmptyState(message: String) {
        binding.rvPlans.visibility = View.GONE
        binding.layoutEmptyState.visibility = View.VISIBLE
        binding.tvEmptyMessage.text = message
    }

    private fun hideEmptyState() {
        binding.rvPlans.visibility = View.VISIBLE
        binding.layoutEmptyState.visibility = View.GONE
    }
}