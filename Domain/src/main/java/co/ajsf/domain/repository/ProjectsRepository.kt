package co.ajsf.domain.repository

import co.ajsf.domain.model.Project
import io.reactivex.Completable
import io.reactivex.Observable

typealias Projects = List<Project>

interface ProjectsRepository {
    fun getProjects(): Observable<Projects>

    fun bookmarkProject(projectId: String): Completable

    fun unbookmarkProject(projectId: String): Completable

    fun getBookmarkedProjects(): Observable<Projects>
}