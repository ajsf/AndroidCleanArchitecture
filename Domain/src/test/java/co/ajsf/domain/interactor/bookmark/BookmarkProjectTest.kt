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

class BookmarkProjectTest {
    private lateinit var bookmarkProject: BookmarkProject

    @Mock
    lateinit var projectsRepository: ProjectsRepository

    @Mock
    lateinit var postExecutionThread: PostExecutionThread

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        bookmarkProject = BookmarkProject(projectsRepository, postExecutionThread)
    }

    @Test
    fun bookmarkProjectCompletes() {
        stubBookmarkProject(Completable.complete())
        val project = ProjectDataFactory.randomUuid()
        val params = BookmarkProject.Params.forProject(project)
        val testObserver = bookmarkProject
            .buildUseCaseCompletable(params).test()
        testObserver.assertComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun bookmarkProjectThrowsExceptionIfNoParamsAreProvided() {
        bookmarkProject.buildUseCaseCompletable().test()

    }

    private fun stubBookmarkProject(completable: Completable) {
        whenever(projectsRepository.bookmarkProject(any()))
            .thenReturn(completable)
    }

}