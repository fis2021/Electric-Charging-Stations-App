package org.fis2021.services;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.Duration;
import org.dizitart.no2.objects.ObjectRepository;
import org.fis2021.model.Stations;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Set;

public class TimerService {

    private final Timeline timer;

    private final ObjectProperty<Duration> timeLeft;

    @FXML
    private final Label timeLabel;

    public TimerService(Label label, Stations station) {
        this.timeLabel = label;

        timer = new Timeline();
        timer.getKeyFrames().add(new KeyFrame(Duration.seconds(1), ae -> updateTimer(station)));
        timer.setCycleCount(Timeline.INDEFINITE);

        timeLeft = new SimpleObjectProperty<>();
    }


    public void initialize() {
        timeLabel.textProperty().bind(Bindings.createStringBinding(() -> getTimeStringFromDuration(timeLeft.get()), timeLeft));
    }

    @FXML
    public void startTimer(int t) {
        timeLeft.set(Duration.seconds(t));
        timer.playFromStart();
    }

    @FXML
    public void stopTimer() {
        timer.stop();
    }

    public void updateTimer(Stations station) {
        timeLeft.set(timeLeft.get().subtract(Duration.seconds(1)));

    }

    public String getTimeStringFromDuration(Duration duration) {
        DecimalFormat dFormat = new DecimalFormat("00");
        int second = (int)Math.round(duration.toSeconds());

        if (second < 0) {
            stopTimer();
            return "00:00:00";
        }

        second = second % (24 * 3600);
        String ddHour = dFormat.format(second / 3600);
        second %= 3600;
        String ddMinute = dFormat.format(second / 60);
        second %= 60;
        String ddSecond = dFormat.format(second);

        //System.out.println(ddHour + ":" + ddMinute + ":" + ddSecond);
        return ddHour + ":" + ddMinute + ":" + ddSecond;
    }
}