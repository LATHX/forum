package com.forum;

import com.forum.mapper.UserMapper;
import com.forum.model.dto.LoginInfo;
import com.forum.model.entity.UserEntity;
import com.forum.rabbit.util.RabbitUtil;
import com.forum.redis.util.RedisUtil;
import com.forum.utils.CommonUtil;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class His {
    @Autowired
    private LoginInfo loginInfo;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private UserMapper test;
    @Autowired
    RabbitUtil rabbitUtil;
    @GetMapping("r")
    public String f(){
        System.out.println( this.test.findUserByUserName("admin").getRoleId());
        redisUtil.set("k", "kkkk111");
        loginInfo.setPassword("121311");
        return redisUtil.get("k").toString();
    }
    @RequestMapping("/w")
    public void w(UserEntity user){
        rabbitUtil.deliveryMessage("key.1","sssss1123231213asggetq");
    }
    @RabbitListener(queues = "hello.queue1")
    public String processMessage1(byte[] msg) {
        String s = (String)CommonUtil.getObjectFromBytes(msg);
        System.out.println(s);
        System.out.println(Thread.currentThread().getName() + " 接收到来自hello.queue1队列的消息：" + msg);
        return "";
    }

    @RabbitListener(queues = "hello.queue2")
    public void processMessage2(byte[] msg) {
        String s = (String)CommonUtil.getObjectFromBytes(msg);
        System.out.println(s);
        System.out.println(Thread.currentThread().getName() + " 接收到来自hello.queue2队列的消息：" + msg);
    }

}
