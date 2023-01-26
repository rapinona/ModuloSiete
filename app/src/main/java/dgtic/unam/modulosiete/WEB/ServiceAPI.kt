package dgtic.unam.modulosiete.WEB

import dgtic.unam.modulosiete.WEB.Empleado.Empleado
import dgtic.unam.modulosiete.WEB.Respuesta.Puesto
import dgtic.unam.modulosiete.WEB.Respuesta.Respuesta
import dgtic.unam.modulosiete.WEB.Respuesta.Tienda
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url
interface ServiceAPI {
    @GET
    suspend fun getEmpleados(@Url url:String):Response<List<Empleado>>
    @GET
    suspend fun getRespuesta(@Url url:String):Response<List<Respuesta>>
    @GET
    suspend fun getTiendas(@Url url:String):Response<List<Tienda>>
    @GET
    suspend fun getPuestos(@Url url:String):Response<List<Puesto>>
}