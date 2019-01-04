package co.ajsf.remote.test.factory

import co.ajsf.data.model.ProjectEntity
import co.ajsf.remote.model.OwnerModel
import co.ajsf.remote.model.ProjectModel
import co.ajsf.remote.model.ProjectsResponseModel

internal object ProjectDataFactory {

    fun makeOwner(): OwnerModel = OwnerModel(
        DataFactory.randomUuid(),
        DataFactory.randomUuid()
    )

    fun makeProject(): ProjectModel = ProjectModel(
        DataFactory.randomUuid(),
        DataFactory.randomUuid(),
        DataFactory.randomUuid(),
        DataFactory.randomInt(),
        DataFactory.randomUuid(),
        makeOwner()
    )

    fun makeProjectsResponse(): ProjectsResponseModel =
        ProjectsResponseModel(listOf(makeProject()))

    fun makeProjectEntity(): ProjectEntity = ProjectEntity(
        DataFactory.randomUuid(),
        DataFactory.randomUuid(),
        DataFactory.randomUuid(),
        DataFactory.randomUuid(),
        DataFactory.randomUuid(),
        DataFactory.randomUuid(),
        DataFactory.randomUuid()
    )
}