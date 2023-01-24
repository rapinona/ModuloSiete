package dgtic.unam.modulosiete.Holder

import android.content.Context
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
            val dialog = MyDialog("Eliminar","¿Estas seguro de eliminar el recorrido?.",context,data[position].id,position)
            dialog.setTargetFragment(fragment, REQUEST_CODE)
            dialog.show(fragment.parentFragmentManager,"MyDialog")
        }
    }
    override fun getItemCount(): Int {
        data = db.recorridoDao().getAll() as ArrayList<Recorrido>
        data = data.filter { it.status == status } as ArrayList<Recorrido>
        return data.size
    }


}