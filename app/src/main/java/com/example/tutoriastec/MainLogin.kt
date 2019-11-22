package com.example.tutoriastec

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main_login.*

class MainLogin : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_login)

        btnIniciar.setOnClickListener { view ->
            if (edtcontrolregistro.text!!.isEmpty() || edtnip.text!!.isEmpty()) {
                Toast.makeText(this, "Existen campos vacios", Toast.LENGTH_SHORT).show();
                edtcontrolregistro.requestFocus()
            } else {
                val control = edtcontrolregistro.text.toString()
                val nip = edtnip.text.toString()

                val sentencia = "INSERT INTO users(nocontrol,nip) VALUES('$control','$nip')"
                val admin = adminBD(this)
                if (admin.Ejecuta(sentencia) == 1) {
                    Toast.makeText(this, "Usuario Agregado con Exito", Toast.LENGTH_SHORT).show();
                    val lista = Intent(this, MainActivity::class.java)
                    lista.putExtra(MainActivity.EXTRA_CONTROL, control)
                    lista.putExtra(MainActivity.EXTRA_NIP, nip)
                    startActivity(lista)
                } else {
                    Toast.makeText(this, "ERROR: No se pudo registrar", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }
}