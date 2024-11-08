package com.example.cp3.activity

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cp3.util.MyDataBase
import com.example.cp3.element.Pokemon
import com.example.cp3.R

class EditPokemonActivity : AppCompatActivity() {

    private lateinit var db: MyDataBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_pokemon)

        db = MyDataBase(this)

        val pokemonId = intent.getIntExtra("POKEMON_ID", -1)

        if (pokemonId == -1) {
            Toast.makeText(this, "ID do Pokémon inválido!", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val etEditNome: EditText = findViewById(R.id.etEditNome)
        val etEditTipo: EditText = findViewById(R.id.etEditTipo)
        val etEditHabilidade: EditText = findViewById(R.id.etEditHabilidade)
        val etEditFraqueza: EditText = findViewById(R.id.etEditFraqueza)
        val btnSalvar: Button = findViewById(R.id.btnSalvarEdicao)


        val pokemon = db.getPokemonById(pokemonId)
        if (pokemon != null) {
            etEditNome.setText(pokemon.nome)
            etEditTipo.setText(pokemon.tipo)
            etEditHabilidade.setText(pokemon.habilidade)
            etEditFraqueza.setText(pokemon.fraqueza)
        } else {
            Toast.makeText(this, "Pokémon não encontrado!", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        btnSalvar.setOnClickListener {
            val novoNome = etEditNome.text.toString()
            val novoTipo = etEditTipo.text.toString()
            val novaHabilidade = etEditHabilidade.text.toString()
            val novaFraqueza = etEditFraqueza.text.toString()

            val pokemonAtualizado =
                Pokemon(pokemonId, novoNome, novoTipo, novaHabilidade, novaFraqueza, pokemon.hp)
            val sucesso = db.updatePokemon(pokemonAtualizado)

            if (sucesso) {
                Toast.makeText(this, "Pokémon atualizado com sucesso!", Toast.LENGTH_SHORT).show()
                finish() // Fecha a Activity após salvar
            } else {
                Toast.makeText(this, "Erro ao atualizar o Pokémon!", Toast.LENGTH_SHORT).show()
            }
        }
    }}