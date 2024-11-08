package com.example.cp3.util

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cp3.R

class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val nomeTextView: TextView = itemView.findViewById(R.id.nomeTextView)
    val tipoTextView: TextView = itemView.findViewById(R.id.tipoTextView)
    val habilidadeTextView: TextView = itemView.findViewById(R.id.habilidadeTextView)
    val fraquezaTextView: TextView = itemView.findViewById(R.id.fraquezaTextView)
    val hpTextView: TextView = itemView.findViewById(R.id.hpTextView)
    val btnEdit: Button = itemView.findViewById(R.id.btnEdit) // Botão para editar
    val btnDelete: Button = itemView.findViewById(R.id.btnDelete) // Botão para excluir
}

