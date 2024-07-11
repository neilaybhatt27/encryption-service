package com.greatkapital.encryptionservice.service;

import com.greatkapital.encryptionservice.model.MessageRequestPOJO;
import com.greatkapital.encryptionservice.utils.AESUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EncryptionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EncryptionService.class);

    /**
     * This service method encrypts the input message.
     *
     * @param messageRequestPOJO MessageRequestPOJO object containing the input message.
     * @return Encrypted string.
     */
    public String getEncryptedMessage(MessageRequestPOJO messageRequestPOJO) {
        LOGGER.info("IN EncryptionService.getEncryptedMessage with MessageRequestPOJO: {}", messageRequestPOJO);
        String encryptedMessage = null;
        try {
            encryptedMessage = AESUtil.encrypt(messageRequestPOJO.getMessage(), AESUtil.generateKey(128), AESUtil.generateIv());
        } catch (Exception e) {
            LOGGER.error("Exception occurred while encrypting message. Error: {}, Error Stack Trace: {}", e.getMessage(), e.getStackTrace());
            throw new RuntimeException(e);
        }
        LOGGER.info("OUT EncryptionService.getEncryptedMessage with output: {}", encryptedMessage);
        return encryptedMessage;
    }
}
