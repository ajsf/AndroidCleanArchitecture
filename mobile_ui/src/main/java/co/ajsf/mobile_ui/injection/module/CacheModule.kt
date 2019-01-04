package co.ajsf.mobile_ui.injection.module

import android.app.Application
import co.ajsf.cache.ProjectsCacheImpl
import co.ajsf.cache.db.ProjectsDatabase
import co.ajsf.data.repository.ProjectsCache
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class CacheModule {

    @Module
    companion object {
        @Provides
        @JvmStatic
        fun providesDatabase(application: Application): ProjectsDatabase {
            return ProjectsDatabase.getInstance(application)
        }
    }

    @Binds
    abstract fun bindProjectsCache(projectsCache: ProjectsCacheImpl): ProjectsCache
}