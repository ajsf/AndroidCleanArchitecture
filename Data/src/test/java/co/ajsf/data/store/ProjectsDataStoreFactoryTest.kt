package co.ajsf.data.store

import com.nhaarman.mockitokotlin2.mock
import org.junit.Test
import kotlin.test.assertEquals

class ProjectsDataStoreFactoryTest {

    private val cacheStore = mock<ProjectsCacheDataStore>()
    private val remoteStore = mock<ProjectsRemoteDataStore>()
    private val factory = ProjectsDataStoreFactory(cacheStore, remoteStore)

    @Test
    fun `getDataStore returns remoteStore when cache is expired`() {
        assertEquals(remoteStore, factory.getDataStore(true, true))
    }

}