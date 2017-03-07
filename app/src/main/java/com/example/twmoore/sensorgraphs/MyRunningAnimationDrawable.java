package com.example.twmoore.sensorgraphs;

import android.graphics.drawable.AnimationDrawable;
import android.os.SystemClock;

/**
 * Created by twmoore on 3/6/17.
 */

public class MyRunningAnimationDrawable extends AnimationDrawable {
    private volatile int duration;
    private int currentFrame;

    public MyRunningAnimationDrawable() {
        currentFrame = 0;
    }

    @Override
    public void run() {
        int frameCount = getNumberOfFrames();
        currentFrame++;
        if (currentFrame >= frameCount) {
            currentFrame = 0;
        }

        selectDrawable(currentFrame);
        scheduleSelf(this, SystemClock.uptimeMillis() + duration);
    }

    public void setDuration(int duration) {
        this.duration = duration;

        unscheduleSelf(this);
        selectDrawable(currentFrame);
        scheduleSelf(this, SystemClock.uptimeMillis() + duration);
    }
}
