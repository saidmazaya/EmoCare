import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.emocare.application.ChatModel
import com.emocare.application.R

class ChatAdapter(private val chatList: List<ChatModel>) : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    // ViewHolder untuk item chat
    class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val profileImage: ImageView = itemView.findViewById(R.id.profileImage)
        val doctorName: TextView = itemView.findViewById(R.id.doctorName)
        val lastMessage: TextView = itemView.findViewById(R.id.lastMessage)
        val messageTime: TextView = itemView.findViewById(R.id.messageTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat, parent, false)
        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val chat = chatList[position]
        holder.profileImage.setImageResource(chat.profileImage)
        holder.doctorName.text = chat.doctorName
        holder.lastMessage.text = chat.lastMessage
        holder.messageTime.text = chat.messageTime
    }

    override fun getItemCount(): Int = chatList.size
}
