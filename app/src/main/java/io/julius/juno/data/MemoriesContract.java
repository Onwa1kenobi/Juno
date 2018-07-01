package io.julius.juno.data;

import java.util.List;

import io.julius.juno.base.BaseMvpPresenter;
import io.julius.juno.base.BaseView;
import io.julius.juno.model.Memory;

public interface MemoriesContract {

    /**
     * User actions for presenter implementation
     */
    interface Presenter extends BaseMvpPresenter<MemoryListView> {
        void loadMemories();

        void deleteMemory(Memory memory);
    }

    interface MemoryDetailPresenter extends BaseMvpPresenter<MemoryDetailView> {
        void deleteMemory(Memory memory);

        void saveMemory(Memory memory);

        void updateMemory(Memory memory);
    }

    /**
     * Action callbacks for Activity/Fragment implementation
     */
    interface MemoryListView extends BaseView {
        void onMemoriesLoaded(List<Memory> memories);
    }

    interface MemoryDetailView extends BaseView {
        void onMemorySaved();

        void onMemoryDeleted();

        void onDisplayMessage(String message);
    }
}
