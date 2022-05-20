package com.drozd.lib.service;

import com.drozd.lib.model.FileSnapshot;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;

public class SnapshotService {
    private MessageDigest md;

    public SnapshotService() {
        try {
            this.md = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean hasSnapshot(String path) {
        String snapshotPath;
        if (Files.isDirectory(Paths.get(path))) {
            snapshotPath = getSnapshotDirectoryPath(path);
        } else {
            snapshotPath = getFullSnapshotFilePath(path);
        }
        return Paths.get(snapshotPath).toFile().exists();
    }

    private String getFullSnapshotFilePath(String path) {
        String snapshotPath;
        snapshotPath = getSnapshotDirectoryPath(path) + File.separator + getSnapshotFileName(path) + ".md5";
        return snapshotPath;
    }

    public boolean wasChanged(String path) throws Exception {
        Path filePath = Paths.get(path);
        Path snapshotFilePath = Paths.get(getFullSnapshotFilePath(path));
        if (Files.isDirectory(filePath)) {
            throw new IllegalArgumentException("com.drozd.lib.SnapshotService.wasChanged() : passed path is directory. Path: " + path.toString());
        } else {
            FileSnapshot currentFileSnapshot = new FileSnapshot(filePath, md);
            FileSnapshot savedFileSnapshot = FileSnapshot.parseJSON(snapshotFilePath);
            return !compareByteArrays(currentFileSnapshot.getMd5(), savedFileSnapshot.getMd5());
        }
    }

    public Long getSnapshotTime(String path) throws IOException {
        Path filePath = Paths.get(path);
        Path snapshotFilePath = Paths.get(getFullSnapshotFilePath(path));
        if (Files.isDirectory(filePath)) {
            throw new IllegalArgumentException("com.drozd.lib.SnapshotService.wasChanged() : passed path is directory. Path: " + path.toString());
        } else {
            FileSnapshot savedFileSnapshot = FileSnapshot.parseJSON(snapshotFilePath);
            return savedFileSnapshot.getUpdateTime().getTime();
        }
    }

    private boolean compareByteArrays(byte[] a, byte[] b) {
        if (a.length != b.length) {
            return false;
        }
        for (int i = 0; i < a.length; i++) {
            if (a[i] != b[i]) {
                return false;
            }
        }
        return true;
    }

    public void createSnapshot(Path filePath) {
        if (!Files.isDirectory(filePath)) {
            try {
                FileSnapshot fileSnapshot = new FileSnapshot(filePath, md);
                String snapshotDestinationDirectoryPath = filePath.toString();

                String snapshotFileName = getSnapshotFileName(snapshotDestinationDirectoryPath);
                snapshotDestinationDirectoryPath = getSnapshotDirectoryPath(snapshotDestinationDirectoryPath);

                // S  ystem.out.println("Snapshotting " + snapshotFileName);

                File snapshotFile = new File(snapshotDestinationDirectoryPath);
                snapshotFile.mkdirs();

                Process p = Runtime.getRuntime().exec("attrib +h -r \"" + System.getProperty("user.dir") + File.separator + ".md5\"");
                p.waitFor();
                snapshotDestinationDirectoryPath = snapshotDestinationDirectoryPath + File.separator + snapshotFileName + ".md5";
                try {
                    FileWriter fw = new FileWriter(snapshotDestinationDirectoryPath);
                    BufferedWriter bw = new BufferedWriter(fw);
                    bw.write(' ');
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                fileSnapshot.toJSON(Paths.get(snapshotDestinationDirectoryPath));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            throw new IllegalArgumentException("com.drozd.lib.service.SnapshotSevice.createSnapshot() : passed path is directory. Path: " + filePath.toString());
        }
    }

    //filePath is path to some file, for instance file.txt
    //returns snapshot filename in format <filename>_<file_extension>, for example file_txt
    private String getSnapshotFileName(String filePath) {
        int tmpIndex = filePath.lastIndexOf(File.separator);
        return filePath.substring(tmpIndex).substring(1).replace(".", "_");
    }

    //returns path where snapshot file will be saved
    private String getSnapshotDirectoryPath(String filePath) {
        String path = filePath.substring(0, filePath.lastIndexOf(File.separator));
        path = path.replace(":" + File.separator, File.separator);
        path = System.getProperty("user.dir") + File.separator + ".md5" + File.separator + path;
        return path;
    }
}
