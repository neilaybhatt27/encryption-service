package com.greatkapital.encryptionservice.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class AESUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(AESUtil.class);

    /**
     * This method is to encrypt the plain text string using AES algorithm.
     *
     * @param plainText Input string which needs to be encrypted.
     * @param key SecretKey which will encrypt the input string
     * @param ivParameterSpec Initialization vector which is a pseudo-random value.
     * @return THe encrypted string
     * @throws Exception If an error occurs during encryption.
     */
    public static String encrypt(String plainText, SecretKey key, IvParameterSpec ivParameterSpec) throws Exception {
        LOGGER.info("IN AESUtil.encrypt with input string: {}", plainText);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        LOGGER.info("OUT AESUtil.encrypt");
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    /**
     * This method decrypts the encrypted texts to the original String.
     *
     * @param encryptedText String containing the encrypted texts
     * @param key SecretKey object containing the secret key.
     * @param ivParameterSpec IvParameterSpec representing the Initialization Vector.
     * @return The decrypted string.
     * @throws Exception If an error occurs during decryption.
     */
    public static String decrypt(String encryptedText, SecretKey key, IvParameterSpec ivParameterSpec) throws Exception {
        LOGGER.info("IN AESUtil.decrypt with input string: {}", encryptedText);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
        LOGGER.info("OUT AESUtil.decrypt");
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    /**
     * This method generates a secret key of specified size.
     *
     * @param size integer value representing size of bits (128, 256, etc.).
     * @return SecretKey object representing the generated key.
     * @throws NoSuchAlgorithmException If there is no algorithm found.
     */
    public static SecretKey generateKey(int size) throws NoSuchAlgorithmException {
        LOGGER.info("IN AESUtil.generateKey with block size {}", size);
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(size);
        SecretKey key = keyGenerator.generateKey();
        LOGGER.info("OUT AESUtil.generateKey with key: {}", key);
        return key;
    }

    /**
     * This method generates an initialization vector.
     *
     * @return IvParameterSpec object containing the initialization vector.
     */
    public static IvParameterSpec generateIv() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }
}
