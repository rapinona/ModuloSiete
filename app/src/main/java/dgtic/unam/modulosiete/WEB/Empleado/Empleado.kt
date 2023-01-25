package dgtic.unam.modulosiete.WEB.Empleado
import com.google.gson.annotations.SerializedName

data class Empleado(
    @SerializedName("id") val id: String,
    @SerializedName("nombre") val nombre: String,
    @SerializedName("apellido") val apellido: String,
    @SerializedName("email") val email: String
)
