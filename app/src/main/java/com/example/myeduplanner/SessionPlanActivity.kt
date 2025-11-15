package com.example.myeduplanner

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myeduplanner.databinding.ActivitySessionPlanBinding
import java.io.File
import java.io.FileWriter
import java.io.IOException

class SessionPlanActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySessionPlanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySessionPlanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGenerate.setOnClickListener {
            if (validateInputs()) {
                generateDocument()
            }
        }
    }

    private fun validateInputs(): Boolean {
        if (binding.etDate.text.toString().trim().isEmpty()) {
            Toast.makeText(this, "Please enter Date", Toast.LENGTH_SHORT).show()
            return false
        }

        if (binding.etTime.text.toString().trim().isEmpty()) {
            Toast.makeText(this, "Please enter Time", Toast.LENGTH_SHORT).show()
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

        if (binding.etClass.text.toString().trim().isEmpty()) {
            Toast.makeText(this, "Please enter Class", Toast.LENGTH_SHORT).show()
            return false
        }

        if (binding.etUnitCode.text.toString().trim().isEmpty()) {
            Toast.makeText(this, "Please enter Unit Code", Toast.LENGTH_SHORT).show()
            return false
        }

        if (binding.etUnitOfCompetence.text.toString().trim().isEmpty()) {
            Toast.makeText(this, "Please enter Unit of Competence", Toast.LENGTH_SHORT).show()
            return false
        }

        if (binding.etSessionTitle.text.toString().trim().isEmpty()) {
            Toast.makeText(this, "Please enter Session Title", Toast.LENGTH_SHORT).show()
            return false
        }

        if (binding.etLearningOutcomes.text.toString().trim().isEmpty()) {
            Toast.makeText(this, "Please enter Learning Outcomes", Toast.LENGTH_SHORT).show()
            return false
        }

        if (binding.etIntroduction.text.toString().trim().isEmpty()) {
            Toast.makeText(this, "Please enter Introduction", Toast.LENGTH_SHORT).show()
            return false
        }

        // Step 1 validation
        if (binding.etStep1Time.text.toString().trim().isEmpty()) {
            Toast.makeText(this, "Please enter Step 1 Time", Toast.LENGTH_SHORT).show()
            return false
        }

        if (binding.etStep1Trainer.text.toString().trim().isEmpty()) {
            Toast.makeText(this, "Please enter Step 1 Trainer Activity", Toast.LENGTH_SHORT).show()
            return false
        }

        if (binding.etStep1Trainee.text.toString().trim().isEmpty()) {
            Toast.makeText(this, "Please enter Step 1 Trainee Activity", Toast.LENGTH_SHORT).show()
            return false
        }

        // Step 2a validation
        if (binding.etStep2aTime.text.toString().trim().isEmpty()) {
            Toast.makeText(this, "Please enter Step 2a Time", Toast.LENGTH_SHORT).show()
            return false
        }

        if (binding.etStep2aTrainer.text.toString().trim().isEmpty()) {
            Toast.makeText(this, "Please enter Step 2a Trainer Activity", Toast.LENGTH_SHORT).show()
            return false
        }

        if (binding.etStep2aTrainee.text.toString().trim().isEmpty()) {
            Toast.makeText(this, "Please enter Step 2a Trainee Activity", Toast.LENGTH_SHORT).show()
            return false
        }

        // Step 2b validation
        if (binding.etStep2bTime.text.toString().trim().isEmpty()) {
            Toast.makeText(this, "Please enter Step 2b Time", Toast.LENGTH_SHORT).show()
            return false
        }

        if (binding.etStep2bTrainer.text.toString().trim().isEmpty()) {
            Toast.makeText(this, "Please enter Step 2b Trainer Activity", Toast.LENGTH_SHORT).show()
            return false
        }

        if (binding.etStep2bTrainee.text.toString().trim().isEmpty()) {
            Toast.makeText(this, "Please enter Step 2b Trainee Activity", Toast.LENGTH_SHORT).show()
            return false
        }

        // Step 2c validation
        if (binding.etStep2cTime.text.toString().trim().isEmpty()) {
            Toast.makeText(this, "Please enter Step 2c Time", Toast.LENGTH_SHORT).show()
            return false
        }

        if (binding.etStep2cTrainer.text.toString().trim().isEmpty()) {
            Toast.makeText(this, "Please enter Step 2c Trainer Activity", Toast.LENGTH_SHORT).show()
            return false
        }

        if (binding.etStep2cTrainee.text.toString().trim().isEmpty()) {
            Toast.makeText(this, "Please enter Step 2c Trainee Activity", Toast.LENGTH_SHORT).show()
            return false
        }

        if (binding.etSessionReview.text.toString().trim().isEmpty()) {
            Toast.makeText(this, "Please enter Session Review", Toast.LENGTH_SHORT).show()
            return false
        }

        if (binding.etTotalTime.text.toString().trim().isEmpty()) {
            Toast.makeText(this, "Please enter Total Time", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun generateDocument() {
        val sessionPlan = SessionPlan(
            date = binding.etDate.text.toString().trim(),
            time = binding.etTime.text.toString().trim(),
            trainerName = binding.etTrainerName.text.toString().trim(),
            admissionNumber = binding.etAdmissionNumber.text.toString().trim(),
            institution = binding.etInstitution.text.toString().trim(),
            level = binding.etLevel.text.toString().trim(),
            className = binding.etClass.text.toString().trim(),
            unitCode = binding.etUnitCode.text.toString().trim(),
            unitOfCompetence = binding.etUnitOfCompetence.text.toString().trim(),
            sessionTitle = binding.etSessionTitle.text.toString().trim(),
            llnRequirements = binding.etLlnRequirements.text.toString().trim(),
            learningOutcomes = binding.etLearningOutcomes.text.toString().trim(),
            referenceMaterials = binding.etReferenceMaterials.text.toString().trim(),
            learningAids = binding.etLearningAids.text.toString().trim(),
            safetyRequirements = binding.etSafetyRequirements.text.toString().trim(),
            introduction = binding.etIntroduction.text.toString().trim(),
            step1Time = binding.etStep1Time.text.toString().trim(),
            step1Trainer = binding.etStep1Trainer.text.toString().trim(),
            step1Trainee = binding.etStep1Trainee.text.toString().trim(),
            step1Assessment = binding.etStep1Assessment.text.toString().trim(),
            step2aTime = binding.etStep2aTime.text.toString().trim(),
            step2aTrainer = binding.etStep2aTrainer.text.toString().trim(),
            step2aTrainee = binding.etStep2aTrainee.text.toString().trim(),
            step2aAssessment = binding.etStep2aAssessment.text.toString().trim(),
            step2bTime = binding.etStep2bTime.text.toString().trim(),
            step2bTrainer = binding.etStep2bTrainer.text.toString().trim(),
            step2bTrainee = binding.etStep2bTrainee.text.toString().trim(),
            step2bAssessment = binding.etStep2bAssessment.text.toString().trim(),
            step2cTime = binding.etStep2cTime.text.toString().trim(),
            step2cTrainer = binding.etStep2cTrainer.text.toString().trim(),
            step2cTrainee = binding.etStep2cTrainee.text.toString().trim(),
            step2cAssessment = binding.etStep2cAssessment.text.toString().trim(),
            sessionReview = binding.etSessionReview.text.toString().trim(),
            assignment = binding.etAssignment.text.toString().trim(),
            totalTime = binding.etTotalTime.text.toString().trim(),
            sessionReflection = binding.etSessionReflection.text.toString().trim(),
            signature = binding.etSignature.text.toString().trim()
        )

        val htmlTemplate = loadHtmlTemplate()

        if (htmlTemplate.isEmpty()) {
            Toast.makeText(this, "Error: Template not found", Toast.LENGTH_SHORT).show()
            return
        }

        val htmlContent = sessionPlan.toHtmlDocument(htmlTemplate)
        val success = saveHtmlToFile(htmlContent, sessionPlan.getFileName())

        if (success) {
            Toast.makeText(
                this,
                "Session Plan saved successfully!\nCheck Downloads/MyEduPlanner folder",
                Toast.LENGTH_LONG
            ).show()
            clearForm()
        } else {
            Toast.makeText(this, "Failed to save document", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadHtmlTemplate(): String {
        return try {
            assets.open("session_plan_template.html").bufferedReader().use { it.readText() }
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
        binding.etDate.text.clear()
        binding.etTime.text.clear()
        binding.etTrainerName.text.clear()
        binding.etAdmissionNumber.text.clear()
        binding.etInstitution.text.clear()
        binding.etLevel.text.clear()
        binding.etClass.text.clear()
        binding.etUnitCode.text.clear()
        binding.etUnitOfCompetence.text.clear()
        binding.etSessionTitle.text.clear()
        binding.etLlnRequirements.text.clear()
        binding.etLearningOutcomes.text.clear()
        binding.etReferenceMaterials.text.clear()
        binding.etLearningAids.text.clear()
        binding.etSafetyRequirements.text.clear()
        binding.etIntroduction.text.clear()
        binding.etStep1Time.text.clear()
        binding.etStep1Trainer.text.clear()
        binding.etStep1Trainee.text.clear()
        binding.etStep1Assessment.text.clear()
        binding.etStep2aTime.text.clear()
        binding.etStep2aTrainer.text.clear()
        binding.etStep2aTrainee.text.clear()
        binding.etStep2aAssessment.text.clear()
        binding.etStep2bTime.text.clear()
        binding.etStep2bTrainer.text.clear()
        binding.etStep2bTrainee.text.clear()
        binding.etStep2bAssessment.text.clear()
        binding.etStep2cTime.text.clear()
        binding.etStep2cTrainer.text.clear()
        binding.etStep2cTrainee.text.clear()
        binding.etStep2cAssessment.text.clear()
        binding.etSessionReview.text.clear()
        binding.etAssignment.text.clear()
        binding.etTotalTime.text.clear()
        binding.etSessionReflection.text.clear()
        binding.etSignature.text.clear()

        binding.etDate.requestFocus()
    }
}