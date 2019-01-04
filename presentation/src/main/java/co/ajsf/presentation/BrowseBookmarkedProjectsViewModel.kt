package co.ajsf.presentation

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import co.ajsf.domain.interactor.bookmark.GetBookmarkedProjects
import co.ajsf.domain.model.Project
import co.ajsf.presentation.mapper.ProjectViewMapper
import co.ajsf.presentation.model.ProjectView
import co.ajsf.presentation.state.Resource
import co.ajsf.presentation.state.ResourceState
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

private typealias ProjectListResource = Resource<List<ProjectView>>

class BrowseBookmarkedProjectsViewModel @Inject constructor(
    private val getBookmarkedProjects: GetBookmarkedProjects,
    private val mapper: ProjectViewMapper
) : ViewModel() {

    private val liveData: MutableLiveData<ProjectListResource> = MutableLiveData()

    override fun onCleared() {
        getBookmarkedProjects.dispose()
        super.onCleared()
    }

    fun getProjects(): LiveData<ProjectListResource> = liveData

    fun fetchProjects() {
        liveData.postValue(Resource(ResourceState.LOADING, null, null))
        return getBookmarkedProjects.execute(ProjectsSubscriber())
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
}