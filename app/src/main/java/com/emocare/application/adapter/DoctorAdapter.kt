package com.emocare.application.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.emocare.application.R

// Model data dokter
data class Doctor(
    val name: String,
    val specialization: String,
    val experience: String,
    val price: String,
    val gender: String,
    val imageResId: Int
)

class DoctorAdapter(
    private val doctors: List<Doctor>,
    private val onSelectClick: (Doctor) -> Unit // Lambda untuk menangani klik tombol "Pilih"
) : RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder>() {

    class DoctorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val doctorImage: ImageView = view.findViewById(R.id.doctorImage)
        val tvName: TextView = view.findViewById(R.id.tvName)
        val tvSpecialization: TextView = view.findViewById(R.id.tvSpecialization)
        val tvExperience: TextView = view.findViewById(R.id.tvExperience)
        val tvPrice: TextView = view.findViewById(R.id.tvPrice)
        val tvGender: TextView = view.findViewById(R.id.tvGenderDokter)
        val btnSelect: Button = view.findViewById(R.id.btnSelectPsikolog)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_doctor, parent, false)
        return DoctorViewHolder(view)
    }

    override fun onBindViewHolder(holder: DoctorViewHolder, position: Int) {
        val doctor = doctors[position]
        holder.doctorImage.setImageResource(doctor.imageResId)
        holder.tvName.text = doctor.name
        holder.tvSpecialization.text = doctor.specialization
        holder.tvExperience.text = doctor.experience
        holder.tvPrice.text = doctor.price
        holder.tvGender.text = doctor.gender

        // Tombol "Pilih" untuk setiap item dokter
        holder.btnSelect.setOnClickListener {
            onSelectClick(doctor) // Panggil lambda dengan data dokter yang dipilih
        }

        // Navigasi ke halaman detail saat item RecyclerView diklik
        holder.itemView.setOnClickListener { view ->
            val navController = findNavController(view)
            navController.navigate(R.id.action_psikologFragment_to_detail3CounsellingFragment)
        }
    }

    override fun getItemCount(): Int = doctors.size
}