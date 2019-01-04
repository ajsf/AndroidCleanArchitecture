package co.ajsf.mobile_ui.injection.module

import co.ajsf.data.ProjectsDataRepository
import co.ajsf.domain.repository.ProjectsRepository
import dagger.Binds
import dagger.Module

@Module
abstract class DataModule {

    @Binds
    abstract fun bindDataRepository(dataRepository: ProjectsDataRepository): ProjectsRepository
}