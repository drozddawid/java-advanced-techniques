package com.drozd.filecrypto;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.io.IOException;
import java.nio.file.Path;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;

public interface Crypto {
    void encrypt(Path inputFile, Path outputFile) throws IOException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException;
    void decrypt(Path inputFile, Path outputFile) throws IOException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException;
    void setKey(Key key, int keySizeInBytes) throws InvalidKeyException;
}
