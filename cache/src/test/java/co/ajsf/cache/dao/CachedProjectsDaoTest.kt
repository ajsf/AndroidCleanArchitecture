package co.ajsf.cache.dao

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
import co.ajsf.cache.db.ProjectsDatabase
import co.ajsf.cache.test.factory.ProjectDataFactory
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class CachedProjectsDaoTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val database = Room
        .inMemoryDatabaseBuilder(
            RuntimeEnvironment.application.applicationContext,
            ProjectsDatabase::class.java
        )
        .allowMainThreadQueries()
        .build()

    @After
    fun closeDb() = database.close()

    @Test
    fun getProjectsReturnsData() {
        val projectList = listOf(ProjectDataFactory.makeCachedProject())
        database.cachedProjectsDao().insertProjects(projectList)
        val testObserver = database.cachedProjectsDao().getProjects().test()
        testObserver.assertValue(projectList)
    }

    @Test
    fun deleteProjectsClearsData() {
        val projectList = listOf(ProjectDataFactory.makeCachedProject())
        database.cachedProjectsDao().insertProjects(projectList)
        database.cachedProjectsDao().deleteProjects()
        val testObserver = database.cachedProjectsDao().getProjects().test()
        testObserver.assertValue(emptyList())
    }

    @Test
    fun getBookmarkedProjectsReturnsData() {
        val project = ProjectDataFactory.makeCachedProject()
        val bookmarkedProject = ProjectDataFactory.makeBookmarkedCachedProject()
        database.cachedProjectsDao().insertProjects(listOf(project, bookmarkedProject))
        val testObserver = database.cachedProjectsDao().getBookmarkedProjects().test()
        testObserver.assertValue(listOf(bookmarkedProject))
    }

    @Test
    fun setProjectAsBookmarkedSavesData() {
        val project = ProjectDataFactory.makeCachedProject()
        database.cachedProjectsDao().insertProjects(listOf(project))
        database.cachedProjectsDao().setBookmarkStatus(true, project.id)
        val testObserver = database.cachedProjectsDao().getBookmarkedProjects().test()
        testObserver.assertValue(listOf(project.copy(isBookmarked = true)))
    }

    @Test
    fun setProjectAsNotBookmarkedSavesData() {
        val project = ProjectDataFactory.makeBookmarkedCachedProject()
        database.cachedProjectsDao().insertProjects(listOf(project))
        database.cachedProjectsDao().setBookmarkStatus(false, project.id)
        val testObserver = database.cachedProjectsDao().getBookmarkedProjects().test()
        testObserver.assertValue(emptyList())
    }
}