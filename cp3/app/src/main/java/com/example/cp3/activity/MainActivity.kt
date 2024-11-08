package com.example.cp3.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.cp3.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, window.decorView).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        window.decorView.setBackgroundColor(Color.WHITE)

        val botaoVerPokemon: Button = findViewById(R.id.button_view_pokemon)
        val botaoAdicionarPokemon: Button = findViewById(R.id.button_add_pokemon)
        val botaoDesenvolvedores: Button = findViewById(R.id.btnDesenvolvedores)
        val botaoAdicionarCards: Button = findViewById(R.id.btnadicionarCards)

        botaoVerPokemon.setOnClickListener {

            val intent = Intent(this, ListaPokemonActivity::class.java)
            startActivity(intent)
        }

        botaoAdicionarPokemon.setOnClickListener {

            val intent = Intent(this, FormularioActivity::class.java)
            startActivity(intent)
        }

        botaoAdicionarCards.setOnClickListener {

            val intent = Intent(this, UploadImagemActivity::class.java)
            startActivity(intent)
        }

        botaoDesenvolvedores.setOnClickListener {

            val intent = Intent(this, IntegrantesActivity::class.java)
            startActivity(intent)
        }
    }
}