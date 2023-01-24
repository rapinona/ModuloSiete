package dgtic.unam.modulosiete.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.android.material.tabs.TabLayout
import dgtic.unam.modulosiete.BD.AppDatabase
import dgtic.unam.modulosiete.BD.Recorrido
import dgtic.unam.modulosiete.Holder.AdapterViewHolder
import dgtic.unam.modulosiete.R
import dgtic.unam.modulosiete.databinding.FragmentMisCaminatasBinding

class MisCaminatasFragment : Fragment(R.layout.fragment_mis_caminatas) {

    private var _binding: FragmentMisCaminatasBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView
    private var adapter: AdapterViewHolder?=null
    private var listaRecorridos=ArrayList<Recorrido>()
    private val REQUEST_CODE = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMisCaminatasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        renderRecorridos(0)

        val tabLayout = binding.tabLayout
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> {
                        renderRecorridos(0)
                    }
                    1 -> {
                        renderRecorridos(1)
                    }
                    2 -> {
                        renderRecorridos(2)
                    }
                    else -> {
                        // code to be executed if none of the above conditions are met
                    }
                }

            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        if(adapter==null) run {
            renderRecorridos(0)
        }
    }


    fun renderRecorridos(status:Int){

        adapter = AdapterViewHolder(requireActivity(), status,this)
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
            val db = Room.databaseBuilder(
                requireActivity(),
                AppDatabase::class.java,
                "recorridos"
            ).allowMainThreadQueries().build()

            val recorrido_delete = db.recorridoDao().getById(id)
            db.recorridoDao().delete(recorrido_delete)
            if (position != null) {
                adapter?.notifyItemRemoved(position)
            }
        }
    }
}