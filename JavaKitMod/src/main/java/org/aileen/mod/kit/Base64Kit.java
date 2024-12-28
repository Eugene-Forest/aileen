package org.aileen.mod.kit;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Base64工具类，提供字符串与Base64编码之间的转换功能。
 *
 * @author Eugene-Forest
 */
public class Base64Kit {

    private static final Base64.Encoder encoder = Base64.getEncoder();

    private static final Base64.Decoder decoder = Base64.getDecoder();

    /**
     * 将字节数组转换为UTF-8字符串。
     *
     * @param bytes 字节数组
     * @return UTF-8字符串
     */
    public static String getString(byte[] bytes) {
        return new String(bytes, StandardCharsets.UTF_8);
    }

    /**
     * 将字符串转换为UTF-8字节数组。
     *
     * @param str 字符串
     * @return UTF-8字节数组
     */
    public static byte[] getBytes(String str) {
        return str.getBytes(StandardCharsets.UTF_8);
    }

    /**
     * 对字符串进行Base64编码。
     *
     * @param str 要编码的字符串
     * @return 编码后的Base64字符串
     */
    public static String encode(String str) {
        return encoder.encodeToString(getBytes(str));
    }

    public static String decode(String str) {
        return getString(decoder.decode(str));
    }
}
