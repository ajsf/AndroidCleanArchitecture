package co.ajsf.presentation.test.factory

import co.ajsf.domain.model.Project
import co.ajsf.presentation.model.ProjectView

object ProjectFactory {
    fun makeProjectView(): ProjectView = ProjectView(
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

    fun makeProjectViewList(count: Int): List<ProjectView> = (1..count)
        .map { makeProjectView() }

    fun makeProjectList(count: Int): List<Project> = (1..count)
        .map { makeProject() }
}