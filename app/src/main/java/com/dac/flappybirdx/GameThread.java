package com.dac.flappybirdx;

import android.os.SystemClock;
import android.view.SurfaceHolder;
import android.graphics.Canvas;
import android.util.Log;

public class GameThread extends Thread {
    SurfaceHolder surfaceHolder;
    boolean isRunning;

    long startTime, loopTime;
    long DELAY = 33;

    public GameThread(SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;
        isRunning = true;
    }

    @Override
    public void run() {
        while (isRunning) {
            startTime = SystemClock.uptimeMillis();
            Canvas canvas = surfaceHolder.lockCanvas(null);

            if (canvas != null) {
                AppConstants.getGameEngine().updateAndDrawBackgroundImage(canvas);
                AppConstants.getGameEngine().updateAndDrawBird(canvas);
                surfaceHolder.unlockCanvasAndPost(canvas);
            }

            loopTime = SystemClock.uptimeMillis() - startTime;
            if (loopTime < DELAY) {
                try {
                    Thread.sleep(DELAY - loopTime);
                } catch (InterruptedException e) {
                    Log.e("Interrupted", "Interrupted while sleeping");
                }
            }
        }
    }
    public boolean isRunning(){
        return isRunning;
    }

    public void setRunning(boolean state){
        isRunning = state;
    }
}
