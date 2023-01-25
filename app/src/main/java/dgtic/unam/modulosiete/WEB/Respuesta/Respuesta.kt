package dgtic.unam.modulosiete.WEB.Respuesta
import com.google.gson.annotations.SerializedName

data class Respuesta(
    @SerializedName("id_recorrido") val id_recorrido: String,
    @SerializedName("id_empleado") val id_empleado: String,
    @SerializedName("pregunta") val pregunta: String,
    @SerializedName("Observaciones") val Observaciones: String,
    @SerializedName("categoria") val categoria: String,
    @SerializedName("cumple") val cumple: Boolean
)
