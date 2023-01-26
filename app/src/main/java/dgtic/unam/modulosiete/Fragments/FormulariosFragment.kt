package dgtic.unam.modulosiete.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.android.material.tabs.TabLayout
import dgtic.unam.modulosiete.BD.AppDatabase
import dgtic.unam.modulosiete.BD.Formulario
import dgtic.unam.modulosiete.BD.Recorrido
import dgtic.unam.modulosiete.Holder.AdapterFormularios
import dgtic.unam.modulosiete.Holder.AdapterViewHolder
import dgtic.unam.modulosiete.MainActivity
import dgtic.unam.modulosiete.R
import dgtic.unam.modulosiete.Views.FormularioActivity
import dgtic.unam.modulosiete.databinding.FragmentFormulariosBinding
import dgtic.unam.modulosiete.databinding.FragmentMisCaminatasBinding

class FormulariosFragment : Fragment(R.layout.fragment_formularios) {

    private var _binding: FragmentFormulariosBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView
    private var adapter: AdapterFormularios?=null
    private var listaFormularios=ArrayList<Formulario>()
    private val REQUEST_CODE = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFormulariosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        renderFormularios()

        val floating = binding.addFormulario
        floating.setOnClickListener {
            val newFormIntent = Intent(requireContext(), FormularioActivity::class.java).apply {
                putExtra("id_formulario", "")
            }
            newFormIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(newFormIntent)
        }
    }


    fun renderFormularios(){

        adapter = AdapterFormularios(requireActivity(),this)
        val recyclerView = view?.findViewById<RecyclerView>(R.id.recycler_view)
        if (recyclerView != null) {
            recyclerView.layoutManager = LinearLayoutManager(context)
        }
        if (recyclerView != null) {
            recyclerView.adapter = adapter
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val delete = data?.extras?.getInt("delete")
        val id = data?.extras?.getString("id")
        val position = data?.extras?.getInt("position")

        if(delete == 1){
            if (position != null) {
                adapter?.notifyItemRemoved(position)
            }
            val db = Room.databaseBuilder(
                requireActivity(),
                AppDatabase::class.java,
                "formularios"
            ).allowMainThreadQueries().build()

            val formulario_delete = db.formularioDao().getById(id)
            db.formularioDao().delete(formulario_delete)
        }
    }
}