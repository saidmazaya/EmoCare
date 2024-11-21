package com.emocare.application.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.emocare.application.R
import com.emocare.application.activity.QuestionGkActivity

class gangguanKecemasan : Fragment() {
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
        return inflater.inflate(R.layout.fragment_gangguan_kecemasan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnPindah = view.findViewById(R.id.btn_back_gangguan_kecemasan)
        btnPindah.setOnClickListener {
            // Memastikan untuk menghapus fragment sebelumnya di backstack
            findNavController().popBackStack(R.id.gangguanKecemasan, false)

            // Navigasi langsung ke EmotFragment
            findNavController().navigate(R.id.action_gangguanKecemasan_to_emotFragment)
        }

        btnPindah2 = view.findViewById(R.id.btn_ambilTesGangguanKecemasan)
        btnPindah2.setOnClickListener {
            Intent(requireActivity(), QuestionGkActivity::class.java).also {
                startActivity(it)
            }
        }
    }
}