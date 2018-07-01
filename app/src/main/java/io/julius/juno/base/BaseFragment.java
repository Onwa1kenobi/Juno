package io.julius.juno.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import butterknife.ButterKnife;
import io.julius.juno.di.component.DaggerFragmentComponent;
import io.julius.juno.di.component.FragmentComponent;
import io.julius.juno.di.module.FragmentModule;

public abstract class BaseFragment<T extends BaseMvpPresenter> extends Fragment implements BaseView {

    /**
     * Injected Presenter
     */
    @Inject
    T mPresenter;

    private FragmentComponent mFragmentComponent;
    private View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFragmentComponent = DaggerFragmentComponent.builder()
                .fragmentModule(new FragmentModule())
                .build();
        injectDependencies();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(getContentResource(), container, false);

            ButterKnife.bind(this, view);
            mPresenter.attach(this);

            init(savedInstanceState);
        }
        return view;
    }

    /**
     * Detach Presenter
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detach();
    }

    /**
     * @return the FragmentComponent object
     */
    public FragmentComponent getFragmentComponent() {
        return mFragmentComponent;
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
