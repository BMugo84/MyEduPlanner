package com.example.myeduplanner.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "learning_plans")
data class LearningPlanEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    // Basic Information
    val unitOfCompetence: String,
    val unitCode: String,
    val trainerName: String,
    val admissionNumber: String,
    val institution: String,
    val level: String,
    val dateOfPreparation: String,
    val dateOfRevision: String,
    val numberOfTrainees: String,
    val classCode: String,

    // Skills and Criteria
    val skillOrJobTask: String,
    val benchmarkCriteria: String,

    // Weekly Session Details
    val week: String,
    val sessionNo: String,
    val sessionTitle: String,
    val learningOutcome: String,
    val trainerActivities: String,
    val traineeActivities: String,
    val traineeAssignment: String,
    val resourcesRefs: String,
    val trainingAids: String,

    // Learning Checks
    val knowledgeChecks: String,
    val skillsChecks: String,
    val attitudesChecks: String,
    val reflectionsDate: String,

    // Metadata
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
    val pdfFilePath: String = ""
)