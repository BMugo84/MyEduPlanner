package com.example.myeduplanner

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myeduplanner.databinding.ActivityLearningPlanBinding

class LearningPlanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLearningPlanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLearningPlanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up Generate button click
        binding.btnGenerate.setOnClickListener {
            if (validateInputs()) {
                collectData()
            }
        }
    }

    private fun validateInputs(): Boolean {
        // Check if required fields are filled
        if (binding.etWeekNumber.text.toString().trim().isEmpty()) {
            Toast.makeText(this, "Please enter Week Number", Toast.LENGTH_SHORT).show()
            return false
        }

        if (binding.etClass.text.toString().trim().isEmpty()) {
            Toast.makeText(this, "Please enter Class/Grade", Toast.LENGTH_SHORT).show()
            return false
        }

        if (binding.etSubject.text.toString().trim().isEmpty()) {
            Toast.makeText(this, "Please enter Subject", Toast.LENGTH_SHORT).show()
            return false
        }

        if (binding.etObjectives.text.toString().trim().isEmpty()) {
            Toast.makeText(this, "Please enter Learning Objectives", Toast.LENGTH_SHORT).show()
            return false
        }

        if (binding.etTopics.text.toString().trim().isEmpty()) {
            Toast.makeText(this, "Please enter Topics to Cover", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun collectData() {
        // Collect all the data from input fields
        val weekNumber = binding.etWeekNumber.text.toString().trim()
        val className = binding.etClass.text.toString().trim()
        val subject = binding.etSubject.text.toString().trim()
        val objectives = binding.etObjectives.text.toString().trim()
        val topics = binding.etTopics.text.toString().trim()
        val materials = binding.etMaterials.text.toString().trim()
        val assessment = binding.etAssessment.text.toString().trim()

        // For now, just show a success message
        // In Step 3, we'll generate the actual document
        Toast.makeText(
            this,
            "Data collected successfully!\nWeek: $weekNumber\nClass: $className",
            Toast.LENGTH_LONG
        ).show()
    }
}