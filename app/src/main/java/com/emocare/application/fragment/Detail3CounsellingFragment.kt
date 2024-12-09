package com.emocare.application.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.emocare.application.R

/**
 * A simple [Fragment] subclass.
 * Use the [Detail3CounsellingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Detail3CounsellingFragment : Fragment() {

    private lateinit var btnPindah: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail3_counselling, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnPindah = view.findViewById(R.id.btn_konfirmasi_ke_3)
        btnPindah.setOnClickListener {
            // Memastikan untuk menghapus fragment sebelumnya di backstack
            findNavController().popBackStack(R.id.detail3CounsellingFragment, false)

            // Navigasi langsung ke EmotFragment
            findNavController().navigate(R.id.action_detail3CounsellingFragment_to_detail4CounselingFragment)
        }
    }

}