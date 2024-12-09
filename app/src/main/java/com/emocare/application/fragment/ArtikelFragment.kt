package com.emocare.application.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.emocare.application.R

class ArtikelFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_artikel, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Daftar artikel dengan ID tombol dan judul artikel
        val articles = listOf(
            Pair(R.id.artikel_pertama, "Mengapa Kesehatan Mental Itu Penting"),
            Pair(R.id.artikel_kedua, "Kesehatan Mental dan Kesejahteraan Hidup"),
            Pair(R.id.artikel_ketiga, "Apa itu Depresi?"),
            Pair(R.id.artikel_keempat, "Apa Itu Serangan Panik?"),
            Pair(R.id.artikel_kelima, "Terjawab: 7 Mitos tentang Kesehatan Mental")
        )

        // Set tombol klik untuk setiap artikel
        articles.forEach { (buttonId, title) ->
            val button: View = view.findViewById(buttonId)
            button.setOnClickListener {
                val bundle = Bundle().apply {
                    putString("JUDUL_ARTIKEL", title) // Simpan dengan key yang konsisten
                }
                findNavController().navigate(
                    R.id.action_artikelFragment_to_detailartikel,
                    bundle
                )
            }
        }
    }
}
