package com.drozd.processloader;

import com.drozd.processloader.classloaders.FantasticClassLoader;
import com.drozd.processloader.components.StatusProgressBar;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import processing.Processor;
import processing.StatusListener;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Controller {
    private HashMap<String /*class name*/, Processor> loadedProcessors;
    private Path classSourcePath;
    private Processor selectedProcessor;
    private Method submitTask;
    private Method getInfo;
    private Method getResult;


    @FXML
    private Button loadButton;
    @FXML
    private Button unloadButton;
    @FXML
    private Button chooseDirButton;
    @FXML
    private ListView<String> classListView;

    @FXML
    private Label instructionLabel;
    @FXML
    private TextField inputTextField;
    @FXML
    private TextField outputTextField;
    @FXML
    private VBox statusBarContainer;
    @FXML
    private Label infoLabel;

    @FXML
    private void initialize() {
        classSourcePath = Paths.get(System.getProperty("user.dir") + File.separator + "classes");
        loadedProcessors = new HashMap<>();
    }

    @FXML
    protected void onLoadButtonAction(ActionEvent e) {
        FantasticClassLoader classLoader = new FantasticClassLoader(classSourcePath);
        List<String> classNames = findClasses(classSourcePath);
        System.out.println("Found classes: ");
        for (String s : classNames) System.out.println(s);
        System.out.println("------------------");
        for (String className : classNames) {
            try {
                Class<?> c = classLoader.loadClass(className);
                System.out.println("loading class:" + c.getName());
                if (Arrays.stream(c.getInterfaces()).anyMatch(i -> i.getName().equals("processing.Processor"))) {
                    Constructor<?> constructor = c.getConstructor();
                    Processor processor = (Processor) constructor.newInstance();
                    loadedProcessors.put(c.getName(), processor);
                }
            } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException ex) {
                ex.printStackTrace();
            }
        }
        unloadButton.setDisable(false);
        refreshClassList();
    }

    private List<String> findClasses(Path path) {
        List<String> names = new LinkedList<>();
        findClasses(path, names, "");
        return names;
    }

    private void findClasses(Path path, List<String> names, String packageName) {
        try {
            Files.list(path).forEach(p -> {
                if (Files.isDirectory(p)) {
                    findClasses(p, names, (packageName.length() == 0 ? "" : packageName + ".") + p.getFileName());
                } else {
                    if (String.valueOf(p.getFileName()).contains(".class")) {
                        String className = p.getFileName().toString();
                        names.add((packageName.length() == 0 ? "" : packageName + ".") + className.substring(0, className.lastIndexOf('.')));
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void onChooseDirectoryButtonClick() {
        DirectoryChooser dirChooser = new DirectoryChooser();
        dirChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        dirChooser.titleProperty().set("Choose folder.");
        File selectedDirectory = dirChooser.showDialog(new Stage());
        if (selectedDirectory != null) {
            classSourcePath = selectedDirectory.toPath();
            loadButton.setDisable(false);
        }
    }

    @FXML
    private void onClassListViewClick() {
        String className = classListView.getSelectionModel().getSelectedItem();
        Processor selection = loadedProcessors.get(className);
        if (selection != null) {
            this.selectedProcessor = selection;

            instructionLabel.setText(this.selectedProcessor.getInfo());
        }
    }

    @FXML
    private void executeButtonOnAction() {
        if (inputTextField.getText().length() > 0 && selectedProcessor != null) {
            StatusProgressBar statusBar = new StatusProgressBar(selectedProcessor.getClass().getName(), selectedProcessor);
            boolean accepted = selectedProcessor.submitTask(inputTextField.getText(), statusBar);
            if (accepted) {
                infoLabel.setText("");
                HBox bar = (HBox) statusBar;
                statusBarContainer.getChildren().add(bar);
                bar.setOnMouseClicked(event -> {
                    if (event.getButton() == MouseButton.PRIMARY) {
                        outputTextField.setText(statusBar.getResult().isPresent()? statusBar.getResult().get() : "Task didn't finish yet.");
                    }
                    if (event.getButton() == MouseButton.SECONDARY) {
                        statusBarContainer.getChildren().remove(bar);
                    }
                });
            }else{
                infoLabel.setText("Processor " + selectedProcessor.getClass().getName() + " is busy now.");
            }
        }
    }

    @FXML
    private void unloadButtonOnAction(ActionEvent event) {
        loadedProcessors.clear();
        selectedProcessor = null;
        System.gc();
        refreshClassList();
    }

    private void refreshClassList() {
        classListView.getItems().clear();
        for (Map.Entry<String, Processor> entry : loadedProcessors.entrySet()) {
            classListView.getItems().add(entry.getKey());
        }
        classListView.refresh();
    }
}