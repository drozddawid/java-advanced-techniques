package com.drozd.processloader.classloaders;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FantasticClassLoader extends ClassLoader{
    Path classSourceDirectory;
    public FantasticClassLoader(Path classSourceDirectory) throws IllegalArgumentException{
        if(!Files.isDirectory(classSourceDirectory))
            throw new IllegalArgumentException("classSourceDirectory should be a directory.");

        this.classSourceDirectory = classSourceDirectory;
    }


    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        System.out.println("FantasticLoader: LOADING " + name);
        Path fullClassPath = Paths.get(classSourceDirectory.toString()
                + File.separator + name.replace(".", File.separator) + ".class");
        byte[] tmp;
        try{
            tmp = Files.readAllBytes(fullClassPath);
        }catch(IOException e){
            throw new ClassNotFoundException("Class: " + name + " not found in " + fullClassPath.toString(), e);
        }

        //return defineClass(name, tmp, 0, tmp.length); this requires class to be inside package folders,
        // i.e. class com.drozd.processors. Processor must be in com/drozd/processors/
        return defineClass(null, tmp, 0, tmp.length);
    }
}
