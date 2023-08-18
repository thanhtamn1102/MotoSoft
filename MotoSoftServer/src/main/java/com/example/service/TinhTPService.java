package com.example.service;

import com.example.model.TinhTP;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface TinhTPService extends Remote {

    public boolean addTinhTP(TinhTP tinhTP) throws RemoteException;
    public List<TinhTP> getAllTinhTP() throws RemoteException;
    public TinhTP getTinhTPById(String id) throws RemoteException;

}
