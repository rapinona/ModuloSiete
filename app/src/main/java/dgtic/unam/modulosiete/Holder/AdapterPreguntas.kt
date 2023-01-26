package dgtic.unam.modulosiete.Holder

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import dgtic.unam.modulosiete.BD.AppDatabase
import dgtic.unam.modulosiete.BD.Pregunta
import dgtic.unam.modulosiete.R
import dgtic.unam.modulosiete.Views.FormularioActivity

@Suppress("DEPRECATION")
class AdapterPreguntas(private val context: Context,private val id_formulario : String) :
    RecyclerView.Adapter<AdapterPreguntas.ViewHolder>() {

    private var data=ArrayList<Pregunta>()
    private val REQUEST_CODE = 1

    private val db = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "preguntas"
    ).allowMainThreadQueries().build()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name:TextView
        var delete : Button
        init {
            name=view.findViewById(R.id.pregunta)
            delete = view.findViewById(R.id.delete_pregunta)
        }
    }

    //pasara el layout a viewholder para el pintado
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val li=LayoutInflater.from(parent.context)
        val v=li.inflate(R.layout.preguntas_lista,parent,false)
        return ViewHolder(v)
    }
    //este realizara el pinta de cada elemento
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val info=data[position]
        holder.name.text=info.pregunta
        holder.delete.setOnClickListener{
            val otherActivity = FormularioActivity()
            otherActivity.showDialogDelete(context,data[holder.adapterPosition].id,id_formulario,holder.adapterPosition,this)
        }
    }
    override fun getItemCount(): Int {
        data = db.preguntasDao().getAll(id_formulario) as ArrayList<Pregunta>
        return data.size
    }


}