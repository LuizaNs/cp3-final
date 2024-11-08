package com.example.cp3.activity

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cp3.R
import com.google.firebase.storage.FirebaseStorage
import com.example.cp3.adapter.ImageAdapter

class ExibirImagensActivity : AppCompatActivity() {

    private val armazenamento: FirebaseStorage by lazy { FirebaseStorage.getInstance() }
    private val imageUrls: MutableList<String> = mutableListOf()
    private lateinit var recyclerView: RecyclerView
    private lateinit var btnVoltar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exibir_imagens)

        recyclerView = findViewById(R.id.recyclerViewImages)
        btnVoltar = findViewById(R.id.btnVoltar)

        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerView.adapter = ImageAdapter(imageUrls)

        btnVoltar.setOnClickListener {
            onBackPressed()
        }

        exibirImagens()
    }

    private fun exibirImagens() {
        val storageRef = armazenamento.reference.child("fotos")

        storageRef.listAll()
            .addOnSuccessListener { result ->
                val items = result.items

                if (items.isEmpty()) {
                    Toast.makeText(this, "Nenhuma imagem encontrada.", Toast.LENGTH_SHORT).show()
                    return@addOnSuccessListener // Sai da função se não houver imagens
                }


                var urlsBaixadas = 0
                val totalUrls = items.size


                for (item in items) {
                    item.downloadUrl.addOnSuccessListener { uri ->
                        imageUrls.add(uri.toString())
                        urlsBaixadas++

                        if (urlsBaixadas == totalUrls) {

                            recyclerView.post {
                                recyclerView.adapter?.notifyDataSetChanged()
                            }
                        }
                    }.addOnFailureListener { exception ->

                        Toast.makeText(this, "Erro ao baixar URL: ${exception.message}", Toast.LENGTH_SHORT).show()

                        urlsBaixadas++
                        if (urlsBaixadas == totalUrls) {
                            recyclerView.post {
                                recyclerView.adapter?.notifyDataSetChanged()
                            }
                        }
                    }
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Erro ao listar imagens: ${exception.message}", Toast.LENGTH_SHORT).show()

            }
    }
}
