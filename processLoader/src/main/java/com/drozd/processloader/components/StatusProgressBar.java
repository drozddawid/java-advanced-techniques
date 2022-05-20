package com.drozd.processloader.components;

import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import processing.Processor;
import processing.Status;
import processing.StatusListener;

import java.util.Optional;

public class StatusProgressBar extends HBox implements StatusListener {
    private ProgressBar progressBar;
    private Label label;
    private Region region;
    private Processor processor;
    private String result = null;

    public StatusProgressBar(String name, Processor processor) {
        super();
        this.processor = processor;
        label = new Label(name);
        progressBar = new ProgressBar();
        progressBar.setProgress(0.0);
        region = new Region();
        region.setPrefWidth(10);
        Tooltip.install(this, new Tooltip("Left mouse click to see the result.\nRight mouse click to remove from the list."));
        this.getChildren().addAll(label, region, progressBar);
    }

    public Optional<String> getResult() {
        return Optional.ofNullable(result);
    }

    @Override
    public void statusChanged(Status s) {
        if(s.getProgress() < 1) label.setText(label.getText() + ": " + s.getTaskId());
        progressBar.setProgress(s.getProgress() / (double) 100);
        if(s.getProgress() >= 100){
            result = processor.getResult();
        }
    }
}
