package com.drozd.contentreaders;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.HashMap;

public class Reader implements ContentReader {
    private Path sourcePath = null;
    private Integer width = 486;
    private Integer height = 577;
    private Font font = null;
    private HashMap<String, WeakReference<Node>> currentSourceNodes; //each node represents one person in one source
    private HashMap<Path, WeakReference<HashMap<String, WeakReference<Node>>>> contentSources = new HashMap<>();
    private boolean isLastNodeLoadedFromDisk = true;



    public void cleanUp(){
        contentSources.values().remove(null);
        currentSourceNodes.values().remove(null);
    }

    @Override
    public void setContentSourcePath(Path path) throws IllegalArgumentException {
        if (Files.isDirectory(path)) {
            sourcePath = path;
            WeakReference<HashMap<String, WeakReference<Node>>> contentSourceReference = contentSources.get(path);
            if (contentSourceReference == null) {
                //adding new nodes hashmap reference to contentSources when no reference found
                currentSourceNodes = new HashMap<>();
                contentSources.put(path, new WeakReference<>(currentSourceNodes));
            } else {
                HashMap<String, WeakReference<Node>> contentSource = contentSourceReference.get();
                if (contentSource == null) {
                    //replacing null with new nodes hashmap reference
                    currentSourceNodes = new HashMap<>();
                    contentSources.replace(path, new WeakReference<>(currentSourceNodes));
                } else {
                    //setting found content source
                    currentSourceNodes = contentSource;
                }
            }
        } else
            throw new IllegalArgumentException("com.drozd.contentreaders.Reader.setContentSourcePath(): given path is not a directory. Path: " + path.toString());
    }

    @Override
    public Node getContentNode(String id) {
        if (sourcePath != null) {
            WeakReference<Node> nodeReference = currentSourceNodes.get(id);
            if (nodeReference == null) {
                Node node = loadNode(id);
                currentSourceNodes.put(id, new WeakReference<>(node));
                return node;
            } else {
                Node node = nodeReference.get();
                if (node == null) {
                    node = loadNode(id);
                    currentSourceNodes.put(id, new WeakReference<>(node));
                    return node;
                } else {
                    isLastNodeLoadedFromDisk = false;
                    return node;
                }
            }
        }else{
            throw new IllegalStateException("com.drozd.contentreaders.Reader.getContentNode() : Source path is not set. Use setContentSourcePath() to set source directory before calling getContentNode().");
        }
    }

    @Override
    public boolean isLoadedFromDisk() {
        return isLastNodeLoadedFromDisk;
    }

    private Node loadNode(String id) {
        isLastNodeLoadedFromDisk = true;
        VBox box = getvBox();
        Path filePath = getImagePath(id);
        boolean isEmpty = true;
        if(Files.exists(filePath)) {
            try {
                ImageView imageView = new ImageView(new Image(Files.newInputStream(filePath, StandardOpenOption.READ)));
                imageView.setFitHeight(100);
                imageView.setFitWidth(100);
                box.getChildren().add(imageView);
                isEmpty = false;
            } catch (IOException ignored) {}
        }
        filePath = getRecordPath(id);
        if(Files.exists(filePath)) {
            try {
                Label label = getLabel(new String(Files.newInputStream(filePath, StandardOpenOption.READ).readAllBytes()));
                box.getChildren().add(label);
                isEmpty = false;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(isEmpty){
            Label label = getLabel("Directory is empty.");
            box.getChildren().add(label);
        }
        return box;
    }
    private Label getLabel(String text){
        Label label = new Label(text);
        if(font !=null) label.setFont(font);
        return label;
    }

    private Path getRecordPath(String id) {
        return Paths.get(sourcePath.toString()+ File.separator + id + File.separator + "record.txt");
    }

    private Path getImagePath(String id) {
        return Paths.get(sourcePath.toString()+ File.separator + id + File.separator + "image.png");
    }

    private VBox getvBox() {
        VBox box = new VBox();
        box.prefHeight(height);
        box.prefWidth(width);
        box.minHeight(Region.USE_COMPUTED_SIZE);
        box.minWidth(Region.USE_COMPUTED_SIZE);
        box.maxHeight(Region.USE_COMPUTED_SIZE);
        box.maxWidth(Region.USE_COMPUTED_SIZE);
        box.setAlignment(Pos.TOP_CENTER);
        return box;
    }

    @Override
    public void setNodeWidth(int width) {
        if (width > 0) {
            this.width = width;
        }
    }

    @Override
    public void setNodeHeight(int height) {
        if (width > 0 && height > 0) {
            this.height = height;
        }
    }

    @Override
    public void setFont(Font font) {
        this.font = font;
    }
}
