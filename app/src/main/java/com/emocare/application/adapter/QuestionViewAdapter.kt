package com.emocare.application.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.emocare.application.databinding.QuestionLayoutBinding
import com.emocare.application.placeholder.PlaceholderContent.PlaceholderItem

class QuestionViewAdapter(
    private val values: List<PlaceholderItem>
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

        holder.itemNumber.text = item.id // ID pertanyaan
        holder.questionText.text = item.content // Teks pertanyaan

        // Atur teks untuk setiap RadioButton
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(private val binding: QuestionLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        val itemNumber: TextView = binding.itemNumber // Referensi TextView ID dari XML
        val questionText: TextView = binding.itemNumber // Konten pertanyaan
        val radioButton1: RadioButton = binding.radioButton
        val radioButton2: RadioButton = binding.radioButton2
        val radioButton3: RadioButton = binding.radioButton5
        val radioButton4: RadioButton = binding.radioButton4
    }
}
