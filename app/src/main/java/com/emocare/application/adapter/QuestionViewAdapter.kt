package com.emocare.application.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.emocare.application.databinding.QuestionLayoutBinding
import com.emocare.application.placeholder.PlaceholderContent.PlaceholderItem

data class Question(
    val question: String,
)

class QuestionViewAdapter(
    private val values: List<Question>
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

        holder.questionText.text = item.question // Teks pertanyaan

        // Atur teks untuk setiap RadioButton
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(private val binding: QuestionLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val questionText: TextView = binding.tvQuestionItem // Konten pertanyaan
    }
}
