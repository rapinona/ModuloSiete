package dgtic.unam.modulosiete.Fragments

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.room.Room
import dgtic.unam.modulosiete.BD.AppDatabase
import dgtic.unam.modulosiete.BD.Formulario
import dgtic.unam.modulosiete.BD.Recorrido
import dgtic.unam.modulosiete.R
import dgtic.unam.modulosiete.WEB.Empleado.Empleado
import dgtic.unam.modulosiete.WEB.Respuesta.Puesto
import dgtic.unam.modulosiete.WEB.Respuesta.Tienda
import dgtic.unam.modulosiete.WEB.ServiceAPI
import dgtic.unam.modulosiete.databinding.FragmentAgregarCaminataBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class AgregarCaminataFragment : Fragment() {

    private var _binding: FragmentAgregarCaminataBinding? = null
    private val binding get() = _binding!!
    private var data=ArrayList<Formulario>()
    lateinit var spinnerRecorrdo : Spinner
    var calendar = Calendar.getInstance()
    var year = calendar.get(Calendar.YEAR)
    var month = calendar.get(Calendar.MONTH)
    var day = calendar.get(Calendar.DAY_OF_MONTH)

    private var listaTienda=ArrayList<Tienda>()
    private var listaPuesto=ArrayList<Puesto>()
    private var listaColaborador=ArrayList<Empleado>()

    lateinit var spinnerTienda : Spinner
    lateinit var spinnerPuesto : Spinner
    lateinit var spinnerColaborador : Spinner

    var tiendaLoaded = false
    var puestoLoaded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAgregarCaminataBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()


        val db = Room.databaseBuilder(
                requireActivity(),
                AppDatabase::class.java,
                "formularios"
            ).allowMainThreadQueries().build()

        data = db.formularioDao().getAll() as ArrayList<Formulario>
        var array = arrayOf<String>()

        for (recorrido in data) {
            array += recorrido.formulario
        }

        val adapter = ArrayAdapter(requireActivity(), R.layout.spinner_item , array)
        spinnerRecorrdo = binding.recorrido
        spinnerRecorrdo.adapter = adapter

        spinnerTienda = binding.tienda
        spinnerPuesto = binding.puesto
        spinnerColaborador = binding.colaborador

        searchTienda()

        spinnerTienda.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                if(tiendaLoaded){
                    cleanPuesto()
                    cleanColab()
                    searchPuesto()
                }else{
                    tiendaLoaded = true
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        spinnerPuesto.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                if(puestoLoaded){
                    searchColaborador()
                    cleanColab()
                }else{
                    puestoLoaded = true
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                // Handle nothing selected
            }
        }

        val cancelButton = binding.cancelar
        cancelButton.setOnClickListener {
            cancelar()
        }
        val saveButton = binding.guardar
        saveButton.setOnClickListener{
            guardar()
        }

        val fechaInicio = binding.fechaInicio
        fechaInicio.text = "$day/${month+1}/$year"
        fechaInicio.setOnClickListener {
            val datePicker = DatePickerDialog(requireActivity(), { _, year, month, day ->
                fechaInicio.text = "$day/${month+1}/$year"
            }, year, month, day)
            datePicker.show()
        }

        val fechaFin = binding.fechaFin
        fechaFin.text = "$day/${month+1}/$year"
        fechaFin.setOnClickListener {
            val datePicker = DatePickerDialog(requireActivity(), { _, year, month, day ->
                fechaFin.text = "$day/${month+1}/$year"
            }, year, month, day)
            datePicker.show()
        }
    }

    fun guardar() {

        if(spinnerColaborador.selectedItem != null){
            val db = Room.databaseBuilder(
                requireActivity(),
                AppDatabase::class.java,
                "recorridos"
            ).allowMainThreadQueries().build()
            val name = binding.recorrido
            val fechaInicio = binding.fechaInicio
            val fechaFin = binding.fechaFin
            val recorrido = Recorrido(
                UUID.randomUUID().toString(),
                spinnerRecorrdo.selectedItem.toString(),
                0,
                fechaInicio.text.toString(),
                fechaFin.text.toString())
            db.recorridoDao().insert(recorrido)
            Toast.makeText(requireActivity(),"Agregado con exito",Toast.LENGTH_SHORT).show()
            cancelar()
        }else{
            Toast.makeText(requireActivity(),"Faltan campos",Toast.LENGTH_SHORT).show()
        }
    }
    fun cancelar() {
        val fechaInicio = binding.fechaInicio
        fechaInicio.text = "$day/${month+1}/$year"
        val fechaFin = binding.fechaFin
        fechaFin.text = "$day/${month+1}/$year"
        cleanPuesto()
        cleanColab()

        val recorrdio = binding.recorrido
    }

    private fun getRedrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/breed/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    private fun searchTienda() {
        CoroutineScope(Dispatchers.IO).launch {
            val call=getRedrofit().create(ServiceAPI::class.java)
                .getTiendas("https://my.api.mockaroo.com/tiendas.json?key=42d45cc0")

            requireActivity().runOnUiThread {
                if (call.isSuccessful) {
                    println("peticion exitosa 200")
                    if (call.body()?.size ?: 0 > 0){
                        listaTienda = call.body() as ArrayList<Tienda>

                        spinnerTienda = binding.tienda

                        var array = arrayOf<String>()
                        for (tienda in listaTienda) {
                            array += tienda.tienda
                        }

                        val adapter = ArrayAdapter(requireActivity(), R.layout.spinner_item , array)
                        spinnerTienda.adapter = adapter
                    }
                }
            }
        }
    }

    private fun searchPuesto() {
        CoroutineScope(Dispatchers.IO).launch {
            val call=getRedrofit().create(ServiceAPI::class.java)
                .getPuestos("https://my.api.mockaroo.com/puestos.json?key=42d45cc0")

            requireActivity().runOnUiThread {
                if (call.isSuccessful) {
                    println("peticion exitosa 200")
                    if (call.body()?.size ?: 0 > 0){
                        listaPuesto = call.body() as ArrayList<Puesto>

                        spinnerPuesto = binding.puesto

                        var array = arrayOf<String>()
                        for (puesto in listaPuesto) {
                            array += puesto.puesto
                        }

                        val adapter = ArrayAdapter(requireActivity(), R.layout.spinner_item , array)
                        spinnerPuesto.adapter = adapter
                    }
                }
            }
        }
    }

    private fun searchColaborador() {
        CoroutineScope(Dispatchers.IO).launch {
            val call=getRedrofit().create(ServiceAPI::class.java)
                .getEmpleados("https://my.api.mockaroo.com/empleados.json?key=42d45cc0")

            requireActivity().runOnUiThread {
                if (call.isSuccessful) {
                    println("peticion exitosa 200")
                    if (call.body()?.size ?: 0 > 0){
                        listaColaborador = call.body() as ArrayList<Empleado>

                        spinnerColaborador = binding.colaborador

                        var array = arrayOf<String>()
                        for (colaborador in listaColaborador) {
                            array += colaborador.nombre
                        }

                        val adapter = ArrayAdapter(requireActivity(), R.layout.spinner_item , array)
                        spinnerColaborador.adapter = adapter
                    }
                }
            }
        }
    }
    fun cleanPuesto(){
        puestoLoaded = false
        var array = arrayOf<String>()
        val adapter = ArrayAdapter(requireActivity(), R.layout.spinner_item , array)
        spinnerPuesto.adapter = adapter
    }

    fun cleanColab(){
        var array = arrayOf<String>()
        val adapter = ArrayAdapter(requireActivity(), R.layout.spinner_item , array)
        spinnerColaborador.adapter = adapter
    }

}