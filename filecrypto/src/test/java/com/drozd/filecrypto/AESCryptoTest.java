package com.drozd.filecrypto;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

@Suite
@SuiteDisplayName("AESCrypto test")
class AESCryptoTest {
    private static Key key;
    private static Path fileToEncrypt;
    private static String encryptedFile;
    private static String decryptedFile;
    private static String[] algorithms;

    @BeforeAll
    static void setUp() {
        try {
            algorithms = new String[]{"AES/CBC/PKCS5Padding", "AES/ECB/PKCS5Padding"};
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            keygen.init(128);
            key = keygen.generateKey();

            fileToEncrypt = Paths.get(AESCryptoTest.class.getResource("/data_to_encrypt.txt").toURI());
            encryptedFile = fileToEncrypt.getParent().toString() + File.separator + "encrypted_";
            decryptedFile = fileToEncrypt.getParent().toString() + File.separator + "decrypted_";

        } catch (NoSuchAlgorithmException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @AfterAll
    static void tearDown() {
        Arrays.stream(algorithms).forEach(alg -> {
            try {
                Path encryptedFilePath = Paths.get(encryptedFile + alg.replaceAll("/", "_") + ".benc");
                Path decryptedFilePath = Paths.get(decryptedFile + alg.replaceAll("/", "_") + ".txt");
                Files.deleteIfExists(encryptedFilePath);
                Files.deleteIfExists(decryptedFilePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    void encrypt() {
        Arrays.stream(algorithms).forEach(alg -> {
            try {
                AESCrypto aesCrypto = new AESCrypto(key, alg);
                Path encryptedFilePath = Paths.get(encryptedFile + alg.replaceAll("/", "_") + ".benc");
                aesCrypto.encrypt(fileToEncrypt, encryptedFilePath);
            } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | IllegalBlockSizeException | IOException | BadPaddingException e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    void decrypt() {
        Arrays.stream(algorithms).forEach(alg -> {
            try {
                AESCrypto aesCrypto = new AESCrypto(key, alg);
                Path encryptedFilePath = Paths.get(encryptedFile + alg.replaceAll("/", "_") + ".benc");
                Path decryptedFilePath = Paths.get(decryptedFile + alg.replaceAll("/", "_") + ".txt");

                aesCrypto.decrypt(encryptedFilePath, decryptedFilePath);

                InputStream fileToEncryptStream = Files.newInputStream(fileToEncrypt, StandardOpenOption.READ);
                InputStream decryptedFileStream = Files.newInputStream(decryptedFilePath, StandardOpenOption.READ);
                StreamComparator.assertStreamsHaveTheSameContent(fileToEncryptStream, decryptedFileStream);
            } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | IllegalBlockSizeException | IOException | BadPaddingException | InvalidAlgorithmParameterException e) {
                e.printStackTrace();
            }
        });
    }

}