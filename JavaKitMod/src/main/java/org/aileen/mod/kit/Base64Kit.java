package org.aileen.mod.kit;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger log = LoggerFactory.getLogger(Base64Kit.class);

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
        try {
            log.debug("开始对字符串进行Base64编码。");
            return encoder.encodeToString(getBytes(str));
        }catch (Exception e){
            throw new RuntimeException("Base64编码失败",e);
        }
    }

    public static String decode(String str) {
        try {
            log.debug("开始对字符串进行Base64解码。");
            return getString(decoder.decode(str));
        }catch (Exception e){
            throw new RuntimeException("Base64解码失败",e);
        }
    }

    public static String encodeBase64String(byte[] binaryData) {
        return encoder.encodeToString(binaryData);
    }

    public static byte[] decodeBase64(byte[] encodedText) {
        return decoder.decode(encodedText);
    }
}
