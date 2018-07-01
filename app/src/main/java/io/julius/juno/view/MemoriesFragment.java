package io.julius.juno.view;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.julius.juno.R;
import io.julius.juno.base.BaseFragment;
import io.julius.juno.data.MemoriesContract;
import io.julius.juno.data.presenter.MemoriesPresenter;
import io.julius.juno.model.Memory;
import io.julius.juno.util.SimpleDividerItemDecoration;

/**
 * A simple {@link Fragment} subclass.
 */
public class MemoriesFragment extends BaseFragment<MemoriesPresenter>
        implements MemoriesContract.MemoryListView, MemoryInterface.Adapter {

    @BindView(R.id.recycler_view_memories)
    RecyclerView mRecyclerView;

    @BindView(R.id.text_empty_feed)
    TextView emptyFeedText;

    private MemoryFeedAdapter mAdapter;
    private MemoryInterface.List mListener;

    public MemoriesFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getContentResource() {
        return R.layout.fragment_memories;
    }

    @Override
    protected void init(@Nullable Bundle state) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));

        mAdapter = new MemoryFeedAdapter(new ArrayList<Memory>(), this);
        mRecyclerView.setAdapter(mAdapter);

        getPresenter().loadMemories();
    }

    @Override
    protected void injectDependencies() {
        getFragmentComponent().inject(this);
    }

    @Override
    public void onMemoriesLoaded(List<Memory> memories) {
        if (memories.size() == 0) {
            emptyFeedText.setVisibility(View.VISIBLE);
        } else {
            emptyFeedText.setVisibility(View.GONE);
        }

        mAdapter.refill(memories);
    }

    @Override
    public void onMemorySelected(Memory memory, View container) {
        mListener.onMemorySelected(memory, container);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MemoryInterface.List) {
            mListener = (MemoryInterface.List) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        getPresenter().loadMemories();
    }

    @OnClick(R.id.fab)
    public void addNewMemory(View v) {
        mListener.onAddNewMemory();
    }
}
