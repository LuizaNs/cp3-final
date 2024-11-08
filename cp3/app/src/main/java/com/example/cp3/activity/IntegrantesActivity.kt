package com.example.cp3.activity

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.cp3.R

class IntegrantesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_integrantes)

        val tvNomesDesenvolvedores = findViewById<TextView>(R.id.tvNomesDesenvolvedores)

        tvNomesDesenvolvedores.text = "Pamella S.Engholm-551600\nLuiza Nunes de Jesus-99768"


        val nomes = SpannableStringBuilder(tvNomesDesenvolvedores.text)
        nomes.setSpan(ForegroundColorSpan(resources.getColor(R.color.black)), 0, nomes.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        tvNomesDesenvolvedores.text = nomes
    }
}