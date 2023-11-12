package com.hanhan.task;

import com.hanhan.mapper.CipherTextMapper;
import com.hanhan.mapper.KeyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

//使用 Spring 提供的一个注解： @Schedule，开发简单，使用比较方便
//Spring 自身提供了对定时任务的支持
@Configuration //标记配置类，是让springboot容器扫描到
@EnableScheduling  //开启定时任务
public class ScheduledDelete {

    @Autowired
    private CipherTextMapper cipherTextMapper;

    @Autowired
    private KeyMapper keyMapper;

    /**
     * https://cron.qqe2.com  有表达式的生成
     */
    //添加定时任务，注明定时任务的表达式
    @Scheduled(cron = "0 0 0 1/1 * ?") //参数是来指定每隔多久执行一次任务
    private void publishArticles(){
        //采用Redis的过期键的删除策略（定时删除），过期时间30天
        //每天执行一次（1次删除100条，在create_time字段上加索引），可以根据实际情况进行调整
        cipherTextMapper.deleteCipherTextByCreatTime();
        keyMapper.deletePKeyByCreatTime();
    }

}

