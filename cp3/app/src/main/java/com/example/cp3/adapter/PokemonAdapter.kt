package com.example.cp3.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cp3.R
import com.example.cp3.element.Pokemon

class PokemonAdapter(
    private val pokemonList: List<Pokemon>,
    private val onEditClickListener: (Pokemon) -> Unit,
    private val onDeleteClickListener: (Pokemon) -> Unit
) : RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNome: TextView = itemView.findViewById(R.id.nomeTextView) // Use os IDs do seu layout
        val tvTipo: TextView = itemView.findViewById(R.id.tipoTextView)
        val btnExcluir: Button = itemView.findViewById(R.id.btnDelete) // Use os IDs do seu layout
        val btnEditar: Button = itemView.findViewById(R.id.btnEdit) // Use os IDs do seu layout
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.pokemon_item, parent, false)
        return PokemonViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val currentPokemon = pokemonList[position]

        holder.tvNome.text = currentPokemon.nome
        holder.tvTipo.text = currentPokemon.tipo

        // Configurar o listener de clique para o botão Excluir
        holder.btnExcluir.setOnClickListener {
            onDeleteClickListener.invoke(currentPokemon)
        }

        // Configurar o listener de clique para o botão Editar
        holder.btnEditar.setOnClickListener {
            onEditClickListener.invoke(currentPokemon)
        }
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }

    // Função para atualizar a lista de Pokémons
    fun updatePokemonList(newPokemonList: List<Pokemon>) {
        // Crie uma cópia mutável da lista
        val mutablePokemonList = pokemonList.toMutableList()
        mutablePokemonList.clear()
        mutablePokemonList.addAll(newPokemonList)
        notifyDataSetChanged()
    }
}