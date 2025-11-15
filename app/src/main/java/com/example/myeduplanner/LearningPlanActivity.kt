package com.example.myeduplanner

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myeduplanner.databinding.ActivityLearningPlanBinding
import java.io.File
import java.io.FileWriter
import java.io.IOException



class LearningPlanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLearningPlanBinding
    private lateinit var settings: AppSettings
    private lateinit var datePickerHelper: DatePickerHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLearningPlanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        settings = AppSettings(this)
        datePickerHelper = DatePickerHelper(this)  // Add this line

        loadDefaultValues()

        // Setup date pickers
        setupDatePickers()  // Add this line

        binding.btnGenerate.setOnClickListener {
            if (validateInputs()) {
                generateDocument()
            }
        }
    }

    private fun validateInputs(): Boolean {
        // Check required fields
        if (binding.etUnitOfCompetence.text.toString().trim().isEmpty()) {
            Toast.makeText(this, "Please enter Unit of Competence", Toast.LENGTH_SHORT).show()
            return false
        }

        if (binding.etUnitCode.text.toString().trim().isEmpty()) {
            Toast.makeText(this, "Please enter Unit Code", Toast.LENGTH_SHORT).show()
            return false
        }

        if (binding.etTrainerName.text.toString().trim().isEmpty()) {
            Toast.makeText(this, "Please enter Trainer Name", Toast.LENGTH_SHORT).show()
            return false
        }

        if (binding.etInstitution.text.toString().trim().isEmpty()) {
            Toast.makeText(this, "Please enter Institution", Toast.LENGTH_SHORT).show()
            return false
        }

        if (binding.etLevel.text.toString().trim().isEmpty()) {
            Toast.makeText(this, "Please enter Level", Toast.LENGTH_SHORT).show()
            return false
        }

        if (binding.etDateOfPreparation.text.toString().trim().isEmpty()) {
            Toast.makeText(this, "Please enter Date of Preparation", Toast.LENGTH_SHORT).show()
            return false
        }

        if (binding.etNumberOfTrainees.text.toString().trim().isEmpty()) {
            Toast.makeText(this, "Please enter Number of Trainees", Toast.LENGTH_SHORT).show()
            return false
        }

        if (binding.etClassCode.text.toString().trim().isEmpty()) {
            Toast.makeText(this, "Please enter Class", Toast.LENGTH_SHORT).show()
            return false
        }

        if (binding.etSkillOrJobTask.text.toString().trim().isEmpty()) {
            Toast.makeText(this, "Please enter Skill or Job Task", Toast.LENGTH_SHORT).show()
            return false
        }

        if (binding.etBenchmarkCriteria.text.toString().trim().isEmpty()) {
            Toast.makeText(this, "Please enter Benchmark Criteria", Toast.LENGTH_SHORT).show()
            return false
        }

        if (binding.etWeek.text.toString().trim().isEmpty()) {
            Toast.makeText(this, "Please enter Week", Toast.LENGTH_SHORT).show()
            return false
        }

        if (binding.etSessionNo.text.toString().trim().isEmpty()) {
            Toast.makeText(this, "Please enter Session Number", Toast.LENGTH_SHORT).show()
            return false
        }

        if (binding.etSessionTitle.text.toString().trim().isEmpty()) {
            Toast.makeText(this, "Please enter Session Title", Toast.LENGTH_SHORT).show()
            return false
        }

        if (binding.etLearningOutcome.text.toString().trim().isEmpty()) {
            Toast.makeText(this, "Please enter Learning Outcome", Toast.LENGTH_SHORT).show()
            return false
        }

        if (binding.etTrainerActivities.text.toString().trim().isEmpty()) {
            Toast.makeText(this, "Please enter Trainer Activities", Toast.LENGTH_SHORT).show()
            return false
        }

        if (binding.etTraineeActivities.text.toString().trim().isEmpty()) {
            Toast.makeText(this, "Please enter Trainee Activities", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun loadDefaultValues() {
        // Only pre-fill if the field is currently empty
        if (binding.etTrainerName.text.toString().isEmpty()) {
            binding.etTrainerName.setText(settings.getTrainerName())
        }

        if (binding.etAdmissionNumber.text.toString().isEmpty()) {
            binding.etAdmissionNumber.setText(settings.getAdmissionNumber())
        }

        if (binding.etInstitution.text.toString().isEmpty()) {
            binding.etInstitution.setText(settings.getInstitution())
        }

        if (binding.etLevel.text.toString().isEmpty()) {
            binding.etLevel.setText(settings.getLevel())
        }

        if (binding.etClassCode.text.toString().isEmpty()) {
            binding.etClassCode.setText(settings.getClass())
        }

        if (binding.etUnitCode.text.toString().isEmpty()) {
            binding.etUnitCode.setText(settings.getUnitCode())
        }

        if (binding.etUnitOfCompetence.text.toString().isEmpty()) {
            binding.etUnitOfCompetence.setText(settings.getUnitOfCompetence())
        }

        if (binding.etNumberOfTrainees.text.toString().isEmpty()) {
            binding.etNumberOfTrainees.setText(settings.getNumberOfTrainees())
        }
    }

    private fun setupDatePickers() {
        // Date of Preparation - click to open calendar
        binding.etDateOfPreparation.setOnClickListener {
            datePickerHelper.showDatePicker(binding.etDateOfPreparation)
        }

        // Disable keyboard on focus (optional - forces use of date picker)
        binding.etDateOfPreparation.isFocusable = false
        binding.etDateOfPreparation.isClickable = true

        // Date of Revision - click to open calendar
        binding.etDateOfRevision.setOnClickListener {
            datePickerHelper.showDatePicker(binding.etDateOfRevision)
        }

        binding.etDateOfRevision.isFocusable = false
        binding.etDateOfRevision.isClickable = true
    }

    private fun generateDocument() {
        // Collect all data
        val learningPlan = LearningPlan(
            unitOfCompetence = binding.etUnitOfCompetence.text.toString().trim(),
            unitCode = binding.etUnitCode.text.toString().trim(),
            trainerName = binding.etTrainerName.text.toString().trim(),
            admissionNumber = binding.etAdmissionNumber.text.toString().trim(),
            institution = binding.etInstitution.text.toString().trim(),
            level = binding.etLevel.text.toString().trim(),
            dateOfPreparation = binding.etDateOfPreparation.text.toString().trim(),
            dateOfRevision = binding.etDateOfRevision.text.toString().trim(),
            numberOfTrainees = binding.etNumberOfTrainees.text.toString().trim(),
            classCode = binding.etClassCode.text.toString().trim(),
            skillOrJobTask = binding.etSkillOrJobTask.text.toString().trim(),
            benchmarkCriteria = binding.etBenchmarkCriteria.text.toString().trim(),
            week = binding.etWeek.text.toString().trim(),
            sessionNo = binding.etSessionNo.text.toString().trim(),
            sessionTitle = binding.etSessionTitle.text.toString().trim(),
            learningOutcome = binding.etLearningOutcome.text.toString().trim(),
            trainerActivities = binding.etTrainerActivities.text.toString().trim(),
            traineeActivities = binding.etTraineeActivities.text.toString().trim(),
            traineeAssignment = binding.etTraineeAssignment.text.toString().trim(),
            resourcesRefs = binding.etResourcesRefs.text.toString().trim(),
            trainingAids = binding.etTrainingAids.text.toString().trim(),
            knowledgeChecks = binding.etKnowledgeChecks.text.toString().trim(),
            skillsChecks = binding.etSkillsChecks.text.toString().trim(),
            attitudesChecks = binding.etAttitudesChecks.text.toString().trim(),
            reflectionsDate = binding.etReflectionsDate.text.toString().trim()
        )

        // Load HTML template
        val htmlTemplate = loadHtmlTemplate()

        if (htmlTemplate.isEmpty()) {
            Toast.makeText(this, "Error: Template not found", Toast.LENGTH_SHORT).show()
            return
        }

        // Generate HTML document
        val htmlContent = learningPlan.toHtmlDocument(htmlTemplate)

        // Save to file
        val success = saveHtmlToFile(htmlContent, learningPlan.getFileName())

        if (success) {
            Toast.makeText(
                this,
                "Learning Plan saved successfully!\nCheck Downloads/MyEduPlanner folder",
                Toast.LENGTH_LONG
            ).show()
            clearForm()
        } else {
            Toast.makeText(this, "Failed to save document", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadHtmlTemplate(): String {
        return try {
            assets.open("learning_plan_template.html").bufferedReader().use { it.readText() }
        } catch (e: IOException) {
            e.printStackTrace()
            ""
        }
    }

    private fun saveHtmlToFile(htmlContent: String, fileName: String): Boolean {
        return try {
            val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val appFolder = File(downloadsDir, "MyEduPlanner")

            if (!appFolder.exists()) {
                appFolder.mkdirs()
            }

            val file = File(appFolder, fileName)
            FileWriter(file).use { writer ->
                writer.write(htmlContent)
            }

            // Notify media scanner
            val intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
            intent.data = android.net.Uri.fromFile(file)
            sendBroadcast(intent)

            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    private fun clearForm() {
        binding.etUnitOfCompetence.text.clear()
        binding.etUnitCode.text.clear()
        binding.etTrainerName.text.clear()
        binding.etAdmissionNumber.text.clear()
        binding.etInstitution.text.clear()
        binding.etLevel.text.clear()
        binding.etDateOfPreparation.text.clear()
        binding.etDateOfRevision.text.clear()
        binding.etNumberOfTrainees.text.clear()
        binding.etClassCode.text.clear()
        binding.etSkillOrJobTask.text.clear()
        binding.etBenchmarkCriteria.text.clear()
        binding.etWeek.text.clear()
        binding.etSessionNo.text.clear()
        binding.etSessionTitle.text.clear()
        binding.etLearningOutcome.text.clear()
        binding.etTrainerActivities.text.clear()
        binding.etTraineeActivities.text.clear()
        binding.etTraineeAssignment.text.clear()
        binding.etResourcesRefs.text.clear()
        binding.etTrainingAids.text.clear()
        binding.etKnowledgeChecks.text.clear()
        binding.etSkillsChecks.text.clear()
        binding.etAttitudesChecks.text.clear()
        binding.etReflectionsDate.text.clear()

        binding.etUnitOfCompetence.requestFocus()
    }
}