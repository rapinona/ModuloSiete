package dgtic.unam.modulosiete.DAO

import androidx.room.*
import dgtic.unam.modulosiete.BD.Formulario
import dgtic.unam.modulosiete.BD.Pregunta
import dgtic.unam.modulosiete.BD.Recorrido

@Dao
interface PreguntaDAO {
    @Insert
    fun insert(user: Pregunta)

    @Update
    fun update(user: Pregunta)

    @Delete
    fun delete(user: Pregunta)

    @Query("SELECT * FROM preguntas where id_formulario = :id")
    fun getAll(id:String?): List<Pregunta>

    @Query("SELECT * FROM preguntas where id = :id and id_formulario = :id_formulario")
    fun getById(id: String?,id_formulario : String?): Pregunta
}