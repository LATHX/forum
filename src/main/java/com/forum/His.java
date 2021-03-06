package com.forum;

import com.forum.mapper.ForumListMapper;
import com.forum.mapper.UserMapper;
import com.forum.model.dto.LoginInfo;
import com.forum.model.entity.ForumListEntity;
import com.forum.model.entity.UserEntity;
import com.forum.rabbit.util.RabbitUtil;
import com.forum.redis.util.RedisUtil;
import com.forum.utils.CommonUtil;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class His {
    @Autowired
    private LoginInfo loginInfo;
    @Autowired
    private UserMapper test;
    @Autowired
    RabbitUtil rabbitUtil;
    @Autowired
    ForumListMapper forumListMapper;
    @GetMapping("r")
    public String f(){
        System.out.println( this.test.findUserByUserName("admin").getRoleId());
        RedisUtil.set("k", "kkkk111");
        loginInfo.setPassword("121311");
        return RedisUtil.get("k").toString();
    }
    @RequestMapping("/w")
    public void w(UserEntity user)throws Exception{
        List<ForumListEntity> list = forumListMapper.selectAll();
//        rabbitUtil.deliveryDelayMessage("delay_queue","sssss1123231213asggetq",3000);
//        rabbitUtil.deliveryMessage("key.1","aaaa1123231213asggetq");
    }
    @RabbitListener(queues = "hello.queue1")
    public String processMessage1(byte[] msg) {
        String s = (String)CommonUtil.getObjectFromBytes((byte[]) msg);
        System.out.println("Rec Date:"+CommonUtil.getCurrentDate());
        System.out.println(s);
        System.out.println(Thread.currentThread().getName() + " 接收到来自delay_queue队列的消息：" + msg);
        return "";
    }

    @RabbitListener(queues = "hello.queue2")
    public void processMessage2(byte[] msg) {
        System.out.println("Rec Date:"+CommonUtil.getCurrentDate());
        System.out.println(msg);
        String s = (String)CommonUtil.getObjectFromBytes(msg);
        System.out.println(s);
        System.out.println(Thread.currentThread().getName() + " 接收到来自hello.queue2队列的消息：" + msg);
    }

}
