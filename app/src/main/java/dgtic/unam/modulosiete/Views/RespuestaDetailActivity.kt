package dgtic.unam.modulosiete.Views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
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
import dgtic.unam.modulosiete.databinding.ActivityRespuestaDetailBinding
import dgtic.unam.modulosiete.databinding.ActivityRespuestasBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class RespuestaDetailActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityRespuestaDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRespuestaDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val pregunta = intent.getStringExtra("pregunta")
        val categoria = intent.getStringExtra("categoria")
        val observaciones = intent.getStringExtra("observaciones")
        val id = intent.getStringExtra("id")
        val cumple = intent.getBooleanExtra("cumple",false)

        val pregunta_respuesta = binding.nombreCamimnata
        val categoria_respuesta = binding.categoria
        val observaciones_respuesta = binding.observaciones
        val cumple_respuesta = binding.cumple

        pregunta_respuesta.text = pregunta
        categoria_respuesta.text = categoria
        observaciones_respuesta.text = observaciones
        cumple_respuesta.isChecked = cumple

        var array = arrayOf<String>("Creada","En Curso","Completada")

        val adapter = ArrayAdapter(this, R.layout.spinner_item , array)
        val spinnerStatus = binding.status
        spinnerStatus.adapter = adapter
    }
}