package co.ajsf.cache.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import co.ajsf.cache.dao.CachedProjectsDao
import co.ajsf.cache.dao.ConfigDao
import co.ajsf.cache.model.CachedProject
import co.ajsf.cache.model.Config
import javax.inject.Inject

@Database(entities = [CachedProject::class, Config::class], version = 1)
abstract class ProjectsDatabase @Inject constructor() : RoomDatabase() {

    abstract fun cachedProjectsDao(): CachedProjectsDao
    abstract fun configDao(): ConfigDao

    companion object {

        private var INSTANCE: ProjectsDatabase? = null
        private val lock = Any()

        fun getInstance(context: Context): ProjectsDatabase {
            if (INSTANCE == null) {
                synchronized(lock) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        ProjectsDatabase::class.java,
                        "projects.db"
                    ).build()
                }
            }
            return INSTANCE as ProjectsDatabase
        }
    }
}