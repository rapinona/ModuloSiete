package dgtic.unam.modulosiete.BD

import androidx.room.Database
import androidx.room.RoomDatabase
import dgtic.unam.modulosiete.DAO.FormularioDAO
import dgtic.unam.modulosiete.DAO.PreguntaDAO
import dgtic.unam.modulosiete.DAO.RecorridoDAO

@Database(entities = [Recorrido::class,Formulario::class,Pregunta::class], version = 4)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recorridoDao(): RecorridoDAO
    abstract fun formularioDao(): FormularioDAO
    abstract fun preguntasDao(): PreguntaDAO
}