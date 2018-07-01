package io.julius.juno.view;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.julius.juno.R;
import io.julius.juno.model.Memory;
import io.julius.juno.util.Utils;

public class MemoryFeedAdapter extends RecyclerView.Adapter<MemoryFeedAdapter.ViewHolder> {

    private List<Memory> mMemories;
    private MemoryInterface.Adapter mListener;

    MemoryFeedAdapter(List<Memory> mMemories, MemoryInterface.Adapter mListener) {
        this.mMemories = mMemories;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_memory,
                parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        return mMemories.size();
    }

    void refill(List<Memory> items) {
        mMemories.clear();
        mMemories.addAll(items);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.text_title)
        TextView titleText;

        @BindView(R.id.text_description)
        TextView descriptionText;

        @BindView(R.id.text_time)
        TextView timeText;

        @BindView(R.id.item_container)
        View container;

        Memory memory;

        ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        void bindView(int position) {
            memory = mMemories.get(position);

            titleText.setText(memory.getTitle());
            if (TextUtils.isEmpty(memory.getTitle())) {
                titleText.setVisibility(View.GONE);
            }

            descriptionText.setText(memory.getDescription());
            timeText.setText(Utils.getFormattedTime(memory.getCreatedAt()));

            ViewCompat.setTransitionName(container, memory.getTitle() + "_" + position);
        }

        @Override
        public void onClick(View v) {
            mListener.onMemorySelected(memory, container);
        }
    }
}
