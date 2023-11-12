package com.hanhan.tellmename;

import com.hanhan.mapper.CipherTextMapper;
import com.hanhan.mapper.KeyMapper;
import com.hanhan.util.RSACoder;
import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Map;

@SpringBootTest
public class KeyTest {

    @Test
    public void RSACoderTest(){
        try {
            Map<String, Object> map = RSACoder.initKey();
            System.out.println(map);
            byte[] privateKey = RSACoder.getPrivateKey(map);
            System.out.println("私钥长度：" + privateKey.length);

            byte[] publicKey = RSACoder.getPublicKey(map);
            System.out.println("公钥长度：" + publicKey.length);

            System.out.println("Base64公钥长度：" + Base64.encode(publicKey).length());

            //密钥的长度越长，加密的长度越长
            //Data must not be longer than 245 bytes
//            byte[] b = new byte[2000];
            byte[] b = "憨憨憨憨".getBytes();
            byte[] bytes = RSACoder.encryptByPublicKey(b, publicKey);
            System.out.println("加密后长度：" + Base64.encode(bytes).length()); //348
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //测试加密解密
    @Test
    public void KeyTest(){
        String privateKey = "MIIBVgIBADANBgkqhkiG9w0BAQEFAASCAUAwggE8AgEAAkEAypHaETVlDS6hsPxbw6V9qxCspux+\nRe/v3Ul/6I6xHfbpWYoNADcpyoLV50vLPiGOKJThehrI7jVx7wE/sC/M5wIDAQABAkA5+GFimVgw\n3I5zOtT7+WksQlpWbsl4NED9n+IV6HfxbcxfXCqg1Aix1+8oFDn9GeLAp+Ju+vYhzRZqVVybD+95\nAiEA5/T/HcefWQfXPZBY32O5LamfvEMHXphwHQ7AtPVQQVsCIQDfkQ+HGe1BYr7M2Y6+yoHuYImS\nJ7pq91bZFMd4T41MZQIhAMSvHsAOq9SfbGi8hE9vzO6mPVcycwZINhC9mLQYqyOvAiEAz4iUVoC9\n2rW0kuredMP+C2/4JQHWLle+ejOs20VV0HUCIQCa/vKtdrY1eB5k/bHBq5YCb49X8XyI8MK8rPgi\nJIi6cA==";
        String cipherText = "JbsVI+0qTAmuz1kTkhlcKo29FsYj9R4bzHSybl9Jg12We1rUXVH5pxaoaO+YKrR8TsfbbB1Ryfd86/TgARFjCA==";
        try {
            byte[] pk = Base64.decode(privateKey);
            byte[] ct = Base64.decode(cipherText);
            byte[] bytes = RSACoder.decryptByPrivateKey(ct, pk);
            System.out.println(new String(bytes));
        } catch (Base64DecodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Autowired
    private CipherTextMapper cipherTextMapper;

    @Autowired
    private KeyMapper keyMapper;

    //删除测试
    @Test
    public void deleteTest(){
        cipherTextMapper.deleteCipherTextByCreatTime();
        keyMapper.deletePKeyByCreatTime();
    }

}
