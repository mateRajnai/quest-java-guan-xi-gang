package com.codecool.quest;

import com.codecool.quest.layers.Layout;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class CountdownTimer {

    private final Integer startTime = 20;
    private Integer secondsLeft = startTime;
    private Label countdownTimer;

    public CountdownTimer(Layout layout) {
        this.countdownTimer = layout.getSidePanel().getCountdownTimer();
        update();
    }

    public void countdown() {
        Timeline time = new Timeline();
        time.setCycleCount(Timeline.INDEFINITE);

        KeyFrame frame = new KeyFrame(Duration.seconds(1), actionEvent -> {
            secondsLeft--;
            countdownTimer.setText("Wait " + secondsLeft + " seconds\nif you want to\nfight the Boss");
            if (secondsLeft <= 0) {
                time.stop();
            }
        });

        time.getKeyFrames().add(frame);
        time.playFromStart();
    }

    public boolean isTimeZero() {
        return secondsLeft == 0;
    }

    public void update() {
        countdownTimer.setText("Boss appears in: " + secondsLeft + " s");
    }

    public void reset() {
        this.secondsLeft = startTime;
    }
}
