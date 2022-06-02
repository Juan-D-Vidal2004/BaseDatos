package com.jose.basedatos.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Usuario::class], version = 1)
abstract class  DbPrueba : RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDao
    companion object{
    var INSTANCE: DbPrueba?=null
    fun getInstance(context: Context): DbPrueba
    {
        if(INSTANCE ==null)
        {
            INSTANCE =Room.databaseBuilder(
                          context.applicationContext,
                          DbPrueba::class.java,
                    "usuario.db").build()

        }

        return INSTANCE!!
    }
    }
}