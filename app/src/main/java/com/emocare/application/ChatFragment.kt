package com.emocare.application

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.emocare.application.ChatItem
import com.emocare.application.databinding.FragmentChatBinding
import com.emocare.application.adapter.ChatAdapter


class ChatFragment : Fragment() {
    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize RecyclerView
        val chatAdapter = ChatAdapter(getDummyChatData()) // Replace with actual data source
        binding.chatRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.chatRecyclerView.adapter = chatAdapter

        // Set click listener for back button
        binding.btnBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getDummyChatData(): List<ChatItem> {
        // Example dummy data
        return listOf(
            ChatItem("Dr. John Doe Sp.Kj", "My Plasure", "12:00 PM"),
            ChatItem("Dr. Jane Smith S.Kj", "Thank you doctor", "11:45 AM"),
            ChatItem("Dr. Alice M. Psi", "Don't forget to relax your mind", "10:30 AM")
        )
    }
}
