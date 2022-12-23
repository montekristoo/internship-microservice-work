package com.internship.microservice.util;

import lombok.SneakyThrows;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.util.Base64Utils;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;

public class PasswordUtils {
    private static final SecureRandom secureRandom = new SecureRandom();

    public static byte[] generateSalt() {
        byte[] salt = new byte[32];
        secureRandom.nextBytes(salt);
        return salt;
    }

    @SneakyThrows
    private static byte[] getEncodedPassword(String password, byte[] salt) {
        KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
        return secretKeyFactory.generateSecret(keySpec).getEncoded();
    }

    @SneakyThrows
    public static String generateHashingPassword(String password, byte[] salt) {
        return Base64Utils.encodeToString(getEncodedPassword(password, salt));
    }

    public static boolean verifyPassword(String passwordFromDb, String hashFromDb, String clientPassword) {
        byte[] salt = Base64.getDecoder().decode(hashFromDb);
        String passwordToVerify = generateHashingPassword(clientPassword, salt);
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

    public static String generateRandomString() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=+[{]}\\\\|;:\\'\\\",<.>/?";
        return RandomStringUtils.random(10, characters);
    }

}
