package co.ajsf.presentation

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import co.ajsf.domain.interactor.bookmark.BookmarkProject
import co.ajsf.domain.interactor.bookmark.UnbookmarkProject
import co.ajsf.domain.interactor.browse.GetProjects
import co.ajsf.domain.model.Project
import co.ajsf.presentation.mapper.ProjectViewMapper
import co.ajsf.presentation.model.ProjectView
import co.ajsf.presentation.state.Resource
import co.ajsf.presentation.state.ResourceState
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

class BrowseProjectsViewModel @Inject internal constructor(
    private val getProjects: GetProjects?,
    private val bookmarkProject: BookmarkProject,
    private val unbookmarkProject: UnbookmarkProject,
    private val mapper: ProjectViewMapper
) : ViewModel() {

    private val liveData: MutableLiveData<Resource<List<ProjectView>>> = MutableLiveData()

    init {
        fetchProjects()
    }

    override fun onCleared() {
        getProjects?.dispose()
        super.onCleared()
    }

    fun getProjects(): LiveData<Resource<List<ProjectView>>> = liveData

    fun fetchProjects() {
        liveData.postValue(Resource(ResourceState.LOADING, null, null))
        getProjects?.execute(ProjectsSubscriber())
    }

    fun bookmarkProject(projectId: String) {
        liveData.postValue(Resource(ResourceState.LOADING, null, null))
        return bookmarkProject.execute(
            BookmarkProjectsSubscriber(),
            BookmarkProject.Params.forProject(projectId)
        )
    }

    fun unBookmarkProject(projectId: String) {
        liveData.postValue(Resource(ResourceState.LOADING, null, null))
        return unbookmarkProject.execute(
            BookmarkProjectsSubscriber(),
            UnbookmarkProject.Params.forProject(projectId)
        )
    }

    inner class ProjectsSubscriber : DisposableObserver<List<Project>>() {
        override fun onNext(t: List<Project>) {
            val projectViews = t.map { mapper.mapToView(it) }
            liveData.postValue(Resource(ResourceState.SUCCESS, projectViews, null))
        }

        override fun onComplete() {}

        override fun onError(e: Throwable) {
            liveData.postValue(Resource(ResourceState.ERROR, null, e.localizedMessage))
        }
    }

    inner class BookmarkProjectsSubscriber : DisposableCompletableObserver() {
        override fun onComplete() {
            val res = Resource(ResourceState.SUCCESS, liveData.value?.data, null)
            liveData.postValue(res)
        }

        override fun onError(e: Throwable) {
            val res = Resource(ResourceState.ERROR, null, e.localizedMessage)
            liveData.postValue(res)
        }
    }
}