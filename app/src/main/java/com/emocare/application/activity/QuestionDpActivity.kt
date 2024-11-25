package com.emocare.application.activity

import android.os.Bundle
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
    }
}