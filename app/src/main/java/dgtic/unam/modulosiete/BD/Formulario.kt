package dgtic.unam.modulosiete.BD

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "formularios")
data class Formulario(
    @PrimaryKey val id: String,
    val formulario: String
)