package com.emocare.application.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.emocare.application.R

class DetailCounselling : Fragment() {

    private lateinit var btnPindah: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_stress_counsel, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val topic = arguments?.getString("TOPIC") ?: "Unknown"
        val titleTextView: TextView = view.findViewById(R.id.titleTextView)
        val causesDescription: TextView = view.findViewById(R.id.causesDescription)
        val solution1Text: TextView = view.findViewById(R.id.solution1Text)
        val solution2Text: TextView = view.findViewById(R.id.solution2Text)
        val solution3Text: TextView = view.findViewById(R.id.solution3Text)
        val solution4Text: TextView = view.findViewById(R.id.solution4Text)

        titleTextView.text = topic

        btnPindah = view.findViewById(R.id.btn_kembalistress)
        btnPindah.setOnClickListener {
            // Memastikan untuk menghapus fragment sebelumnya di backstack
            findNavController().popBackStack(R.id.detailCounselling, false)

            // Navigasi langsung ke EmotFragment
            findNavController().navigate(R.id.action_detailCounselling_to_counselingFragment)
        }
        // Define dynamic content based on the topic
        when (topic) {
            "Stress" -> {
                causesDescription.text =
                    "Stres adalah respons alami tubuh terhadap tekanan, yang bisa dipicu oleh pekerjaan, masalah pribadi, keuangan, perubahan hidup, atau kesehatan fisik yang buruk. Setiap orang merespons stres berbeda, sehingga penting menjaga keseimbangan antara tuntutan hidup dan kemampuan beradaptasi untuk mencegah stres berlebihan."
                solution1Text.text = "Cari dukungan sosial dari teman, keluarga, atau rekan kerja."
                solution2Text.text =
                    "Lakukan aktivitas fisik teratur, seperti olahraga, dan praktik teknik relaksasi seperti meditasi."
                solution3Text.text = "Kelola waktu dengan baik dan kembangkan keterampilan coping."
                solution4Text.text =
                    "Jika stres berlanjut atau memicu masalah kesehatan mental serius, konsultasikan dengan seorang profesional seperti psikolog atau psikiater."
            }

            "Gangguan Kecemasan" -> {
                causesDescription.text =
                    "Beberapa faktor yang dapat menyebabkan gangguan kecemasan termasuk keturunan, perubahan kimia dalam otak, pengalaman traumatis, atau stres kronis. Kecemasan juga dapat muncul sebagai respons terhadap situasi tertentu atau kondisi kehidupan yang menekan."
                solution1Text.text =
                    "Terapi Kognitif Perilaku (CBT) untuk mengidentifikasi dan mengubah pola pikir negatif"
                solution2Text.text =
                    "Antidepresan benzodiazepin, dapat diresepkan oleh profesional kesehatan mental untuk mengelola gejala kecemasan."
                solution3Text.text =
                    "Olahraga teratur dapat membantu mengurangi tingkat kecemasan dan meningkatkan kesejahteraan mental."
                solution4Text.text =
                    "Praktik meditasi, pernapasan dalam, atau yoga dapat membantu mengurangi tingkat stres dan kecemasan."
            }

            "Depresi" -> {
                causesDescription.text =
                    "Depresi dipicu oleh kombinasi genetika, perubahan kimia otak, stres, trauma, atau kondisi medis. Faktor lingkungan seperti isolasi dan konflik juga dapat memperburuk gejala, seperti perubahan berat badan, gangguan tidur, dan sulit konsentrasi."
                solution1Text.text =
                    "Terapi Kognitif Perilaku (CBT) untuk mengidentifikasi dan mengubah pola pikir negatif."
                solution2Text.text =
                    "Antidepresan untuk membantu mengatasi ketidakseimbangan kimia otak."
                solution3Text.text =
                    "Membangun hubungan sosial yang sehat dan mencari dukungan dari orang terdekat."
                solution4Text.text =
                    "Olahraga teratur dapat membantu meningkatkan mood melalui pelepasan endorfin dan peningkatan energi."
            }

            "Keluarga & Hubungan" -> {
                causesDescription.text =
                    "Gangguan hubungan keluarga bisa disebabkan oleh kurangnya komunikasi, ketidaksetaraan peran, perbedaan nilai, krisis finansial, perubahan hidup, dan tekanan eksternal."
                solution1Text.text =
                    "Terlibat dalam konseling dapat membantu membongkar masalah, meningkatkan komunikasi, dan mengidentifikasi solusi bersama."
                solution2Text.text =
                    "Meningkatkan keterampilan komunikasi seperti mendengarkan aktif dan mengungkapkan perasaan dengan jelas dapat membantu mengurangi konflik."
                solution3Text.text =
                    "Menjaga keseimbangan dalam pembagian tanggung jawab dan peran di antara anggota keluarga atau pasangan."
                solution4Text.text =
                    "Membangun pemahaman yang lebih baik tentang perbedaan individual dan menumbuhkan empati."
            }

            "Trauma" -> {
                causesDescription.text =
                    "Beberapa faktor yang dapat menyebabkan trauma meliputi kejadian traumatis seperti bencana alam, kecelakaan atau pelecehan, serta pengalaman kehilangan yang signifikan seperti kematian orang terkasih."
                solution1Text.text =
                    "EMDR / terapi berbicara untuk membantu individu  mengatasi dampak emosional dari pengalaman traumatis."
                solution2Text.text =
                    "Mendapatkan dukungan dari keluarga, teman, atau kelompok dukungan lainnya."
                solution3Text.text =
                    "Membangun perasaan keamanan dan stabilitas melalui rutinitas yang konsisten dan lingkungan yang mendukung."
                solution4Text.text =
                    "Menggunakan teknik relaksasi dan manajemen stres membantu individu mengatasi gejala trauma yang muncul."
            }

            "Gangguan & Mood" -> {
                causesDescription.text =
                    "Gangguan mood dapat dipicu oleh faktor keturunan, perubahan kimia otak, trauma, kondisi medis, stres kronis, isolasi sosial, dan lingkungan yang tidak mendukung."
                solution1Text.text =
                    "Terapi Kognitif Perilaku (CBT) untuk mengidentifikasi dan mengubah pola pikir negatif"
                solution2Text.text =
                    "Antidepresan benzodiazepin, dapat diresepkan oleh profesional kesehatan mental untuk mengelola gejala kecemasan."
                solution3Text.text =
                    "Membangun hubungan sosial yang sehat untuk dukungan emosional yang penting."
                solution4Text.text =
                    "Aktivitas fisik dapat meningkatkan produksi endorfin dan meredakan gejala gangguan mood."
            }

        }
    }
}
