package com.zongcc.kafka;

import com.zongcc.kafka.model.KafkaBizData;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.util.*;
import java.util.Map.Entry;

/**
 * 生产者封装类
 * @author SlimRhinoceri
 */
public class Productor<Key>{
	
	public static final String NO_RECEIPT = "0";
	public static final String LEAD_RECEIPT = "1";
	public static final String ALL_RECEIPT = "-1";
	
	private static final String KEY_SERIALIZER_CLS = "kafka.serializer.StringEncoder";
	private static final String VAL_SERIALIZER_CLS = "demo.basic.serializer.DefaultSerializer";

	private String topic;
	
	private List<String> brokers;
	
	private String receipt;
	
	private Class<?> partitionCls;
	
	private Producer<Key, KafkaBizData> producer;
	
	/**
	 * 
	 * @param topic topic名称
	 * @param brokers broker地址
	 * @param receipt 发送时的等待回执参数
	 */
	public Productor(String topic, List<String> brokers, String receipt){
		this.topic = topic;
		this.brokers = brokers;
		if(NO_RECEIPT.equals(receipt) || LEAD_RECEIPT.equals(receipt) || ALL_RECEIPT.equals(receipt)){
			this.receipt = receipt;
		}else{
			throw new IllegalArgumentException("receipt参数必须取值于本类的三个公开常量");
		}
	}
	
	/**
	 * @param topic topic名称
	 * @param brokers broker地址
	 * @param receipt 发送时的等待回执参数
	 * @param partitionCls 分区类
	 */
	public Productor(String topic, List<String> brokers, String receipt, Class<?> partitionCls){
		this(topic,brokers, receipt);
		if(partitionCls.isInterface()){
			throw new IllegalArgumentException("partitionCls 必须是类而不能是接口");
		}
		if(partitionCls.getGenericSuperclass().toString().contains("demo.basic.partition.AbstractPartition") == false){
			throw new IllegalArgumentException("partitionCls 必须是demo.basic.partition.AbstractPartition的子类");
		}
		this.partitionCls = partitionCls;
	}
	
	/**
	 * 发送单条消息
	 * @param key
	 * @param val
	 * @return
	 */
	public boolean sendOne(Key key, KafkaBizData val){
		boolean res;
		try{
			KeyedMessage<Key, KafkaBizData> data = new KeyedMessage<Key, KafkaBizData>(this.topic, key, val);
			this.producer.send(data);
			res = true;
		}catch(Exception e){
			e.printStackTrace();
			res = false;
		}
		
		return res;
	}
	
	/**
	 * 批量发送消息
	 * @param batchMap
	 * @return
	 */
	public boolean sendBatch(Map<Key, KafkaBizData> batchMap){
		boolean success = true;
		try{
			Set<Entry<Key, KafkaBizData>> dataEntrys = batchMap.entrySet();
			List<KeyedMessage<Key, KafkaBizData>> list = new ArrayList<KeyedMessage<Key, KafkaBizData>>();
			for(Entry<Key, KafkaBizData> dataEntry : dataEntrys){
				KeyedMessage<Key, KafkaBizData> data = new KeyedMessage<Key, KafkaBizData>(this.topic, dataEntry.getKey(), dataEntry.getValue());
				list.add(data);
			}
			this.producer.send(list);
		}catch(Exception e){
			e.printStackTrace();
			success = false;
		}
		return success;
	}
	
	public void open(){
		
        Properties props = new Properties();
        /**
         * 所有broker的ip:port 的字符串，用逗号分隔
         */
        props.put("metadata.broker.list",this.getBrokerAddrs());
        
        /**
         * 把发送的消息进行序列化时使用的类
         */
        props.put("serializer.class", VAL_SERIALIZER_CLS);
       
        /**
         * 发送的消息的KEY进行序列化时使用的类
         */
        props.put("key.serializer.class", KEY_SERIALIZER_CLS);
        /**
         * 标记消息发送时是否需要服务端给一个回执
         *  1 发送之后，leader给回执即可(较安全)
         *  0 发送之后不需要回执 (此时会丢失数据)
         * -1 发送之后，所有节点都给回执才可以(最安全，但耗费资源多)
         * 默认是0.
         */
        props.put("request.required.acks", this.receipt);
        
        /**
         * 用于判断一条消息应该发送到哪个分区的类
         * 这是一个可选的配置，当topic无分区时，不需要写
         * TODO 当topic存在分区时如果不写会怎么样还有待学习
         */
        if(this.partitionCls != null){
        	props.put("partitioner.class",this.partitionCls.getName());
        }
        ProducerConfig config = new ProducerConfig(props);
        this.producer = new Producer<Key, KafkaBizData>(config);
	}
	
	public void close(){
		if(this.producer != null){
			this.producer.close();
		}
	}
	
	
	private String getBrokerAddrs(){
		
		StringBuilder builder = new StringBuilder();
		for(int i = 0, len = this.brokers.size(); i < len; i++){
			if(i != 0){
				builder.append(",");
			}
			builder.append(this.brokers.get(i));
		}
		return builder.toString();
	}
}
