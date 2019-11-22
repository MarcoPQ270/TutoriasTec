package com.example.tutoriastec

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.itextpdf.text.Document
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main_login.*
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private val STORAGE_CODE: Int = 100;

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

        if (edtnombre.text!!.isEmpty() || edtgradoygrupo.text!!.isEmpty() || edtfecha.text!!.isEmpty()||edtlugar.text!!.isEmpty()
            || edtsugerencia.text!!.isEmpty()||edttemas.text!!.isEmpty()|| edtcompromisos.text!!.isEmpty()|| edtobser.text!!.isEmpty()){
         Toast.makeText(this,"Existen campos vacios",Toast.LENGTH_LONG).show()
        }else {

            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED
                ) {
                    val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    requestPermissions(permissions, STORAGE_CODE)
                } else {
                    savePdf()
                }
            } else {
                savePdf()
            }
        }
    }

    private fun savePdf() {

        val mDoc = Document()
        val mFileName = SimpleDateFormat(
            "yyyyMMdd_HHmmss",
            Locale.getDefault()
        ).format(System.currentTimeMillis())
        val mFilePath =
            Environment.getExternalStorageDirectory().toString() + "/" + "Reporte"+mFileName + ".pdf"
        try {
            PdfWriter.getInstance(mDoc, FileOutputStream(mFilePath))
            mDoc.open()

            val titulo ="Reporte de Tutorias"

            val nom = edtnombre.text.toString()
            val sem = edtgradoygrupo.text.toString()
            val fech = edtfecha.text.toString()
            val lug = edtlugar.text.toString()
            val suge= edtsugerencia.text.toString()
            val tem=edttemas.text.toString()
            val com=edtcompromisos.text.toString()
            val obser=edtobser.text.toString()

            //add
            mDoc.addAuthor("MARCO PQ")

            //add paragraph to the document
            mDoc.add(Paragraph(titulo))
            mDoc.add(Paragraph(nom))
            mDoc.add(Paragraph(sem))
            mDoc.add(Paragraph(fech))
            mDoc.add(Paragraph(lug))
            mDoc.add(Paragraph(suge))
            mDoc.add(Paragraph(tem))
            mDoc.add(Paragraph(com))
            mDoc.add(Paragraph(obser))

            //close document
            mDoc.close()

            //show file saved message with file name and path
            Toast.makeText(this, "$mFileName.pdf\n Guardado en \n$mFilePath", Toast.LENGTH_SHORT)
                .show()
        } catch (e: Exception) {
            //if anything goes wrong causing exception, get and show exception message
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            STORAGE_CODE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //permission from popup was granted, call savePdf() method
                    savePdf()
                } else {
                    //permission from popup was denied, show error message
                    Toast.makeText(this, "Permiso denegado", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}

