package dgtic.unam.modulosiete.WEB.Respuesta
import com.google.gson.annotations.SerializedName

data class Puesto(
    @SerializedName("id") val id: String,
    @SerializedName("id_tienda") val id_tienda: String,
    @SerializedName("puesto") val puesto: String,
)
