package com.internship.microservice.util;

import org.springframework.util.Base64Utils;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

public class PasswordUtils {
    private static final SecureRandom secureRandom = new SecureRandom();


    public static byte[] generateSalt() {
        byte[] salt = new byte[32];
        secureRandom.nextBytes(salt);
        return salt;
    }

    public static String generateHashingPassword(String password, byte[] salt) {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        SecretKeyFactory secretKeyFactory;
        try {
            secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        byte[] hash;
        try {
            hash = secretKeyFactory.generateSecret(spec).getEncoded();
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
        return Base64Utils.encodeToString(hash);
    }

    public static boolean verifyPassword(String passwordFromDb, String hashFromDb, String clientPassword) {
        System.out.println(hashFromDb);
        byte[] salt = Base64.getDecoder().decode(hashFromDb);
        System.out.println(clientPassword);
        String passwordToVerify = generateHashingPassword(clientPassword, salt);
        System.out.println(passwordToVerify);
        return passwordFromDb.equals(passwordToVerify);
    }

    public static void writeToFile(String dbName, String dbPassword) {
        try {
            Files.write(Paths.get("passwords.txt"), (dbName + " " + dbPassword + '\n').getBytes(),
                    StandardOpenOption.APPEND,
                    StandardOpenOption.CREATE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
