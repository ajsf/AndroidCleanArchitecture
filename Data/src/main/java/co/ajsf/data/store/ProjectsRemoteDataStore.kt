package co.ajsf.data.store

import co.ajsf.data.model.ProjectEntity
import co.ajsf.data.repository.ProjectsDataStore
import co.ajsf.data.repository.ProjectsRemote
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import javax.inject.Inject

class ProjectsRemoteDataStore @Inject constructor(
    private val projectsRemote: ProjectsRemote
) : ProjectsDataStore {
    override fun getProjects(): Observable<List<ProjectEntity>> = projectsRemote.getProjects()

    override fun saveProjects(projects: List<ProjectEntity>): Completable {
        throw UnsupportedOperationException("Saving projects isn't supported here...")
    }

    override fun clearProjects(): Completable {
        throw UnsupportedOperationException("Clearing projects isn't supported here...")
    }

    override fun getBookmarkedProjects(): Flowable<List<ProjectEntity>> {
        throw UnsupportedOperationException("Getting bookmarked projects isn't supported here...")
    }

    override fun setProjectAsBookmarked(projectId: String): Completable {
        throw UnsupportedOperationException("Setting projects as bookmarked isn't supported here...")
    }

    override fun setProjectAsNotBookmarked(projectId: String): Completable {
        throw UnsupportedOperationException("Setting projects as not bookmarked isn't supported here...")
    }
}