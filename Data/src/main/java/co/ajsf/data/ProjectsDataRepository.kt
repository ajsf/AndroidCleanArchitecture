package co.ajsf.data

import co.ajsf.data.mapper.ProjectMapper
import co.ajsf.data.repository.ProjectsCache
import co.ajsf.data.store.ProjectsDataStoreFactory
import co.ajsf.domain.repository.Projects
import co.ajsf.domain.repository.ProjectsRepository
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class ProjectsDataRepository @Inject constructor(
    private val mapper: ProjectMapper,
    private val cache: ProjectsCache,
    private val factory: ProjectsDataStoreFactory
) : ProjectsRepository {

    override fun getProjects(): Observable<Projects> {
        return Observable.zip(
            cache.areProjectsCached().toObservable(),
            cache.isProjectsCacheExpired().toObservable(),
            BiFunction<Boolean, Boolean, Pair<Boolean, Boolean>> {
                    areCached, isExpired -> Pair(areCached, isExpired)
            })
            .flatMap {
                factory
                    .getDataStore(it.first, it.second)
                    .getProjects()
            }
            .flatMap { projects ->
                factory
                    .getCachedDataStore()
                    .saveProjects(projects)
                    .andThen(Observable.just(projects))
            }.map { it.map { projectEntity -> mapper.mapFromEntity(projectEntity) } }
    }

    override fun bookmarkProject(projectId: String): Completable =
        factory.getCachedDataStore().setProjectAsBookmarked(projectId)

    override fun unbookmarkProject(projectId: String): Completable =
        factory.getCachedDataStore().setProjectAsNotBookmarked(projectId)

    override fun getBookmarkedProjects(): Observable<Projects> =
        factory.getCachedDataStore().getBookmarkedProjects().toObservable().map {
            it.map { projectEntity -> mapper.mapFromEntity(projectEntity) }
        }
}