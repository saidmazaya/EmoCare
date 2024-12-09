package com.emocare.application.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.emocare.application.R

class CounselingFragment : Fragment() {

    private lateinit var btnPindahPsikolog: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_counseling, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val topics = listOf(
            Pair(R.id.btn_stress, "Stress"),
            Pair(R.id.btn_gangguan_kecemasan_detail, "Gangguan Kecemasan"),
            Pair(R.id.btn_depresi_detail, "Depresi"),
            Pair(R.id.btn_keluarga_dan_hubungan, "Keluarga dan Hubungan"),
            Pair(R.id.btn_trauma, "Trauma"),
            Pair(R.id.btn_gangguan_mood, "Gangguan Mood")
        );

        btnPindahPsikolog = view.findViewById(R.id.btn_pilih_psikolog)
        btnPindahPsikolog.setOnClickListener{
            findNavController().navigate(R.id.action_counselingFragment_to_psikologFragment)
        }

        topics.forEach { (buttonId, topic) ->
            val button: View = view.findViewById(buttonId)
            button.setOnClickListener {
                val bundle = Bundle()
                bundle.putString("TOPIC", topic)
                findNavController().navigate(
                    R.id.action_counselingFragment_to_detailCounselling,
                    bundle
                )
            }
        }
    }
}
