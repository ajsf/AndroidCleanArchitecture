package co.ajsf.data.test.factory

import co.ajsf.data.model.ProjectEntity
import co.ajsf.domain.model.Project

internal object ProjectFactory {

    fun makeProjectEntity(): ProjectEntity = ProjectEntity(
        DataFactory.randomString(), DataFactory.randomString(),
        DataFactory.randomString(), DataFactory.randomString(),
        DataFactory.randomString(), DataFactory.randomString(),
        DataFactory.randomString(), DataFactory.randomBoolean()
    )

    fun makeProject(): Project = Project(
        DataFactory.randomString(), DataFactory.randomString(),
        DataFactory.randomString(), DataFactory.randomString(),
        DataFactory.randomString(), DataFactory.randomString(),
        DataFactory.randomString(), DataFactory.randomBoolean()
    )
}