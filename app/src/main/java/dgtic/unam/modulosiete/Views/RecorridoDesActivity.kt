package dgtic.unam.modulosiete.Views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dgtic.unam.modulosiete.R
import dgtic.unam.modulosiete.WEB.Empleado.AdapterEmpleado
import dgtic.unam.modulosiete.WEB.Empleado.Empleado
import dgtic.unam.modulosiete.WEB.ServiceAPI
import dgtic.unam.modulosiete.databinding.ActivityRecorridoDesBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class RecorridoDesActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityRecorridoDesBinding

    private lateinit var recyclerView: RecyclerView
    private var adapter: AdapterEmpleado?=null
    private var listaEmpleados=ArrayList<Empleado>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRecorridoDesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val nombre = intent.getStringExtra("nombre")
        val fecha_inicio = intent.getStringExtra("fecha_inicio")
        val fecha_fin = intent.getStringExtra("fecha_fin")

        val nombreRecorrido = binding.nombreCamimnata
        val fechaInicio = binding.fechaInicioLbl
        val fechaFin = binding.fechaFinLbl

        nombreRecorrido.text = nombre
        fechaInicio.text = "Fecha Inicio : " + fecha_inicio
        fechaFin.text = "Fecha Fin : " + fecha_fin

        search()
    }

    private fun initReclyView() {
        adapter = AdapterEmpleado(this,listaEmpleados)
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
                .getEmpleados("https://my.api.mockaroo.com/empleados.json?key=42d45cc0")
            runOnUiThread {
                if (call.isSuccessful) {
                    println("peticion exitosa 200")
                    if (call.body()?.size ?: 0 > 0){
                        listaEmpleados = call.body() as ArrayList<Empleado>
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