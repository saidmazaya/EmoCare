package com.emocare.application.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.emocare.application.databinding.QuestionLayoutBinding
import com.emocare.application.placeholder.PlaceholderContent.PlaceholderItem
import com.emocare.application.singleton.DataSingleton

data class Question(
    val question: String,
    var score: Int = 0
)

class QuestionViewAdapter(
    val values: List<Question>
) : RecyclerView.Adapter<QuestionViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            QuestionLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]

        // Set teks pertanyaan
        holder.questionText.text = item.question

        // Bind pilihan radio sesuai dengan nilai score
        when (item.score) {
            0 -> holder.radioButton1.isChecked = true
            1 -> holder.radioButton2.isChecked = true
            2 -> holder.radioButton3.isChecked = true
            3 -> holder.radioButton4.isChecked = true
        }

        // Listener untuk menangkap perubahan pada RadioButton
        holder.radioButton1.setOnClickListener { item.score = 0 }
        holder.radioButton2.setOnClickListener { item.score = 1 }
        holder.radioButton3.setOnClickListener { item.score = 2 }
        holder.radioButton4.setOnClickListener { item.score = 3 }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: QuestionLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val questionText: TextView = binding.tvQuestionItem // Konten pertanyaan
        val radioButton1: RadioButton = binding.radioButton1
        val radioButton2: RadioButton = binding.radioButton2
        val radioButton3: RadioButton = binding.radioButton3
        val radioButton4: RadioButton = binding.radioButton4
    }
}

