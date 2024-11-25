package com.emocare.application.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.emocare.application.adapter.QuestionViewAdapter
import com.emocare.application.R
import com.emocare.application.adapter.Question
import com.emocare.application.placeholder.PlaceholderContent

/**
 * A fragment representing a list of Items.
 */
class QuestionFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_question_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
//                layoutManager = when {
//                    columnCount <= 1 -> LinearLayoutManager(context)
//                    else -> GridLayoutManager(context, columnCount)
//                }
                val listQuestion = mutableListOf<Question>()
                val dataQuestion = resources.getStringArray(R.array.data_questions)
                for (i in dataQuestion.indices) {
                    listQuestion.add(Question(dataQuestion[i]))
                }
            }
        }
        return view
    }
}