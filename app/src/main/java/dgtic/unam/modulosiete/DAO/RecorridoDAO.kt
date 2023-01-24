package dgtic.unam.modulosiete.DAO

import androidx.room.*
import dgtic.unam.modulosiete.BD.Recorrido

@Dao
interface RecorridoDAO {
    @Insert
    fun insert(user: Recorrido)

    @Update
    fun update(user: Recorrido)

    @Delete
    fun delete(user: Recorrido)

    @Query("SELECT * FROM recorridos")
    fun getAll(): List<Recorrido>

    @Query("SELECT * FROM recorridos where id = :id")
    fun getById(id: String?): Recorrido
}