package com.ap.utils;

import com.badlogic.gdx.utils.GdxRuntimeException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Crypto {
    public static String hash(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new GdxRuntimeException("not found", e);
        }
    }
    public static String generatePassword(RegistrationValidator validator) {
        String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String specials = "?><,\"';:/|][}{+=)(*&^%$#!";
        StringBuilder builder = new StringBuilder();

        //adding two special characters
        builder.append(specials.charAt(new Random().nextInt(specials.length())));
        builder.append(new Random().nextInt( 10));
        while(!validator.passwordValidity(builder.toString()).isSuccess()) {
            builder.append(alphabet.charAt(new Random().nextInt(alphabet.length())));
        }
        Character[] chars = new Character[builder.toString().length()];
        for(int i = 0; i < builder.toString().length(); i++)
            chars[i] = builder.toString().charAt(i);
        Collections.shuffle(Arrays.asList(chars));
        StringBuilder shuffled = new StringBuilder();
        for (Character aChar : chars) shuffled.append(aChar);
        return shuffled.toString();
    }
}
