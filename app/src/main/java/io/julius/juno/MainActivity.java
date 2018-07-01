package io.julius.juno;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.transition.Fade;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.SignInButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import io.julius.juno.base.BaseActivity;
import io.julius.juno.data.MemoriesContract;
import io.julius.juno.data.presenter.MemoriesPresenter;
import io.julius.juno.model.Memory;
import io.julius.juno.util.MemoryDetailsTransition;
import io.julius.juno.view.MemoriesFragment;
import io.julius.juno.view.MemoryFragment;
import io.julius.juno.view.MemoryInterface;
import io.julius.juno.view.auth.AuthActivity;

public class MainActivity extends BaseActivity<MemoriesPresenter>
        implements MemoriesContract.MemoryListView, MemoryInterface.List {

    private static final String MEMORY_LIST_FRAGMENT = "memory_list_fragment";
    private static final String MEMORY_DETAIL_FRAGMENT = "memory_detail_fragment";

    private MemoriesFragment memoriesFragment;

    @Override
    protected int getContentResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(@Nullable Bundle state) {
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivity(new Intent(this, AuthActivity.class));
            return;
        }

        Toast.makeText(this, "Init", Toast.LENGTH_SHORT).show();
        memoriesFragment = new MemoriesFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, memoriesFragment, MEMORY_LIST_FRAGMENT)
                .commit();

    }

    @Override
    protected void injectDependencies() {
        getActivityComponent().inject(this);
    }

    @Override
    public void onMemoriesLoaded(List<Memory> memories) {
        Toast.makeText(this, "Came here with " + memories, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMemorySelected(Memory memory, View container) {
        Fragment memoryFragment = MemoryFragment.newInstance(memory, ViewCompat.getTransitionName(container));

        MemoryDetailsTransition transition = new MemoryDetailsTransition();
        memoryFragment.setSharedElementEnterTransition(transition);
        memoriesFragment.setSharedElementReturnTransition(transition);
        memoriesFragment.setExitTransition(new Fade().setDuration(300));
        memoriesFragment.setReenterTransition(new Fade().setDuration(300));
        memoryFragment.setEnterTransition(new Fade().setDuration(300));

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.addSharedElement(container, ViewCompat.getTransitionName(container));
        fragmentTransaction.replace(R.id.container, memoryFragment, MEMORY_DETAIL_FRAGMENT);
        fragmentTransaction.addToBackStack(MEMORY_DETAIL_FRAGMENT);
        fragmentTransaction.commit();
    }

    @Override
    public void onAddNewMemory() {
        Fragment memoryFragment = new MemoryFragment();

//        previousFragment.setExitTransition(new Fade());
//        memoryFragment.setEnterTransition(new Fade());

        MemoryDetailsTransition transition = new MemoryDetailsTransition();
        memoryFragment.setSharedElementEnterTransition(transition);
        memoriesFragment.setSharedElementReturnTransition(transition);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.container, memoryFragment, MEMORY_DETAIL_FRAGMENT);
        fragmentTransaction.addToBackStack(MEMORY_DETAIL_FRAGMENT);
        fragmentTransaction.commit();
    }
}
