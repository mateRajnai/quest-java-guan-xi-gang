package com.codecool.quest;

import com.codecool.quest.layers.Screen;
import com.codecool.quest.logic.MapLoader;
import com.codecool.quest.logic.actors.TheBoss;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class TheBossClock {

    private final Integer startTime = 20;
    private Integer secondsLeft = startTime;
    private Label countdownTimer;
    private Timeline time;
    private Runnable bossHatchery;

    public TheBossClock(Screen screen) {
        this.countdownTimer = screen.getSidePanel().getCountdownTimer();
        update();
    }

    public void setBossHatchery(Runnable bossHatchery) {
        this.bossHatchery = bossHatchery;
    }

    public void countdown() {
        time = new Timeline();
        time.setCycleCount(Timeline.INDEFINITE);

        KeyFrame frame = new KeyFrame(Duration.seconds(1), actionEvent -> {
            if (secondsLeft > 0) {
                secondsLeft--;
                update();
            } else if (!MapLoader.hasNextLevel()) {
                bossHatchery.run();
                time.stop();
            }
        });

        time.getKeyFrames().add(frame);
        time.playFromStart();
    }

    public boolean isCounting() {
        return secondsLeft > 0;
    }

    public void update() {
        countdownTimer.setText("Boss appears in: " + secondsLeft + " s");
    }

    public void reset() {
        TheBoss.reset();
        this.secondsLeft = startTime;
        time.stop();
        this.countdown();
    }
}
