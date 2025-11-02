package com.example.myeduplanner

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myeduplanner.databinding.ActivitySessionPlanBinding

class SessionPlanActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySessionPlanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySessionPlanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGenerate.setOnClickListener {
            if (validateInputs()) {
                collectData()
            }
        }
    }

    private fun validateInputs(): Boolean {
        if (binding.etDate.text.toString().trim().isEmpty()) {
            Toast.makeText(this, "Please enter Date", Toast.LENGTH_SHORT).show()
            return false
        }

        if (binding.etTime.text.toString().trim().isEmpty()) {
            Toast.makeText(this, "Please enter Time/Duration", Toast.LENGTH_SHORT).show()
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

        if (binding.etLessonTitle.text.toString().trim().isEmpty()) {
            Toast.makeText(this, "Please enter Lesson Title", Toast.LENGTH_SHORT).show()
            return false
        }

        if (binding.etIntroduction.text.toString().trim().isEmpty()) {
            Toast.makeText(this, "Please enter Introduction Activity", Toast.LENGTH_SHORT).show()
            return false
        }

        if (binding.etMainActivities.text.toString().trim().isEmpty()) {
            Toast.makeText(this, "Please enter Main Activities", Toast.LENGTH_SHORT).show()
            return false
        }

        if (binding.etConclusion.text.toString().trim().isEmpty()) {
            Toast.makeText(this, "Please enter Conclusion", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun collectData() {
        val sessionPlan = SessionPlan(
            date = binding.etDate.text.toString().trim(),
            time = binding.etTime.text.toString().trim(),
            className = binding.etClass.text.toString().trim(),
            subject = binding.etSubject.text.toString().trim(),
            lessonTitle = binding.etLessonTitle.text.toString().trim(),
            introduction = binding.etIntroduction.text.toString().trim(),
            mainActivities = binding.etMainActivities.text.toString().trim(),
            conclusion = binding.etConclusion.text.toString().trim(),
            homework = binding.etHomework.text.toString().trim(),
            resources = binding.etResources.text.toString().trim()
        )

        // For now, just show success message
        // We'll add document generation in the next step
        Toast.makeText(
            this,
            "Session Plan data collected!\nLesson: ${sessionPlan.lessonTitle}",
            Toast.LENGTH_LONG
        ).show()
    }
}