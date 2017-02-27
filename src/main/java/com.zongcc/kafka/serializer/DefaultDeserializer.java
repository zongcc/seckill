package com.zongcc.kafka.serializer;

import com.zongcc.kafka.model.KafkaBizData;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;


/**
 * 
 * @author SlimRhinoceri
 *
 */
public class DefaultDeserializer{
	
	public static KafkaBizData fromBytes(byte[] value) throws Exception {
		return (KafkaBizData)new ObjectInputStream(new ByteArrayInputStream(value)).readObject();
	}
}
