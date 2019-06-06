package com.skuniv.cs.geonyeong.portal.service;

import java.io.UnsupportedEncodingException;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CryptorService implements InitializingBean {
    private static final String MODE = "AES/CBC/PKCS5Padding";
    private final String encode = "UTF-8";
    private IvParameterSpec ivSpec;
    private SecretKey secretKey;

    @Value("${com.skuniv.cs.geonyeong.crypt.key}")
    private String key;


    public String encryptBase64(String source) {
        byte[] raw = encrypt(source.getBytes());
        String encryptSource = null;
        try {
            encryptSource = new String(Base64.encodeBase64(raw), encode);
        } catch (UnsupportedEncodingException e) {
            log.error("UnsupportedEncodingException => {}", e);
            throw new RuntimeException();
        }
        return encryptSource;
    }

    private byte[] encrypt(byte[] org){
        try {
            Cipher cipher = Cipher.getInstance(MODE);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey,ivSpec);
            return cipher.doFinal(org);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String decryptBase64(String encryptedSource) {
        byte[] raw = new byte[0];
        try {
            raw = Base64.decodeBase64(encryptedSource.getBytes(encode));
        } catch (UnsupportedEncodingException e) {
            log.error("UnsupportedEncodingException => {}", e);
            throw new RuntimeException();
        }
        return decrypt(raw);
    }

    private String decrypt(byte[] data){
        try {
            Cipher cipher = Cipher.getInstance(MODE);
            cipher.init(Cipher.DECRYPT_MODE, secretKey,ivSpec);
            byte[] decryptedText = cipher.doFinal(data);
            return new String(decryptedText, encode);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keydata = key.substring(0,16).getBytes();
        this.secretKey = new SecretKeySpec(keydata, "AES");
        this.ivSpec = new IvParameterSpec(keydata);
    }
}
