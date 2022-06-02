package com.jose.basedatos

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.jose.basedatos.data.DbPrueba
import com.jose.basedatos.data.Usuario
import com.jose.basedatos.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity(), View.OnClickListener {
  private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.adicionar.setOnClickListener(this)
        binding.btnMostrar.setOnClickListener(this)
        binding.borrar.setOnClickListener(this)
        binding.actualizar.setOnClickListener(this)

       /* val db = Room.databaseBuilder(
            this,
            DbPrueba::class.java, "usuario"
        ).build()*/
       // val userDao = db.userDao()

        //mejorada
       /* val usuarioinfo = Usuario(null,"JOSE","CUELLAR")
        GlobalScope.launch {
            DbPrueba.getInstance(this@MainActivity).userDao().insertAll(usuarioinfo)
            //userDao.insertAll(Usuario(null,"jose","cuellar"))

            var usuarios = DbPrueba.getInstance(this@MainActivity).userDao().getAll()

            for (item in usuarios){
                println("${item.uid},${item.firstName},${item.lastName}")
            }

        }*/

    }

    override fun onClick(v: View?) {
        when (v?.id){
            R.id.adicionar->{
                var nom:String?=null
                var ape:String?=null
                if(TextUtils.isEmpty(binding.nombre.text.toString())){
                    binding.nombre.error= "ingrese El nombre"
                    binding.nombre.requestFocus()
                    return
                }else{
                    nom = binding.nombre.text.toString()
                }
                if(TextUtils.isEmpty(binding.apellido.text.toString())){
                    binding.apellido.error= "ingrese el apellido"
                    binding.apellido.requestFocus()
                    return
                }else{
                    ape = binding.apellido.text.toString()
                }

                val usuarioinfo = Usuario(null,nom,ape)
                GlobalScope.launch {
                    DbPrueba.getInstance(this@MainActivity).usuarioDao().insertAll(usuarioinfo)
                }
            }//cierre boton adicionar


            R.id.btnMostrar -> {

                lateinit var alluser:List<Usuario>
                val userData:StringBuffer= StringBuffer()
                GlobalScope.launch(Dispatchers.IO) {
                    alluser = DbPrueba.getInstance(this@MainActivity).usuarioDao().getAll()

                    launch(Dispatchers.Main) {
                        alluser.forEach {
                            userData.append("id=" + it.uid + " Nombre " + it.firstName + " Apellido " + it.lastName + " \n")
                        }
                        mostrar(userData.toString())
                    }
                }
            }//cierre boton mostrar

            R.id.borrar -> {
                val userId:EditText = EditText(this@MainActivity)
                val alert = AlertDialog.Builder(this@MainActivity)
                            alert.setTitle("Ingrese el id a eliminar")
                            alert.setView(userId)
                alert.setPositiveButton("Borrar usuario",object :DialogInterface.OnClickListener{
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        val uid:Int = userId.text.toString().toInt()
                        GlobalScope.launch {
                            DbPrueba.getInstance(this@MainActivity).usuarioDao().eliminar(uid)
                        }
                        mostrarborrar("Informacion del usuario","Registro eliminado")
                    }
                })
                        alert.create()
                        alert.show()
            }
            R.id.actualizar -> {
                val name = binding.nombre.text.toString()
                val apellido = binding.apellido.text.toString()

                val userId:EditText = EditText(this@MainActivity)
                val alert = AlertDialog.Builder(this@MainActivity)
                alert.setTitle("Ingrese el id a actuaizar")
                alert.setView(userId)
                alert.setPositiveButton("Actualizar usuario",object :DialogInterface.OnClickListener{
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        val uid:Int = userId.text.toString().toInt()
                        GlobalScope.launch {
                            DbPrueba.getInstance(this@MainActivity).usuarioDao().actualizarUsuarios(name,apellido,uid)
                        }
                        mostrarborrar("Informacion del usuario","Registro actualizado")
                    }
                })
                alert.create()
                alert.show()
            }
        }
    }

            private fun mostrar(userData: String) {
            Toast.makeText(this@MainActivity, "${userData}", Toast.LENGTH_SHORT).show()
    }

        private fun mostrarborrar(titulo: String, mensaje: String){
            val alert = AlertDialog.Builder(this@MainActivity)
            alert.setTitle(titulo)
            alert.setMessage(mensaje)
            alert.setCancelable(true)
            alert.show()
    }
}