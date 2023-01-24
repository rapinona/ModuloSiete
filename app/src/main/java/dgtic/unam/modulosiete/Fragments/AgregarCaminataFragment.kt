package dgtic.unam.modulosiete.Fragments

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.room.Room
import dgtic.unam.modulosiete.BD.AppDatabase
import dgtic.unam.modulosiete.BD.Recorrido
import dgtic.unam.modulosiete.R
import dgtic.unam.modulosiete.databinding.FragmentAgregarCaminataBinding
import dgtic.unam.modulosiete.databinding.FragmentMisCaminatasBinding
import java.util.*

class AgregarCaminataFragment : Fragment() {

    private var _binding: FragmentAgregarCaminataBinding? = null
    private val binding get() = _binding!!
    var calendar = Calendar.getInstance()
    var year = calendar.get(Calendar.YEAR)
    var month = calendar.get(Calendar.MONTH)
    var day = calendar.get(Calendar.DAY_OF_MONTH)

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
            name.text.toString(),
            0,
            fechaInicio.text.toString(),
            fechaFin.text.toString())
        db.recorridoDao().insert(recorrido)
        Toast.makeText(requireActivity(),"Agregado con exito",Toast.LENGTH_SHORT).show()
        cancelar()
    }
    fun cancelar() {
        val fechaInicio = binding.fechaInicio
        fechaInicio.text = "$day/${month+1}/$year"
        val fechaFin = binding.fechaFin
        fechaFin.text = "$day/${month+1}/$year"

        val recorrdio = binding.recorrido
        recorrdio.setText("")
    }


}