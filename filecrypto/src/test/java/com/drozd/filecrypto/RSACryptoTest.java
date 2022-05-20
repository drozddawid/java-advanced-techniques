package com.drozd.filecrypto;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@Suite
@SuiteDisplayName("RSACrypto test")
class RSACryptoTest {
    private static PublicKey[] publicKeys;
    private static PrivateKey[] privateKeys;
    private static Path fileToEncrypt;
    private static String encryptedFile;
    private static String decryptedFile;
    private static String[] algorithms;
    private static Integer[] keySizes;

    @BeforeAll
    static void setUp() {
        try {
            algorithms = new String[]{"RSA/ECB/PKCS1Padding", "RSA/ECB/OAEPWithSHA-1AndMGF1Padding", "RSA/ECB/OAEPWithSHA-256AndMGF1Padding"};
            keySizes = new Integer[]{1024, 2048};
            publicKeys = new PublicKey[keySizes.length];
            privateKeys = new PrivateKey[keySizes.length];
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            int i = 0;
            for (Integer keySize : keySizes) {
                keyPairGenerator.initialize(keySize);
                KeyPair keyPair = keyPairGenerator.generateKeyPair();
                publicKeys[i] = keyPair.getPublic();
                privateKeys[i] = keyPair.getPrivate();
                i++;
            }

            fileToEncrypt = Paths.get(RSACryptoTest.class.getResource("/data_to_encrypt.txt").toURI());
            encryptedFile = fileToEncrypt.getParent().toString() + File.separator + "encrypted_";
            decryptedFile = fileToEncrypt.getParent().toString() + File.separator + "decrypted_";
        } catch (URISyntaxException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @AfterAll
    static void tearDown() {
        Arrays.stream(algorithms).forEach(alg -> {
            Path encryptedPublicFilePath;
            Path encryptedPrivateFilePath;
            Path decryptedPublicFilePath;
            Path decryptedPrivateFilePath;
            for (int i = 0; i < publicKeys.length; i++) {
                encryptedPublicFilePath = Paths.get(encryptedFile + alg.replaceAll("/", "_") + "_" + keySizes[i] + "_encWithPublic" + ".benc");
                encryptedPrivateFilePath = Paths.get(encryptedFile + alg.replaceAll("/", "_") + "_" + keySizes[i] + "_encWithPrivate" + ".benc");
                decryptedPublicFilePath = Paths.get(decryptedFile + alg.replaceAll("/", "_") + "_" + keySizes[i] + "_encWithPublic" + ".txt");
                decryptedPrivateFilePath = Paths.get(decryptedFile + alg.replaceAll("/", "_") + "_" + keySizes[i] + "_encWithPrivate" + ".txt");
                try {
                    Files.deleteIfExists(encryptedPublicFilePath);
                    Files.deleteIfExists(encryptedPrivateFilePath);
                    Files.deleteIfExists(decryptedPublicFilePath);
                    Files.deleteIfExists(decryptedPrivateFilePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Test
    void encrypt() {
        assertEquals(publicKeys.length, privateKeys.length);
        Arrays.stream(algorithms).forEach(alg -> {
            try {
                Key key;
                Path encryptedFilePath;
                for (int i = 0; i < publicKeys.length; i++) {
                    RSACrypto rsaCrypto = new RSACrypto(alg);
                    key = publicKeys[i];
                    encryptedFilePath = Paths.get(encryptedFile + alg.replaceAll("/", "_") + "_" + keySizes[i] + "_encWithPublic" + ".benc");
                    rsaCrypto.setKey(key, keySizes[i] / 8);
                    rsaCrypto.encrypt(fileToEncrypt, encryptedFilePath);

                    if (!alg.contains("OAEP")) { // can't encrypt(sign) with private key using OAEP
                        key = privateKeys[i];
                        rsaCrypto.setKey(key, keySizes[i] / 8);
                        encryptedFilePath = Paths.get(encryptedFile + alg.replaceAll("/", "_") + "_" + keySizes[i] + "_encWithPrivate" + ".benc");
                        rsaCrypto.encrypt(fileToEncrypt, encryptedFilePath);
                    }
                }
            } catch (InvalidKeyException | BadPaddingException | IOException | IllegalBlockSizeException | NoSuchPaddingException | NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    void decrypt() {
        assertEquals(publicKeys.length, privateKeys.length);
        Arrays.stream(algorithms).forEach(alg -> {
            try {
                Key key;
                Path encryptedFilePath;
                Path decryptedFilePath;
                for (int i = 0; i < publicKeys.length; i++) {
                    RSACrypto rsaCrypto = new RSACrypto(alg);
                    key = privateKeys[i];
                    encryptedFilePath = Paths.get(encryptedFile + alg.replaceAll("/", "_") + "_" + keySizes[i] + "_encWithPublic" + ".benc");
                    decryptedFilePath = Paths.get(decryptedFile + alg.replaceAll("/", "_") + "_" + keySizes[i] + "_encWithPublic" + ".txt");
                    rsaCrypto.setKey(key, keySizes[i] / 8);
                    rsaCrypto.decrypt(encryptedFilePath, decryptedFilePath);
                    StreamComparator.assertStreamsHaveTheSameContent(Files.newInputStream(fileToEncrypt), Files.newInputStream(decryptedFilePath));

                    if (!alg.contains("OAEP")) { // can't encrypt(sign) with private key using OAEP
                        key = publicKeys[i];
                        rsaCrypto.setKey(key, keySizes[i] / 8);
                        encryptedFilePath = Paths.get(encryptedFile + alg.replaceAll("/", "_") + "_" + keySizes[i] + "_encWithPrivate" + ".benc");
                        decryptedFilePath = Paths.get(decryptedFile + alg.replaceAll("/", "_") + "_" + keySizes[i] + "_encWithPrivate" + ".txt");
                        rsaCrypto.decrypt(encryptedFilePath, decryptedFilePath);
                        StreamComparator.assertStreamsHaveTheSameContent(Files.newInputStream(fileToEncrypt), Files.newInputStream(decryptedFilePath));
                    }
                }
            } catch (InvalidKeyException | BadPaddingException | IOException | IllegalBlockSizeException | NoSuchPaddingException | NoSuchAlgorithmException | InvalidAlgorithmParameterException e) {
                e.printStackTrace();
            }
        });
    }
}