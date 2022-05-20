package com.drozd.filecrypto;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.*;
import java.util.Objects;

public class AESCrypto implements Crypto {
    private final Cipher cipher;
    private Key key;
    private final String algorithm;

    public AESCrypto(String algorithm) throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException {
        if (!algorithm.contains("AES")) throw new NoSuchAlgorithmException("Given algorithm should be AES.");
        this.algorithm = Objects.requireNonNull(algorithm);
        this.cipher = Cipher.getInstance(algorithm);
    }

    @Override
    public void setKey(Key key, int keySizeInBytes) {
        this.key = Objects.requireNonNull(key);
    }

    public AESCrypto(Key key, String algorithm) throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException {
        if (!key.getAlgorithm().contains("AES"))
            throw new InvalidKeyException("Given key algorithm should be AES. Given key algorithm: " + key.getAlgorithm());
        if (!algorithm.contains("AES")) throw new NoSuchAlgorithmException("Given algorithm should be AES.");
        this.key = Objects.requireNonNull(key);
        this.algorithm = Objects.requireNonNull(algorithm);
        this.cipher = Cipher.getInstance(algorithm);
    }

    @Override
    public void encrypt(Path inputFile, Path outputFile) throws IOException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        if (key == null) throw new IllegalStateException("Key has not been set.");
        InputStream inputStream = Files.newInputStream(inputFile, StandardOpenOption.READ);
        FileOutputStream outputStream = new FileOutputStream(outputFile.toFile(), false);
        byte[] iv = new byte[16];
        if (algorithm.contains("CBC")) {
            try {
                new SecureRandom().nextBytes(iv);
                IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
                cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec);
                outputStream.write(iv);
            } catch (InvalidAlgorithmParameterException e) {
                e.printStackTrace();
            }
        }else if (algorithm.contains("ECB")){
            cipher.init(Cipher.ENCRYPT_MODE, key);
        }

        runCipher(inputStream, outputStream);
    }

    @Override
    public void decrypt(Path inputFile, Path outputFile) throws IOException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        if (key == null) throw new IllegalStateException("Key has not been set.");
        InputStream inputStream = Files.newInputStream(inputFile, StandardOpenOption.READ);
        FileOutputStream outputStream = new FileOutputStream(outputFile.toFile(), false);
        if (algorithm.contains("CBC")) {
            byte[] iv = inputStream.readNBytes(16);
            if (iv == null) return;
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
        }else if (algorithm.contains("ECB")){
            cipher.init(Cipher.DECRYPT_MODE, key);
        }

        runCipher(inputStream, outputStream);
    }

    private void runCipher(InputStream inputStream, FileOutputStream outputStream) throws IOException, IllegalBlockSizeException, BadPaddingException {
        byte[] inputBuffer = new byte[16];
        byte[] outputBuffer;
        int bytesRead;
        while ((bytesRead = inputStream.read(inputBuffer)) != -1) {
            outputBuffer = cipher.update(inputBuffer, 0, bytesRead);
            if (outputBuffer != null) outputStream.write(outputBuffer);
        }
        outputBuffer = cipher.doFinal();
        if (outputBuffer != null) outputStream.write(outputBuffer);
        inputStream.close();
        outputStream.close();
    }
}
