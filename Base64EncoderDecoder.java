package com;

import java.util.Base64;

public class Base64EncoderDecoder {

	public String encodedData(byte[] bytes ) {
		Base64.Encoder encoder = Base64.getEncoder();
		String encodedString = encoder.encodeToString(bytes);
		return encodedString;
	}
	
	public String decodedData(String encodedData) {
		Base64.Decoder decoder = Base64.getDecoder();
		byte [] bytes = decoder.decode(encodedData);
		return new String(bytes);
	}
	
	public static void main(String[] args) {
		
		Base64EncoderDecoder base64EncoderDecoder = new Base64EncoderDecoder();
		String str = "Hello Test String";
		
		String encodedString = base64EncoderDecoder.encodedData(str.getBytes());
		System.out.println("Encoded String -> "+encodedString);
		
		String decodedString = base64EncoderDecoder.decodedData(encodedString);
		System.out.println("Decoded String -> "+decodedString);
	}
}
