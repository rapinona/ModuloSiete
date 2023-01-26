package dgtic.unam.modulosiete.BD

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recorridos")
data class Recorrido(

    @PrimaryKey
    @NonNull
    val id: String,
    val name: String?,
    var status: Int,
    val fechaInicio: String?,
    val fechaFin: String?
)