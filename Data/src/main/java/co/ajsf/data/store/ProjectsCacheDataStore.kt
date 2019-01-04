package co.ajsf.data.store

import co.ajsf.data.model.ProjectEntity
import co.ajsf.data.repository.ProjectsCache
import co.ajsf.data.repository.ProjectsDataStore
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import javax.inject.Inject

class ProjectsCacheDataStore @Inject constructor(
    private val projectsCache: ProjectsCache
) : ProjectsDataStore {

    override fun getProjects(): Observable<List<ProjectEntity>> =
        projectsCache.getProjects()

    override fun saveProjects(projects: List<ProjectEntity>): Completable =
        projectsCache
            .saveProjects(projects)
            .andThen(projectsCache.setLastCacheTime(System.currentTimeMillis()))

    override fun clearProjects(): Completable = projectsCache.clearProjects()

    override fun getBookmarkedProjects(): Flowable<List<ProjectEntity>> =
        projectsCache.getBookmarkedProjects()

    override fun setProjectAsBookmarked(projectId: String): Completable =
        projectsCache.setProjectAsBookmarked(projectId)

    override fun setProjectAsNotBookmarked(projectId: String): Completable =
        projectsCache.setProjectAsNotBookmarked(projectId)
}