package com.greatkapital.encryptionservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EncryptedMessageResponsePOJO implements Serializable {
    private String encryptedMessage;
}
