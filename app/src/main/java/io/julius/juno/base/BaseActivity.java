package io.julius.juno.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;
import io.julius.juno.di.component.ActivityComponent;
import io.julius.juno.di.component.DaggerActivityComponent;
import io.julius.juno.di.module.ActivityModule;


public abstract class BaseActivity<T extends BaseMvpPresenter> extends AppCompatActivity
        implements BaseView {

    /**
     * Injected Presenter
     */
    @Inject
    T mPresenter;

    private ActivityComponent mActivityComponent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getContentResource());

        ButterKnife.bind(this);
        mActivityComponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule())
                .build();
        injectDependencies();

        mPresenter.attach(this);

        init(savedInstanceState);
    }

    /**
     * Detach Presenter
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detach();
    }

    /**
     * @return the ActivityComponent object
     */
    public ActivityComponent getActivityComponent() {
        return mActivityComponent;
    }

    /**
     * Getter for the Presenter
     *
     * @return the Presenter for the Activity
     */
    public T getPresenter() {
        return mPresenter;
    }

    /**
     * Layout resource to be inflated
     *
     * @return layout resource
     */
    @LayoutRes
    protected abstract int getContentResource();

    /**
     * Initialisations
     */
    protected abstract void init(@Nullable Bundle state);

    /**
     * Inject Dependencies
     */
    protected abstract void injectDependencies();
}
