package com.drozd.filecrypto;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class StreamComparator {
    public static void assertStreamsHaveTheSameContent(InputStream file1, InputStream file2) throws IOException {
        byte[] expectedBuffer = new byte[64];
        byte[] actualBuffer = new byte[64];
        while (file1.read(expectedBuffer) != -1
                && file2.read(actualBuffer) != -1) {
            assertArrayEquals(expectedBuffer, actualBuffer);
        }
    }
}
