package com.hanhan.service;

import com.hanhan.entity.CipherText;
import com.hanhan.entity.PKey;
import com.hanhan.mapper.CipherTextMapper;
import com.hanhan.mapper.KeyMapper;
import com.hanhan.util.RSACoder;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class KeyServiceImpl implements KeyService {

    //两个Leaf Server 的 name
    private static final String LEAFSERVER1 = "LeafServer1";

    private static final String LEAFSERVER2 = "LeafServer2";

    //轮询标志位
    private boolean flag;

    //使用自动注入，注入Mapper对象(Dao)
    @Autowired
    private KeyMapper keyMapper;

    @Autowired
    private CipherTextMapper cipherTextMapper;


    @Override
    public List<String> insertKey() {
        //返回私钥和公钥id
        List<String> list = new ArrayList<>();
        //初始化密钥
        try {
            Map<String, Object> map = RSACoder.initKey();
            byte[] publicKey = RSACoder.getPublicKey(map);
            //byte[]数组转成Base64
            String encode = Base64.encode(publicKey);
            //通过Leaf获取分布式id
            Long id = getLeafId();

            PKey key = new PKey();
            key.setId(id);
            key.setEncoding(null);
            key.setExtra(null);
            key.setPublicKey(encode);
            key.setCreateTime(new Date());
            keyMapper.insert(key);

            //返回私钥和公钥id
            list.add(Base64.encode(RSACoder.getPrivateKey(map)));
            list.add(String.valueOf(key.getId()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //自定义步长和Leaf服务名biz_tag，max_id为最大id，一开始设置的是起始id，第一次分发id后就会变成最大id
    //insert into leaf_alloc(biz_tag, max_id, step, description) values('LeafServer1', 1, 2000, 'Test leaf Segment Mode Get Id')
    //insert into leaf_alloc(biz_tag, max_id, step, description) values('LeafServer2', 2001, 2000, 'Test leaf Segment Mode Get Id')

    //Leaf Server我们提供了一个基于spring boot的HTTP服务来获取ID
    //Leaf默认暴露的Rest服务是LeafController（Leaf-Server）中

    //使用Spring中的RestTemplate进行http调用
    @Autowired
    private RestTemplate restTemplate;

    private static final String LeafServerUrl1 = "http://localhost:8080/api/segment/get/";
    private static final String LeafServerUrl2 = "http://localhost:8081/api/segment/get/";

    public Long getLeafId(){
        //通过Leaf获取分布式id
        String id;
        if(flag){
            id = restTemplate.getForEntity(LeafServerUrl1 + LEAFSERVER1, String.class).getBody();
        }else{
            id = restTemplate.getForEntity(LeafServerUrl2 + LEAFSERVER2, String.class).getBody();
        }
        flag = !flag;
        return Long.parseLong(id);
    }

    @Override
    public Long insertCipherText(String plaintext, Long publicKeyId) {
        PKey key = keyMapper.selectById(publicKeyId);
        //采用Redis的过期键的删除策略（惰性删除），过期时间30天
        if(System.currentTimeMillis() - key.getCreateTime().getTime() > 1000L * 60 * 60 * 24 * 30){
            keyMapper.deleteById(publicKeyId);
            return -1L;
        }
        String cipherText = null;
        try {
            byte[] publicKey = Base64.decode(key.getPublicKey());
            //进行明文加密
            byte[] bytes = RSACoder.encryptByPublicKey(plaintext.getBytes(), publicKey);
            cipherText = Base64.encode(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Long id = getLeafId();
        CipherText ct = new CipherText();
        ct.setId(id);
        ct.setContent(cipherText);
        ct.setCreateTime(new Date());
        ct.setEncoding(null);
        ct.setExtra(null);

        cipherTextMapper.insert(ct);
        return id;
    }


    @Override
    public String selectCipherText(Long cipherTextId) {
        CipherText cipherText = cipherTextMapper.selectById(cipherTextId);
        //采用Redis的过期键的删除策略（惰性删除），过期时间30天
        if(System.currentTimeMillis() - cipherText.getCreateTime().getTime() > 1000L * 60 * 60 * 24 * 30){
            cipherTextMapper.deleteById(cipherTextId);
            return null;
        }
        return cipherText.getContent();
    }
}
