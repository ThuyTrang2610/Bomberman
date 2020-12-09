package uet.oop.bomberman.utilities;

import java.util.Timer;
import java.util.TimerTask;

public class MyTimer {
    Timer timer;
    private int timeLeft;

    public MyTimer(int seconds) {
        this.timeLeft = seconds;
        timer = new Timer();
        timer.scheduleAtFixedRate(new RemindTask(), 0, 1000);
    }

    public int getTimeLeft() {
        return timeLeft;
    }

    class RemindTask extends TimerTask {
        public void run() {
            System.out.println(timeLeft);
            timeLeft--;
            if (timeLeft == 0)
                timer.cancel(); //Terminate the timer thread
        }
    }
}
