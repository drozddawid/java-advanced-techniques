package com.drozd.lib.model;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.Date;


public class FileSnapshot implements Serializable {
    private byte[] md5;
    private final Date updateTime;

    public FileSnapshot(){
        md5 = null;
        updateTime = new Date(System.currentTimeMillis());
    }

    public FileSnapshot(byte[] md5, Date updateTime) {
        this.md5 = md5;
        this.updateTime = updateTime;
    }

    public FileSnapshot(Path path, MessageDigest md) throws Exception {
        if (!Files.isDirectory(path)) {
            updateTime = new Date(System.currentTimeMillis());
            try {
                md.update(Files.readAllBytes(path));
                md5 = md.digest();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            throw new Exception("com.drozd.lib.model.FileSnapshot.FileSnapshot() : Passed file is directory. Path: " + path.toString());
        }
    }

    public void toJSON(Path destinationPath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(destinationPath.toFile(), this);
    }

    public static FileSnapshot parseJSON(Path filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(filePath.toFile(), FileSnapshot.class);
    }

    public byte[] getMd5() {
        return md5;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setMd5(byte[] md5) {
        this.md5 = md5;
    }


}
