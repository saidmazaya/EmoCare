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
 * Use the [konfirmasiPembayaran2Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class konfirmasiPembayaran2Fragment : Fragment() {
    private lateinit var btnPindah: Button
    // TODO: Rename and change types of parameters

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_konfirmasi_pembayaran2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnPindah = view.findViewById(R.id.btn_transfer_ke_2)
        btnPindah.setOnClickListener {
            // Memastikan untuk menghapus fragment sebelumnya di backstack
            findNavController().popBackStack(R.id.konfirmasiPembayaran2Fragment, false)

            // Navigasi langsung ke EmotFragment
            findNavController().navigate(R.id.action_konfirmasiPembayaran2Fragment_to_konfrimasiPembayaran3Fragment)
        }
    }
}