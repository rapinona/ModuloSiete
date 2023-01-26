package dgtic.unam.modulosiete.Holder

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import dgtic.unam.modulosiete.BD.AppDatabase
import dgtic.unam.modulosiete.BD.Recorrido
import dgtic.unam.modulosiete.Dialog.MyDialog
import dgtic.unam.modulosiete.R
import dgtic.unam.modulosiete.Views.FormularioActivity
import dgtic.unam.modulosiete.Views.RecorridoDesActivity

@Suppress("DEPRECATION")
class AdapterViewHolder(private val context: Context, private val status:Int, private val fragment: Fragment) :
    RecyclerView.Adapter<AdapterViewHolder.ViewHolder>() {

    private var data=ArrayList<Recorrido>()
    private val REQUEST_CODE = 1

    private val db = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "recorridos"
    ).allowMainThreadQueries().build()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name:TextView
        var fechaIncio:TextView
        var fechaFin:TextView
        var delete : Button
        init {
            name=view.findViewById(R.id.nombre_recorrido)
            fechaIncio=view.findViewById(R.id.fecha_inicio_recorrido)
            fechaFin=view.findViewById(R.id.fecha_fin_recorrido)
            delete = view.findViewById(R.id.delete)
        }
    }

    //pasara el layout a viewholder para el pintado
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val li=LayoutInflater.from(parent.context)
        val v=li.inflate(R.layout.recorrido_lista,parent,false)
        return ViewHolder(v)
    }
    //este realizara el pinta de cada elemento
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val info=data[position]
        holder.name.text=info.name
        holder.fechaIncio.text= "Fecha Inicio : " + info.fechaInicio
        holder.fechaFin.text="Fecha Fin : "+info.fechaFin
        holder.delete.setOnClickListener{
            if(data.size > 0){
                val dialog = MyDialog("Eliminar","¿Estas seguro de eliminar el recorrido?.",context,data[holder.adapterPosition].id,holder.adapterPosition)
                dialog.setTargetFragment(fragment, REQUEST_CODE)
                dialog.show(fragment.parentFragmentManager,"MyDialog")
            }
        }
        holder.itemView.setOnClickListener{
            val recorrido = data[position].name
            val fecha_inicio = data[position].fechaInicio
            val fecha_fin = data[position].fechaFin
            val id = data[position].id
            val status = data[position].status
            val newFormIntent = Intent(context, RecorridoDesActivity::class.java).apply {
                putExtra("id", id)
                putExtra("nombre", recorrido)
                putExtra("fecha_inicio", fecha_inicio)
                putExtra("fecha_fin", fecha_fin)
                putExtra("status", status)
            }
            newFormIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            context.startActivity(newFormIntent)
        }
    }

    override fun getItemCount(): Int {
        data = db.recorridoDao().getAll() as ArrayList<Recorrido>
        data = data.filter { it.status == status } as ArrayList<Recorrido>
        return data.size
    }


}