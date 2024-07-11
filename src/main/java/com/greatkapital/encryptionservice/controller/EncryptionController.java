package com.greatkapital.encryptionservice.controller;

import com.greatkapital.encryptionservice.model.EncryptedMessageResponsePOJO;
import com.greatkapital.encryptionservice.model.MessageRequestPOJO;
import com.greatkapital.encryptionservice.service.EncryptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(path = "/api")
public class EncryptionController {
    @Autowired
    private final EncryptionService encryptionService;

    private static final Logger LOGGER = LoggerFactory.getLogger(EncryptionController.class);

    public EncryptionController(EncryptionService encryptionService) {
        this.encryptionService = encryptionService;
    }

    /**
     * This POST API takes the input message string and encrypts it for security.
     *
     * @param messageRequestPOJO MessageRequestPOJO object containing the input string.
     * @return Response Entity containing the encrypted string or error.
     */

    @Operation(summary = "Encrypt the input message text.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Encryption successful",
                    content = {@Content(mediaType = "application/json",
                            examples = {@ExampleObject(value = """
                            {
                                "encryptedMessage": "90+9gorTSMatT3CfzKCxRg=="
                            }
                            """)})}),
            @ApiResponse(responseCode = "422",
                    description = "Invalid input",
                    content = {@Content(mediaType = "application/json",
                            examples = {@ExampleObject(value = """
                            {
                                "errorObject": "Invalid input: message cannot be null"
                            }
                            """)})})
    })

    @PostMapping(path = "/encrypt")
    public ResponseEntity<Object> getEncryptedMessage(@RequestBody MessageRequestPOJO messageRequestPOJO) {
        LOGGER.info("IN EncryptionController.getEncryptedMessage with MessageRequestPOJO: {}", messageRequestPOJO);
        ResponseEntity<Object> response;
        EncryptedMessageResponsePOJO encryptedMessageResponsePOJO;
        try {
            encryptedMessageResponsePOJO = encryptionService.getEncryptedMessage(messageRequestPOJO);
            response = ResponseEntity.ok(encryptedMessageResponsePOJO);
        } catch (IllegalArgumentException e) {
            LOGGER.error("Invalid input: {}", e.getMessage());
            response = new ResponseEntity<>(Map.of("errorObject", e.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (RuntimeException e) {
            LOGGER.error("Server encountered an error. Error: {}, Error Stack Trace: {}", e.getMessage(), e.getStackTrace());
            response = new ResponseEntity<>(Map.of("errorObject", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        LOGGER.info("OUT EncryptionController.getEncryptedMessage");
        return response;
    }
}
