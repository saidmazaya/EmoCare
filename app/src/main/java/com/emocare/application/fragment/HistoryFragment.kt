package com.emocare.application.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.emocare.application.R
import com.emocare.application.adapter.HistoryAdapter
import com.emocare.application.adapter.HistoryItem

/**
 * A fragment representing a list of Items.
 */
class HistoryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_history, container, false)

        // Setup the back button
        val backArrow = view.findViewById<ImageView>(R.id.back_arrow)
        backArrow.setOnClickListener {
            // Handle back press in fragment
            requireActivity().onBackPressed()
        }

        // Enable the back button in Action Bar (using the host activity)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Setup RecyclerView
        val rvHistory: RecyclerView = view.findViewById(R.id.rvHistory)
        rvHistory.layoutManager = LinearLayoutManager(requireContext())

        // Dummy Data
        val historyData = listOf(
            HistoryItem("3 Des 2023", "Konsultasi", "dr. Angelina Skyeum Sp.KJ, Dokter Psikolog"),
            HistoryItem("30 Okt 2023", "Konsultasi", "dr. Amanda Charles Sp.KJ, Dokter Psikolog"),
            HistoryItem("1 Nov 2023", "Konsultasi", "dr. Eka Parameswari M.Psi, Psikolog")
        )

        // Set Adapter
        rvHistory.adapter = HistoryAdapter(historyData)

        return view
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                // Go back to the previous activity
                requireActivity().onBackPressed()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}
