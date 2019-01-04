package co.ajsf.cache

import co.ajsf.cache.db.ProjectsDatabase
import co.ajsf.cache.mapper.CachedProjectMapper
import co.ajsf.cache.model.Config
import co.ajsf.data.model.ProjectEntity
import co.ajsf.data.repository.ProjectsCache
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class ProjectsCacheImpl @Inject constructor(
    private val projectsDatabase: ProjectsDatabase,
    private val mapper: CachedProjectMapper
) : ProjectsCache {

    override fun clearProjects(): Completable = Completable.defer {
        projectsDatabase.cachedProjectsDao().deleteProjects()
        Completable.complete()
    }

    override fun saveProjects(projects: List<ProjectEntity>): Completable = Completable.defer {
        projectsDatabase.cachedProjectsDao().insertProjects(
            projects.map { mapper.mapToCached(it) })
        Completable.complete()
    }

    override fun getProjects(): Observable<List<ProjectEntity>> = projectsDatabase
        .cachedProjectsDao()
        .getProjects()
        .toObservable()
        .map { projects ->
            projects.map { mapper.mapFromCached(it) }
        }

    override fun getBookmarkedProjects(): Flowable<List<ProjectEntity>> =
        projectsDatabase
            .cachedProjectsDao()
            .getBookmarkedProjects()
            .map { projects ->
                projects.map { mapper.mapFromCached(it) }
            }

    override fun setProjectAsBookmarked(projectId: String): Completable = Completable.defer {
        projectsDatabase
            .cachedProjectsDao()
            .setBookmarkStatus(true, projectId)
        Completable.complete()
    }

    override fun setProjectAsNotBookmarked(projectId: String): Completable = Completable.defer {
        projectsDatabase
            .cachedProjectsDao()
            .setBookmarkStatus(false, projectId)
        Completable.complete()
    }

    override fun areProjectsCached(): Single<Boolean> =
        projectsDatabase
            .cachedProjectsDao()
            .getProjects()
            .isEmpty
            .map { it.not() }

    override fun setLastCacheTime(lastCache: Long): Completable = Completable.defer {
        projectsDatabase
            .configDao()
            .insertConfig(Config(lastCacheTime = lastCache))
        Completable.complete()
    }

    override fun isProjectsCacheExpired(): Single<Boolean> {
        val currentTime = System.currentTimeMillis()
        val expirationTime = (60 * 10 * 1000).toLong()
        return projectsDatabase
            .configDao()
            .getConfig()
            .onErrorReturn { Config(lastCacheTime = 0) }
            .toSingle()
            .map { currentTime - it.lastCacheTime > expirationTime }
    }
}