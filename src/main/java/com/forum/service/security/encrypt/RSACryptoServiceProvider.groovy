package com.forum.service.security.encrypt

import org.springframework.stereotype.Service

import javax.crypto.Cipher
import java.security.KeyFactory
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec

/**
 * @author LJL
 * @Description 生成RSA密钥和公钥
 * @version 1.0
 */
@Service
class RSACryptoServiceProvider {
    // Public key and Private key
    private static Map<String, String> keyStore = new HashMap<String, String>()

    private static class SingletonClassInstance{
        private static final RSACryptoServiceProvider instance=new RSACryptoServiceProvider()
    }

    private RSACryptoServiceProvider(){}

    RSACryptoServiceProvider getInstance(){
        println 'Initializing RSACryptoServiceProvider'
        return SingletonClassInstance.instance
    }

    String getPublickey(){
        return keyStore?.get('PublicKey')
    }

    /**
     * @Description 生成公钥和密钥并用Base64加密
     */
    static  void generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA")
        keyPairGen.initialize(1024,new SecureRandom())
        KeyPair keyPair = keyPairGen.generateKeyPair()
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate()
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic()
        String publicKeyString = new String(Base64.getEncoder().encode(publicKey.getEncoded()))
        String privateKeyString = new String(Base64.getEncoder().encode((privateKey.getEncoded())))
        keyStore.put("PublicKey",publicKeyString)
        keyStore.put('PrivateKey',privateKeyString)
    }

    /**
     * @param str 需要加密的字符串
     * @param publicKey 公钥
     * @Description RSA公钥加密
     * @return 加密后的字符串
     */
    private static String encryptWithPublicKey( String str, String publicKey ) throws Exception {
        byte[] decoded = Base64.getDecoder().decode(publicKey)
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded))
        Cipher cipher = Cipher.getInstance("RSA")
        cipher.init(Cipher.ENCRYPT_MODE, pubKey)
        String outStr = Base64.getEncoder().encodeToString(cipher.doFinal(str.getBytes("UTF-8")))
        return outStr
    }

    /**
     * @param str 需要解密的字符串
     * @param privateKey 密钥
     * @Description RSA密钥解密
     * @return 解密后的字符串
     */
    private static String decryptWithPrivateKey(String str, String privateKey) throws Exception {
        byte[] inputByte = Base64.getDecoder().decode(str.getBytes("UTF-8"))
        byte[] decoded = Base64.getDecoder().decode(privateKey)
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded))
        Cipher cipher = Cipher.getInstance("RSA")
        cipher.init(Cipher.DECRYPT_MODE, priKey)
        String outStr = new String(cipher.doFinal(inputByte))
        return outStr
    }

    static String decrypt(String str) throws Exception {
        return decryptWithPrivateKey(str, keyStore?.get('PrivateKey'))
    }

//    public static void main(String[] args) throws Exception {
//        //生成公钥和私钥
////        generateKeyPair()
//        //加密字符串
//        keyStore.put('PublicKey','MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCZYitq7OfctyOQd71UJZ+q4WM3MOW5km/XkVKjSAX3fk3SBOLGGn5hD+qdSVABRxOOeZQQYaEXPzA2HlZ/ZTSrN0o9UDBB6yRTZ6m3t+0bXrGNGIJL7nHcrFyaXao5E8K7JRlgeNItz8wjpzQpxFzuo1pHRYPgfbo9y7p3LkhmyQIDAQAB')
//        keyStore.put('PrivateKey','MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJliK2rs59y3I5B3vVQln6rhYzcw5bmSb9eRUqNIBfd+TdIE4sYafmEP6p1JUAFHE455lBBhoRc/MDYeVn9lNKs3Sj1QMEHrJFNnqbe37RtesY0YgkvucdysXJpdqjkTwrslGWB40i3PzCOnNCnEXO6jWkdFg+B9uj3LuncuSGbJAgMBAAECgYBMiAsZ4vMd0dNQ9i3lmpA8dxFGLIOzQDnF2aDeD8XKY574t69fIUEFtSdfK4Fb4dcEM+2I7u2t571qg2CtWBYavcTL9zzkYdMgW6mW6qCPNqj9xubndIYjJh9j52hSyckKkPB24kYQoE0/qpUW5Ep1VMA9b4Rtfajs1Ss9EpA6AQJBAPVzWzrZVaB0l2JKVnWOHu3Dt7EBN2zqR/Ua15hssVBiw25GDp+o8jQ6PyHDsi5WTNBxGjswAW0ZAGWJUZRtQ9ECQQCf+dEsSWXd1uR1Ru8dTFbahYUdECvPDZSOpxyPWHGra5UuhNiDOa6L5dErfyxzWYKb6NB5o0c7y1bHfLFAcQl5AkB5/FHb5oWq0Ccof1+G3xI6Aqo5eyxLoggPJb7+jw50sBEGriS4buOo10XnBvZ4FwXoFj3+Knaa/PD4hY2Y4dDxAkEAhg4LkHb5G8qlqvkECqMb7QYSI+aXL2yL3nZ+4kgVCLagTlkYLe66K5UrkK2DDC4n2opfmG6QlLSyRXxh9ERE0QJBAOXgmsfopeY78BpMQSq9pJq/GYd2lJ0RUxxrFLNhHGJNSbl/V3ZAbld1QEXs7BEHaAk0Z8J596k6HKVxnm95TE4=')
//        String message = "123"
//        System.out.println("随机生成的公钥为:" + keyStore.get('PublicKey'))
//        System.out.println("随机生成的私钥为:" + keyStore.get('PrivateKey'))
//        String messageEn = ''
////        messageEn =  encryptWithPublicKey(message,keyStore.get('PublicKey'))
//         messageEn = 'aBx03VBZyjW32WJsRMwCMm1dqCqAf2ruCsDN+TR6605FzeIUdQD4WPWM7/BvXh5Z2nTLSQyo8oABY5MN9FtgO9Xd9wL++9CFV5xBxc5q597I34RhSQ/zsg83WDsPPvDmjlj6fpuHy9T29NH9mrn/DFEUrVanrz/mkqrHr23EYIE='
//        System.out.println(message + "\t加密后的字符串为:" + messageEn)
//        String messageDe = decrypt(messageEn)
//        System.out.println("还原后的字符串为:" + messageDe)
//    }
}
