package com.zongcc.utils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 
 * @author zongcc
 */
public class JsonMapperUtil {

	private static Logger logger = LoggerFactory.getLogger(JsonMapperUtil.class);

	private ObjectMapper mapper;

	public JsonMapperUtil() {
		this(Include.ALWAYS);
	}

	public JsonMapperUtil(Include include) {
		mapper = new ObjectMapper();
		mapper.setSerializationInclusion(include);
	}

	public static JsonMapperUtil nonEmptyMapper() {
		return new JsonMapperUtil(Include.NON_EMPTY);
	}

	public static JsonMapperUtil nonDefaultMapper() {
		return new JsonMapperUtil(Include.NON_DEFAULT);
	}
	
	public static JsonMapperUtil nonNullMapper() {
        return new JsonMapperUtil(Include.NON_NULL);
    }

	public String toJson(Object object) {

		try {
			return mapper.writeValueAsString(object);
		} catch (IOException e) {
			logger.warn("write to json string error:" + object, e);
			return null;
		}
	}

	public <T> T fromJson(String jsonString, Class<T> clazz) {
		if (null == jsonString) {
			return null;
		}

		try {
			return mapper.readValue(jsonString, clazz);
		} catch (IOException e) {
			logger.warn("parse json string error:" + jsonString, e);
			return null;
		}
	}

	public <T> T fromJson(String jsonString, JavaType javaType) {
		if (null == jsonString) {
			return null;
		}

		try {
			return (T) mapper.readValue(jsonString, javaType);
		} catch (IOException e) {
			logger.warn("parse json string error:" + jsonString, e);
			return null;
		}
	}

	public JavaType createCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
		return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
	}

	public <T> T update(String jsonString, T object) {
		try {
			return (T) mapper.readerForUpdating(object).readValue(jsonString);
		} catch (JsonProcessingException e) {
			logger.warn("update json string:" + jsonString + " to object:" + object + " error.", e);
		} catch (IOException e) {
			logger.warn("update json string:" + jsonString + " to object:" + object + " error.", e);
		}
		return null;
	}

	public String toJsonP(String functionName, Object object) {
		return toJson(new JSONPObject(functionName, object));
	}

	public void enableEnumUseToString() {
		mapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
		mapper.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
	}

	public ObjectMapper getMapper() {
		return mapper;
	}
	
	public static void outJson(HttpServletResponse response , String data) throws IOException{
		 response.setContentType("text/html;charset=GBK");
		 response.getWriter().print(data);
	}
}
