package com.example.myeduplanner

data class LearningPlan(
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
    val reflectionsDate: String
) {
    fun toHtmlDocument(htmlTemplate: String): String {
        return htmlTemplate
            .replace("{{unit_of_competence}}", unitOfCompetence)
            .replace("{{unit_code}}", unitCode)
            .replace("{{trainer_name}}", trainerName)
            .replace("{{admission_number}}", admissionNumber)
            .replace("{{institution}}", institution)
            .replace("{{level}}", level)
            .replace("{{date_of_preparation}}", dateOfPreparation)
            .replace("{{date_of_revision}}", dateOfRevision)
            .replace("{{number_of_trainees}}", numberOfTrainees)
            .replace("{{class_code}}", classCode)
            .replace("{{skill_or_job_task}}", skillOrJobTask)
            .replace("{{benchmark_criteria}}", benchmarkCriteria)
            .replace("{{week}}", week)
            .replace("{{session_no}}", sessionNo)
            .replace("{{session_title}}", sessionTitle)
            .replace("{{learning_outcome}}", learningOutcome)
            .replace("{{trainer_activities}}", trainerActivities)
            .replace("{{trainee_activities}}", traineeActivities)
            .replace("{{trainee_assignment}}", traineeAssignment)
            .replace("{{resources_refs}}", resourcesRefs)
            .replace("{{training_aids}}", trainingAids)
            .replace("{{knowledge_checks}}", knowledgeChecks)
            .replace("{{skills_checks}}", skillsChecks)
            .replace("{{attitudes_checks}}", attitudesChecks)
            .replace("{{reflections_date}}", reflectionsDate)
    }

    fun getFileName(): String {
        val timestamp = System.currentTimeMillis()
        return "LearningPlan_${unitCode.replace("/", "-")}_Week${week}_$timestamp.html"
    }
}