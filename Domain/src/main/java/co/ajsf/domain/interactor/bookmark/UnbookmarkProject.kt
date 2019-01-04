package co.ajsf.domain.interactor.bookmark

import co.ajsf.domain.interactor.CompletableUseCase
import co.ajsf.domain.repository.ProjectsRepository
import co.ajsf.domain.scheduler.PostExecutionThread
import io.reactivex.Completable
import javax.inject.Inject

open class UnbookmarkProject @Inject constructor(
    private val projectsRepository: ProjectsRepository,
    postExecutionThread: PostExecutionThread
) : CompletableUseCase<UnbookmarkProject.Params>(postExecutionThread) {

    public override fun buildUseCaseCompletable(params: UnbookmarkProject.Params?): Completable {
        if (params == null) throw IllegalArgumentException("Params can't be null.")
        return projectsRepository.unbookmarkProject(params.projectId)
    }

    data class Params(val projectId: String) {
        companion object {
            fun forProject(projectId: String): Params {
                return Params(projectId)
            }
        }
    }
}

