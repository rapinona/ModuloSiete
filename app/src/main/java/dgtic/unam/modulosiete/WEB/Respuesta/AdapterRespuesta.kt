package dgtic.unam.modulosiete.WEB.Empleado

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import dgtic.unam.modulosiete.BD.Pregunta
import dgtic.unam.modulosiete.R
import dgtic.unam.modulosiete.Views.RespuestaDetailActivity
import dgtic.unam.modulosiete.Views.RespuestasActivity
import dgtic.unam.modulosiete.WEB.Respuesta.Respuesta

class AdapterRespuesta(private val context: Context, private val data:List<Respuesta>) :
    RecyclerView.Adapter<AdapterRespuesta.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var pregunta: TextView
        var rojo : TextView
        var verde : TextView
        init {
            pregunta=view.findViewById(R.id.pregunta)
            rojo = view.findViewById(R.id.borde_rojo)
            verde = view.findViewById(R.id.borde_verde)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val li=LayoutInflater.from(parent.context)
        val v=li.inflate(R.layout.respuestas_lista,parent,false)
        return ViewHolder(v)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val respuesta : Respuesta = data[position]
        holder.pregunta.text = respuesta.pregunta
        if(respuesta.cumple){
            holder.rojo.isVisible = false
        }else{
            holder.verde.isVisible = false
        }

        holder.itemView.setOnClickListener{
            val newFormIntent = Intent(context, RespuestaDetailActivity::class.java).apply {
                putExtra("id", respuesta.id_recorrido)
                putExtra("pregunta", respuesta.pregunta)
                putExtra("observaciones", respuesta.Observaciones)
                putExtra("categoria", respuesta.categoria)
                putExtra("cumple", respuesta.cumple)
            }
            newFormIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            context.startActivity(newFormIntent)

        }
    }
    override fun getItemCount(): Int {
        return data.size
    }
}