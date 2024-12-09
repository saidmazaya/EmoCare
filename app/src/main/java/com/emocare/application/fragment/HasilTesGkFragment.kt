package com.emocare.application.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.emocare.application.R
import com.google.android.material.slider.Slider
import android.content.res.ColorStateList
import android.provider.ContactsContract.Data
import android.util.Log
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.emocare.application.databinding.FragmentHasilTesGkBinding
import com.emocare.application.singleton.DataSingleton

class HasilTesGkFragment : Fragment() {

    private var testType: String? = null // To determine the type of test
    private var score: Int? = null // To store the score
    private lateinit var binding: FragmentHasilTesGkBinding
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//            score = it.getInt("SCORE", 0)
//            testType = it.getString("TEST_TYPE") // Get the test type from the arguments
//        }
//
//        // If testType is not set, use the default value
//        if (testType == null) {
//            testType = "DEPRESSION_TEST" // Set default test type
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHasilTesGkBinding.inflate(inflater, container, false)
        val view = binding.root
        score = DataSingleton.score ?: 0
        testType = DataSingleton.testType
        Log.d("HasilTesGkFragment", "Score: $score, TestType: $testType")
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inflate the layout for this fragment
        val slider = view.findViewById<Slider>(R.id.slider)
        val tvTitle = view.findViewById<TextView>(R.id.tv_tes_gangguan_kecemasan_detail)
        val imgDetail = view.findViewById<ImageView>(R.id.img_tes_gangguan_kecemasan_detail)
        val tvSkor = view.findViewById<TextView>(R.id.tv_skor)
        val tvSkorMenandakan = view.findViewById<TextView>(R.id.tv_skorMenandakan)
        val tvDeskripsiHasil = view.findViewById<TextView>(R.id.tv_deskripsi_hasil)

        binding.btnUlangtes.setOnClickListener {
            try {
                DataSingleton.score = null
                val action = when (DataSingleton.testType) {
                    "ANXIETY_TEST" -> R.id.action_hasilTesGkFragment_to_gangguanKecemasan
                    "DEPRESSION_TEST" -> R.id.action_hasilTesGkFragment_to_depresiFragment
                    else -> null
                }

                action?.let {
                    findNavController().navigate(it)
                } ?: run {
                    Toast.makeText(requireContext(), "Tes tidak valid!", Toast.LENGTH_SHORT).show()
                }

                DataSingleton.testType = null
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(requireContext(), "Error: ${e.localizedMessage}", Toast.LENGTH_LONG)
                    .show()
            }
        }

        binding.btnBackHasilTes.setOnClickListener {
            try {
                DataSingleton.score = null
                val action = when (DataSingleton.testType) {
                    "ANXIETY_TEST" -> R.id.action_hasilTesGkFragment_to_emotFragment
                    "DEPRESSION_TEST" -> R.id.action_hasilTesGkFragment_to_emotFragment
                    else -> null
                }

                action?.let {
                    findNavController().navigate(it)
                } ?: run {
                    Toast.makeText(requireContext(), "Tes tidak valid!", Toast.LENGTH_SHORT).show()
                }

                DataSingleton.testType = null
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(requireContext(), "Error: ${e.localizedMessage}", Toast.LENGTH_LONG)
                    .show()
            }
        }

        binding.btnCarikanAhli.setOnClickListener {
            try {
                DataSingleton.score = null
                val action = when (DataSingleton.testType) {
                    "ANXIETY_TEST" -> R.id.action_hasilTesGkFragment_to_counselingFragment
                    "DEPRESSION_TEST" -> R.id.action_hasilTesGkFragment_to_counselingFragment
                    else -> null
                }

                action?.let {
                    findNavController().navigate(it)
                } ?: run {
                    Toast.makeText(requireContext(), "Tes tidak valid!", Toast.LENGTH_SHORT).show()
                }

                DataSingleton.testType = null
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(requireContext(), "Error: ${e.localizedMessage}", Toast.LENGTH_LONG)
                    .show()
            }
        }

        // Get test configuration based on the test type
        val testConfig = getTestConfig(testType)
        tvTitle.text = testConfig.title
        imgDetail.setImageResource(testConfig.imageRes)

        // Set the slider value based on the score
        slider.value = score?.toFloat() ?: 0f

        // Update the UI based on the score
        score?.let {
            updateUI(
                it,
                tvSkor,
                tvSkorMenandakan,
                tvDeskripsiHasil,
                slider,
                testConfig
            )
        }

        // Optionally disable the slider for interaction
        slider.isEnabled = false

        // Update UI dynamically if the slider value changes
        slider.addOnChangeListener { _, value, _ ->
            updateUI(value.toInt(), tvSkor, tvSkorMenandakan, tvDeskripsiHasil, slider, testConfig)
        }
    }

    private fun updateUI(
        score: Int,
        tvSkor: TextView,
        tvSkorMenandakan: TextView,
        tvDeskripsiHasil: TextView,
        slider: Slider,
        testConfig: TestConfig
    ) {
        // Update the score textF
        tvSkor.text = "Skor kamu: $score"

        // Determine the color and description based on the score range
        val (color, menandakanText, deskripsiText) = when (score) {
            in 0..5 -> Triple(
                ContextCompat.getColor(requireContext(), R.color.green_track),
                testConfig.range1Text,
                testConfig.range1Description
            )

            in 6..10 -> Triple(
                ContextCompat.getColor(requireContext(), R.color.light_green_track),
                testConfig.range2Text,
                testConfig.range2Description
            )

            in 11..15 -> Triple(
                ContextCompat.getColor(requireContext(), R.color.yellow_track),
                testConfig.range3Text,
                testConfig.range3Description
            )

            in 16..20 -> Triple(
                ContextCompat.getColor(requireContext(), R.color.orange_track),
                testConfig.range4Text,
                testConfig.range4Description
            )

            in 21..Int.MAX_VALUE -> Triple(
                ContextCompat.getColor(requireContext(), R.color.red_track),
                testConfig.range5Text,
                testConfig.range5Description
            )

            else -> Triple(
                ContextCompat.getColor(requireContext(), R.color.red_track),
                "Skor tidak valid.",
                "Ada kesalahan dalam pengukuran skor. Silakan ulangi tes atau konsultasikan dengan profesional."
            )
        }

        // Update the TextViews with the calculated texts
        tvSkorMenandakan.text = menandakanText
        tvDeskripsiHasil.text = deskripsiText

        // Update the slider colors based on the score
        slider.trackActiveTintList = ColorStateList.valueOf(color)
        slider.trackInactiveTintList = ColorStateList.valueOf(lightenColor(color))
    }

    private fun getTestConfig(testType: String?): TestConfig {
        return when (testType) {
            "ANXIETY_TEST" -> TestConfig(
                title = "Hasil Tes Gangguan Kecemasan",
                imageRes = R.drawable.tesgangguankecemasan,
                range1Text = "Skor kamu menunjukkan tidak ada gangguan kecemasan.",
                range1Description = "Kamu dalam kondisi emosional yang stabil dan sehat. Pertahankan gaya hidup yang positif untuk menjaga kesehatan mental.",
                range2Text = "Skor kamu menandakan gangguan kecemasan ringan.",
                range2Description = "Kamu mungkin sesekali merasa cemas, tetapi hal ini tidak mengganggu aktivitas sehari-hari. Cobalah teknik relaksasi seperti meditasi atau olahraga ringan untuk mengurangi stres.",
                range3Text = "Skor kamu menandakan gangguan kecemasan sedang.",
                range3Description = "Kamu terkadang merasakan kecemasan yang tidak dapat dijelaskan. Penting untuk mengenali pemicu stres dan mempertimbangkan berkonsultasi dengan ahli kesehatan mental.",
                range4Text = "Skor kamu menandakan gangguan kecemasan berat.",
                range4Description = "Kamu mungkin mengalami kecemasan yang cukup intens dan sulit dikendalikan. Disarankan untuk mencari bantuan profesional untuk mendiskusikan strategi penanganan.",
                range5Text = "Skor kamu menandakan gangguan kecemasan sangat berat.",
                range5Description = "Kamu berada pada tingkat kecemasan yang serius, yang mungkin memengaruhi aktivitas harianmu. Segera konsultasikan dengan psikolog atau psikiater untuk mendapatkan perawatan yang tepat."
            )

            "DEPRESSION_TEST" -> TestConfig(
                title = "Hasil Tes Depresi",
                imageRes = R.drawable.tesdepresi,
                range1Text = "Skor kamu menunjukkan tidak ada tanda-tanda depresi.",
                range1Description = "Kamu berada dalam kondisi mental yang sehat. Tetaplah menjaga pola hidup yang seimbang dan dukungan sosial untuk mempertahankan kesehatan mental.",
                range2Text = "Skor kamu menandakan depresi ringan.",
                range2Description = "Kamu mungkin merasa sedih atau kurang termotivasi pada beberapa waktu. Pertimbangkan untuk berbagi perasaan dengan teman atau keluarga dan lakukan aktivitas yang kamu nikmati.",
                range3Text = "Skor kamu menandakan depresi sedang.",
                range3Description = "Kamu mungkin mengalami kehilangan minat atau kebahagiaan dalam aktivitas sehari-hari. Penting untuk mencari dukungan dari orang terdekat atau mulai berbicara dengan seorang konselor.",
                range4Text = "Skor kamu menandakan depresi berat.",
                range4Description = "Kamu mungkin merasa putus asa, lelah, atau sulit menjalani aktivitas harian. Sebaiknya segera mencari bantuan profesional untuk memulai proses pemulihan.",
                range5Text = "Skor kamu menandakan depresi sangat berat.",
                range5Description = "Kondisi kamu menunjukkan gejala depresi yang parah dan memerlukan perhatian segera. Segera hubungi psikolog, psikiater, atau fasilitas kesehatan untuk mendapatkan bantuan."
            )

            else -> TestConfig(
                title = "Hasil Tes Tidak Diketahui",
                imageRes = R.drawable.tesgangguankecemasan,
                range1Text = "Skor tidak valid.",
                range1Description = "Ada kesalahan dalam pengukuran skor.",
                range2Text = "",
                range2Description = "",
                range3Text = "",
                range3Description = "",
                range4Text = "",
                range4Description = "",
                range5Text = "",
                range5Description = ""
            )
        }
    }

    private fun lightenColor(color: Int): Int {
        val alpha = 50 // Transparency for inactive part
        val red = (color shr 16) and 0xFF
        val green = (color shr 8) and 0xFF
        val blue = color and 0xFF
        return android.graphics.Color.argb(alpha, red, green, blue)
    }

    data class TestConfig(
        val title: String,
        val imageRes: Int,
        val range1Text: String,
        val range1Description: String,
        val range2Text: String,
        val range2Description: String,
        val range3Text: String,
        val range3Description: String,
        val range4Text: String,
        val range4Description: String,
        val range5Text: String,
        val range5Description: String
    )
}
