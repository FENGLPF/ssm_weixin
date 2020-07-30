package com.ssm_weixin.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssm_weixin.model.Student;
import com.ssm_weixin.service.DemoMongoDBServiceImpl;
import com.ssm_weixin.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value="/mongodb")
public class MongoDBController {

    private static Logger logger = LoggerFactory.getLogger(MongoDBController.class);

    @Resource
    private DemoMongoDBServiceImpl demoMongoDBService;

    @RequestMapping(value="/getStudentList",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public String findAll() throws JsonProcessingException {
        List<Student> list = demoMongoDBService.findAll();
        return JsonUtils.objectToJsonStr(list);
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public void update(@RequestBody Student stu){
        demoMongoDBService.update(stu);
    }

    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    public void insert(@RequestBody Student stu){
        demoMongoDBService.insert(stu);
    }

    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public void delete(@RequestBody Student student){
        demoMongoDBService.delete(student);
    }

}
