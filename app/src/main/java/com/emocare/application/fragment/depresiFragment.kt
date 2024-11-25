package com.emocare.application.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.emocare.application.R
import com.emocare.application.activity.QuestionDpActivity
import com.emocare.application.activity.QuestionGkActivity

class depresiFragment : Fragment() {
    private lateinit var btnPindah: Button
    private lateinit var btnPindah2: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_depresi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnPindah = view.findViewById(R.id.btn_back_depresi)
        btnPindah.setOnClickListener {
            // Memastikan untuk menghapus fragment sebelumnya di backstack
            findNavController().popBackStack(R.id.depresiFragment, false)

            // Navigasi langsung ke EmotFragment
            findNavController().navigate(R.id.action_depresiFragment_to_emotFragment)
        }
        btnPindah2 = view.findViewById(R.id.btn_ambilTesDepresi)
        btnPindah2.setOnClickListener {
            Intent(requireActivity(), QuestionDpActivity::class.java).also {
                startActivity(it)
            }
        }
    }

}