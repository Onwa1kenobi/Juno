package io.julius.juno.view;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

import butterknife.BindView;
import io.julius.juno.R;
import io.julius.juno.base.BaseFragment;
import io.julius.juno.data.MemoriesContract;
import io.julius.juno.data.presenter.MemoryDetailPresenter;
import io.julius.juno.model.Memory;
import io.julius.juno.util.MemoryDetailsTransition;

/**
 * A simple {@link Fragment} subclass.
 */
public class MemoryFragment extends BaseFragment<MemoryDetailPresenter>
        implements MemoriesContract.MemoryDetailView {

    private static final String ARGUMENT_MEMORY = "memory";
    private static final String ARGUMENT_TRANSITION_NAME = "transition_name";

    @BindView(R.id.content_container)
    View container;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.edit_text_title)
    EditText memoryTitleInput;

    @BindView(R.id.edit_text_description)
    EditText memoryDescriptionInput;

    private Memory mMemory;

    public MemoryFragment() {
        // Required empty public constructor
    }

    public static MemoryFragment newInstance(Memory memory, String transitionName) {
        MemoryFragment memoryFragment = new MemoryFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARGUMENT_MEMORY, memory);
        bundle.putString(ARGUMENT_TRANSITION_NAME, transitionName);
        memoryFragment.setArguments(bundle);
        return memoryFragment;
    }

    @Override
    protected int getContentResource() {
        return R.layout.fragment_memory;
    }

    @Override
    protected void init(@Nullable Bundle state) {
        postponeEnterTransition();
        setSharedElementEnterTransition(new MemoryDetailsTransition());
    }

    @Override
    protected void injectDependencies() {
        getFragmentComponent().inject(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        toolbar.inflateMenu(R.menu.menu_memory_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_delete:
                        getPresenter().deleteMemory(mMemory);
                        break;

                    case R.id.action_save:
                        if (mMemory == null) {
                            mMemory = new Memory();
                            mMemory.setTitle(memoryTitleInput.getText().toString());
                            mMemory.setDescription(memoryDescriptionInput.getText().toString());
                            mMemory.setCreatedAt(new Date());

                            getPresenter().saveMemory(mMemory);
                        } else {
                            mMemory.setTitle(memoryTitleInput.getText().toString());
                            mMemory.setDescription(memoryDescriptionInput.getText().toString());

                            getPresenter().updateMemory(mMemory);
                        }

                        break;
                }

                return onOptionsItemSelected(item);
            }
        });

        Bundle bundle = getArguments();
        if (bundle != null) {
            String transitionName = bundle.getString(ARGUMENT_TRANSITION_NAME);
            ViewCompat.setTransitionName(container, transitionName);

            startPostponedEnterTransition();

            mMemory = bundle.getParcelable(ARGUMENT_MEMORY);

            memoryTitleInput.setText(mMemory.getTitle());
            memoryDescriptionInput.setText(mMemory.getDescription());
        } else {
            toolbar.getMenu().removeItem(R.id.action_delete);
            memoryDescriptionInput.requestFocus();
        }


    }

    @Override
    public void onMemorySaved() {
        getActivity().getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onMemoryDeleted() {
        getActivity().getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onDisplayMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
