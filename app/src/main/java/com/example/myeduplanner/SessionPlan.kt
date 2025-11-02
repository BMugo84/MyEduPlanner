package com.example.myeduplanner

data class SessionPlan(
    val date: String,
    val time: String,
    val className: String,
    val subject: String,
    val lessonTitle: String,
    val introduction: String,
    val mainActivities: String,
    val conclusion: String,
    val homework: String,
    val resources: String
)