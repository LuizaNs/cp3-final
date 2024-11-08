package com.example.cp3.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.cp3.databinding.ActivityUploadImagemBinding
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso

class UploadImagemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUploadImagemBinding
    private val armazenamento: FirebaseStorage by lazy { FirebaseStorage.getInstance() }
    private var uriImagemSelecionada: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadImagemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
    }

    private fun setupListeners() {
        binding.BtnPegarImagem.setOnClickListener {
            pegarImagem()
        }

        binding.btnUpload.setOnClickListener {
            uploadGaleria()
        }


        binding.btnVerUploads.setOnClickListener {
            val intent = Intent(this, ExibirImagensActivity::class.java)
            startActivity(intent)
        }
    }

    private fun pegarImagem() {
        val intent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        launcher.launch(intent)
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            uriImagemSelecionada = data?.data
            Picasso.get().load(uriImagemSelecionada).into(binding.ImgSelecionada)
        } else {
            Toast.makeText(this, "Seleção de imagem cancelada", Toast.LENGTH_SHORT).show()
        }
    }

    private fun uploadGaleria() {
        val nomeArquivo = "${System.currentTimeMillis()}.jpg"

        if (uriImagemSelecionada != null) {
            val storageRef = armazenamento.reference.child("fotos").child(nomeArquivo)

            storageRef.putFile(uriImagemSelecionada!!)
                .addOnSuccessListener { task ->
                    Toast.makeText(this, "Sucesso ao fazer upload da imagem", Toast.LENGTH_SHORT).show()
                    task.metadata?.reference?.downloadUrl
                        ?.addOnSuccessListener { urlFirebase ->

                            Toast.makeText(this, "Imagem salva com sucesso! URL: $urlFirebase", Toast.LENGTH_LONG).show()
                        }
                        ?.addOnFailureListener {
                            Toast.makeText(this, "Erro ao obter URL da imagem", Toast.LENGTH_SHORT).show()
                        }
                }
                .addOnFailureListener { erro ->
                    Toast.makeText(this, "Erro ao fazer upload da imagem: ${erro.message}", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(this, "Nenhuma imagem foi selecionada", Toast.LENGTH_SHORT).show()
        }
    }
}
