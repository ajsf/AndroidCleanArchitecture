package co.ajsf.cache.mapper

import co.ajsf.cache.model.CachedProject
import co.ajsf.data.model.ProjectEntity
import javax.inject.Inject

class CachedProjectMapper @Inject constructor() : CacheMapper<CachedProject, ProjectEntity> {
    override fun mapFromCached(type: CachedProject): ProjectEntity =
        ProjectEntity(
            type.id,
            type.name,
            type.fullName,
            type.starCount,
            type.dateCreated,
            type.ownerName,
            type.ownerAvatar,
            type.isBookmarked
        )

    override fun mapToCached(type: ProjectEntity): CachedProject = CachedProject(
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