package co.ajsf.domain.interactor.bookmark

import co.ajsf.domain.repository.ProjectsRepository
import co.ajsf.domain.scheduler.PostExecutionThread
import co.ajsf.domain.test.ProjectDataFactory
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Completable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class UnbookmarkProjectTest {
    private lateinit var unbookmarkProject: UnbookmarkProject

    @Mock
    lateinit var projectsRepository: ProjectsRepository

    @Mock
    lateinit var postExecutionThread: PostExecutionThread

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        unbookmarkProject = UnbookmarkProject(projectsRepository, postExecutionThread)
    }

    @Test
    fun unbookmarkProjectCompletes() {
        stubBookmarkProject(Completable.complete())
        val project = ProjectDataFactory.randomUuid()
        val params = UnbookmarkProject.Params.forProject(project)
        val testObserver = unbookmarkProject
            .buildUseCaseCompletable(params).test()
        testObserver.assertComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun bookmarkProjectThrowsExceptionIfNoParamsAreProvided() {
        unbookmarkProject.buildUseCaseCompletable().test()
    }

    private fun stubBookmarkProject(completable: Completable) {
        whenever(projectsRepository.unbookmarkProject(any()))
            .thenReturn(completable)
    }
}