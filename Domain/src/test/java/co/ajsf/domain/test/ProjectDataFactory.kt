package co.ajsf.domain.test

import co.ajsf.domain.model.Project
import co.ajsf.domain.repository.Projects
import java.util.*

internal object ProjectDataFactory {

    fun randomUuid(): String {
        return UUID.randomUUID().toString()
    }

    fun randomBoolean(): Boolean {
        return Math.random() < 0.5
    }

    fun makeProject(): Project {
        return Project(
            randomUuid(), randomUuid(), randomUuid(),
            randomUuid(), randomUuid(), randomUuid(),
            randomUuid(), randomBoolean()
        )
    }

    fun makeProjectList(count: Int): Projects {
        val projects = mutableListOf<Project>()
        repeat(count) {
            projects.add(makeProject())
        }
        return projects
    }
}