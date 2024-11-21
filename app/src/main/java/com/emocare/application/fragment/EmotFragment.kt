package com.emocare.application.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.emocare.application.R

class EmotFragment : Fragment() {
    private lateinit var btnPindah: Button
    private lateinit var btnPindah2: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_emot, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnPindah = view.findViewById(R.id.btn_arrowGangguanKecemasan)
        btnPindah.setOnClickListener {
            // Memastikan untuk menghapus fragment sebelumnya di backstack
            findNavController().popBackStack(R.id.emotFragment, false)

            // Navigasi langsung ke EmotFragment
            findNavController().navigate(R.id.action_emotFragment_to_gangguanKecemasan)
        }

        btnPindah2 = view.findViewById(R.id.btn_arrowDepresi)
        btnPindah2.setOnClickListener {
            // Memastikan untuk menghapus fragment sebelumnya di backstack
            findNavController().popBackStack(R.id.emotFragment, false)

            // Navigasi langsung ke EmotFragment
            findNavController().navigate(R.id.action_emotFragment_to_depresiFragment)
        }
    }

}