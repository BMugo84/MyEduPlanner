package com.example.myeduplanner

data class SessionPlan(
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
    val signature: String
) {
    fun toHtmlDocument(htmlTemplate: String): String {
        return htmlTemplate
            .replace("{{logo_image_url}}", "") // We'll handle logo later
            .replace("{{date}}", date)
            .replace("{{time}}", time)
            .replace("{{trainer_name}}", trainerName)
            .replace("{{admission_number}}", admissionNumber)
            .replace("{{institution}}", institution)
            .replace("{{level}}", level)
            .replace("{{class}}", className)
            .replace("{{unit_code}}", unitCode)
            .replace("{{unit_of_competence}}", unitOfCompetence)
            .replace("{{session_title}}", sessionTitle)
            .replace("{{lln_requirements}}", llnRequirements)
            .replace("{{learning_outcomes}}", learningOutcomes)
            .replace("{{reference_materials}}", referenceMaterials)
            .replace("{{learning_aids}}", learningAids)
            .replace("{{safety_requirements}}", safetyRequirements)
            .replace("{{introduction}}", introduction)
            .replace("{{step1_time}}", step1Time)
            .replace("{{step1_trainer}}", step1Trainer)
            .replace("{{step1_trainee}}", step1Trainee)
            .replace("{{step1_assessment}}", step1Assessment)
            .replace("{{step2a_time}}", step2aTime)
            .replace("{{step2a_trainer}}", step2aTrainer)
            .replace("{{step2a_trainee}}", step2aTrainee)
            .replace("{{step2a_assessment}}", step2aAssessment)
            .replace("{{step2b_time}}", step2bTime)
            .replace("{{step2b_trainer}}", step2bTrainer)
            .replace("{{step2b_trainee}}", step2bTrainee)
            .replace("{{step2b_assessment}}", step2bAssessment)
            .replace("{{step2c_time}}", step2cTime)
            .replace("{{step2c_trainer}}", step2cTrainer)
            .replace("{{step2c_trainee}}", step2cTrainee)
            .replace("{{step2c_assessment}}", step2cAssessment)
            .replace("{{session_review}}", sessionReview)
            .replace("{{assignment}}", assignment)
            .replace("{{total_time}}", totalTime)
            .replace("{{session_reflection}}", sessionReflection)
            .replace("{{signature}}", signature)
    }

    fun getFileName(): String {
        val timestamp = System.currentTimeMillis()
        return "SessionPlan_${unitCode.replace("/", "-")}_${sessionTitle.replace(" ", "_")}_$timestamp.html"
    }
}