package co.ajsf.presentation.bookmarked

import android.arch.core.executor.testing.InstantTaskExecutorRule
import co.ajsf.domain.interactor.bookmark.GetBookmarkedProjects
import co.ajsf.domain.model.Project
import co.ajsf.presentation.BrowseBookmarkedProjectsViewModel
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
class BrowseBookmarkedProjectsViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val getBookmarkedProjects = mock<GetBookmarkedProjects>()
    private val projectMapper = mock<ProjectViewMapper>()
    private val projectViewModel = BrowseBookmarkedProjectsViewModel(
        getBookmarkedProjects, projectMapper
    )

    @Captor
    val captor = argumentCaptor<DisposableObserver<List<Project>>>()

    @Test
    fun fetchProjectsExecutesUseCase() {
        projectViewModel.fetchProjects()
        verify(getBookmarkedProjects, times(1)).execute(any(), eq(null))
    }

    @Test
    fun fetchProjectsReturnsSuccess() {
        val projects = ProjectFactory.makeProjectList(2)
        val projectViews = ProjectFactory.makeProjectViewList(2)
        projectViews.zip(projects).forEach { stubProjectMapperMapToView(it.first, it.second) }
        projectViewModel.fetchProjects()
        verify(getBookmarkedProjects).execute(captor.capture(), eq(null))
        captor.firstValue.onNext(projects)
        assertEquals(ResourceState.SUCCESS, projectViewModel.getProjects().value?.status)
    }

    @Test
    fun fetchProjectsReturnsData() {
        val projects = ProjectFactory.makeProjectList(2)
        val projectViews = ProjectFactory.makeProjectViewList(2)
        projectViews.zip(projects).forEach { stubProjectMapperMapToView(it.first, it.second) }
        projectViewModel.fetchProjects()
        verify(getBookmarkedProjects).execute(captor.capture(), eq(null))
        captor.firstValue.onNext(projects)
        assertEquals(projectViews, projectViewModel.getProjects().value?.data)
    }

    @Test
    fun fetchProjectsReturnsError() {
        projectViewModel.fetchProjects()
        verify(getBookmarkedProjects).execute(captor.capture(), eq(null))
        captor.firstValue.onError(RuntimeException())
        assertEquals(ResourceState.ERROR, projectViewModel.getProjects().value?.status)
    }

    @Test
    fun fetchProjectsReturnsErrorMessage() {
        val errorMessage = DataFactory.randomString()
        projectViewModel.fetchProjects()
        verify(getBookmarkedProjects).execute(captor.capture(), eq(null))
        captor.firstValue.onError(RuntimeException(errorMessage))
        assertEquals(errorMessage, projectViewModel.getProjects().value?.message)
    }

    private fun stubProjectMapperMapToView(projectView: ProjectView, project: Project) {
        whenever(projectMapper.mapToView(project))
            .thenReturn(projectView)
    }
}