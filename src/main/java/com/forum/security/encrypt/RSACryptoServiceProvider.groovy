package com.forum.security.encrypt
import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import java.util.Base64;
/**
 * @author LJL
 * @Description Generate RSA Key
 * @version 1.0
 */

class RSACryptoServiceProvider {
    // Public key and Private key
    private static Map<String, String> keyStore = new HashMap<String, String>();

    private static class SingletonClassInstance{
        private static final RSACryptoServiceProvider instance=new RSACryptoServiceProvider();
    }

    private RSACryptoServiceProvider(){}

    public static RSACryptoServiceProvider getInstance(){
        return SingletonClassInstance.instance
    }

    static getPublickey(){
        return keyStore?.get('PublicKey')
    }

    /**
     * @Description Generate Key With Base64 And Set Key Size 1024
     */
    public static void generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(1024,new SecureRandom());
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        String publicKeyString = new String(Base64.getEncoder().encode(publicKey.getEncoded()));
        String privateKeyString = new String(Base64.getEncoder().encode((privateKey.getEncoded())));
        keyStore.put("PublicKey",publicKeyString);
        keyStore.put('PrivateKey',privateKeyString);
    }

    /**
     * @Description RSA Public Key Encrypt
     */
    private static String encryptWithPublicKey( String str, String publicKey ) throws Exception {
        byte[] decoded = Base64.getDecoder().decode(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        String outStr = Base64.getEncoder().encodeToString(cipher.doFinal(str.getBytes("UTF-8")));
        return outStr;
    }

    /**
     * @Description RSA Private Key Decrypt
     */
    private static String decryptWithPrivateKey(String str, String privateKey) throws Exception {
        byte[] inputByte = Base64.getDecoder().decode(str.getBytes("UTF-8"));
        byte[] decoded = Base64.getDecoder().decode(privateKey);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        String outStr = new String(cipher.doFinal(inputByte));
        return outStr;
    }

    public static String decrypt(String str) throws Exception {
        return decryptWithPrivateKey(str, keyStore?.get('PrivateKey'))
    }

    public static void main(String[] args) throws Exception {
        //生成公钥和私钥
        generateKeyPair();
        //加密字符串
        String message = "df723820";
        System.out.println("随机生成的公钥为:" + keyStore.get('PublicKey'));
        System.out.println("随机生成的私钥为:" + keyStore.get('PrivateKey'));
        String messageEn = encryptWithPublicKey(message,keyStore.get('PublicKey'));
        System.out.println(message + "\t加密后的字符串为:" + messageEn);
        String messageDe = decrypt(messageEn);
        System.out.println("还原后的字符串为:" + messageDe);
    }
}
