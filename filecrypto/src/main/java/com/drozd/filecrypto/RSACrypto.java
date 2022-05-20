package com.drozd.filecrypto;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.*;
import java.util.Arrays;
import java.util.Objects;

public class RSACrypto implements Crypto {
    private final Cipher cipher;
    private final String algorithm;
    private Key key;
    private Integer encryptBufferLen;
    private Integer decryptBufferLen;

    public RSACrypto(String algorithm) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException {
        if (!algorithm.contains("RSA")) throw new NoSuchAlgorithmException("Given algorithm should be RSA.");
        this.algorithm = Objects.requireNonNull(algorithm);
        this.cipher = Cipher.getInstance(algorithm);
    }

    public RSACrypto(String algorithm, Key key, int keySizeInBytes) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException {
        if (!key.getAlgorithm().contains("RSA"))
            throw new InvalidKeyException("Given key algorithm should be RSA. Given key algorithm: " + key.getAlgorithm());
        if (!algorithm.contains("RSA")) throw new NoSuchAlgorithmException("Given algorithm should be RSA.");
        this.algorithm = Objects.requireNonNull(algorithm);
        this.cipher = Cipher.getInstance(algorithm);
        setKey(key, keySizeInBytes);
    }

    @Override
    public void setKey(Key key, int keySizeInBytes) throws InvalidKeyException {
        this.key = Objects.requireNonNull(key);
        encryptBufferLen = keySizeInBytes;
        decryptBufferLen = keySizeInBytes;
        if (algorithm.contains("OAEPWithSHA-1AndMGF1Padding")) { // max size key_length - 42 bytes
            encryptBufferLen -= 42;
        } else if (algorithm.contains("OAEPWithSHA-256AndMGF1Padding")) { // max size key_length - 66 bytes
            encryptBufferLen -= 66;
        } else {
            encryptBufferLen -= 11;
        }

    }

    @Override
    public void encrypt(Path inputFile, Path outputFile) throws IOException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        if (key == null) throw new IllegalStateException("Key has not been set.");
        InputStream inputStream = Files.newInputStream(inputFile, StandardOpenOption.READ);
        FileOutputStream outputStream = new FileOutputStream(outputFile.toFile(), false);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        runCipher(inputStream, outputStream, encryptBufferLen);
    }

    @Override
    public void decrypt(Path inputFile, Path outputFile) throws IOException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        if (key == null) throw new IllegalStateException("Key has not been set.");
        InputStream inputStream = Files.newInputStream(inputFile, StandardOpenOption.READ);
        FileOutputStream outputStream = new FileOutputStream(outputFile.toFile(), false);
        cipher.init(Cipher.DECRYPT_MODE, key);
        runCipher(inputStream, outputStream, decryptBufferLen);
    }

    private void runCipher(InputStream inputStream, FileOutputStream outputStream, Integer bufferLen) throws IOException, IllegalBlockSizeException, BadPaddingException {
        byte[] inputBuffer = new byte[bufferLen];
        byte[] outputBuffer;
        int bytesRead;
        while ((bytesRead = inputStream.read(inputBuffer)) != -1) {
            outputBuffer = cipher.doFinal(inputBuffer, 0, bytesRead);
            if (outputBuffer != null) outputStream.write(outputBuffer);
        }
        inputStream.close();
        outputStream.close();
    }
}
