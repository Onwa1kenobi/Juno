package io.julius.juno.di.component;

import javax.inject.Singleton;

import dagger.Component;
import io.julius.juno.MainActivity;
import io.julius.juno.base.BaseActivity;
import io.julius.juno.di.module.ActivityModule;

@Singleton
@Component(modules = {ActivityModule.class})
public interface ActivityComponent {

    void inject(MainActivity obj);

}
