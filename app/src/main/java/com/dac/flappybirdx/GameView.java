package com.dac.flappybirdx;

import android.content.Context;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import androidx.annotation.NonNull;


public class GameView extends SurfaceView implements SurfaceHolder.Callback { // Fix here
    GameThread gameThread;
    public GameView(Context context) {
        super(context);
        InitView();
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {

        if (!gameThread.isRunning()){
            gameThread = new GameThread(surfaceHolder);
            gameThread.start();
        } else {
            gameThread.start();
        }
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) { // Fix missing parenthesis
        // Implement logic here
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        // Implement logic here
        if (gameThread.isRunning()){
            gameThread.setRunning(false);
            boolean retry = true;
            while (retry){
                try {
                    gameThread.join();
                    retry = false;
                } catch (InterruptedException e){ }
            }
        }
    }

    void InitView() {
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        setFocusable(true);

        gameThread = new GameThread(holder);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            {
                AppConstants.getGameEngine().gameState = 1;
                AppConstants.getGameEngine().bird.setVelocity(AppConstants.VELOCITY_WHEN_JUMPED);
            }
            return true;
        }
        return false;
    }
}
