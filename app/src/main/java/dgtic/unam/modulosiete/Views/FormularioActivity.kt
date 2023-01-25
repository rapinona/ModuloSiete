package dgtic.unam.modulosiete.Views

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import dgtic.unam.modulosiete.BD.AppDatabase
import dgtic.unam.modulosiete.BD.Formulario
import dgtic.unam.modulosiete.BD.Pregunta
import dgtic.unam.modulosiete.Holder.AdapterPreguntas
import dgtic.unam.modulosiete.R
import dgtic.unam.modulosiete.databinding.ActivityFormularioBinding
import java.util.*


public class FormularioActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityFormularioBinding
    private var id_formulario: String = ""
    private var id_recorrido = UUID.randomUUID().toString()

    private lateinit var recyclerView: RecyclerView
    private var adapter: AdapterPreguntas?=null
    private var listaPreguntas=ArrayList<Pregunta>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFormularioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        val addPregunta = binding.addPregunta

        id_formulario = id_recorrido
        renderPreguntas()

        val nombreFormulario = binding.nombreFormulario


        addPregunta.setOnClickListener{

            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setTitle("Nueva Pregunta")

            val input = EditText(this)

            input.inputType = InputType.TYPE_CLASS_TEXT
            input.hint = "Pregunta..."
            builder.setView(input)

            builder.setPositiveButton("Aceptar",
                DialogInterface.OnClickListener { dialog, which ->
                    val db = Room.databaseBuilder(
                        this,
                        AppDatabase::class.java,
                        "preguntas"
                    ).allowMainThreadQueries().build()

                    val id = UUID.randomUUID().toString()
                    val id_formulario = id_recorrido
                    val pregunta = input.text.toString()

                    val preguntaOb = Pregunta(
                        id,
                        id_formulario,
                        pregunta
                        )
                    db.preguntasDao().insert(preguntaOb)
                    renderPreguntas()
                })
            builder.setNegativeButton("Cancel",
                DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

            builder.show()
        }

        val saveButton = findViewById<Button>(R.id.save_button)
        saveButton.setOnClickListener{
            val db = Room.databaseBuilder(
                this,
                AppDatabase::class.java,
                "formularios"
            ).allowMainThreadQueries().build()

            val id = id_recorrido
            val formulario = nombreFormulario.text.toString()

            val formularioOb = Formulario(
                id,
                formulario
            )
            db.formularioDao().insert(formularioOb)

            finish()
        }
    }

    fun renderPreguntas(){
        val intent = intent.getStringExtra("id_formulario").toString()
        if(intent != ""){
            id_formulario = intent
        }else{
            id_formulario = id_recorrido
        }
        adapter = AdapterPreguntas(this, id_formulario)
        val recyclerView = binding.recyclerView
        if (recyclerView != null) {
            recyclerView.layoutManager = LinearLayoutManager(this)
        }
        if (recyclerView != null) {
            recyclerView.adapter = adapter
        }
    }

    public fun showDialogDelete(context: Context,id : String,id_formulario : String , position : Int,adapterP : AdapterPreguntas){
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Eliminar")
        builder.setMessage("¿Estas seguro de eliminar la pregunta?")
//builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))

        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            val db = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "preguntas"
                ).allowMainThreadQueries().build()

            val pregunta_delete = db?.preguntasDao()?.getById(id,id_formulario)
            if (pregunta_delete != null) {
                db?.preguntasDao()?.delete(pregunta_delete)
            }
            if (position != null) {
                adapterP.notifyItemRemoved(position)
            }
        }

        builder.setNegativeButton(android.R.string.no) { dialog, which ->

        }
        builder.show()
    }
}