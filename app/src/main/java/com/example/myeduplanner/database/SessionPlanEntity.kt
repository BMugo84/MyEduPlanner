package com.example.myeduplanner.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "session_plans")
data class SessionPlanEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    // Basic Information
    val date: String,
    val time: String,
    val trainerName: String,
    val admissionNumber: String,
    val institution: String,
    val level: String,
    val className: String,
    val unitCode: String,
    val unitOfCompetence: String,
    val sessionTitle: String,
    val llnRequirements: String,

    // Learning Outcomes & Resources
    val learningOutcomes: String,
    val referenceMaterials: String,
    val learningAids: String,
    val safetyRequirements: String,

    // Introduction
    val introduction: String,

    // Step 1
    val step1Time: String,
    val step1Trainer: String,
    val step1Trainee: String,
    val step1Assessment: String,

    // Step 2a
    val step2aTime: String,
    val step2aTrainer: String,
    val step2aTrainee: String,
    val step2aAssessment: String,

    // Step 2b
    val step2bTime: String,
    val step2bTrainer: String,
    val step2bTrainee: String,
    val step2bAssessment: String,

    // Step 2c
    val step2cTime: String,
    val step2cTrainer: String,
    val step2cTrainee: String,
    val step2cAssessment: String,

    // Review & Assignment
    val sessionReview: String,
    val assignment: String,
    val totalTime: String,
    val sessionReflection: String,
    val signature: String,

    // Metadata
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
    val pdfFilePath: String = ""
)