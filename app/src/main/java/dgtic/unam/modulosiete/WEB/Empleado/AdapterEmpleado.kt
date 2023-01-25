package dgtic.unam.modulosiete.WEB.Empleado

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import dgtic.unam.modulosiete.R
import dgtic.unam.modulosiete.Views.FormularioActivity
import dgtic.unam.modulosiete.Views.RespuestasActivity

class AdapterEmpleado(private val context: Context, private val data:List<Empleado>) :
    RecyclerView.Adapter<AdapterEmpleado.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name: TextView
        init {
            name=view.findViewById(R.id.nombre_empleado)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val li=LayoutInflater.from(parent.context)
        val v=li.inflate(R.layout.empleado_lista,parent,false)
        return ViewHolder(v)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val empleado : Empleado = data[position]
        holder.name.text = empleado.nombre + " " + empleado.apellido

        holder.itemView.setOnClickListener{
            val newFormIntent = Intent(context, RespuestasActivity::class.java).apply {
            }
            newFormIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            context.startActivity(newFormIntent)
        }
    }
    override fun getItemCount(): Int {
        return data.size
    }
}