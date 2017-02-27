package com.zongcc.kafka.partition;

import kafka.producer.Partitioner;
import kafka.utils.VerifiableProperties;

/**
 * 抽象的分区类，该类的getPartition方法用于判断一个数据到底发送到哪个分区
 * 需要使用这自己实现
 * @author SlimRhinoceri
 */
public abstract class AbstractPartition<T> implements Partitioner{

	public AbstractPartition(VerifiableProperties props) {
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public int partition(Object arg0, int arg1) {
		return this.getPartition((T)arg0, arg1);
	}
	
	protected abstract int getPartition(T key, int arg);

}
