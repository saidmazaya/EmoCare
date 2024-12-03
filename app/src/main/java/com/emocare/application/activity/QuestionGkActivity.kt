package com.emocare.application.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.emocare.application.R
import com.emocare.application.adapter.Question
import com.emocare.application.adapter.QuestionViewAdapter
import com.emocare.application.databinding.ActivityQuestionGkBinding
import com.emocare.application.fragment.HasilTesGkFragment
import com.emocare.application.singleton.DataSingleton

class QuestionGkActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuestionGkBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityQuestionGkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inisialisasi RecyclerView
        binding.rvQuestion.layoutManager = LinearLayoutManager(this)

        // Isi daftar pertanyaan dari resource
        val listQuestion = mutableListOf<Question>()
        val dataQuestion = resources.getStringArray(R.array.data_questions)
        for (i in dataQuestion.indices) {
            listQuestion.add(Question(dataQuestion[i]))
        }

        // Set adapter ke RecyclerView
        binding.rvQuestion.adapter = QuestionViewAdapter(listQuestion)

        // Tombol Back untuk menutup Activity
        binding.btnBackTesGangguanKecemasan.setOnClickListener {
            finish()
        }

//         Tombol Kirim untuk membuka fragment HasilTesGkFragment
        binding.btnKirim.setOnClickListener {
            val totalScore =
                (binding.rvQuestion.adapter as QuestionViewAdapter).values.sumOf { it.score }

            // Set nilai untuk DataSingleton
            DataSingleton.score = totalScore
            DataSingleton.testType = "ANXIETY_TEST"

            // Kirim data ke MainActivity
            Intent(this, MainActivity::class.java).apply {
                putExtra("EXTRA_FRAGMENT", "HasilTesGkFragment")
                putExtra("EXTRA_SCORE", totalScore)
                putExtra("EXTRA_TEST_TYPE", "ANXIETY_TEST")
                startActivity(this)
            }
        }
    }
}
