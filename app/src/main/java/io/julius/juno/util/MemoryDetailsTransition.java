package io.julius.juno.util;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.transition.ChangeBounds;
import android.support.transition.ChangeImageTransform;
import android.support.transition.ChangeTransform;
import android.support.transition.TransitionSet;

/**
 * Created by Jules on 7/01/2018.
 */
@TargetApi(Build.VERSION_CODES.KITKAT)
public class MemoryDetailsTransition extends TransitionSet {
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MemoryDetailsTransition() {
        setOrdering(ORDERING_TOGETHER);
        addTransition(new ChangeBounds().setDuration(300));
//                .addTransition(new ChangeTransform());
//                .addTransition(new ChangeImageTransform());
    }
}
