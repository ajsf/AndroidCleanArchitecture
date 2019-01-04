package co.ajsf.data.store

import co.ajsf.data.model.ProjectEntity
import co.ajsf.data.repository.ProjectsRemote
import co.ajsf.data.test.factory.DataFactory
import co.ajsf.data.test.factory.ProjectFactory
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ProjectsRemoteDataStoreTest {

    private val projectsRemote = mock<ProjectsRemote>()
    private val store = ProjectsRemoteDataStore(projectsRemote)

    private fun stubRemoteGetProjects(observable: Observable<List<ProjectEntity>>) {
        whenever(projectsRemote.getProjects())
            .thenReturn(observable)
    }

    private lateinit var data: List<ProjectEntity>

    @Before
    fun setup() {
        data = listOf(ProjectFactory.makeProjectEntity())
    }

    @Test
    fun getProjectsCompletes() {
        stubRemoteGetProjects(Observable.just(data))
        val testObserver = store.getProjects().test()
        testObserver.assertComplete()
    }

    @Test
    fun getProjectsReturnsData() {
        stubRemoteGetProjects(Observable.just(data))
        val testObserver = store.getProjects().test()
        testObserver.assertValue(data)
    }

    @Test(expected = UnsupportedOperationException::class)
    fun saveProjectsThrowsException() {
        store.saveProjects(data).test()
    }

    @Test(expected = UnsupportedOperationException::class)
    fun clearProjectsThrowsException() {
        store.clearProjects().test()
    }

    @Test(expected = UnsupportedOperationException::class)
    fun getBookmarkedProjectsThrowsException() {
        store.getBookmarkedProjects().test()
    }

    @Test(expected = UnsupportedOperationException::class)
    fun setProjectAsBookmarkedThrowsException() {
        store.setProjectAsBookmarked(DataFactory.randomString()).test()
    }

    @Test(expected = UnsupportedOperationException::class)
    fun setProjectAsNotBookmarkedThrowsException() {
        store.setProjectAsNotBookmarked(DataFactory.randomString()).test()
    }
}