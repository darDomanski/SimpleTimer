package com.darski.SimpleTimer.controller;

import com.darski.SimpleTimer.service.Timer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;

public class MainController {
    @FXML
    private TextField timerName;
    @FXML
    private VBox timersVBox;

    public void addTimer() throws IOException {
        HBox timerHBox = getTimerHBox();
        Timer timerToAdd = new Timer();

        setPropertiesOfTimerHbox(timerHBox, timerToAdd);

        timersVBox.getChildren().add(timerHBox);
    }

    private void setPropertiesOfTimerHbox(HBox timerHBox, Timer timerToAdd) {
        for (Node node : timerHBox.getChildren()) {
            if (node instanceof Label) {
                setTimerName((Label) node);
            } else if (node instanceof Text) {
                bindTimerWithTextField(timerToAdd, (Text) node);
            } else if (node instanceof ToggleButton) {
                setActionForPlayPauseButton(timerToAdd, (ToggleButton) node);
            } else if (node instanceof Button) {
                setActionForStopButton(timerHBox, timerToAdd, (Button) node);
            }
        }
    }

    private void setActionForStopButton(HBox timerHBox, Timer timerToAdd, Button node) {
        node.setOnAction(event -> {
            timerToAdd.stopTimer();
            timersVBox.getChildren().remove(timerHBox);
        });
    }

    private void setActionForPlayPauseButton(Timer timerToAdd, ToggleButton node) {
        node.setOnAction(event -> {
            if (node.isSelected()) {
                timerToAdd.startTimer();
            } else {
                timerToAdd.pause();
            }
        });
    }

    private void bindTimerWithTextField(Timer timerToAdd, Text node) {
        node.textProperty().bind(timerToAdd.getTimerProperty());
    }

    private void setTimerName(Label node) {
        node.setText(timerName.getText());
    }

    private HBox getTimerHBox() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/Timer.fxml"));
        return loader.load();
    }
}