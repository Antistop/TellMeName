package com.hanhan.service;

import java.util.List;

public interface KeyService {

    /**
     * 存入公私密钥
     */
    List<String> insertKey();

    /**
     * 存入密文
     */
    Long insertCipherText(String plaintext, Long publicKeyId);

    /**
     * 取出密文
     */
    String selectCipherText(Long cipherTextId);
}