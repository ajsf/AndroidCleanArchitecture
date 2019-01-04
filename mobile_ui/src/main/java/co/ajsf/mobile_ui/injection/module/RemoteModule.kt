package co.ajsf.mobile_ui.injection.module

import co.ajsf.data.repository.ProjectsRemote
import co.ajsf.remote.ProjectsRemoteImpl
import co.ajsf.remote.service.GithubTrendingService
import co.ajsf.remote.service.GithubTrendingServiceFactory
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class RemoteModule {

    @Module
    companion object {
        @Provides
        @JvmStatic
        fun provideGithubService(): GithubTrendingService {
            return GithubTrendingServiceFactory.makeGithubTrendingService(true)
        }
    }

    @Binds
    abstract fun bindProjectsRemote(projectsRemote: ProjectsRemoteImpl): ProjectsRemote
}