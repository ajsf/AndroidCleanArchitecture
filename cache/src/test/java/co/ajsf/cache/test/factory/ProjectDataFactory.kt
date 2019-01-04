package co.ajsf.cache.test.factory

import co.ajsf.cache.model.CachedProject
import co.ajsf.data.model.ProjectEntity

internal object ProjectDataFactory {

    fun makeCachedProject(): CachedProject = CachedProject(
        DataFactory.randomUuid(),
        DataFactory.randomUuid(),
        DataFactory.randomUuid(),
        DataFactory.randomUuid(),
        DataFactory.randomUuid(),
        DataFactory.randomUuid(),
        DataFactory.randomUuid(),
        false
    )

    fun makeBookmarkedCachedProject(): CachedProject = CachedProject(
        DataFactory.randomUuid(),
        DataFactory.randomUuid(),
        DataFactory.randomUuid(),
        DataFactory.randomUuid(),
        DataFactory.randomUuid(),
        DataFactory.randomUuid(),
        DataFactory.randomUuid(),
        true
    )

    fun makeProjectEntity(): ProjectEntity = ProjectEntity(
        DataFactory.randomUuid(),
        DataFactory.randomUuid(),
        DataFactory.randomUuid(),
        DataFactory.randomUuid(),
        DataFactory.randomUuid(),
        DataFactory.randomUuid(),
        DataFactory.randomUuid(),
        false
    )

    fun makeBookmarkedProjectEntity(): ProjectEntity = ProjectEntity(
        DataFactory.randomUuid(),
        DataFactory.randomUuid(),
        DataFactory.randomUuid(),
        DataFactory.randomUuid(),
        DataFactory.randomUuid(),
        DataFactory.randomUuid(),
        DataFactory.randomUuid(),
        true
    )
}