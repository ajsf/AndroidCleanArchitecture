package co.ajsf.cache.dao

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
import co.ajsf.cache.db.ProjectsDatabase
import co.ajsf.cache.test.factory.ConfigDataFactory
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class ConfigDaoTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val database = Room
        .inMemoryDatabaseBuilder(
            RuntimeEnvironment.application.applicationContext, ProjectsDatabase::class.java
        )
        .allowMainThreadQueries()
        .build()

    @After
    fun clearDb() = database.close()

    @Test
    fun saveConfigurationSavesData() {
        val config = ConfigDataFactory.makeCachedConfig()
        database.configDao().insertConfig(config)
        val testObserver = database.configDao().getConfig().test()
        testObserver.assertValue(config)
    }
}