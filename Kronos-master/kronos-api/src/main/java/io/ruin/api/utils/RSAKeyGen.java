package io.ruin.api.utils;

import java.security.*;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

public class RSAKeyGen {

    public static void main(String[] args) throws Exception {
        KeyFactory factory = KeyFactory.getInstance("RSA");
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(1024); //512 for 530
        KeyPair keypair = keyGen.genKeyPair();
        PrivateKey privateKey = keypair.getPrivate();
        PublicKey publicKey = keypair.getPublic();

        RSAPrivateKeySpec privSpec = factory.getKeySpec(privateKey, RSAPrivateKeySpec.class);
        System.out.println("PRIVATE:");
        System.out.println("    EXPONENT: " + privSpec.getPrivateExponent().toString());
        System.out.println("    MODULUS: " + privSpec.getModulus().toString());

        RSAPublicKeySpec pubSpec = factory.getKeySpec(publicKey, RSAPublicKeySpec.class);
        System.out.println("PUBLIC:");
        System.out.println("    EXPONENT: " + pubSpec.getPublicExponent().toString());
        System.out.println("    MODULUS: " + pubSpec.getModulus().toString());
    }

}