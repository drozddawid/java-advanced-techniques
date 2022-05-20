package com.drozd.filecrypto;


import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses(value = {AESCryptoTest.class, RSACryptoTest.class})
public class TestSuite {
}
