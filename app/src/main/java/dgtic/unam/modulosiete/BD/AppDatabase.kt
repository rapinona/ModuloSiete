package dgtic.unam.modulosiete.BD

import androidx.room.Database
import androidx.room.RoomDatabase
import dgtic.unam.modulosiete.DAO.RecorridoDAO

@Database(entities = [Recorrido::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recorridoDao(): RecorridoDAO
}