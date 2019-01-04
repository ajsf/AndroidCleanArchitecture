package co.ajsf.mobile_ui.browse

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import co.ajsf.mobile_ui.R
import co.ajsf.mobile_ui.injection.ViewModelFactory
import co.ajsf.mobile_ui.mapper.ProjectViewMapper
import co.ajsf.mobile_ui.model.Project
import co.ajsf.presentation.BrowseProjectsViewModel
import co.ajsf.presentation.model.ProjectView
import co.ajsf.presentation.state.Resource
import co.ajsf.presentation.state.ResourceState
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_browse.*
import javax.inject.Inject

class BrowseActivity : AppCompatActivity() {

    private val tag: String = BrowseActivity::class.java.simpleName

    @Inject
    lateinit var mapper: ProjectViewMapper

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var browseViewModel: BrowseProjectsViewModel

    @Inject
    lateinit var adapter: BrowseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browse)
        AndroidInjection.inject(this)
        browseViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(BrowseProjectsViewModel::class.java)
        setupBrowseRecycler()
    }

    override fun onStart() {
        super.onStart()
        browseViewModel
            .getProjects()
            .observe(this, Observer<Resource<List<ProjectView>>> {
                it?.let { res -> handleDataState(res) }
            })
        Log.d("AJSF", "ABOut to fetch")
        browseViewModel.fetchProjects()
    }

    private fun setupBrowseRecycler() {
        recycler_projects.layoutManager = LinearLayoutManager(this)
        recycler_projects.adapter = adapter
    }

    private fun handleDataState(resource: Resource<List<ProjectView>>) {
        Log.i(tag, resource.status.toString())

        when (resource.status) {
            ResourceState.LOADING -> {
                progress.visibility = View.VISIBLE
                recycler_projects.visibility = View.GONE
            }
            ResourceState.SUCCESS -> {
                setupScreenForSuccess(resource.data?.map { mapper.mapToView(it) })
            }
            ResourceState.ERROR -> {
                Log.e(tag, resource.message)
            }
        }
    }

    private fun setupScreenForSuccess(projects: List<Project>?) {
        progress.visibility = View.GONE
        projects?.let {
            adapter.projects = it
            adapter.notifyDataSetChanged()
            recycler_projects.visibility = View.VISIBLE
        } ?: run {

        }
    }
}