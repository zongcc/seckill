package com.zongcc.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by chunchengzong on 2017-06-14.
 */
public class HelloImpl extends UnicastRemoteObject implements IHello{
    protected HelloImpl() throws RemoteException {
    }

    @Override
    public String helloWorld() throws RemoteException {
        return "hello world";
    }

    @Override
    public String sayHelloToSomeBody(String someBodyName) throws RemoteException {
        return "你好，"+someBodyName;
    }
}
