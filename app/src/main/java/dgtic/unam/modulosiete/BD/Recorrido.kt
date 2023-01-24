package dgtic.unam.modulosiete.BD

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recorridos")
data class Recorrido(
    @PrimaryKey val id: String,
    val name: String,
    var status : Int,
    val fechaInicio: String,
    val fechaFin: String
)