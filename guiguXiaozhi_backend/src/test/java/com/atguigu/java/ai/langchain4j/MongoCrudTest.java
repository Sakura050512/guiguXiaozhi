package com.atguigu.java.ai.langchain4j;

import com.atguigu.java.ai.langchain4j.bean.ChatMessages;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

@SpringBootTest
public class MongoCrudTest {
@Autowired
private MongoTemplate mongoTemplate;
    /**
    * 插入文档
    */
    /* @Test
    public void testInsert() {
    mongoTemplate.insert(new ChatMessages(1L, "聊天记录"));
    }*/
    /**
    * 插入文档
    */
    @Test
    public void testInsert2() {
    ChatMessages chatMessages = new ChatMessages();
    chatMessages.setContent("聊天记录列表");
    mongoTemplate.insert(chatMessages);
    }


    /**
    * 根据id查询文档
    */
    @Test
    public void testFindById() {
    ChatMessages chatMessages = mongoTemplate.findById("69acdd612449f473be356c40",
    ChatMessages.class);
    System.out.println(chatMessages);
    }



    /**
     * 修改文档
     */
    @Test
    public void testUpdate() {
        Criteria criteria = Criteria.where("_id").is("69acdd612449f473be356c40");
        Query query = new Query(criteria);
        Update update = new Update();
        update.set("content", "新的聊天记录列表");
        //修改或新增
        mongoTemplate.upsert(query, update, ChatMessages.class);
    }



    /**
     * 新增或修改文档
     */
    @Test
    public void testUpdate2() {
        Criteria criteria = Criteria.where("_id").is("100");
        Query query = new Query(criteria);
        Update update = new Update();
        update.set("content", "新的聊天记录列表");
        //修改或新增
        mongoTemplate.upsert(query, update, ChatMessages.class);
    }



    /**
     * 删除文档
     */
    @Test
    public void testDelete() {
        Criteria criteria = Criteria.where("_id").is("100");
        Query query = new Query(criteria);
        mongoTemplate.remove(query, ChatMessages.class);
    }
}