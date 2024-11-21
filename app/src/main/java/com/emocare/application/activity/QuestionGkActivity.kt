package com.emocare.application.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.emocare.application.adapter.QuestionViewAdapter
import com.emocare.application.databinding.ActivityQuestionGkBinding
import com.emocare.application.placeholder.PlaceholderContent

class QuestionGkActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuestionGkBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityQuestionGkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvQuestion.layoutManager = LinearLayoutManager(this)
        binding.rvQuestion.adapter = QuestionViewAdapter(PlaceholderContent.ITEMS)
    }
}