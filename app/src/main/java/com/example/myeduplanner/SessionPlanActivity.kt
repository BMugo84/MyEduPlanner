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

import androidx.lifecycle.lifecycleScope
import com.example.myeduplanner.database.AppDatabase
import com.example.myeduplanner.database.SessionPlanEntity
import com.example.myeduplanner.database.PlanRepository
import kotlinx.coroutines.launch

class SessionPlanActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySessionPlanBinding
    private lateinit var settings: AppSettings
    private lateinit var datePickerHelper: DatePickerHelper

    private lateinit var repository: PlanRepository
    private lateinit var pdfGenerator: PdfGenerator  // Make sure this exists
    private var editingPlanId: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySessionPlanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        settings = AppSettings(this)
        datePickerHelper = DatePickerHelper(this)
        pdfGenerator = PdfGenerator(this)

        // Initialize database repository
        val database = AppDatabase.getDatabase(this)
        repository = PlanRepository(database)

        // Check if editing
        editingPlanId = intent.getLongExtra("PLAN_ID", -1L).takeIf { it != -1L }

        loadDefaultValues()
        setupDatePicker()

        // Load plan if editing
        if (editingPlanId != null) {
            loadPlanForEditing(editingPlanId!!)
        }

        binding.btnGenerate.setOnClickListener {
            if (validateInputs()) {
                generateDocument()
            }
        }
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

        if (binding.etClass.text.toString().isEmpty()) {
            binding.etClass.setText(settings.getClass())
        }

        if (binding.etUnitCode.text.toString().isEmpty()) {
            binding.etUnitCode.setText(settings.getUnitCode())
        }

        if (binding.etUnitOfCompetence.text.toString().isEmpty()) {
            binding.etUnitOfCompetence.setText(settings.getUnitOfCompetence())
        }
    }

    //Add function to load plan for editing:
    private fun loadPlanForEditing(planId: Long) {
        lifecycleScope.launch {
            val plan = repository.getSessionPlanById(planId)
            plan?.let {
                binding.etDate.setText(it.date)
                binding.etTime.setText(it.time)
                binding.etTrainerName.setText(it.trainerName)
                binding.etAdmissionNumber.setText(it.admissionNumber)
                binding.etInstitution.setText(it.institution)
                binding.etLevel.setText(it.level)
                binding.etClass.setText(it.className)
                binding.etUnitCode.setText(it.unitCode)
                binding.etUnitOfCompetence.setText(it.unitOfCompetence)
                binding.etSessionTitle.setText(it.sessionTitle)
                binding.etLlnRequirements.setText(it.llnRequirements)
                binding.etLearningOutcomes.setText(it.learningOutcomes)
                binding.etReferenceMaterials.setText(it.referenceMaterials)
                binding.etLearningAids.setText(it.learningAids)
                binding.etSafetyRequirements.setText(it.safetyRequirements)
                binding.etIntroduction.setText(it.introduction)
                binding.etStep1Time.setText(it.step1Time)
                binding.etStep1Trainer.setText(it.step1Trainer)
                binding.etStep1Trainee.setText(it.step1Trainee)
                binding.etStep1Assessment.setText(it.step1Assessment)
                binding.etStep2aTime.setText(it.step2aTime)
                binding.etStep2aTrainer.setText(it.step2aTrainer)
                binding.etStep2aTrainee.setText(it.step2aTrainee)
                binding.etStep2aAssessment.setText(it.step2aAssessment)
                binding.etStep2bTime.setText(it.step2bTime)
                binding.etStep2bTrainer.setText(it.step2bTrainer)
                binding.etStep2bTrainee.setText(it.step2bTrainee)
                binding.etStep2bAssessment.setText(it.step2bAssessment)
                binding.etStep2cTime.setText(it.step2cTime)
                binding.etStep2cTrainer.setText(it.step2cTrainer)
                binding.etStep2cTrainee.setText(it.step2cTrainee)
                binding.etStep2cAssessment.setText(it.step2cAssessment)
                binding.etSessionReview.setText(it.sessionReview)
                binding.etAssignment.setText(it.assignment)
                binding.etTotalTime.setText(it.totalTime)
                binding.etSessionReflection.setText(it.sessionReflection)
                binding.etSignature.setText(it.signature)

                binding.btnGenerate.text = "Update Session Plan"
            }
        }
    }

    private fun setupDatePicker() {
        // Date field - use long format for session plan
        binding.etDate.setOnClickListener {
            datePickerHelper.showDatePickerLongFormat(binding.etDate)
        }

        binding.etDate.isFocusable = false
        binding.etDate.isClickable = true
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

        Toast.makeText(this, "Generating PDF...", Toast.LENGTH_SHORT).show()

        val fileName = sessionPlan.getFileName()
        val outputFile = getPdfOutputFile(fileName)

        pdfGenerator.generatePdfFromHtml(htmlContent, outputFile, object : PdfGenerator.PdfGenerationListener {
            override fun onPdfGenerated(file: File) {
                lifecycleScope.launch {
                    try {
                        val entity = SessionPlanEntity(
                            id = editingPlanId ?: 0,
                            date = sessionPlan.date,
                            time = sessionPlan.time,
                            trainerName = sessionPlan.trainerName,
                            admissionNumber = sessionPlan.admissionNumber,
                            institution = sessionPlan.institution,
                            level = sessionPlan.level,
                            className = sessionPlan.className,
                            unitCode = sessionPlan.unitCode,
                            unitOfCompetence = sessionPlan.unitOfCompetence,
                            sessionTitle = sessionPlan.sessionTitle,
                            llnRequirements = sessionPlan.llnRequirements,
                            learningOutcomes = sessionPlan.learningOutcomes,
                            referenceMaterials = sessionPlan.referenceMaterials,
                            learningAids = sessionPlan.learningAids,
                            safetyRequirements = sessionPlan.safetyRequirements,
                            introduction = sessionPlan.introduction,
                            step1Time = sessionPlan.step1Time,
                            step1Trainer = sessionPlan.step1Trainer,
                            step1Trainee = sessionPlan.step1Trainee,
                            step1Assessment = sessionPlan.step1Assessment,
                            step2aTime = sessionPlan.step2aTime,
                            step2aTrainer = sessionPlan.step2aTrainer,
                            step2aTrainee = sessionPlan.step2aTrainee,
                            step2aAssessment = sessionPlan.step2aAssessment,
                            step2bTime = sessionPlan.step2bTime,
                            step2bTrainer = sessionPlan.step2bTrainer,
                            step2bTrainee = sessionPlan.step2bTrainee,
                            step2bAssessment = sessionPlan.step2bAssessment,
                            step2cTime = sessionPlan.step2cTime,
                            step2cTrainer = sessionPlan.step2cTrainer,
                            step2cTrainee = sessionPlan.step2cTrainee,
                            step2cAssessment = sessionPlan.step2cAssessment,
                            sessionReview = sessionPlan.sessionReview,
                            assignment = sessionPlan.assignment,
                            totalTime = sessionPlan.totalTime,
                            sessionReflection = sessionPlan.sessionReflection,
                            signature = sessionPlan.signature,
                            pdfFilePath = file.absolutePath,
                            updatedAt = System.currentTimeMillis()
                        )

                        if (editingPlanId != null) {
                            repository.updateSessionPlan(entity)
                        } else {
                            repository.insertSessionPlan(entity)
                        }

                        runOnUiThread {
                            val message = if (editingPlanId != null) {
                                "Session Plan updated successfully!"
                            } else {
                                "Session Plan saved successfully!"
                            }
                            Toast.makeText(
                                this@SessionPlanActivity,
                                "$message\nPDF saved to Downloads/MyEduPlanner",
                                Toast.LENGTH_LONG
                            ).show()
                            finish()
                        }
                    } catch (e: Exception) {
                        runOnUiThread {
                            Toast.makeText(
                                this@SessionPlanActivity,
                                "Error saving to database: ${e.message}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }

            override fun onError(error: String) {
                runOnUiThread {
                    Toast.makeText(
                        this@SessionPlanActivity,
                        "Failed to generate PDF: $error",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })
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