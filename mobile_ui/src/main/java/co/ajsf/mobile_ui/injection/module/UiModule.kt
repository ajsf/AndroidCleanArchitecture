package co.ajsf.mobile_ui.injection.module

import co.ajsf.domain.scheduler.PostExecutionThread
import co.ajsf.mobile_ui.browse.BrowseActivity
import co.ajsf.mobile_ui.UiThread
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class UiModule {

    @Binds
    abstract fun bindPostExecutionThread(uiThread: UiThread): PostExecutionThread

    @ContributesAndroidInjector
    abstract fun contributesBrowseActivity(): BrowseActivity
}