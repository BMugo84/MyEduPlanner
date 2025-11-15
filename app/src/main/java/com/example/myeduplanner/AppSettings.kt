package com.example.myeduplanner

import android.content.Context
import android.content.SharedPreferences

class AppSettings(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("MyEduPlannerPrefs", Context.MODE_PRIVATE)

    // Save default values
    fun saveTrainerName(value: String) {
        prefs.edit().putString("trainer_name", value).apply()
    }

    fun saveAdmissionNumber(value: String) {
        prefs.edit().putString("admission_number", value).apply()
    }

    fun saveInstitution(value: String) {
        prefs.edit().putString("institution", value).apply()
    }

    fun saveLevel(value: String) {
        prefs.edit().putString("level", value).apply()
    }

    fun saveClass(value: String) {
        prefs.edit().putString("class", value).apply()
    }

    fun saveUnitCode(value: String) {
        prefs.edit().putString("unit_code", value).apply()
    }

    fun saveUnitOfCompetence(value: String) {
        prefs.edit().putString("unit_of_competence", value).apply()
    }

    fun saveNumberOfTrainees(value: String) {
        prefs.edit().putString("number_of_trainees", value).apply()
    }

    // Get default values
    fun getTrainerName(): String {
        return prefs.getString("trainer_name", "") ?: ""
    }

    fun getAdmissionNumber(): String {
        return prefs.getString("admission_number", "") ?: ""
    }

    fun getInstitution(): String {
        return prefs.getString("institution", "") ?: ""
    }

    fun getLevel(): String {
        return prefs.getString("level", "") ?: ""
    }

    fun getClass(): String {
        return prefs.getString("class", "") ?: ""
    }

    fun getUnitCode(): String {
        return prefs.getString("unit_code", "") ?: ""
    }

    fun getUnitOfCompetence(): String {
        return prefs.getString("unit_of_competence", "") ?: ""
    }

    fun getNumberOfTrainees(): String {
        return prefs.getString("number_of_trainees", "") ?: ""
    }

    // Check if defaults are set
    fun hasDefaultValues(): Boolean {
        return getTrainerName().isNotEmpty() && getInstitution().isNotEmpty()
    }
}