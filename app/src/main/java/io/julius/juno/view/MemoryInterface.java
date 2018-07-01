package io.julius.juno.view;

import android.view.View;

import io.julius.juno.model.Memory;

public interface MemoryInterface {

    interface List {
        void onMemorySelected(Memory memory, View container);

        void onAddNewMemory();
    }

    interface Adapter {
        void onMemorySelected(Memory memory, View container);
    }
}
