package co.ajsf.data.repository

import co.ajsf.data.model.ProjectEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable

interface ProjectsDataStore {

    fun getProjects(): Observable<List<ProjectEntity>>

    fun saveProjects(projects: List<ProjectEntity>): Completable

    fun clearProjects(): Completable

    fun getBookmarkedProjects(): Flowable<List<ProjectEntity>>

    fun setProjectAsBookmarked(projectId: String): Completable

    fun setProjectAsNotBookmarked(projectId: String): Completable

}