package com.ujjwal.blogapi.security;

import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Component // This class will auto-generate a secure 2048-bit RSA key pair as soon as our Spring Boot app turns on
public class RsaKeyManager {

    // not autowired bcz using constructor injection
    private final RSAPublicKey publicKey;
    private final RSAPrivateKey privateKey;

    public RsaKeyManager() {
        try {
            // Initialize a standard RSA cryptographic key generator
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            // Assign the public and private parts
            this.publicKey = (RSAPublicKey) keyPair.getPublic();
            this.privateKey = (RSAPrivateKey) keyPair.getPrivate();
        } catch (Exception e) {
            throw new IllegalStateException("Failed to generate in-memory RSA cryptographic keys", e);
        }
    }

    public RSAPublicKey getPublicKey() {
        return publicKey;
    }

    public RSAPrivateKey getPrivateKey() {
        return privateKey;
    }

}
