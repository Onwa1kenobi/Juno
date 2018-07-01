package io.julius.juno.di.component;

import dagger.Component;
import io.julius.juno.di.module.FragmentModule;
import io.julius.juno.view.MemoriesFragment;
import io.julius.juno.view.MemoryFragment;

@Component(modules = {FragmentModule.class})
public interface FragmentComponent {

    void inject(MemoriesFragment obj);

    void inject(MemoryFragment memoryFragment);
}
