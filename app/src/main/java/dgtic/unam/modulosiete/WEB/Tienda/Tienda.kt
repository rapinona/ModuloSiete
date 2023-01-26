package dgtic.unam.modulosiete.WEB.Respuesta
import com.google.gson.annotations.SerializedName

data class Tienda(
    @SerializedName("id") val id: String,
    @SerializedName("tienda") val tienda: String,
)
