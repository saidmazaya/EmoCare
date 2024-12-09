package com.emocare.application.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.emocare.application.R
import com.emocare.application.adapter.Question
import com.emocare.application.adapter.QuestionViewAdapter
import com.emocare.application.databinding.ActivityQuestionDpBinding
import com.emocare.application.databinding.ActivityQuestionGkBinding
import com.emocare.application.singleton.DataSingleton

class QuestionDpActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuestionDpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityQuestionDpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvQuestion.layoutManager = LinearLayoutManager(this)

        val listQuestion = mutableListOf<Question>()
        val dataQuestion = resources.getStringArray(R.array.data_questions_dp)
        for (i in dataQuestion.indices) {
            listQuestion.add(Question(dataQuestion[i]))
        }

        binding.rvQuestion.adapter = QuestionViewAdapter(listQuestion)

        binding.btnBackTesDepresi.setOnClickListener {
            finish()
        }

        binding.btnKirim.setOnClickListener {
            val totalScore =
                (binding.rvQuestion.adapter as QuestionViewAdapter).values.sumOf { it.score }

            // Set nilai untuk DataSingleton
            DataSingleton.score = totalScore
            DataSingleton.testType = "DEPRESSION_TEST"

            // Kirim data ke MainActivity
            Intent(this, MainActivity::class.java).apply {
                putExtra("EXTRA_FRAGMENT", "HasilTesGkFragment")
                putExtra("EXTRA_SCORE", totalScore)
                putExtra("EXTRA_TEST_TYPE", "DEPRESSION_TEST")
                startActivity(this)
            }
        }
    }
}