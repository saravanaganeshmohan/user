package com.bank.user.util;

import org.springframework.stereotype.Component;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.*;

@Component
public class AESUtil {

    /**
     * Method that encrypt and decrypt the input file based on the mode(Encrypt or Decrypt)*
     * @param cipherMode
     * @param key
     * @param inputFile
     * @param outputFile
     * @throws RuntimeException
     */
    public void doCrypto(int cipherMode, String key, byte[] inputBytes,
                         File outputFile) throws RuntimeException {
        try {
            Key secretKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(cipherMode, secretKey);
            byte[] outputBytes = cipher.doFinal(inputBytes);
            FileOutputStream outputStream = new FileOutputStream(outputFile);
            outputStream.write(outputBytes);
            outputStream.close();
        } catch (Exception exception) {
            System.out.println("Couldn't encrypt or decrypting" + exception.getMessage());
        }
    }
}

