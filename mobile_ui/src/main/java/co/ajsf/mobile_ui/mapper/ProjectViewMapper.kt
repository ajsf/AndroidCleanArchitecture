package co.ajsf.mobile_ui.mapper

import co.ajsf.mobile_ui.model.Project
import co.ajsf.presentation.model.ProjectView
import javax.inject.Inject

class ProjectViewMapper @Inject constructor() : ViewMapper<ProjectView, Project> {

    override fun mapToView(presentation: ProjectView): Project = Project(
        presentation.id,
        presentation.name,
        presentation.fullName,
        presentation.starCount,
        presentation.dateCreated,
        presentation.ownerName,
        presentation.ownerAvatar,
        presentation.isBookmarked
    )
}