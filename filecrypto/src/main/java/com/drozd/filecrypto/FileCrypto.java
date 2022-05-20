package com.drozd.filecrypto;

import javax.crypto.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.*;
import java.util.Objects;

public class FileCrypto {
    private Crypto crypto;

    public FileCrypto(String algorithm) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        setSecurityOptions();
        if(algorithm.contains("AES")){
            crypto = new AESCrypto(algorithm);
        }else if(algorithm.contains("RSA")){
            crypto = new RSACrypto(algorithm);
        }else throw new NoSuchAlgorithmException("Algorithm " + algorithm + " is not supported by FileCrypto.");
    }

    private void setSecurityOptions() {
        Security.setProperty("crypto.policy", "unlimited");
        if (System.getSecurityManager() == null) System.setSecurityManager(new SecurityManager());
    }

    public void setKey(Key key, int keyLengthInBytes) throws InvalidKeyException {
        crypto.setKey(key, keyLengthInBytes);
    }

    public void setAlgorithm(String algorithm) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        if(algorithm.contains("AES")){
            crypto = new AESCrypto(algorithm);
        }else if(algorithm.contains("RSA")){
            crypto = new RSACrypto(algorithm);
        }else throw new NoSuchAlgorithmException("Algorithm " + algorithm + " is not supported by FileCrypto.");
    }

    public void encrypt(Path inputFilePath, Path outputFilePath) throws IOException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        crypto.encrypt(inputFilePath, outputFilePath);
    }

    public void decrypt(Path inputFilePath, Path outputFilePath) throws IOException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        crypto.decrypt(inputFilePath, outputFilePath);
    }
}
