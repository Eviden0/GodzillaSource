import util.functions;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import java.security.MessageDigest;

import static util.functions.base64Encode;

/**
 * @FileName: test
 * @Date: 2025/7/28/16:45
 * @Author: Eviden
 */
public class test {
    public static void main(String[] args) throws Exception {
        // String data="system('whoami');";
        // System.out.println(new String(base64Encode(data.getBytes())));
        byte[] encrypted = encrypt("system('whoami');".getBytes("UTF-8"), "key");
        String base64Encoded = java.util.Base64.getEncoder().encodeToString(encrypted);
        System.out.println(base64Encoded);
        System.out.println(new String(decrypt(java.util.Base64.getDecoder().decode("DWKjgF9T1/G0JQ4XNuTsaw=="), "key")));
    }

    // AES加密
    // 计算MD5并返回16字节数组
    public static byte[] md5(String s) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        return md.digest(s.getBytes("UTF-8"));
    }

    // 手动PKCS7填充
    public static byte[] pkcs7Pad(byte[] data) {
        int blockSize = 16;
        int pad = blockSize - (data.length % blockSize);
        byte[] padded = new byte[data.length + pad];
        System.arraycopy(data, 0, padded, 0, data.length);
        for (int i = data.length; i < padded.length; i++) {
            padded[i] = (byte) pad;
        }
        return padded;
    }

    public static byte[] encrypt(byte[] data, String rawKey) throws Exception {
        // 生成key，取md5(rawKey)转成hex后前16个字符的ASCII bytes
        byte[] md5Bytes = md5(rawKey);
        StringBuilder hexStr = new StringBuilder();
        for (byte b : md5Bytes) {
            hexStr.append(String.format("%02x", b));
        }
        String keyStr = hexStr.substring(0, 16);
        byte[] keyBytes = keyStr.getBytes("ASCII");

        // 填充
        byte[] paddedData = pkcs7Pad(data);

        Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);

        return cipher.doFinal(paddedData);
    }

    // AES解密
    // 手动PKCS7去除填充
    public static byte[] pkcs7Unpad(byte[] data) throws Exception {
        int pad = data[data.length - 1] & 0xFF;  // 转成无符号整数
        if (pad < 1 || pad > 16) {
            throw new Exception("Invalid padding");
        }
        for (int i = data.length - pad; i < data.length; i++) {
            if ((data[i] & 0xFF) != pad) {
                throw new Exception("Invalid padding");
            }
        }
        byte[] unpadded = new byte[data.length - pad];
        System.arraycopy(data, 0, unpadded, 0, unpadded.length);
        return unpadded;
    }


    public static byte[] decrypt(byte[] encryptedData, String rawKey) throws Exception {
        // 生成key，取md5(rawKey)转成hex后前16个字符的ASCII bytes
        byte[] md5Bytes = md5(rawKey);
        StringBuilder hexStr = new StringBuilder();
        for (byte b : md5Bytes) {
            hexStr.append(String.format("%02x", b));
        }
        String keyStr = hexStr.substring(0, 16);
        byte[] keyBytes = keyStr.getBytes("ASCII");

        Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec);

        byte[] decryptedPadded = cipher.doFinal(encryptedData);

        return pkcs7Unpad(decryptedPadded);
    }
}
