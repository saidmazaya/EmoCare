package com.emocare.application.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.emocare.application.R

class Detailartikel : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detailartikel, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Tombol kembali
        val btnBack: ImageButton = view.findViewById(R.id.btn_back_dari_detail_artikel)
        btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        // Referensi ke elemen tampilan
        val titleTextView: TextView = view.findViewById(R.id.article_title_1)
        val articleImage: ImageView = view.findViewById(R.id.article_image)
        val profileImage: ImageView = view.findViewById(R.id.profile_image)
        val articleDate: TextView = view.findViewById(R.id.article_date_1)
        val articleContent: TextView = view.findViewById(R.id.article_content)

        // Judul artikel yang dikirim dari navigasi sebelumnya
        val judulArtikel =
            arguments?.getString("JUDUL_ARTIKEL") ?: "Tidak Diketahui" // Gunakan key yang konsisten

        // Tampilkan data artikel sesuai judul
        when (judulArtikel) {
            "Mengapa Kesehatan Mental Itu Penting" -> {
                titleTextView.text = judulArtikel
                articleDate.text = "Nov 16, 2020 . 6 min read"
                articleContent.text = """
            Kesehatan mental merupakan aspek kesehatan yang tak kalah pentingnya dibandingkan dengan kesehatan fisik. 
            Kesehatan mental tidak hanya sebatas ketiadaan gangguan jiwa, tetapi mencakup kesejahteraan emosional, psikologis, 
            dan sosial seseorang. Kesehatan mental yang baik memungkinkan seseorang untuk menghadapi tantangan hidup dengan 
            lebih baik, mengelola stres, berinteraksi dengan orang lain secara positif, serta membuat keputusan yang bijak. 
            Sebaliknya, gangguan mental dapat memengaruhi fungsi kognitif, perilaku, dan emosi seseorang, serta menurunkan 
            kualitas hidup. Oleh karena itu, menjaga kesehatan mental sangat penting dalam menciptakan kehidupan yang seimbang.
            
            Berbagai faktor dapat memengaruhi kesehatan mental, seperti genetika, pengalaman hidup, pola tidur, dan gaya hidup 
            yang diterapkan. Misalnya, stres yang berkepanjangan, kurangnya dukungan sosial, atau pola makan yang buruk dapat 
            menjadi pemicu masalah kesehatan mental. Maka dari itu, penting bagi setiap individu untuk menjaga kesehatan mentalnya 
            dengan melakukan aktivitas yang menyenangkan, berolahraga secara rutin, serta membangun hubungan sosial yang sehat.

            Jika merasa tertekan atau cemas, mencari bantuan profesional seperti psikolog atau psikiater dapat sangat membantu 
            dalam mengatasi masalah kesehatan mental. Ingatlah bahwa kesehatan mental tidak bisa dipisahkan dari kesehatan fisik, 
            dan keduanya saling berhubungan erat. Ketika keduanya terjaga dengan baik, kita dapat menjalani hidup dengan penuh 
            kebahagiaan dan produktivitas.
        """.trimIndent()
                articleImage.setImageResource(R.drawable.article_1)
                profileImage.setImageResource(R.drawable.article_1)
            }

            "Kesehatan Mental dan Kesejahteraan Hidup" -> {
                titleTextView.text = judulArtikel
                articleDate.text = "Jan 10, 2021 . 7 min read"
                articleContent.text = """
            Kesehatan mental sangat memengaruhi kesejahteraan hidup seseorang. Kesejahteraan hidup tidak hanya mencakup 
            kondisi fisik seseorang, tetapi juga bagaimana seseorang merasakan dan mengelola emosinya. Seseorang yang memiliki 
            kesehatan mental yang baik cenderung lebih bahagia, lebih mudah beradaptasi dengan perubahan, dan lebih mampu 
            mengatasi tekanan kehidupan sehari-hari.

            Salah satu aspek penting dari kesehatan mental adalah keseimbangan emosional. Seseorang yang mampu mengenali 
            dan mengelola perasaan mereka cenderung memiliki tingkat stres yang lebih rendah dan dapat menjaga hubungan 
            sosial yang lebih sehat. Sebaliknya, ketidakseimbangan emosional, seperti kecemasan berlebihan atau perasaan 
            depresi yang tidak terkelola, dapat memengaruhi kualitas hidup seseorang secara keseluruhan.

            Untuk meningkatkan kesejahteraan hidup, seseorang dapat melakukan berbagai hal, seperti berolahraga secara rutin, 
            bermeditasi, tidur yang cukup, dan berinteraksi dengan orang yang mendukung. Aktivitas seperti berkumpul dengan 
            teman-teman, melakukan hobi, atau bahkan sekadar menikmati waktu di alam terbuka dapat meningkatkan perasaan bahagia 
            dan mengurangi tingkat kecemasan. Selain itu, memiliki tujuan hidup yang jelas dan mencapai tujuan tersebut dapat 
            memberikan rasa pencapaian yang besar, yang secara langsung berdampak positif pada kesehatan mental.

            Dalam masyarakat yang serba cepat ini, penting bagi kita untuk memberikan perhatian khusus pada kesehatan mental 
            kita, karena itu adalah fondasi utama untuk kesejahteraan hidup yang seimbang dan memuaskan.
        """.trimIndent()
                articleImage.setImageResource(R.drawable.article_2)
                profileImage.setImageResource(R.drawable.article_2)
            }

            "Apa itu Depresi?" -> {
                titleTextView.text = judulArtikel
                articleDate.text = "Feb 20, 2021 . 8 min read"
                articleContent.text = """
            Depresi adalah gangguan mental yang sangat umum dan serius, yang memengaruhi suasana hati, perasaan, dan 
            aktivitas sehari-hari seseorang. Depresi bukan hanya perasaan sedih atau kecewa sementara, tetapi merupakan 
            kondisi yang berlangsung lama dan dapat memengaruhi berbagai aspek kehidupan seseorang, mulai dari pekerjaan, 
            hubungan sosial, hingga kesehatan fisik. 

            Gejala depresi termasuk perasaan sedih yang mendalam, kehilangan minat pada hal-hal yang biasanya disukai, 
            perubahan pola tidur, penurunan energi, perasaan putus asa, dan bahkan pikiran untuk menyakiti diri sendiri. 
            Beberapa orang juga mengalami gejala fisik, seperti sakit kepala atau gangguan pencernaan, yang tidak dapat 
            dijelaskan oleh kondisi medis lainnya.

            Depresi dapat dipicu oleh berbagai faktor, seperti stres berat, masalah hubungan, trauma masa lalu, atau ketidakseimbangan 
            kimiawi di otak. Namun, depresi juga dapat terjadi tanpa sebab yang jelas. Faktor genetik dan lingkungan 
            dapat memainkan peran penting dalam seseorang mengembangkan depresi.

            Pengobatan untuk depresi umumnya melibatkan kombinasi terapi psikologis (seperti terapi kognitif-perilaku) dan 
            pengobatan (antidepresan). Selain itu, dukungan dari keluarga dan teman-teman sangat penting untuk pemulihan. 
            Jika Anda merasa depresi, jangan ragu untuk mencari bantuan dari seorang profesional kesehatan mental, karena 
            dengan pengobatan yang tepat, depresi dapat dikelola dan diatasi.
        """.trimIndent()
                articleImage.setImageResource(R.drawable.article_3)
                profileImage.setImageResource(R.drawable.article_3)
            }

            "Apa Itu Serangan Panik?" -> {
                titleTextView.text = judulArtikel
                articleDate.text = "Mar 5, 2021 . 5 min read"
                articleContent.text = """
            Serangan panik adalah kondisi yang ditandai dengan perasaan takut atau cemas yang intens, yang datang secara 
            mendadak tanpa alasan yang jelas. Selama serangan panik, seseorang bisa merasa tercekik, jantung berdebar-debar, 
            berkeringat, dan merasa seolah-olah akan mati atau kehilangan kendali. Serangan panik dapat berlangsung beberapa menit, 
            tetapi sering kali terasa seperti waktu yang lebih lama bagi yang mengalaminya.

            Serangan panik sering terjadi tanpa adanya pemicu yang jelas, meskipun faktor stres atau kecemasan berlebihan 
            bisa menjadi pemicunya. Kondisi ini dapat menyebabkan gangguan dalam aktivitas sehari-hari dan mengarah pada 
            ketakutan berulang untuk mengalami serangan panik lagi (fobia situasional). Bagi sebagian orang, serangan panik 
            menjadi masalah kronis yang memerlukan penanganan medis.

            Pengobatan untuk serangan panik dapat mencakup terapi kognitif-perilaku, yang membantu individu mengidentifikasi 
            dan mengubah pola pikir yang menimbulkan kecemasan. Obat-obatan seperti antidepresan atau obat penenang juga 
            bisa digunakan untuk mengurangi frekuensi dan intensitas serangan panik. Selain itu, latihan relaksasi, meditasi, 
            dan teknik pernapasan dapat membantu seseorang mengelola gejala kecemasan.
        """.trimIndent()
                articleImage.setImageResource(R.drawable.article_4)
                profileImage.setImageResource(R.drawable.article_4)
            }

            "Terjawab: 7 Mitos tentang Kesehatan Mental" -> {
                titleTextView.text = judulArtikel
                articleDate.text = "Apr 11, 2021 . 6 min read"
                articleContent.text = """
            Kesehatan mental sering kali diselimuti oleh berbagai mitos dan kesalahpahaman yang dapat menyebabkan stigma 
            negatif terhadap individu yang mengalami gangguan mental. Di bawah ini, kita akan mengulas 7 mitos umum tentang 
            kesehatan mental dan fakta yang sebenarnya.

            1. **Mitos: Orang dengan gangguan mental selalu terlihat berbeda.**
               **Fakta:** Gangguan mental tidak selalu tampak secara fisik. Banyak orang yang mengalami gangguan mental yang 
               tetap bisa berfungsi normal dalam kehidupan sehari-hari.

            2. **Mitos: Gangguan mental disebabkan oleh kelemahan pribadi.**
               **Fakta:** Gangguan mental disebabkan oleh berbagai faktor, termasuk genetika, lingkungan, dan perubahan kimiawi 
               di otak, bukan kelemahan pribadi.

            3. **Mitos: Pengobatan untuk gangguan mental tidak efektif.**
               **Fakta:** Terapi dan pengobatan untuk gangguan mental terbukti efektif dalam mengurangi gejala dan membantu 
               individu untuk pulih. Pengobatan seringkali termasuk kombinasi terapi dan obat-obatan.

            4. **Mitos: Hanya orang dewasa yang bisa mengalami gangguan mental.**
               **Fakta:** Gangguan mental dapat dialami oleh siapa saja, termasuk anak-anak dan remaja. Penting untuk memberikan 
               dukungan kepada mereka sejak dini.

            5. **Mitos: Semua orang yang depresi ingin bunuh diri.**
               **Fakta:** Meskipun depresi dapat meningkatkan risiko bunuh diri, tidak semua orang yang depresi merasa ingin mengakhiri hidupnya.

            6. **Mitos: Mengatasi stres adalah tugas yang mudah.**
               **Fakta:** Stres yang berlebihan atau berkepanjangan bisa sangat merusak kesehatan mental, dan membutuhkan dukungan 
               profesional untuk diatasi.

            7. **Mitos: Jika Anda mengalami gangguan mental, Anda tidak bisa sembuh.**
               **Fakta:** Banyak orang dengan gangguan mental dapat sembuh atau mengelola gejalanya dengan baik melalui pengobatan dan dukungan.
        """.trimIndent()
                articleImage.setImageResource(R.drawable.article_5)
                profileImage.setImageResource(R.drawable.article_5)
            }

            else -> {
                titleTextView.text = "Artikel Tidak Ditemukan"
                articleDate.text = "Tidak Diketahui"
                articleContent.text = "Maaf, artikel yang Anda cari tidak tersedia."
                articleImage.setImageResource(R.drawable.ic_doctor)
                profileImage.setImageResource(R.drawable.ic_doctor)
            }
        }
    }
}
