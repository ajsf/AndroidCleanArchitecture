package co.ajsf.remote

import co.ajsf.data.model.ProjectEntity
import co.ajsf.data.repository.ProjectsRemote
import co.ajsf.remote.mapper.ProjectsResponseModelMapper
import co.ajsf.remote.service.GithubTrendingService
import io.reactivex.Observable
import javax.inject.Inject

class ProjectsRemoteImpl @Inject constructor(
    private val service: GithubTrendingService,
    private val mapper: ProjectsResponseModelMapper
) : ProjectsRemote {

    override fun getProjects(): Observable<List<ProjectEntity>> {
        return service.searchRepositories(
            "language:kotlin", "stars", "desc"
        ).map { response ->
            response.items.map { mapper.mapFromModel(it) }
        }
    }
}