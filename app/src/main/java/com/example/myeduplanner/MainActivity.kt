package com.example.myeduplanner

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myeduplanner.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Settings button
        binding.btnSettings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        // History button - NEW
        binding.btnHistory.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }

        // Learning Plan button
        binding.btnLearningPlan.setOnClickListener {
            val intent = Intent(this, LearningPlanActivity::class.java)
            startActivity(intent)
        }

        // Session Plan button
        binding.btnSessionPlan.setOnClickListener {
            val intent = Intent(this, SessionPlanActivity::class.java)
            startActivity(intent)
        }
    }
}