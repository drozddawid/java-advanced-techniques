package com.drozd.controller;

import com.drozd.contentreaders.ContentReader;
import com.drozd.contentreaders.Reader;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Controller {
    @FXML
    private TreeView<String> directoryTreeView;
    @FXML
    private Button chooseDirectoryButton;
    @FXML
    private Pane infoPane;
    @FXML
    private Label diskInfoLabel;

    private Path currentDirectory;
    private ContentReader dataProvider;

    @FXML
    private void initialize() {
        dataProvider = new Reader();
    }

    @FXML
    private void onChooseDirectoryButtonClick() {
        DirectoryChooser dirChooser = new DirectoryChooser();
        dirChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        dirChooser.titleProperty().set("Choose folder.");
        File selectedDirectory = dirChooser.showDialog(new Stage());
        if (selectedDirectory != null) {
            currentDirectory = selectedDirectory.toPath();
            dataProvider.setContentSourcePath(currentDirectory);
            refreshDirectoryTreeView();
        }
    }

    private void refreshDirectoryTreeView() {
        try {
            directoryTreeView.setRoot(createTree());
            directoryTreeView.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private TreeItem<String> createTree() throws IOException {
        TreeItem<String> root = new TreeItem<>(currentDirectory.toString());
        root.setExpanded(true);
        Files.list(currentDirectory).filter(Files::isDirectory).forEach(path -> {
            TreeItem<String> dirItem = new TreeItem<>(path.getFileName().toString());
            root.getChildren().add(dirItem);
        });
        return root;
    }

    @FXML
    private void onPersonClick() {
        TreeItem<String> selectedItem = directoryTreeView.getSelectionModel().getSelectedItem();
        if (selectedItem != null && !selectedItem.equals(directoryTreeView.getRoot())) {
            Node v = dataProvider.getContentNode(selectedItem.getValue());
            diskInfoLabel.setText(dataProvider.isLoadedFromDisk() ? "Loaded from disk." : "Loaded from memory.");
            v.setLayoutX(15);
            v.setLayoutY(15);
            infoPane.getChildren().clear();
            infoPane.getChildren().add(v);
            infoPane.getChildren().add(diskInfoLabel);
        }
    }

    @FXML
    private void runGC() {
        System.out.println("GC runs");
        System.gc();
        System.out.println("GC stops");
    }

}