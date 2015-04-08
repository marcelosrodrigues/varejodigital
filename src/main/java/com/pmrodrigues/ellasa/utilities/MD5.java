package com.pmrodrigues.ellasa.utilities;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import java.nio.charset.Charset;

public final class MD5 {

    private MD5() {
    }

    public static String encrypt(final String message) {
        return new String(Hex.encodeHex(DigestUtils.md5(message.getBytes(Charset.forName("UTF8")))));
    }
}
