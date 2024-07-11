package com.greatkapital.encryptionservice.service;

import com.greatkapital.encryptionservice.model.EncryptedMessageResponsePOJO;
import com.greatkapital.encryptionservice.model.MessageRequestPOJO;
import com.greatkapital.encryptionservice.utils.AESUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class EncryptionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EncryptionService.class);

    /**
     * This service method encrypts the input message.
     *
     * @param messageRequestPOJO MessageRequestPOJO object containing the input message.
     * @return EncryptedMessageResponsePOJO object containing the encrypted string.
     */
    public EncryptedMessageResponsePOJO getEncryptedMessage(MessageRequestPOJO messageRequestPOJO) {
        LOGGER.info("IN EncryptionService.getEncryptedMessage with MessageRequestPOJO: {}", messageRequestPOJO);
        if(Objects.isNull(messageRequestPOJO.getMessage())) {
            LOGGER.error("Input string cannot be null");
            throw new IllegalArgumentException("Invalid input: message cannot be null");
        }
        String encryptedMessage;
        EncryptedMessageResponsePOJO encryptedMessageResponsePOJO = new EncryptedMessageResponsePOJO();
        try {
            encryptedMessage = AESUtil.encrypt(messageRequestPOJO.getMessage(), AESUtil.generateKey(128), AESUtil.generateIv());
            encryptedMessageResponsePOJO.setEncryptedMessage(encryptedMessage);
        } catch (Exception e) {
            LOGGER.error("Exception occurred while encrypting message. Error: {}, Error Stack Trace: {}", e.getMessage(), e.getStackTrace());
            throw new RuntimeException("Some error occurred in the server.");
        }
        LOGGER.info("OUT EncryptionService.getEncryptedMessage with output: {}", encryptedMessage);
        return encryptedMessageResponsePOJO;
    }
}
