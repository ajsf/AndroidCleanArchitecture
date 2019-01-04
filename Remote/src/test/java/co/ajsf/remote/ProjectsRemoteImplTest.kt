package co.ajsf.remote

import co.ajsf.data.model.ProjectEntity
import co.ajsf.remote.mapper.ProjectsResponseModelMapper
import co.ajsf.remote.model.ProjectModel
import co.ajsf.remote.model.ProjectsResponseModel
import co.ajsf.remote.service.GithubTrendingService
import co.ajsf.remote.test.factory.ProjectDataFactory
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ProjectsRemoteImplTest {

    private val service = mock<GithubTrendingService>()
    private val mapper = mock<ProjectsResponseModelMapper>()
    private val remote = ProjectsRemoteImpl(service, mapper)

    private fun stubServiceSearchRepositories(observable: Observable<ProjectsResponseModel>) {
        whenever(service.searchRepositories(any(), any(), any()))
            .thenReturn(observable)
    }

    private fun stubMapperMapFromModel(model: ProjectModel, entity: ProjectEntity) {
        whenever(mapper.mapFromModel(model))
            .thenReturn(entity)
    }

    @Test
    fun getProjectsCompletes() {
        val observable = Observable.just(ProjectDataFactory.makeProjectsResponse())
        stubServiceSearchRepositories(observable)
        val testObserver = remote.getProjects().test()
        testObserver.assertComplete()
    }

    @Test
    fun getProjectsCallsService() {
        val observable = Observable.just(ProjectDataFactory.makeProjectsResponse())
        stubServiceSearchRepositories(observable)
        remote.getProjects().test()
        verify(service).searchRepositories(any(), any(), any())
    }

    @Test
    fun getProjectsReturnsData() {
        val response = ProjectDataFactory.makeProjectsResponse()
        val observable = Observable.just(response)
        stubServiceSearchRepositories(observable)

        val entities = response.items.map {
            val entity = ProjectDataFactory.makeProjectEntity()
            stubMapperMapFromModel(it, entity)
            entity
        }
        val testObserver = remote.getProjects().test()
        testObserver.assertValue(entities)
    }

    @Test
    fun getProjectsCallsServiceWithCorrectParameters() {
        val observable = Observable.just(ProjectDataFactory.makeProjectsResponse())
        stubServiceSearchRepositories(observable)
        remote.getProjects()
        verify(service).searchRepositories("language:kotlin", "stars", "desc")
    }
}
