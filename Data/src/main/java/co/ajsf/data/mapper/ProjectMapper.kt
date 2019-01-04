package co.ajsf.data.mapper

import co.ajsf.data.model.ProjectEntity
import co.ajsf.domain.model.Project
import javax.inject.Inject

class ProjectMapper @Inject constructor() : EntityMapper<ProjectEntity, Project> {
    override fun mapFromEntity(entity: ProjectEntity): Project = Project(
        entity.id,
        entity.name,
        entity.fullName,
        entity.starCount,
        entity.dateCreated,
        entity.ownerName,
        entity.ownerAvatar,
        entity.isBookmarked
    )

    override fun mapToEntity(domain: Project): ProjectEntity = ProjectEntity(
        domain.id,
        domain.name,
        domain.fullName,
        domain.starCount,
        domain.dateCreated,
        domain.ownerName,
        domain.ownerAvatar,
        domain.isBookmarked
    )
}