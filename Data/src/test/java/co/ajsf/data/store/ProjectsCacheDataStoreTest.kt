package co.ajsf.data.store

import co.ajsf.data.model.ProjectEntity
import co.ajsf.data.repository.ProjectsCache
import co.ajsf.data.test.factory.DataFactory
import co.ajsf.data.test.factory.ProjectFactory
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Completable
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ProjectsCacheDataStoreTest {

    private val cache = mock<ProjectsCache>()
    private val store = ProjectsCacheDataStore(cache)

    private fun stubCacheGetProjects(observable: Observable<List<ProjectEntity>>) {
        whenever(cache.getProjects())
            .thenReturn(observable)
    }

    private fun stubCacheSaveProjects(completable: Completable = Completable.complete()) {
        whenever(cache.saveProjects(any()))
            .thenReturn(completable)
    }

    private fun stubCacheSetLastCacheTime(completable: Completable = Completable.complete()) {
        whenever(cache.setLastCacheTime(any()))
            .thenReturn(completable)
    }

    private fun stubCacheClearProjects(completable: Completable = Completable.complete()) {
        whenever(cache.clearProjects())
            .thenReturn(completable)
    }

    private fun stubCacheGetBookmarkedProjects(observable: Observable<List<ProjectEntity>>) {
        whenever(cache.getBookmarkedProjects())
            .thenReturn(observable)
    }

    private fun stubCacheSetProjectAsBookmarked(completable: Completable = Completable.complete()) {
        whenever(cache.setProjectAsBookmarked(any()))
            .thenReturn(completable)
    }

    private fun stubCacheSetProjectAsNotBookmarked(completable: Completable = Completable.complete()) {
        whenever(cache.setProjectAsNotBookmarked(any()))
            .thenReturn(completable)
    }

    @Test
    fun getProjectsCompletes() {
        stubCacheGetProjects(Observable.just(listOf(ProjectFactory.makeProjectEntity())))
        val observer = store.getProjects().test()
        observer.assertComplete()
    }

    @Test
    fun getProjectsReturnsData() {
        val data = listOf(ProjectFactory.makeProjectEntity())
        stubCacheGetProjects(Observable.just(data))
        val observer = store.getProjects().test()
        observer.assertValue(data)
    }

    @Test
    fun getProjectsCallsCacheSource() {
        stubCacheGetProjects(Observable.just(listOf(ProjectFactory.makeProjectEntity())))
        store.getProjects()
        verify(cache).getProjects()
    }

    @Test
    fun saveProjectsCompletes() {
        stubCacheSaveProjects()
        stubCacheSetLastCacheTime()
        val testObserver = store.saveProjects(listOf(ProjectFactory.makeProjectEntity())).test()
        testObserver.assertComplete()
    }

    @Test
    fun saveProjectsCallsCache() {
        val data = listOf(ProjectFactory.makeProjectEntity())
        stubCacheSaveProjects()
        stubCacheSetLastCacheTime()
        store.saveProjects(data)
        verify(cache).saveProjects(data)
    }

    @Test
    fun clearProjectsCompletes() {
        stubCacheClearProjects()
        val testObserver = store.clearProjects().test()
        testObserver.assertComplete()
    }

    @Test
    fun clearProjectsCallsCache() {
        stubCacheClearProjects()
        store.clearProjects()
        verify(cache).clearProjects()
    }

    @Test
    fun getBookmarkedProjectsCompletes() {
        stubCacheGetBookmarkedProjects(Observable.just(listOf(ProjectFactory.makeProjectEntity())))
        val testObserver = store.getBookmarkedProjects().test()
        testObserver.assertComplete()
    }

    @Test
    fun getBookmarkedProjectsReturnsData() {
        val data = listOf(ProjectFactory.makeProjectEntity())
        stubCacheGetBookmarkedProjects(Observable.just(data))
        val testObserver = store.getBookmarkedProjects().test()
        testObserver.assertValue(data)
    }

    @Test
    fun getBookmarkedProjectsCallsCache() {
        stubCacheGetBookmarkedProjects(Observable.just(listOf(ProjectFactory.makeProjectEntity())))
        store.getBookmarkedProjects().test()
        verify(cache).getBookmarkedProjects()
    }

    @Test
    fun setProjectAsBookmarkedCompletes() {
        stubCacheSetProjectAsBookmarked()
        val testObserver = store.setProjectAsBookmarked(DataFactory.randomString()).test()
        testObserver.assertComplete()
    }

    @Test
    fun setProjectAsBookmarkedCallsCache() {
        val projectId = DataFactory.randomString()
        stubCacheSetProjectAsBookmarked()
        store.setProjectAsBookmarked(projectId)
        verify(cache).setProjectAsBookmarked(projectId)
    }

    @Test
    fun setProjectAsNotBookmarkedCompletes() {
        stubCacheSetProjectAsNotBookmarked()
        val testObserver = store.setProjectAsNotBookmarked(DataFactory.randomString()).test()
        testObserver.assertComplete()
    }

    @Test
    fun setProjectAsNotBookmarkedCallsCache() {
        val projectId = DataFactory.randomString()
        stubCacheSetProjectAsNotBookmarked()
        store.setProjectAsNotBookmarked(projectId)
        verify(cache).setProjectAsNotBookmarked(projectId)
    }
}