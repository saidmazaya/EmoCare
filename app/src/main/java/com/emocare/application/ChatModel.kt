package com.emocare.application

data class ChatModel(
    val doctorName: String,
    val lastMessage: String,
    val messageTime: String,
    val profileImage: Int
)
