//package com.emocare.application.activity
//
//import com.emocare.application.adapter.ChatAdapter
//import android.os.Bundle
//import androidx.appcompat.app.AppCompatActivity
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.emocare.application.R
//
//class ChatActivity : AppCompatActivity() {
//
//    private lateinit var chatRecyclerView: RecyclerView
//    private lateinit var chatAdapter: ChatAdapter
//
//    // Buat list data chat contoh
//    private val chatList = listOf(
//        ChatModel("Dr. Angelina Skyzern Sp.Kj", "Terima Kasih dokter", "10:29", R.drawable.dokter1),
//        ChatModel("Dr. Puspa S.Kj", "Apa keluhanmu saat ini?", "09:20", R.drawable.dokter2),
//        ChatModel("Dr. Maharani Rizky M.Psi", "Hello!", "03:44", R.drawable.dokter3)
//    )
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_chat)
//
//        // Inisialisasi RecyclerView dan Adapter
//        chatRecyclerView = findViewById(R.id.chatRecyclerView)
//        chatRecyclerView.layoutManager = LinearLayoutManager(this) // Set LayoutManager
//        chatAdapter = ChatAdapter(chatList) // Buat instance dari adapter
//        chatRecyclerView.adapter = chatAdapter // Set adapter ke RecyclerView
//    }
//}
