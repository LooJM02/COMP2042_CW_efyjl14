package model;


import java.util.TimerTask;
import java.util.Timer;

/**
 * TimerModel class is responsible for all the implementations regarding the timer.
 */
public class TimerModel {

    private int gameTimer;
    private int seconds;
    private int minutes;
    private Timer timer;
    private TimerTask task;
    private boolean gameRunning = false;

    /**
     * TimerModel is a Parameterized Constructor that handles the initial implementation of the timer.
     * Handles the timer when game begins. Sets time starting from zero.
     * Creates timer.
     */
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
