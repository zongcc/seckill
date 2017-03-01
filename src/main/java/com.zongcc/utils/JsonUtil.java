package com.zongcc.utils;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.type.TypeFactory;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.*;

public class JsonUtil {

	private static ObjectMapper mapper = new ObjectMapper();
	static{
		//SimpleDateFormat df =  new SimpleDateFormat("yyyy-M-d H:mm:ss");
		//mapper.getSerializationConfig().setDateFormat(df);
		//mapper.getDeserializationConfig().setDateFormat(df);

		//反序列化时忽略多余的属性
		mapper.getDeserializationConfig().set(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		//忽略Null的值,节省空间. 
        mapper.getSerializationConfig().setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
        //忽略Default值木有变化的属性,更节省空间,用于接收方有相同的Class
        //如int属性初始值为0,那么这个属性将不会被序列化
        mapper.getSerializationConfig().setSerializationInclusion(JsonSerialize.Inclusion.NON_DEFAULT);
	}
	public static boolean isJSON(String jsonString) {
		return mapper.canSerialize(HashMap.class);
	}
/*
	public static <V> Map<String, V> toMap(String content,
			Class<? extends V> clazz) throws Exception {
		return mapper.readValue(content, TypeFactory.mapType(HashMap.class,
				String.class, clazz));
	}

	public static <V> Map<String, V> toMap(InputStream is,
			Class<? extends V> clazz) throws Exception {
		return mapper.readValue(is, TypeFactory.mapType(HashMap.class,
				String.class, clazz));
	}

	public static <V> Map<String, V> toMap(Reader is, Class<? extends V> clazz)
			throws Exception {
		return mapper.readValue(is, TypeFactory.mapType(HashMap.class,
				String.class, clazz));
	}

	public static <V> Map<String, V> toMap(URL is, Class<? extends V> clazz)
			throws Exception {
		return mapper.readValue(is, TypeFactory.mapType(HashMap.class,
				String.class, clazz));
	}
*/

	public static <E> List<E> toList(String content, Class<? extends E> clazz)
			throws Exception {
		return jsonToList(content, clazz);
	}
	/**
	 * json转List
	 * 
	 * @param <E>
	 * @param content
	 * @param clazz
	 *            List的元素类型，会一并转换完成 如List<User>
	 * @return 元素类型为E的List
	 * @throws Exception
	 */
	public static <E> List<E> jsonToList(String content,
			Class<? extends E> clazz) throws Exception {
		return mapper.readValue(content, TypeFactory.collectionType(
				ArrayList.class, clazz));
	}

	/**
	 * json转整形数组
	 * 
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public static Integer[] jsonToIntArray(String content) throws Exception {
		return jsonToArray(content, Integer.class);
	}

	public static Integer[] jsonToIntArray(String content,String key) throws Exception {
		return jsonToArray(content, key, Integer.class);
	}
	/**
	 * json转对象数组
	 * 
	 * @param <E>
	 * @param content
	 * @param clazz
	 *            数组中的对象类型
	 * @return E类型的数组，如User[]
	 * @throws Exception
	 */
	public static <E> E[] jsonToArray(String content, Class<? extends E> clazz)
			throws Exception {
		if(content != null){
			return mapper.readValue(content, TypeFactory.arrayType(clazz));
		}else{
			return null;
		}
	}

	/**
	 * json转java对象，为兼容原util类
	 * 
	 * @param <T>
	 * @param content
	 *            json格式的字符串
	 * @param clazz
	 *            目标类型
	 * @return 返回类型为T的对象
	 * @throws Exception
	 */
	public static <T> T fromJsonToObject(String content,
			Class<? extends T> clazz) throws Exception {
		return jsonToObject(content, clazz);
	}

	/**
	 * 将一个json字符串转化为一个java对象
	 * 
	 * @param <T>
	 * @param content
	 *            json格式的字符串
	 * @param clazz
	 *            目标类型
	 * @return 返回类型为T的对象
	 * @throws Exception
	 */
	public static <T> T jsonToObject(String content, Class<? extends T> clazz)
			throws Exception {
		return mapper.readValue(content, clazz);
	}

	/**
	 * 一个jsonStr包含多个java对象，取其中一个转化为java对象的方法
	 * @param content json格式的字符串
	 * @param key 要转换的子json串的key
	 * @param clazz 目标类型
	 * @return 返回类型为T的对象
	 * @throws Exception
	 */
	public static <T> T jsonToObject(String content, String key,Class<? extends T> clazz) throws Exception {
		JsonNode rootNode = mapper.readValue(content, JsonNode.class);
		JsonNode path = rootNode.path(key);
		if(!path.isMissingNode()){
			return jsonToObject(path.toString(), clazz);
		}else{
			return null;
		}
	}
	
	public static Integer getInt(String content, String key) throws Exception {
		JsonNode rootNode = mapper.readValue(content, JsonNode.class);
		JsonNode path = rootNode.path(key);
		if(!path.isMissingNode()){
			return jsonToObject(path.toString(), Integer.class);
		}else{
			return null;
		}
	}
	
	public static String getString(String content, String key) throws Exception {
		JsonNode rootNode = mapper.readValue(content, JsonNode.class);
		JsonNode path = rootNode.path(key);
		if(!path.isMissingNode()){
			return jsonToObject(rootNode.path(key).toString(), String.class);
		}else{
			return null;
		}
	}
	
	public static String getJson(String content, String key) throws Exception {
        JsonNode rootNode = mapper.readValue(content, JsonNode.class);
        JsonNode path = rootNode.path(key);
        if(!path.isMissingNode()){
            return path.toString();
        }else{
            return null;
        }
    }
	
	public static Date getDate(String content, String key) throws Exception {
		JsonNode rootNode = mapper.readValue(content, JsonNode.class);
		JsonNode path = rootNode.path(key);
		if(!path.isMissingNode()){
			return jsonToObject(path.toString(), Date.class);
		}else{
			return null;
		}
	}

	/**
	 * 一个jsonStr包含多个java对象，将指定的key的json转化为对象数组的方法
	 * @param content 原始的json串
	 * @param key 要转换的部分
	 * @param clazz 目标类型
	 * @return 目标类型的对象数组
	 * @throws Exception
	 */
	public static <E> E[] jsonToArray(String content, String key,Class<? extends E> clazz) throws Exception {
		JsonNode rootNode = mapper.readValue(content, JsonNode.class);
		JsonNode path = rootNode.path(key);
		if(!path.isMissingNode()){
			return jsonToArray(rootNode.path(key).toString(), clazz);
		}else{
			return null;
		}
	}
	
	public static Integer[] jsonToArray(String content, String key) throws Exception {
		JsonNode rootNode = mapper.readValue(content, JsonNode.class);
		JsonNode path = rootNode.path(key);
		if(!path.isMissingNode()){
			return jsonToArray(path.toString(), Integer.class);
		}else{
			return null;
		}
	}

	/**
	 * 一个jsonStr包含多个java对象，将指定的key的json转化为List<E>的方法
	 * @param content 原始的json串
	 * @param key 要转换的那部分json
	 * @param clazz 目标类型
	 * @return 元素为目标类型的List
	 * @throws Exception
	 */
	public static <E> List<E> jsonToList(String content, String key,Class<? extends E> clazz) throws Exception {
		JsonNode rootNode = mapper.readValue(content, JsonNode.class);
		JsonNode path = rootNode.path(key);
		if(!path.isMissingNode()){
			return toList(path.toString(), clazz);
		}else{
			return null;
		}
	}

	/**
	 * 已知问题 User u Agent a u中有a，a中有u ， 如果u和a同在一个o中将不能正常转化
	 * 
	 * @param o
	 *            要转换的对象
	 * @return json格式的字符串
	 * @throws Exception
	 */
	public static String toJson(Object o) throws Exception {
		return mapper.writeValueAsString(o);
	}

	/**
	 * 转换成json串到out
	 * 
	 * @param out
	 * @param o
	 * @throws Exception
	 * 
	 */
	public static void toJson(OutputStream out, Object o) throws Exception {
		mapper.writeValue(out, o);
	}

	/**
	 * 转换成json串到writer
	 * 
	 * @param out
	 * @param o
	 * @throws Exception
	 */
	public static void toJson(Writer out, Object o) throws Exception {
		mapper.writeValue(out, o);
	}
	
	public static String map2Json(Map map) throws Exception{
		return toJson(map);
	}

    public static String Object2Json(Object o) {
        String s = null;
        try {
            if (o != null) {
                s = mapper.writeValueAsString(o);
            }
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

    public static <T> T Json2Object(String json, Class<T> c) {
        T t = null;
        try {
            if (json != null) {
                t = mapper.readValue(json, c);
            }
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return t;
    }

    public static <T> T Json2Object(String json, TypeReference<T> tr) {
        T t = null;
        try {
            if (json != null) {
                t = (T) mapper.readValue(json, tr);
            }
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (T) t;
    }
}

