package model;


import java.util.TimerTask;
import java.util.Timer;

public class TimerModel {

    private int gameTimer;
    private int seconds;
    private int minutes;
    private Timer timer;
    private TimerTask task;
    private boolean gameRunning = false;

    public TimerModel() {
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                if (isGameRunning()) {
                    gameTimer++;
                    minutes = gameTimer / 60;
                    seconds = gameTimer % 60;
                }
            }
        };

        timer.schedule(task, 0, 1000);
    }


    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public boolean isGameRunning() {
        return gameRunning;
    }

    public void setGameRunning(boolean gameRunning) {
        this.gameRunning = gameRunning;
    }

    public void resetGameTimer(){
        gameTimer = 0;
    }
}