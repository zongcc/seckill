package com.zongcc.utils;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

/**
 * protostuff序列化util
 * Created by chunchengzong on 2016-09-22.
 */
public class ProtostuffUtil {

    /**
     * 序列化
     *
     * @param t
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> byte[] serializeProtoStuff(T t, Class<T> clazz) {
        if (t == null) {
            return null;
        }
        Schema schema = RuntimeSchema.getSchema(clazz);
        byte[] bytes = ProtobufIOUtil.toByteArray(t, schema,
                LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
        return bytes;
    }

    /**
     * 反序列化
     *
     * @param data
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T deSerializeProtoStuff(byte[] data, Class<T> clazz) {
        RuntimeSchema<T> runtimeSchema = RuntimeSchema.createFrom(clazz);
        T t = runtimeSchema.newMessage();
        ProtobufIOUtil.mergeFrom(data, t, runtimeSchema);
        return t;
    }

}
