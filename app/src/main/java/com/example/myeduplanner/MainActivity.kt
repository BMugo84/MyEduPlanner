package com.example.myeduplanner  // ← Change this to YOUR package name

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myeduplanner.databinding.ActivityMainBinding  // ← And this

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Learning Plan Button Click
        binding.btnLearningPlan.setOnClickListener {
            val intent = Intent(this, LearningPlanActivity::class.java)
            startActivity(intent)
        }

        // Session Plan Button Click
        binding.btnSessionPlan.setOnClickListener {
            val intent = Intent(this, SessionPlanActivity::class.java)
            startActivity(intent)
        }
    }
}