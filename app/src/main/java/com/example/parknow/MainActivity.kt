package com.example.parknow

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var btnLogout: Button
    private lateinit var btnAbrirBarrera: Button
    private lateinit var tvWelcome: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        tvWelcome = findViewById(R.id.tvWelcome)
        btnLogout = findViewById(R.id.btnLogout)
        btnAbrirBarrera = findViewById(R.id.btnAbrirBarrera)

        val currentUser = auth.currentUser
        if (currentUser != null) {
            tvWelcome.text = "Conductor:\n${currentUser.email}\n\n¡Tu espacio está reservado!\nLa barrera se levantará automáticamente al llegar."
        } else {
            irAlLogin()
        }

        btnLogout.setOnClickListener {
            auth.signOut()
            irAlLogin()
        }

        btnAbrirBarrera.setOnClickListener {
            enviarComandoMqtt("ABRIR_BARRERA")
        }

        conectarBrokerMqtt()
    }

    private fun irAlLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun conectarBrokerMqtt() {
    }

    private fun enviarComandoMqtt(comando: String) {
        Toast.makeText(this, "Instrucción enviada al ESP32: $comando", Toast.LENGTH_SHORT).show()
    }
}