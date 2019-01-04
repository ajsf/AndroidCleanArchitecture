package co.ajsf.cache.mapper

import co.ajsf.cache.model.CachedProject
import co.ajsf.cache.test.factory.ProjectDataFactory
import co.ajsf.data.model.ProjectEntity
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class CachedProjectMapperTest {

    private val mapper = CachedProjectMapper()

    @Test
    fun mapFromCachedMapsData() {
        val model = ProjectDataFactory.makeCachedProject()
        val entity = mapper.mapFromCached(model)
        assertEqualData(model, entity)
    }

    @Test
    fun mapToCachedMapsData() {
        val entity = ProjectDataFactory.makeProjectEntity()
        val model = mapper.mapToCached(entity)
        assertEqualData(model, entity)
    }

    private fun assertEqualData(model: CachedProject, entity: ProjectEntity) {
        assertEquals(model.id, entity.id)
        assertEquals(model.name, entity.name)
        assertEquals(model.fullName, entity.fullName)
        assertEquals(model.starCount, entity.starCount)
        assertEquals(model.dateCreated, entity.dateCreated)
        assertEquals(model.ownerName, entity.ownerName)
        assertEquals(model.ownerAvatar, entity.ownerAvatar)
        assertEquals(model.isBookmarked, entity.isBookmarked)
        assertEquals(model.ownerName, entity.ownerName)
    }
}