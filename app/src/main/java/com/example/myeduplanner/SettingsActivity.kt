package com.example.myeduplanner

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myeduplanner.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var settings: AppSettings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        settings = AppSettings(this)

        // Load existing settings
        loadSettings()

        // Save button click
        binding.btnSave.setOnClickListener {
            saveSettings()
        }
    }

    private fun loadSettings() {
        binding.etTrainerName.setText(settings.getTrainerName())
        binding.etAdmissionNumber.setText(settings.getAdmissionNumber())
        binding.etInstitution.setText(settings.getInstitution())
        binding.etLevel.setText(settings.getLevel())
        binding.etClass.setText(settings.getClass())
        binding.etUnitCode.setText(settings.getUnitCode())
        binding.etUnitOfCompetence.setText(settings.getUnitOfCompetence())
        binding.etNumberOfTrainees.setText(settings.getNumberOfTrainees())
    }

    private fun saveSettings() {
        settings.saveTrainerName(binding.etTrainerName.text.toString().trim())
        settings.saveAdmissionNumber(binding.etAdmissionNumber.text.toString().trim())
        settings.saveInstitution(binding.etInstitution.text.toString().trim())
        settings.saveLevel(binding.etLevel.text.toString().trim())
        settings.saveClass(binding.etClass.text.toString().trim())
        settings.saveUnitCode(binding.etUnitCode.text.toString().trim())
        settings.saveUnitOfCompetence(binding.etUnitOfCompetence.text.toString().trim())
        settings.saveNumberOfTrainees(binding.etNumberOfTrainees.text.toString().trim())

        Toast.makeText(this, "Settings saved successfully!", Toast.LENGTH_SHORT).show()
        finish() // Go back to home screen
    }
}