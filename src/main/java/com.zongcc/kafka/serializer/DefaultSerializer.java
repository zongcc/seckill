package com.zongcc.kafka.serializer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import com.zongcc.kafka.model.KafkaBizData;
import kafka.utils.VerifiableProperties;

/**
 * 
 * @author SlimRhinoceri
 */
public class DefaultSerializer implements kafka.serializer.Encoder<KafkaBizData>{
	
	public DefaultSerializer(VerifiableProperties p){
		
	}

	@Override
	public byte[] toBytes(KafkaBizData data) {
		
		byte[] res = null;
		try {
			ByteArrayOutputStream outer = new ByteArrayOutputStream();
			ObjectOutputStream stream = new ObjectOutputStream(outer);
			stream.writeObject(data);
			stream.flush();
			res = outer.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return res;
	}

}
