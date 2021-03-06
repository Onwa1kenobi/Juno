package io.julius.juno.di.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.julius.juno.data.MemoriesContract;
import io.julius.juno.data.presenter.MemoriesPresenter;

@Module
public class ActivityModule {

    public ActivityModule() {
    }

    @Provides
    @Singleton
    MemoriesContract.Presenter providesMemoriesPresenter() {
        return new MemoriesPresenter();
    }
}
