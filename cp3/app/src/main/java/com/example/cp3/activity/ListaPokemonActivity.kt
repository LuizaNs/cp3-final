package com.example.cp3.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cp3.util.MyDataBase
import com.example.cp3.element.Pokemon
import com.example.cp3.adapter.PokemonAdapter
import com.example.cp3.R

class ListaPokemonActivity : AppCompatActivity() {

    private lateinit var recyclerViewPokemon: RecyclerView
    private lateinit var pokemonAdapter: PokemonAdapter
    private lateinit var db: MyDataBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_pokemon)

        db = MyDataBase(this)

        recyclerViewPokemon = findViewById(R.id.recyclerViewPokemon)

        recyclerViewPokemon.layoutManager = LinearLayoutManager(this)

        recyclerViewPokemon.itemAnimator = DefaultItemAnimator()

        pokemonAdapter = PokemonAdapter(db.getAllPokemon(),
            { pokemon -> editarPokemon(pokemon) }, // Listener para editar
            { pokemon -> excluirPokemon(pokemon) } // Listener para excluir
        )

        recyclerViewPokemon.adapter = pokemonAdapter
    }

    private fun editarPokemon(pokemon: Pokemon) {
        // Intent para iniciar a FormularioActivity
        val intent = Intent(this, FormularioActivity::class.java)
        intent.putExtra("pokemonId", pokemon.id)
        startActivity(intent)
    }

    private fun excluirPokemon(pokemon: Pokemon) {

        db.deletePokemon(pokemon.id)

        pokemonAdapter.updatePokemonList(db.getAllPokemon())
    }
}