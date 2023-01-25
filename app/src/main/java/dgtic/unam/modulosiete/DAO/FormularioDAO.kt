package dgtic.unam.modulosiete.DAO

import androidx.room.*
import dgtic.unam.modulosiete.BD.Formulario
import dgtic.unam.modulosiete.BD.Recorrido

@Dao
interface FormularioDAO {
    @Insert
    fun insert(user: Formulario)

    @Update
    fun update(user: Formulario)

    @Delete
    fun delete(user: Formulario)

    @Query("SELECT * FROM formularios")
    fun getAll(): List<Formulario>

    @Query("SELECT * FROM formularios where id = :id")
    fun getById(id: String?): Formulario
}