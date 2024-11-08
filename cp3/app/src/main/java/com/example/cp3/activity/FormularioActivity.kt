package com.example.cp3.activity

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.cp3.util.MyDataBase
import com.example.cp3.element.Pokemon
import com.example.cp3.R

class FormularioActivity : AppCompatActivity() {

    private lateinit var etNome: EditText
    private lateinit var etTipo: EditText
    private lateinit var etHabilidade: EditText
    private lateinit var etFraqueza: EditText
    private lateinit var etHp: EditText
    private lateinit var btnSalvar: Button
    private lateinit var db: MyDataBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_pokemon)

        etNome = findViewById(R.id.etEditNome)
        etTipo = findViewById(R.id.etEditTipo)
        etHabilidade = findViewById(R.id.etEditHabilidade)
        etFraqueza = findViewById(R.id.etEditFraqueza)
        etHp = findViewById(R.id.etEditHp)
        btnSalvar = findViewById(R.id.btnSalvarEdicao)


        db = MyDataBase(this)

        btnSalvar.setOnClickListener {

            val nome = etNome.text.toString()
            val tipo = etTipo.text.toString()
            val habilidade = etHabilidade.text.toString()
            val fraqueza = etFraqueza.text.toString()
            val hp = etHp.text.toString().toIntOrNull() ?: 0 // Obter o HP como Int, 0 se não for válido

            val novoPokemon = Pokemon(nome = nome, tipo = tipo, habilidade = habilidade, fraqueza = fraqueza, hp = hp)

            db.insertPokemon(novoPokemon)

            etNome.setText("")
            etTipo.setText("")
            etHabilidade.setText("")
            etFraqueza.setText("")
            etHp.setText("")
        }
    }
}