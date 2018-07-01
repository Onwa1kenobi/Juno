package io.julius.juno.data.presenter;

import android.text.TextUtils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import javax.inject.Inject;

import io.julius.juno.base.BasePresenter;
import io.julius.juno.data.MemoriesContract;
import io.julius.juno.model.Memory;

public class MemoryDetailPresenter extends BasePresenter<MemoriesContract.MemoryDetailView>
        implements MemoriesContract.MemoryDetailPresenter {

    private CollectionReference collectionReference;

    @Inject
    public MemoryDetailPresenter() {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        collectionReference = database.collection("Users")
                .document(auth.getCurrentUser().getEmail())
                .collection("Memories");
    }

    @Override
    public void deleteMemory(Memory memory) {
        collectionReference.document(memory.getId()).delete();
        getView().onMemoryDeleted();
    }

    @Override
    public void saveMemory(Memory memory) {
        if (TextUtils.isEmpty(memory.getDescription())) {
            getView().onDisplayMessage("You have not added a memory");
            return;
        }

        DocumentReference documentReference = collectionReference.document();

        memory.setId(documentReference.getId());

        getView().onMemorySaved();
    }

    @Override
    public void updateMemory(Memory memory) {
        collectionReference.document(memory.getId()).set(memory, SetOptions.merge());
        getView().onMemorySaved();
    }
}
