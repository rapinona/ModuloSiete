package dgtic.unam.modulosiete.BD

import androidx.room.Entity

@Entity(tableName = "preguntas",primaryKeys = ["id", "id_formulario"])
data class Pregunta(
    val id: String,
    val id_formulario: String,
    val pregunta : String
)