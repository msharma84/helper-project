package com;

import java.util.Base64;

public class Encryption {
	
	public static String encryptDecrypt(String input) {
        char[] key = {'N', 'R', 'B'}; //Can be any chars, and any length array
        StringBuilder output = new StringBuilder();
        for(int i = 0; i < input.length(); i++) {
            output.append((char) (input.charAt(i) ^ key[i % key.length]));
        }
        return output.toString();
    }
	
	public static String encrypt(String input) {
        char[] key = {'N', 'R', 'B'}; //Can be any chars, and any length array
        StringBuilder output = new StringBuilder();

        for(int i = 0; i < input.length(); i++) {
            output.append((char) (input.charAt(i) ^ key[i % key.length]));
        }
        String encryptedUserPAYLOAD = output.toString();
        byte[] encodedBytes = Base64.getEncoder().encode(encryptedUserPAYLOAD.getBytes());
        String base64EncodedEncryptedUserPAYLOAD = new String(encodedBytes);
        return base64EncodedEncryptedUserPAYLOAD;
    }

	 public static String decrypt(String input) {

        byte[] decodedBytes = Base64.getDecoder().decode(input);
        String base64DecodedEncryptedUserPAYLOAD = new String(decodedBytes);

        char[] key = {'N', 'R', 'B'}; //Can be any chars, and any length array
        StringBuilder output = new StringBuilder();
        for(int i = 0; i < base64DecodedEncryptedUserPAYLOAD.length(); i++) {
            output.append((char) (base64DecodedEncryptedUserPAYLOAD.charAt(i) ^ key[i % key.length]));
        }
        return output.toString();
	 }

	 public static void main(String[] args) {
        String encryptedString = encrypt("HelloSigmaTesting1234567890!@#$%^&*(){}[]|`~_-,.<>?/");
        System.out.println("Encrypted String :" + encryptedString);
        
        String decryptedString = Encryption.decrypt(encryptedString);
        System.out.println("Decrypted String :" + decryptedString);
	 }
}
