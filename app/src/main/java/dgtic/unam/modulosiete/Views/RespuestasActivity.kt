package dgtic.unam.modulosiete.Views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dgtic.unam.modulosiete.R
import dgtic.unam.modulosiete.WEB.Empleado.AdapterEmpleado
import dgtic.unam.modulosiete.WEB.Empleado.AdapterRespuesta
import dgtic.unam.modulosiete.WEB.Empleado.Empleado
import dgtic.unam.modulosiete.WEB.Respuesta.Respuesta
import dgtic.unam.modulosiete.WEB.ServiceAPI
import dgtic.unam.modulosiete.databinding.ActivityRecorridoDesBinding
import dgtic.unam.modulosiete.databinding.ActivityRespuestasBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class RespuestasActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityRespuestasBinding

    private lateinit var recyclerView: RecyclerView
    private var adapter: AdapterRespuesta?=null
    private var listaRespuestas=ArrayList<Respuesta>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRespuestasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        search()
    }

    private fun initReclyView() {
        adapter = AdapterRespuesta(this,listaRespuestas)
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        if (recyclerView != null) {
            recyclerView.layoutManager = LinearLayoutManager(this)
        }
        if (recyclerView != null) {
            recyclerView.adapter = adapter
        }
    }
    private fun getRedrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/breed/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    private fun search() {
        CoroutineScope(Dispatchers.IO).launch {
            val call=getRedrofit().create(ServiceAPI::class.java)
                .getRespuesta("https://my.api.mockaroo.com/respuestas/123/123.json?key=42d45cc0")
            runOnUiThread {
                if (call.isSuccessful) {
                    println("peticion exitosa 200")
                    if (call.body()?.size ?: 0 > 0){
                        listaRespuestas = call.body() as ArrayList<Respuesta>
                        initReclyView()
                    }
                } else {
                    msgError()
                }
            }
        }
    }
    private fun msgError() {
        Toast.makeText(this, "Error en Conexion", Toast.LENGTH_SHORT).show()
    }


}