package com.jose.basedatos.data

import androidx.room.*
import com.jose.basedatos.data.Usuario

@Dao
interface   UsuarioDao {
    @Query("SELECT * FROM usuario")
    suspend fun getAll(): List<Usuario>

    @Query("SELECT * FROM usuario WHERE uid IN (:userIds)")
    suspend fun loadAllByIds(userIds: IntArray): List<Usuario>

    @Query("SELECT * FROM usuario WHERE first_name  LIKE :first AND " + "first_name  LIKE :last LIMIT 1")
   suspend fun findByName(first: String, last: String): Usuario

    @Insert
   suspend fun insertAll(usuario: Usuario)

    @Query("update usuario set first_name =:name, `last_name ` =:apellido where uid=:id")
    suspend fun actualizarUsuarios(name:String,apellido:String,id:Int)

    //@Delete
    //suspend fun delete(user: kotlin.Int)

    @Query("delete from usuario where uid=:id")
    suspend fun eliminar(id: Int)

    //@Query("delate from ")
}