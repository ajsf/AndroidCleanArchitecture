package co.ajsf.presentation.mapper

import co.ajsf.domain.model.Project
import co.ajsf.presentation.model.ProjectView
import javax.inject.Inject

class ProjectViewMapper @Inject constructor() : Mapper<ProjectView, Project> {

    override fun mapToView(type: Project): ProjectView = ProjectView(
        type.id,
        type.name,
        type.fullName,
        type.starCount,
        type.dateCreated,
        type.ownerName,
        type.ownerAvatar,
        type.isBookmarked
    )
}