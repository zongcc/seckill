package com.zongcc.kafka;

import com.zongcc.kafka.model.ConsumerData;
import com.zongcc.kafka.model.KafkaBizData;
import com.zongcc.kafka.serializer.DefaultDeserializer;
import kafka.api.FetchRequest;
import kafka.api.FetchRequestBuilder;
import kafka.api.PartitionOffsetRequestInfo;
import kafka.common.ErrorMapping;
import kafka.common.TopicAndPartition;
import kafka.javaapi.*;
import kafka.javaapi.consumer.SimpleConsumer;
import kafka.message.MessageAndOffset;

import java.nio.ByteBuffer;
import java.util.*;

/**
 * 消费者封装类
 * @author SlimRhinoceri
 */
public class Customer {
	
	
	private static final String COLON = ":";
	
	/**
	 * topic名称
	 */
	private String topic;
	
	/**
	 * 分区号
	 */
	private int partition;
	
	/**
	 * 所有的Kafka客户端地址
	 * Key是地址，Value是端口号
	 */
	private Map<String, Integer> brokers;
	
	/**
	 * 低级API消费者对象
	 */
	private SimpleConsumer consumer;
	
	/**
	 * 上次读取的位置
	 */
	private long offset;
	
	/**
	 * @param topic topic名称
	 * @param name 消费者名称
	 * @param partition 分区号
	 * @param offset 开始位置
	 * @param brokerAddrs 消费者地址 ip:port的形式
	 */
	public Customer(String topic,int partition, long offset, List<String> brokerAddrs) {
		super();
		this.topic = topic;
		this.partition = partition;
		this.offset = offset;
		this.brokers = new HashMap<String, Integer>();
		for(int i = 0, len = brokerAddrs.size(); i < len; i++){
			String addr = brokerAddrs.get(i);
			String[] arr = addr.split(COLON);
			this.brokers.put(arr[0], Integer.parseInt(arr[1]));
		}
	}

	
	/**
	 * 获取数据的方法
	 * 返回了当前读到的位置
	 * 和读到的数据的集合
	 * @return
	 */
	public ConsumerData fetch(){
		
		
		long curOffset = this.offset;
		
		List<KafkaBizData> datas = new ArrayList<KafkaBizData>();
		
		/**
		 * 获取FetchResponse对象
		 */
		FetchResponse resp = this.getFetchResponse(curOffset);
		
		/**
		 * 是否出错，如果有错误要做相关判断
		 */
		if(resp.hasError()){
			
			short code = resp.errorCode(this.topic, this.partition);
			/**
			 * 判断是否是要读的位置已经超出实际范围了
			 * 超出范围的话，就把读取标记设置为当前partition中最后一条消息之后
			 */
			if(code == ErrorMapping.OffsetOutOfRangeCode()) {
				curOffset = this.getLastOffset();
				resp = this.getFetchResponse(curOffset);
			}
		}
		
		for (MessageAndOffset messageAndOffset : resp.messageSet(this.topic, this.partition)) {
			long currentOffset = messageAndOffset.offset();
			if (currentOffset < curOffset) {
				continue;
			}

			curOffset = messageAndOffset.nextOffset();
			@SuppressWarnings("unused")
			ByteBuffer keyBuf = messageAndOffset.message().key();
			ByteBuffer payload = messageAndOffset.message().payload();
			byte[] value = new byte[payload.limit()];
			payload.get(value);
			try {
				KafkaBizData  data = DefaultDeserializer.fromBytes(value);
				datas.add(data);
			} catch (Exception e) {
				/**
				 * 如果读取数据出现问题也要处理
				 * 那么，就把这个try catch的范围扩展到合适的大小
				 * 然后把处理代码写在这里
				 */
				e.printStackTrace();
			}
		}
		
		ConsumerData res = new ConsumerData();
		res.setOffset(curOffset);
		res.setDatas(datas);
		return res;
	}
	
	/**
	 * 打开
	 */
	public void open(){
		String leaderBrokerName = this.getLeaderBroderName();
		this.init(leaderBrokerName);
	}
	
	/**
	 * 关闭
	 */
	public void close(){
		if(this.consumer != null){
			this.consumer.close();
			this.consumer = null;
		}
	}
	
	/**
	 * 获取批量查询数据的Response对象
	 * @return
	 */
	private FetchResponse getFetchResponse(long curOffset){
		FetchRequest req = new FetchRequestBuilder().clientId(this.getClientId()).addFetch(this.topic, this.partition, curOffset, Integer.MAX_VALUE).build();
		FetchResponse fetchResponse = this.consumer.fetch(req);
		return fetchResponse;
	}
	
	/**
	 * 获取leader的名字
	 * 此时Kafka集群有可能在做leader选举，所以，没获取到的话就循环等待
	 * @return
	 */
	private String getLeaderBroderName(){
		PartitionMetadata partitionMeta = null;
		int times = 0;
		while(partitionMeta == null || partitionMeta.leader() == null){
			if(times == 5){
				throw new IllegalStateException("试了5次也没有找到leader，退出了");
			}else if(times != 0){
				try {Thread.sleep(100);} catch (InterruptedException e) {e.printStackTrace();}
			}else{
				partitionMeta = this.getPartitionMetadata();
				times++;
			}
		}
		return partitionMeta.leader().host();
	}
	
	/**
	 * 根据leader的地址初始化消费者
	 * 当发现需要读的位置小于0，就把位置设置为当前partition中的最后一条位置
	 * @param leaderBrokerName
	 */
	private void init(String leaderBrokerName){
		this.consumer = new SimpleConsumer(leaderBrokerName, this.getPort(leaderBrokerName) , 100000, 64 * 1024, this.getClientId());
		if(this.offset < 0){
			this.offset = this.getLastOffset();
		}
	}
	
	/**
	 * 获取分区元数据信息
	 * @return
	 */
	private PartitionMetadata getPartitionMetadata(){
		PartitionMetadata partitionMeta = null;
		List<String> topics = Collections.singletonList(this.topic);
		
		boolean find = false;
		for(String broker : this.brokers.keySet()){
			SimpleConsumer leaderSearcher = new SimpleConsumer(broker, this.getPort(broker), 100000, 64 * 1024, "leaderLookup");
			try{
				TopicMetadataRequest req = new TopicMetadataRequest(topics);
				kafka.javaapi.TopicMetadataResponse resp = leaderSearcher.send(req);
				List<TopicMetadata> metaData = resp.topicsMetadata();
				for (TopicMetadata item : metaData) {
					for (PartitionMetadata part : item.partitionsMetadata()) {
						if (part.partitionId() == this.partition) {
							partitionMeta = part;
							find = true;
							break;
						}
					}
					
					if(find){
						break;
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				if(leaderSearcher != null){
					leaderSearcher.close();
				}
			}
			
			if(find){
				break;
			}
		}
		
		return partitionMeta;
	}
	
	private String getClientId(){
		return this.topic + this.partition;
	}
	
	private int getPort(String broker){
		return this.brokers.get(broker);
	}
	
	private long getLastOffset(){
		TopicAndPartition topicAndPartition = new TopicAndPartition(this.topic, this.partition);
		Map<TopicAndPartition, PartitionOffsetRequestInfo> requestInfo = new HashMap<TopicAndPartition, PartitionOffsetRequestInfo>();
		requestInfo.put(topicAndPartition, new PartitionOffsetRequestInfo(kafka.api.OffsetRequest.LatestTime(), 1));
		OffsetRequest request = new OffsetRequest(requestInfo, kafka.api.OffsetRequest.CurrentVersion(), this.getClientId());
		OffsetResponse response = this.consumer.getOffsetsBefore(request);
		if (response.hasError()) {
			return 0;
		}
		long[] offsets = response.offsets(this.topic, this.partition);
		return offsets[0];
	}
}
