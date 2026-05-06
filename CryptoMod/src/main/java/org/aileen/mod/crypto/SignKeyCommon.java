package org.aileen.mod.crypto;

import org.aileen.mod.kit.Base64Kit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author Eugene-Forest
 * {@code @date} 2024/11/27
 */
public class SignKeyCommon {
    private static final String KeyFileBase = "static/";
    private static final String KeyFileRSA = KeyFileBase + "rsa/";
//    private static final String KeyFileDSA = KeyFileBase + "dsa/";

    private static final Logger log = LoggerFactory.getLogger(SignKeyCommon.class);

    public static void createSignKey(String name) {
        try {
            String privateKeyFilePath = getPrivateKeyFileSavePath(name);
            File privateKeyFile = new File(privateKeyFilePath);
            if (privateKeyFile.createNewFile()) {
                log.warn("Private key file created");
            } else if (privateKeyFile.exists()) {
                log.warn("Private key file already exists");
                return;
            }
            String publicKeyFilePath = getPublicKeyFileSavePath(name);
            File publicKeyFile = new File(publicKeyFilePath);
            if (publicKeyFile.createNewFile()) {
                log.warn("Public key file created");
            } else if (publicKeyFile.exists()) {
                log.warn("Public key file already exists");
                return;
            }
            SignKey signKey = SignKeyUnits.createSignKey();
            FileHelper.writeTextFile(privateKeyFile, signKey.getPrivateKey());
            FileHelper.writeTextFile(publicKeyFile, signKey.getPublicKey());
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }

    private static String getPrivateKeyFileSavePath(String name) {
        return KeyFileRSA + name + ".private.pfx";
    }

    private static String getPublicKeyFileSavePath(String name) {
        return KeyFileRSA + name + ".public.cer";
    }

    /**
     * 获取私匙对象
     *
     * @param name      私匙文件名
     * @param algorithm 加密算法
     * @return 私匙对象
     */
    public static PrivateKey getPrivateKey(String name, String algorithm) {
//        String algorithm = RSA;//加密算法
        String privateKeyFilePath = getPrivateKeyFileSavePath(name);
        try {
            File privateKeyFile = new File(privateKeyFilePath);
            if (!privateKeyFile.exists()) {
                log.warn("Private key file not exists");
                return null;
            }
            //读取私匙字符串
            String privatekeyString = FileHelper.readTextFile(privateKeyFile);
            //从私匙字符串中获取私匙
            byte[] privateBytes = Base64Kit.decodeBase64(privatekeyString.getBytes(StandardCharsets.UTF_8));
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
            return keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException e) {
            log.error("使用非法加密算法 [{}] 导致密匙创建异常。", algorithm);
            log.error(e.getMessage());
        } catch (InvalidKeySpecException e) {
            log.error("密匙对象创建失败。");
            log.error(e.getMessage());
        } catch (IOException e) {
            log.error("读取私匙文件 {} 出现异常。", privateKeyFilePath);
            log.error(e.getMessage());
        }
        return null;
    }

    /**
     * 获取公匙对象
     *
     * @param name      公匙文件名
     * @param algorithm 加密算法
     * @return 公匙对象
     */
    public static PublicKey getPublicKey(String name, String algorithm) {
//        String algorithm = RSA;//加密算法
        String publicKeyFilePath = getPublicKeyFileSavePath(name);
        try {
            File publicKeyFile = new File(publicKeyFilePath);
            if (!publicKeyFile.exists()) {
                log.warn("Public key file not exists");
                return null;
            }
            // 获取公匙字符串
            String publicKeyString = FileHelper.readTextFile(publicKeyFile);
            // 通过公匙字符串获取公匙
            byte[] publicKeyBytes = Base64Kit.decodeBase64(publicKeyString.getBytes(StandardCharsets.UTF_8));
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
            return keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            log.error("使用非法加密算法 [{}] 导致密匙创建异常。", algorithm);
            log.error(e.getMessage());
        } catch (InvalidKeySpecException e) {
            log.error("密匙对象创建失败。");
            log.error(e.getMessage());
        } catch (IOException e) {
            log.error("读取公匙文件 {} 出现异常。", publicKeyFilePath);
            log.error(e.getMessage());
        }
        return null;
    }
}
