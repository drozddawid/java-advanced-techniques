package com.drozd.contentreaders;

import javafx.scene.Node;
import javafx.scene.text.Font;

import java.nio.file.Path;

public interface ContentReader {
    //content source path must be a directory
    void setContentSourcePath(Path path) throws IllegalArgumentException;
    Node getContentNode(String id) throws IllegalStateException;
    void setNodeWidth(int width);
    void setNodeHeight(int height);
    void setFont(Font font);
    boolean isLoadedFromDisk();
}
