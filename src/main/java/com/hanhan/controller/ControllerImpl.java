package com.hanhan.controller;

import com.hanhan.service.KeyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ControllerImpl implements ControllerApi{

    @Autowired
    private KeyServiceImpl keyService;

    @Override
    public List<String> addKey() {
        List<String> list = keyService.insertKey();
        return list;
    }

    @Override
    public Long addCipherText(String plaintext, Long publicKeyId) {
        //限制明文长度小于等于50
        if(plaintext.length() > 50){
            return -1L;
        }
        return keyService.insertCipherText(plaintext,publicKeyId);
    }

    @Override
    public String GetCipherText(Long cipherTextId) {
        return keyService.selectCipherText(cipherTextId);
    }
}
