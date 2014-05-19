package com.pmrodrigues.ellasa.utilities;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;

public final class MD5 {

	private MD5() {
	}

	public static String encrypt(final String message)
			throws NoSuchAlgorithmException {
		final MessageDigest messageDigest = MessageDigest.getInstance("MD5");
		messageDigest.reset();
		messageDigest.update(message.getBytes(Charset.forName("UTF8")));
		return new String(Hex.encodeHex(messageDigest.digest()));
	}
}
