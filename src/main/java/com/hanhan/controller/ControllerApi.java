package com.hanhan.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

/**
 * 对于MySQL：
 * varchar定义的长度的单位是字符
 *   如果是latin1字符集下，一个中文汉字占2个字节数
 *   如果是utf8字符集下，一个中文汉字占3个字节数
 *   如果是gbk字符集下，一个中文汉字占2个字节数
 *  MySQL中的varchar是一种用于存储文本的数据类型，其长度最多可以有65535个字符
 *  表中的 varchar 列是可变长度字符串，可以包含数字或字符或两者
 *  此数据类型在 5.0.3 版本之前只能存储 255 个字符，但在此版本及更高版本中
 *  它最多可以存储 65535 个字符，它将值存储在具有(一个或两个字节长度前缀来表示实际长度)的可变长度字符串中
 *  char此数据类型可以用尾随空格填充以保持指定的长度
 *  定义一个char[10]和varchar[10]，如果存进去的是‘tao’
 *  那么char所占的长度依然为3，除了字符‘tao’外后面跟7个空格，varchar就立马把长度变为3了
 *  取数据的时候，char类型的要用trim()去掉多余的空格而varchar是不需要的
 *
 *  mysql各字符集下汉字和字母占字节数
 * varchar(N), 这里的Ｎ是指字符数，并不是字节数．占用的字节数与编码有关
 * latin1：1character=1byte, 1汉字=2character,
 *  也就是说一个字段定义成 varchar(200)，则它可以存储100个汉字或者200个字母。
 * utf8：1character=3bytes, 1汉字=1character
 * 也就是说一个字段定义成 varchar(200)，则它可以存储200个汉字或者200个字母
 * gbk：1character=2bytes,1汉字=1character
 * 也就是说一个字段定义成 varchar(200)，则它可以存储200个汉字或者200个字母
 *
 * Leaf
 * https://tech.meituan.com/2019/03/07/open-source-project-leaf.html
 */
@RequestMapping("key")
public interface ControllerApi {

    //公钥和密文都是使用Base64进行存储，返回也的都是Base64
    /**
     * 生成公私密钥：
     *  对于热点公钥（不会变化），可以设redis缓存，redis出现热点key的问题，也可以设本地缓存concurrentHashMap
     */
    @GetMapping("/addKey")
    List<String> addKey();

    /**
     * 后端明文加密（存储缩小密文）
     */
    //@PostMapping("/addCipherText")
    @GetMapping("/addCipherText") //浏览器测试使用
    Long addCipherText(String plaintext, Long publicKeyId);

    /**
     * 取出密文
     */
    @GetMapping("/GetCipherText")
    String GetCipherText(Long cipherTextId);

}
