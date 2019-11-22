package com.example.tutoriastec

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main_login.*

class MainActivity : AppCompatActivity() {

    var control: String = ""
    var nip: String = ""


    companion object {
        val EXTRA_CONTROL = "extra_control"
        val EXTRA_NIP = "extra_nip"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recibe = intent
        if (recibe != null && recibe.hasExtra(EXTRA_CONTROL) && recibe.hasExtra(EXTRA_NIP)) {

            recibe.getStringExtra(EXTRA_CONTROL)
            recibe.getStringExtra(EXTRA_NIP)

            Toast.makeText(this, " $control", Toast.LENGTH_SHORT).show()
        } else {
            val admin = adminBD(this)
            val tupla = admin.Consulta("SELECT nocontrol, nip FROM users")
            if (tupla!!.moveToFirst()) {
                control = tupla.getString((0))
                nip = tupla.getString((1))
                Toast.makeText(this, "OK: $control", Toast.LENGTH_SHORT).show()
            } else {
                val registro = Intent(this, MainLogin::class.java)
                startActivity(registro)
            }
        }

    }

    fun enviarr(v: View) {
        Toast.makeText(this, "Informacion Enviada, generando PDF", Toast.LENGTH_LONG).show()
    }
}

