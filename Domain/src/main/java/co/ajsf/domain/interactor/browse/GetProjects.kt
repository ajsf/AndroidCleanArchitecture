package co.ajsf.domain.interactor.browse

import co.ajsf.domain.interactor.ObservableUseCase
import co.ajsf.domain.repository.Projects
import co.ajsf.domain.repository.ProjectsRepository
import co.ajsf.domain.scheduler.PostExecutionThread
import io.reactivex.Observable
import javax.inject.Inject


open class GetProjects @Inject constructor(
    private val projectsRepository: ProjectsRepository,
    postExecutionThread: PostExecutionThread
) : ObservableUseCase<Projects, Nothing?>(postExecutionThread) {

    override fun buildUseCaseObservable(params: Nothing?): Observable<Projects> =
        projectsRepository.getProjects()
}