package com.ssm_weixin.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssm_weixin.model.Student;
import com.ssm_weixin.util.JsonUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class DemoMongoDBServiceImpl {

    @Resource
    private MongoTemplate mongoTemplate;

    public List<Student> findAll(){
        List<Student> stuList = mongoTemplate.findAll(Student.class);
        return  stuList;
    }

    public void update(Student student){
        Query query = new Query(Criteria.where("name").is(student.getName()));
        Update update = new Update();
        update.set("age",student.getAge());
        update.set("sex",student.getSex());

        mongoTemplate.updateFirst(query,update,Student.class);
    }

    public void insert(Student student){
        List<Student> list = new ArrayList<>();
        list.add(student);
        try {
            mongoTemplate.insert(list,Student.class);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void delete(Student student){
        Query query = new Query(new Criteria("_id").is(student.getId()));
        try {
            mongoTemplate.findAllAndRemove(query,Student.class);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
