package co.ajsf.data.repository

import co.ajsf.data.model.ProjectEntity
import io.reactivex.Flowable
import io.reactivex.Observable

interface ProjectsRemote {

    fun getProjects() : Observable<List<ProjectEntity>>
}