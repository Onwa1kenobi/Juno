package io.julius.juno.data.presenter;


import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.julius.juno.base.BasePresenter;
import io.julius.juno.data.MemoriesContract;
import io.julius.juno.model.Memory;

public class MemoriesPresenter extends BasePresenter<MemoriesContract.MemoryListView>
        implements MemoriesContract.Presenter {

    private FirebaseFirestore database;
    private FirebaseAuth auth;

    @Inject
    public MemoriesPresenter() {
        database = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public void loadMemories() {
        database.collection("Users")
                .document(auth.getCurrentUser().getEmail())
                .collection("Memories").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Memory> memories = new ArrayList<>();
                            for (DocumentSnapshot document : task.getResult()) {
                                Memory memory = document.toObject(Memory.class);
                                memories.add(memory);
                            }

                            getView().onMemoriesLoaded(memories);
                        }
                    }
                });
    }

    @Override
    public void deleteMemory(Memory memory) {
        // TODO: implement swipe to delete
    }
}
