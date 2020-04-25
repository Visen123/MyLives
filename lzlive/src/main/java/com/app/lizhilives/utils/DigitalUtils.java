package com.app.lizhilives.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * Created by hhr on 16/1/12.
 */
public class DigitalUtils {
    public DigitalUtils() {
    }

    public static String md5(String text) {
        try {
            return md5(text.getBytes("utf-8"));
        } catch (UnsupportedEncodingException var2) {
            throw new SystemException(var2);
        }
    }

    public static String md5(byte[] bytes) {
        MessageDigest messageDigest = getMd5Digest();
        return encodeHex(messageDigest.digest(bytes));
    }

    private static MessageDigest getMd5Digest() {
        try {
            return MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException var1) {
            throw new SystemException(var1);
        }
    }

    private static String encodeHex(byte[] bytes) {
        StringBuffer buf = new StringBuffer(bytes.length * 2);

        for(int i = 0; i < bytes.length; ++i) {
            if((bytes[i] & 255) < 16) {
                buf.append("0");
            }

            buf.append(Long.toString((long)(bytes[i] & 255), 16));
        }

        return buf.toString();
    }
}
