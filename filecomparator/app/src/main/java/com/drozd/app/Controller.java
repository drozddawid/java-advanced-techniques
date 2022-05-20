package com.drozd.app;

import com.drozd.lib.service.SnapshotService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Controller {
    SnapshotService snapshotService = new SnapshotService();
    File currentFolder;
    @FXML
    private TreeView<String> fileView;
    @FXML
    private TextArea consoleTextArea;
    @FXML
    private Button checkChangesButton;
    @FXML
    private Button createSnapshotButton;

    // icons
    Image dirYellowImg;
    Image dirGreenImg;
    Image dirRedImg;
    Image fileWhiteImg;
    Image fileGreenImg;
    Image fileRedImg;

    @FXML
    private void initialize() {
        try {
            dirYellowImg = new Image(Controller.class.getResourceAsStream("images/dir-yellow.png"));
            dirGreenImg = new Image(Controller.class.getResourceAsStream("images/dir-green.png"));
            dirRedImg = new Image(Controller.class.getResourceAsStream("images/dir-red.png"));
            fileWhiteImg = new Image(Controller.class.getResourceAsStream("images/file-white.png"));
            fileGreenImg = new Image(Controller.class.getResourceAsStream("images/file-green.png"));
            fileRedImg = new Image(Controller.class.getResourceAsStream("images/file-red.png"));

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }


    @FXML
    protected void onChooseFolderButtonClick() {
        consoleClear();
        DirectoryChooser dirChooser = new DirectoryChooser();
        dirChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        dirChooser.titleProperty().set("Choose folder.");
        currentFolder = dirChooser.showDialog(new Stage());
        if (currentFolder != null) {
            consolePrintln("Chosen directory: " + currentFolder.getAbsolutePath());
            try {
                createSnapshotButton.disableProperty().set(false);
                checkChangesButton.disableProperty().set(false);
                refreshDirectoryTree();

                //TODO: tell if this directory has snapshot

                //if dir has snapshot, show differences in console and in treeView, also unblock "check changes" button


            } catch (Exception e) {
                createSnapshotButton.disableProperty().set(true);
                checkChangesButton.disableProperty().set(true);
                consolePrintln(e.getMessage());
            }
        } else {
            //if dir doesnt have snapshot tell that snapshot doesnt exist and block "check changes" button
            consolePrintln("No directory chosen.");
            currentFolder = null;
            createSnapshotButton.disableProperty().set(true);
            checkChangesButton.disableProperty().set(true);
        }
    }

    @FXML
    private void onCheckChangesButtonClicked() {
        consolePrintln("Checking changes ...");
        try {
            refreshDirectoryTree();
            consolePrintln("Changes checked.");
        } catch (Exception e) {
            consolePrintln(e.getMessage());
        }

    }

    private void refreshDirectoryTree() {
        try {
            fileView.setRoot(createTreeFromPath(currentFolder));
            fileView.setShowRoot(true);
            fileView.setVisible(true);
            fileView.refresh();
        } catch (Exception e) {
            consolePrintln(e.getMessage());
        }
    }

    private TreeItem<String> createTreeFromPath(File directory) throws Exception {
        TreeItem<String> root = new TreeItem<>(directory.getAbsolutePath());
        root.setExpanded(true);
        List<TreeItem<String>> changedFiles = new LinkedList<>();
        createFileTree(directory, changedFiles, root);
        if (changedFiles.size() != 0) {
            for (TreeItem<String> file : changedFiles) {
                setAllParentGraphic(file, dirRedImg);
            }
        }
        return root;
    }

    private void createFileTree(File directory, List<TreeItem<String>> changedFiles, TreeItem<String> parent) throws Exception, IllegalAccessException {
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (!file.isHidden() && file.isDirectory()) {
                        TreeItem<String> fileItem = new TreeItem<>(file.getName(), new ImageView(dirYellowImg));
                        parent.getChildren().add(fileItem);
                        createFileTree(file, changedFiles, fileItem);

                    } else if (!file.isHidden() && file.isFile()) {
                        TreeItem<String> fileItem;
                        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd | HH:mm:ss");
                        if (!snapshotService.hasSnapshot(file.getAbsolutePath())) {
                            fileItem = new TreeItem<>(file.getName(), new ImageView(fileWhiteImg));
                        } else if (snapshotService.wasChanged(file.getAbsolutePath())) {
                            consolePrintln("Change detected in file: " + file.getAbsolutePath());
                            fileItem = new TreeItem<>(file.getName() + " ( " + dateFormat.format(new Date(snapshotService.getSnapshotTime(file.getAbsolutePath()))) + " )", new ImageView(fileRedImg));
                            changedFiles.add(fileItem);
                        } else {
                            parent.setGraphic(new ImageView(dirGreenImg));
                            setAllParentGraphic(parent, dirGreenImg);
                            fileItem = new TreeItem<>(file.getName() + " (" + dateFormat.format(new Date(snapshotService.getSnapshotTime(file.getAbsolutePath()))) + " )", new ImageView(fileGreenImg));
                        }
                        parent.getChildren().add(fileItem);
                    }
                }

            } else {
                throw new Exception("com.drozd.app.Controller.createFileTree() : directory passed is empty, path: " + directory.getAbsolutePath() + " )\n");
            }
        } else {
            throw new IllegalAccessException("com.drozd.app.Controller.createFileTree() : file passed is not a directory ( " + directory.getAbsolutePath() + " )\n");
        }
    }

    private void setAllParentGraphic(TreeItem<String> item, Image image) {
        TreeItem<String> parent = item.getParent();
        if (parent != null) {
            parent.setGraphic(new ImageView(image));
            TreeItem<String> currentParent = parent;
            while (currentParent.getParent() != null) {
                currentParent.setGraphic(new ImageView(image));
                currentParent = currentParent.getParent();
            }
        }
    }

    @FXML
    private void onCreateSnapshotButtonClicked() {
        if (currentFolder != null) {
            try {
                consolePrintln("Creating snapshot ...");
                long time = System.currentTimeMillis();
                createDirectorySnapshot(currentFolder.getAbsolutePath());
                time = System.currentTimeMillis() - time;
                SimpleDateFormat d = new SimpleDateFormat("mm:ss:SSS");
                consolePrintln("Snapshot created. Time elapsed: " + d.format(new Date(time)));
                consolePrintln("Snapshot saved in: " + System.getProperty("user.dir") + File.separator + ".md5");
                refreshDirectoryTree();
            } catch (Exception e) {
                consolePrintln(e.getMessage());
            }
        }
    }

    private void createDirectorySnapshot(String path) throws Exception {
        File directory = new File(path);
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (!file.isHidden() && file.isDirectory()) {
                        createDirectorySnapshot(file.getAbsolutePath());
                    } else if (!file.isHidden() && file.isFile()) {
                        snapshotService.createSnapshot(file.toPath());
                    }
                }
            } else {
                throw new Exception("com.drozd.app.Controller.createDirectorySnapshot() : directory passed is empty, path: " + directory.getAbsolutePath() + " )\n");
            }
        } else {
            throw new IllegalAccessException("com.drozd.app.Controller.createDirectorySnapshot() : file passed is not a directory ( " + directory.getAbsolutePath() + " )\n");
        }
    }


    private TreeItem<String> createItemFromFile(File file, TreeItem<String> parent) {
        TreeItem<String> item = new TreeItem<>(file.getName());
        item.setExpanded(false);
        parent.getChildren().add(item);
        return item;
    }

    private TreeItem<String> createItem(String text, TreeItem<String> parent, Boolean ifExpanded) {
        TreeItem<String> item = new TreeItem<>(text);
        item.setExpanded(ifExpanded);
        parent.getChildren().add(item);
        return item;
    }

    private void consoleClear() {
        consoleTextArea.setText("");
    }

    private void consolePrintln(String text) {
        consoleTextArea.appendText(text + "\n");
    }

    private void consolePrint(String text) {
        consoleTextArea.appendText(text);
    }
}