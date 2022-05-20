package com.drozd.filecryptoapp;

import com.drozd.filecrypto.FileCrypto;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.interfaces.RSAKey;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private Path inputFilePath = null;
    private Path outputFilePath = null;
    private KeyStore keyStore = null;
    private KeyPair keyPair = null;
    private boolean encryptWithPrivate = false;
    private SecretKey secretKey = null;
    private List<String> rsaAlgorithms;
    private ArrayList<String> aesAlgorithms;

    @FXML
    private ComboBox<String> chooseAlgComboBox;

    @FXML
    private ComboBox<String> chooseKeyComboBox;

    @FXML
    private Label inputFileLabel;

    @FXML
    private Label keystoreFileLabel;

    @FXML
    private PasswordField keystorePasswordField;

    @FXML
    private Label outputFileLabel;

    @FXML
    private Label infoLabel;

    @FXML
    private Button chooseAsymetricEncKeyTypeButton;

    @FXML
    void initialize() {
        setAsymetricEncKeyVisibility(false);

        aesAlgorithms = new ArrayList<>();
        aesAlgorithms.addAll(List.of("AES/CBC/PKCS5Padding", "AES/ECB/PKCS5Padding"));

        rsaAlgorithms = new ArrayList<>();
        rsaAlgorithms.addAll(List.of("RSA/ECB/PKCS1Padding", "RSA/ECB/OAEPWithSHA-1AndMGF1Padding", "RSA/ECB/OAEPWithSHA-256AndMGF1Padding"));

    }

    @FXML
    void onDecryptButtonClick(ActionEvent event) {
        String selectedAlgorithm = chooseAlgComboBox.getSelectionModel().getSelectedItem();
        if (inputFilePath != null && outputFilePath != null && keyStore != null && selectedAlgorithm != null) {
            try {
                FileCrypto fileCrypto = new FileCrypto(selectedAlgorithm);
                boolean keyWasSet = false;
                if (keyPair != null) {
                    Key key;
                    if (selectedAlgorithm.contains("OAEP")) {
                        key = keyPair.getPrivate();
                    } else {
                        key = (encryptWithPrivate ? keyPair.getPublic() : keyPair.getPrivate());
                    }
                    RSAKey rsaKey = (RSAKey) key;
                    int keyLengthInBytes = rsaKey.getModulus().bitLength() / Byte.SIZE;
                    fileCrypto.setKey(key, keyLengthInBytes);
                    keyWasSet = true;
                } else if (secretKey != null) {
                    fileCrypto.setKey(secretKey, secretKey.getEncoded().length);
                    keyWasSet = true;
                } else {
                    infoLabel.setText("Select key.");
                }
                if (keyWasSet) {
                    fileCrypto.decrypt(inputFilePath, outputFilePath);
                    infoLabel.setText("Successfully decrypted.");

                }
            } catch (NoSuchPaddingException | NoSuchAlgorithmException | IllegalBlockSizeException | IOException | BadPaddingException | InvalidKeyException | InvalidAlgorithmParameterException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void onEncryptButtonClick(ActionEvent event) {
        String selectedAlgorithm = chooseAlgComboBox.getSelectionModel().getSelectedItem();
        if (inputFilePath != null && outputFilePath != null && keyStore != null && selectedAlgorithm != null) {
            try {
                FileCrypto fileCrypto = new FileCrypto(selectedAlgorithm);
                boolean keyWasSet = false;
                if (keyPair != null) {
                    Key key;
                    if (selectedAlgorithm.contains("OAEP")) {
                        key = keyPair.getPublic();
                    } else {
                        key = (encryptWithPrivate ? keyPair.getPrivate() : keyPair.getPublic());
                    }
                    RSAKey rsaKey = (RSAKey) key;
                    int keyLengthInBytes = rsaKey.getModulus().bitLength() / Byte.SIZE;
                    fileCrypto.setKey(key, keyLengthInBytes);
                    keyWasSet = true;
                } else if (secretKey != null) {
                    fileCrypto.setKey(secretKey, 1024);
                    keyWasSet = true;
                } else {
                    infoLabel.setText("Select key.");
                }
                if (keyWasSet) {
                    fileCrypto.encrypt(inputFilePath, outputFilePath);
                    infoLabel.setText("Successfully encrypted.");
                }
            } catch (NoSuchPaddingException | NoSuchAlgorithmException | IllegalBlockSizeException | IOException | BadPaddingException | InvalidKeyException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void onSelectInputFileClick(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        fileChooser.setTitle("Select input file");
        File file = fileChooser.showOpenDialog(new Stage());
        if (file == null) return;
        inputFilePath = file.toPath();
        inputFileLabel.setText(inputFilePath.toString());
    }

    @FXML
    void onSelectKeyStoreFileClick(ActionEvent event) {
        char[] pwd = keystorePasswordField.getText().strip().toCharArray();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        fileChooser.setTitle("Select keystore");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PKCS12 keystore", "*.pfx", "*.p12"));
        File keystoreFile = fileChooser.showOpenDialog(new Stage());
        if (keystoreFile == null) {
            keyStore = null;
            return;
        }
        Path keystorePath = keystoreFile.toPath();
        keystoreFileLabel.setText(keystorePath.toString());
        try {
            keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(Files.newInputStream(keystorePath, StandardOpenOption.READ), pwd);
            infoLabel.setText("");
            refreshKeyAliases();
        } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            if (e.getCause() instanceof UnrecoverableKeyException) {
                infoLabel.setText("Wrong keystore password.");
                clearKeyAliases();
            }
            e.printStackTrace();
        }
    }

    @FXML
    void onChooseKey(ActionEvent event) {
        String selectedKeyAlias = chooseKeyComboBox.getSelectionModel().getSelectedItem();
        if (selectedKeyAlias == null || selectedKeyAlias.length() < 1) {
            infoLabel.setText("Select key alias.");
            return;
        }
        char[] keyPwd = keystorePasswordField.getText().toCharArray();
        try {
            Key key = keyStore.getKey(selectedKeyAlias, keyPwd);
            if (key instanceof PrivateKey) {
                Certificate cert = keyStore.getCertificate(selectedKeyAlias);
                PublicKey publicKey = cert.getPublicKey();
                keyPair = new KeyPair(publicKey, (PrivateKey) key);
                secretKey = null;
            } else if (key instanceof SecretKey) {
                secretKey = (SecretKey) key;
                keyPair = null;
            }
            System.out.println(key.getAlgorithm());
            switch (key.getAlgorithm()) {
                case "RSA":
                    setAsymetricEncKeyVisibility(true);
                    chooseAlgComboBox.setItems(FXCollections.observableList(rsaAlgorithms));
                    infoLabel.setText("");
                    break;
                case "AES":
                    setAsymetricEncKeyVisibility(false);
                    chooseAlgComboBox.setItems(FXCollections.observableList(aesAlgorithms));
                    infoLabel.setText("");
                    break;
                default:
                    setAsymetricEncKeyVisibility(false);
                    chooseAlgComboBox.setItems(FXCollections.observableList(new ArrayList<>()));
                    infoLabel.setText("Unsupported key algorithm. Supported algorithms are AES and RSA.");
            }
        } catch (KeyStoreException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            infoLabel.setText("Wrong key password.");
            e.printStackTrace();
        }
    }

    void clearKeyAliases() {
        chooseKeyComboBox.setItems(FXCollections.observableList(new ArrayList<>()));
    }

    void refreshKeyAliases() {
        if (keyStore != null) {
            try {
                List<String> aliases = new ArrayList<>();
                keyStore.aliases().asIterator().forEachRemaining(aliases::add);
                chooseKeyComboBox.setItems(FXCollections.observableList(aliases));
            } catch (KeyStoreException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void onSelectOutputFileClick(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        fileChooser.setTitle("Select output file");
        File file = fileChooser.showOpenDialog(new Stage());
        if (file == null) return;
        outputFilePath = file.toPath();
        outputFileLabel.setText(outputFilePath.toString());
    }

    @FXML
    void onChooseAsymetricEncKeyButtonClick(ActionEvent event) {
        changeAsymetricEncKey(!encryptWithPrivate);
    }

    void setAsymetricEncKeyVisibility(boolean isVisible) {
        chooseAsymetricEncKeyTypeButton.setVisible(isVisible);
        chooseAsymetricEncKeyTypeButton.setManaged(isVisible);
    }

    void changeAsymetricEncKey(boolean state) {
        encryptWithPrivate = state;
        if (state) {
            chooseAsymetricEncKeyTypeButton.setText("Encrypt with private key / Decrypt with public key");
        } else {
            chooseAsymetricEncKeyTypeButton.setText("Encrypt with public key / Decrypt with private key");
        }
    }

    @FXML
    void onSwapFilesButtonClick(ActionEvent e) {
        Path tmp = outputFilePath;
        outputFilePath = inputFilePath;
        inputFilePath = tmp;
        outputFileLabel.setText(outputFilePath.toString());
        inputFileLabel.setText(inputFilePath.toString());
    }

    @FXML
    void onSetAlgorithm(ActionEvent event) {
        String selectedAlgorithm = chooseAlgComboBox.getSelectionModel().getSelectedItem();
        if (selectedAlgorithm == null || selectedAlgorithm.length() < 1) {
            infoLabel.setText("Select algorithm to contiune.");
            return;
        }
        if (selectedAlgorithm.contains("RSA")) {
            setAsymetricEncKeyVisibility(true);
            if (selectedAlgorithm.contains("OAEP")) {
                changeAsymetricEncKey(false);
                chooseAsymetricEncKeyTypeButton.setDisable(true);
            } else {
                chooseAsymetricEncKeyTypeButton.setDisable(false);
            }

        } else if (selectedAlgorithm.contains("AES")) {
            setAsymetricEncKeyVisibility(false);
        }
    }
}