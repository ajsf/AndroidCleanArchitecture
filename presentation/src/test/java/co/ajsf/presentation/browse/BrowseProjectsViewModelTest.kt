package co.ajsf.presentation.browse

import android.arch.core.executor.testing.InstantTaskExecutorRule
import co.ajsf.domain.interactor.bookmark.BookmarkProject
import co.ajsf.domain.interactor.bookmark.UnbookmarkProject
import co.ajsf.domain.interactor.browse.GetProjects
import co.ajsf.domain.model.Project
import co.ajsf.presentation.BrowseProjectsViewModel
import co.ajsf.presentation.mapper.ProjectViewMapper
import co.ajsf.presentation.model.ProjectView
import co.ajsf.presentation.state.ResourceState
import co.ajsf.presentation.test.factory.DataFactory
import co.ajsf.presentation.test.factory.ProjectFactory
import com.nhaarman.mockitokotlin2.*
import io.reactivex.observers.DisposableObserver
import junit.framework.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Captor

@RunWith(JUnit4::class)
class BrowseProjectsViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val getProjects = mock<GetProjects>()
    private val bookmarkProject = mock<BookmarkProject>()
    private val unbookmarkProject = mock<UnbookmarkProject>()
    private val projectMapper = mock<ProjectViewMapper>()
    private val projectViewModel = BrowseProjectsViewModel(
        getProjects, bookmarkProject, unbookmarkProject, projectMapper
    )

    @Captor
    val captor = argumentCaptor<DisposableObserver<List<Project>>>()

    @Test
    fun fetchProjectsExecutesUseCase() {
        projectViewModel.fetchProjects()
        verify(getProjects, times(1)).execute(any(), eq(null))
    }

    @Test
    fun fetchProjectsReturnsSuccess() {
        val projects = ProjectFactory.makeProjectList(2)
        val projectViews = ProjectFactory.makeProjectViewList(2)
        projectViews.zip(projects).forEach { stubProjectMapperMapToView(it.first, it.second) }
        projectViewModel.fetchProjects()
        verify(getProjects).execute(captor.capture(), eq(null))
        captor.firstValue.onNext(projects)
        assertEquals(ResourceState.SUCCESS, projectViewModel.getProjects().value?.status)
    }

    @Test
    fun fetchProjectsReturnsData() {
        val projects = ProjectFactory.makeProjectList(2)
        val projectViews = ProjectFactory.makeProjectViewList(2)
        projectViews.zip(projects).forEach { stubProjectMapperMapToView(it.first, it.second) }
        projectViewModel.fetchProjects()
        verify(getProjects).execute(captor.capture(), eq(null))
        captor.firstValue.onNext(projects)
        assertEquals(projectViews, projectViewModel.getProjects().value?.data)
    }

    @Test
    fun fetchProjectsReturnsError() {
        projectViewModel.fetchProjects()
        verify(getProjects).execute(captor.capture(), eq(null))
        captor.firstValue.onError(RuntimeException())
        assertEquals(ResourceState.ERROR, projectViewModel.getProjects().value?.status)
    }

    @Test
    fun fetchProjectsReturnsErrorMessage() {
        val errorMessage = DataFactory.randomString()
        projectViewModel.fetchProjects()
        verify(getProjects).execute(captor.capture(), eq(null))
        captor.firstValue.onError(RuntimeException(errorMessage))
        assertEquals(errorMessage, projectViewModel.getProjects().value?.message)
    }

    private fun stubProjectMapperMapToView(projectView: ProjectView, project: Project) {
        whenever(projectMapper.mapToView(project))
            .thenReturn(projectView)
    }
}