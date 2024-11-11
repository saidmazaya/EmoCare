package com.emocare.application

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController

class gangguanKecemasan : Fragment() {
    private lateinit var btnPindah: Button

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
    }
}