package org.aileen.mod.crypto;

import org.aileen.mod.kit.Base64Kit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

/**
 * 加密解密工具类
 *
 * @author Eugene-Forest
 * {@code @date} 2024/12/10
 */
public class CryptoUnits {

    private static final Logger log = LoggerFactory.getLogger(CryptoUnits.class);
    private static final String DEFAULT_PASSWORD = "GTG02kJ82RnFAaoiMHaUy4GLAEYoz0gC"; // 默认加密秘钥
    private static final String Random_Factor = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int AES_KeySize = 128;

    /**
     * 随机生成长度为 length 的密码
     * @param length
     * @return
     */
    private static String getRandomPassword(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        int bound = Random_Factor.length();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(bound);
            sb.append(Random_Factor.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 生成密码
     */
    public static String generatePassword() {
        return getRandomPassword(32);
    }

    /**
     * 加密
     *
     * @param content  需要加密的内容
     * @param password 加密密码
     * @return
     */
    public static String encrypt(String content, String password) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            // 注意这句是关键，防止linux下 随机生成key。用其他方式在Windows上正常，但Linux上会有问题
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(password.getBytes());
            kgen.init(AES_KeySize, secureRandom);
            // kgen.init(128, new SecureRandom(password.getBytes()));
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            byte[] byteContent = content.getBytes(StandardCharsets.UTF_8);
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(byteContent);
            return Base64Kit.encodeBase64String(result); // 加密
        } catch (Exception e) {
            log.error("加密异常", e);
        }
        return null;
    }

    /**
     * 默认加密
     * @param content
     * @return
     */
    public static String defaultEncrypt(String content) {
        return encrypt(content, DEFAULT_PASSWORD);
    }

    /**
     * 解密
     *
     * @param content  待解密内容
     * @param password 解密密钥
     * @return
     */
    public static String decrypt(String content, String password) {
        try {
            byte[] contentByte = Base64Kit.decodeBase64(content.getBytes(StandardCharsets.UTF_8));
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            // 防止linux下 随机生成key
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(password.getBytes());
            kgen.init(AES_KeySize, secureRandom);
            // kgen.init(128, new SecureRandom(password.getBytes()));
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(contentByte);
            return new String(result, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("解密异常", e);
        }
        return null;
    }

    /**
     * 默认解密
     * @param content
     * @return
     */
    public static String defaultDecrypt(String content) {
        return decrypt(content, DEFAULT_PASSWORD);
    }

    /**
     * （伪）随机数生成器生成 Base64密钥
     *
     * @return
     */
    public static String generateByteKey() {
        try {
            //Take-care: 虽然AES keySize 长度可以这三个，但是在实际使用的时候进行解密的时候会出现密匙位数异常
            // 可能与Java默认的加密策略有关，Java默认是使用128位的加密密匙。
//            if(n != 128 && n != 192 && n != 256) {
//                throw new RuntimeException("Invalid key length");
//            }
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(AES_KeySize);
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] keyBytes = secretKey.getEncoded();
            log.debug("keyBytes length: " + keyBytes.length);
            return Base64Kit.encodeBase64String(keyBytes);
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    /**
     * 字符串加密
     *
     * @param sSrc 加密字符串
     * @param key  Base64密钥
     * @return
     */
    public static String encryptWithByteKey(String sSrc, String key) {
        try {
            String sKey = Base64Kit.encodeBase64String(key.getBytes());
            if (sKey == null) {
                log.error("encrypt - Key为空null");
                return null;
            }
            byte[] raw = sKey.getBytes();
//            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            SecretKey skeySpec = new SecretKeySpec(raw, 0, raw.length, "AES");

            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            byte[] encrypted = cipher.doFinal(sSrc.getBytes(StandardCharsets.UTF_8));
            return Base64Kit.encodeBase64String(encrypted);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 字符串解密
     *
     * @param sSrc 解密字符串
     * @param key  Base64密钥
     * @return
     */
    public static String decryptWithByteKey(String sSrc, String key) {
        try {
            String sKey = Base64Kit.encodeBase64String(key.getBytes());
            if (sKey == null) {
                log.error("decrypt - Key为空null");
                return null;
            }
            byte[] raw = sKey.getBytes();
            SecretKey skeySpec = new SecretKeySpec(raw, 0, raw.length, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] encrypted1 = Base64Kit.decodeBase64(sSrc.getBytes(StandardCharsets.UTF_8));
            byte[] original = cipher.doFinal(encrypted1);
            return new String(original, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

}
