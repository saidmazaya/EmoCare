package com.emocare.application.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.emocare.application.R
import com.emocare.application.adapter.Question
import com.emocare.application.adapter.QuestionViewAdapter
import com.emocare.application.databinding.ActivityQuestionGkBinding
import com.emocare.application.databinding.FragmentGangguanKecemasanBinding
import com.emocare.application.fragment.QuestionFragment
import com.emocare.application.fragment.gangguanKecemasan
import com.emocare.application.placeholder.PlaceholderContent

class QuestionGkActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuestionGkBinding
    private lateinit var btnPindah: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityQuestionGkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvQuestion.layoutManager = LinearLayoutManager(this)

        val listQuestion = mutableListOf<Question>()
        val dataQuestion = resources.getStringArray(R.array.data_questions)
        for (i in dataQuestion.indices) {
            listQuestion.add(Question(dataQuestion[i]))
        }

        binding.rvQuestion.adapter = QuestionViewAdapter(listQuestion)

        btnPindah = findViewById(R.id.btn_back_tes_gangguan_kecemasan)
        btnPindah.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fm_questionGk, gangguanKecemasan())
                addToBackStack(null)
                commit()
            }
        }
    }
}