package co.ajsf.mobile_ui.bookmarked

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import co.ajsf.mobile_ui.R
import co.ajsf.mobile_ui.injection.ViewModelFactory
import co.ajsf.mobile_ui.mapper.ProjectViewMapper
import co.ajsf.mobile_ui.model.Project
import co.ajsf.presentation.BrowseBookmarkedProjectsViewModel
import co.ajsf.presentation.model.ProjectView
import co.ajsf.presentation.state.Resource
import co.ajsf.presentation.state.ResourceState
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_bookmarked.*
import javax.inject.Inject

class BookmarkedActivity : AppCompatActivity() {

    @Inject
    lateinit var mapper: ProjectViewMapper

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var browseViewModel: BrowseBookmarkedProjectsViewModel

    @Inject
    lateinit var adapter: BookmarkedAdapter

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, BookmarkedActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookmarked)
        browseViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(BrowseBookmarkedProjectsViewModel::class.java)
        setupBrowseRecycler()
    }

    override fun onStart() {
        super.onStart()
        browseViewModel
            .getProjects()
            .observe(this, Observer<Resource<List<ProjectView>>> {
                it?.let { res -> handleDataState(res) }
            })
        browseViewModel.fetchProjects()
    }

    private fun setupBrowseRecycler() {
        recycler_projects.layoutManager = LinearLayoutManager(this)
        recycler_projects.adapter = adapter
    }

    private fun handleDataState(resource: Resource<List<ProjectView>>) {
        when (resource.status) {
            ResourceState.LOADING -> {
                setupScreenForSuccess(resource.data?.map { mapper.mapToView(it) })
                progress.visibility = View.VISIBLE
                recycler_projects.visibility = View.GONE
            }
            ResourceState.SUCCESS -> {
                progress.visibility = View.GONE
                recycler_projects.visibility = View.VISIBLE
            }
            ResourceState.ERROR -> {
            }
        }
    }

    private fun setupScreenForSuccess(projects: List<Project>?) {
        projects?.let {
            adapter.projects = it
            adapter.notifyDataSetChanged()
        } ?: run {

        }
    }
}