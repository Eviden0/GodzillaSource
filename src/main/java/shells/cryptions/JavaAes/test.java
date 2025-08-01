package shells.cryptions.JavaAes;

import util.functions;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @FileName: test
 * @Date: 2025/7/25/15:47
 * @Author: Eviden
 */
public class test {

    public static void main(String[] args) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher decodeCipher;
       Cipher encodeCipher;
       String key= functions.md5("key").substring(0, 16);
        encodeCipher = Cipher.getInstance("AES");
        decodeCipher = Cipher.getInstance("AES");
        // this.key="functions.md5("key").substring(0, 16)
        encodeCipher.init(1, new SecretKeySpec(key.getBytes(), "AES"));
        decodeCipher.init(2, new SecretKeySpec(key.getBytes(), "AES"));
        String data="system('whoami');";
        byte[] encrypted = encodeCipher.doFinal(data.getBytes());
        String base64Encoded = java.util.Base64.getEncoder().encodeToString(encrypted);
        System.out.println("Payload: "+base64Encoded);

        byte[] decrypted = decodeCipher.doFinal(java.util.Base64.getDecoder().decode("Me1hDF8mvaRxwpTOARl701o9qbXcyKwkOA79oSckpoo="));
        System.out.println(new String(decrypted));

    }
}
