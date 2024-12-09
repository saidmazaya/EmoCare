package com.emocare.application.adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.emocare.application.R


data class Doctor(
    val name: String,
    val specialization: String,
    val experience: String,
    val price: String,
    val imageRes: Int
)

class DoctorAdapter(private val doctorList: List<Doctor>) : RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder>() {

    inner class DoctorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val doctorImage: ImageView = itemView.findViewById(R.id.doctorImage)
        private val doctorName: TextView = itemView.findViewById(R.id.tvName)
        private val doctorSpecialization: TextView = itemView.findViewById(R.id.tvSpecialization)
        private val doctorExperience: TextView = itemView.findViewById(R.id.tvExperience)
        private val doctorPrice: TextView = itemView.findViewById(R.id.tvPrice)
        private val selectButton: Button = itemView.findViewById(R.id.btnSelect)

        fun bind(doctor: Doctor) {
            doctorImage.setImageResource(doctor.imageRes)
            doctorName.text = doctor.name
            doctorSpecialization.text = doctor.specialization
            doctorExperience.text = doctor.experience
            doctorPrice.text = doctor.price

            selectButton.setOnClickListener {
                Toast.makeText(
                    itemView.context,
                    "Dokter ${doctor.name} dipilih!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_doctor, parent, false)
        return DoctorViewHolder(view)
    }

    override fun onBindViewHolder(holder: DoctorViewHolder, position: Int) {
        holder.bind(doctorList[position])
    }

    override fun getItemCount(): Int = doctorList.size
}
