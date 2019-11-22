package com.example.tutoriastec

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class adminBD(context: Context):SQLiteOpenHelper(context, DATABASE ,null,1) {

    companion object{
        val DATABASE="users"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL( "CREATE TABLE users(" +
                "nocontrol text primary key," +
                "nip text)")
    }
    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    fun Ejecuta(sentencia : String):Int{
        try {
            val db = this.writableDatabase// se abre la base de datos en modo de lectura y escritura
            db.execSQL(sentencia)
            db.close()
            return 1 //regresa 1 si la sentencia es exitosa
        }catch (ex:Exception){
            return 0
        }
    }

    fun Consulta(select :String): Cursor?{
        try {
            val db = this.readableDatabase // abre solo en modo lectura no permite escribir
            return db.rawQuery(select,null)
        }catch(ex:Exception) {
            return null
        }
    }
}