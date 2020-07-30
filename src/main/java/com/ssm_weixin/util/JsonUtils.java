package com.ssm_weixin.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

public class JsonUtils {
public static ObjectMapper objectMapper;
	
	static {
		objectMapper=new ObjectMapper();
	}
	
	/** 
     * 对象转json字符串 
     * @param value 对象 
     * @return    json字符串 
     * @throws Exception 
     */  
	public static String objectToJsonStr(Object value) throws JsonProcessingException{
		return objectMapper.writeValueAsString(value);
	}
	
	/** 
     * json字符串转对象  
     * @param content  json字符串 
     * @param valueType  对象类型 
     * @return   对象 
     * @throws Exception 
     */  
	public static<T> T objectFromJsonStr(String content,Class<T> valueType) throws JsonParseException, JsonMappingException, IOException{
		T objT = objectMapper.readValue(content, valueType);
		return objT;
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String,String> stringToMap(String jsonStr){
		Gson gson = new Gson();
		Map<String, String> map = new HashMap<String, String>();
		map = gson.fromJson(jsonStr, map.getClass());
		return map;
	}
}
