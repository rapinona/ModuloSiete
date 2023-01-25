package dgtic.unam.modulosiete.Holder

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import dgtic.unam.modulosiete.BD.AppDatabase
import dgtic.unam.modulosiete.BD.Formulario
import dgtic.unam.modulosiete.BD.Recorrido
import dgtic.unam.modulosiete.Dialog.MyDialog
import dgtic.unam.modulosiete.R
import dgtic.unam.modulosiete.Views.FormularioActivity

@Suppress("DEPRECATION")
class AdapterFormularios(private val context: Context, private val fragment: Fragment) :
    RecyclerView.Adapter<AdapterFormularios.ViewHolder>() {

    private var data=ArrayList<Formulario>()
    private val REQUEST_CODE = 1

    private val db = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "formularios"
    ).allowMainThreadQueries().build()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name:TextView
        var delete : Button
        init {
            name=view.findViewById(R.id.nombre_formulario)
            delete = view.findViewById(R.id.delete_formulario)
        }
    }

    //pasara el layout a viewholder para el pintado
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val li=LayoutInflater.from(parent.context)
        val v=li.inflate(R.layout.formulario_lista,parent,false)
        return ViewHolder(v)
    }
    //este realizara el pinta de cada elemento
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val info=data[position]
        holder.name.text=info.formulario
        holder.delete.setOnClickListener{
            val dialog = MyDialog("Eliminar","¿Estas seguro de eliminar el recorrido?.",context,data[position].id,position)
            dialog.setTargetFragment(fragment, REQUEST_CODE)
            dialog.show(fragment.parentFragmentManager,"MyDialog")
        }
        holder.itemView.setOnClickListener{
            val id = data[position].id.toString()
            val newFormIntent = Intent(context, FormularioActivity::class.java).apply {
                putExtra("id_formulario", id)
            }
            newFormIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            context.startActivity(newFormIntent)
        }
    }
    override fun getItemCount(): Int {
        data = db.formularioDao().getAll() as ArrayList<Formulario>
        return data.size
    }


}