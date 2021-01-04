package com.igzafer.viking.Animation;

import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;

public class Animation {

    public TranslateAnimation shakeIt() {
        TranslateAnimation shake = new TranslateAnimation(0, 15, 0, 0);
        shake.setDuration(700);
        shake.setInterpolator(new CycleInterpolator(7));
        return shake;
    }
}
