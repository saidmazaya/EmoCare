package com.emocare.application.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.emocare.application.R

class StressActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Menghubungkan layout XML dengan Activity ini
        setContentView(R.layout.fragment_detail_stress)
    }
}
