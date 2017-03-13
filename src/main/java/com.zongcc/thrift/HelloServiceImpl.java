package com.zongcc.thrift;

import org.apache.thrift.TException;

/**
 * Created by zongcc on 2017/3/10.
 */
public class HelloServiceImpl implements Hello.Iface {

    @Override
    public String helloString(String para) throws TException {
        return null;
    }

    @Override
    public int helloInt(int para) throws TException {
        return 0;
    }

    @Override
    public boolean helloBoolean(boolean para) throws TException {
        return false;
    }

    @Override
    public void helloVoid() throws TException {

    }

    @Override
    public String helloNull() throws TException {
        return null;
    }
}
