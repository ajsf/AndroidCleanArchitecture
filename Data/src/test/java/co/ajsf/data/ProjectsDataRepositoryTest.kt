package co.ajsf.data

import co.ajsf.data.mapper.ProjectMapper
import co.ajsf.data.model.ProjectEntity
import co.ajsf.data.repository.ProjectsCache
import co.ajsf.data.repository.ProjectsDataStore
import co.ajsf.data.store.ProjectsDataStoreFactory
import co.ajsf.data.test.factory.DataFactory
import co.ajsf.data.test.factory.ProjectFactory
import co.ajsf.domain.model.Project
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ProjectsDataRepositoryTest {

    private val mapper = mock<ProjectMapper>()
    private val cache = mock<ProjectsCache>()
    private val factory = mock<ProjectsDataStoreFactory>()

    private val repository = ProjectsDataRepository(mapper, cache, factory)
    private val store = mock<ProjectsDataStore>()

    @Before
    fun setup() {
        stubFactoryGetDataStore()
        stubFactoryGetCacheDataStore()
        stubIsCacheExpired(Single.just(false))
        stubAreProjectsCached(Single.just(false))
        stubSaveProjects(Completable.complete())
    }

    private fun stubFactoryGetDataStore() {
        whenever(factory.getDataStore(any(), any()))
            .thenReturn(store)
    }

    private fun stubFactoryGetCacheDataStore() {
        whenever(factory.getCachedDataStore())
            .thenReturn(store)
    }

    private fun stubSaveProjects(completable: Completable) {
        whenever(store.saveProjects(any()))
            .thenReturn(completable)
    }

    private fun stubGetProjects(observable: Observable<List<ProjectEntity>>) {
        whenever(store.getProjects())
            .thenReturn(observable)
    }

    private fun stubGetBookmarkedProjects(observable: Observable<List<ProjectEntity>>) {
        whenever(store.getBookmarkedProjects())
            .thenReturn(observable)
    }

    private fun stubMapper(model: Project, entity: ProjectEntity) {
        whenever(mapper.mapFromEntity(entity))
            .thenReturn(model)
    }

    private fun stubIsCacheExpired(expired: Single<Boolean>) {
        whenever(cache.isProjectsCacheExpired())
            .thenReturn(expired)
    }

    private fun stubAreProjectsCached(cached: Single<Boolean>) {
        whenever(cache.areProjectsCached())
            .thenReturn(cached)
    }

    private fun stubBookmarkProject(completable: Completable = Completable.complete()) {
        whenever(store.setProjectAsBookmarked(any()))
            .thenReturn(completable)
    }

    private fun stubUnbookmarkProject(completable: Completable = Completable.complete()) {
        whenever(store.setProjectAsNotBookmarked(any()))
            .thenReturn(completable)
    }

    @Test
    fun getProjectsCompletes() {
        val observable = Observable.just(listOf(ProjectFactory.makeProjectEntity()))
        stubGetProjects(observable)

        val testObserver = repository.getProjects().test()
        testObserver.assertComplete()
    }

    @Test
    fun getProjectsReturnsData() {
        val projectEntity = ProjectFactory.makeProjectEntity()
        val project = ProjectFactory.makeProject()
        stubGetProjects(Observable.just(listOf(projectEntity)))
        stubMapper(project, projectEntity)

        val testObserver = repository.getProjects().test()
        testObserver.assertValue(listOf(project))
    }

    @Test
    fun getBookmarkedProjectsCompletes() {
        val observable = Observable.just(listOf(ProjectFactory.makeProjectEntity()))
        stubGetBookmarkedProjects(observable)

        val testObserver = repository.getBookmarkedProjects().test()
        testObserver.assertComplete()
    }

    @Test
    fun getBookmarkedProjectsReturnsData() {
        val projectEntity = ProjectFactory.makeProjectEntity()
        val project = ProjectFactory.makeProject()
        stubGetBookmarkedProjects(Observable.just(listOf(projectEntity)))
        stubMapper(project, projectEntity)

        val testObserver = repository.getBookmarkedProjects().test()
        testObserver.assertValue(listOf(project))
    }

    @Test
    fun bookmarkProjectCompletes() {
        stubBookmarkProject()
        val testObserver = repository.bookmarkProject(DataFactory.randomString()).test()
        testObserver.assertComplete()
    }

    @Test
    fun unbookmarkProjectCompletes() {
        stubUnbookmarkProject()
        val testObserver = repository.unbookmarkProject(DataFactory.randomString()).test()
        testObserver.assertComplete()
    }
}