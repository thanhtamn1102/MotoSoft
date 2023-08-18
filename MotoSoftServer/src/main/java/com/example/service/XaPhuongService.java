package com.example.service;

import com.example.model.XaPhuong;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface XaPhuongService extends Remote {

    public boolean addXaPhuong(XaPhuong xaPhuong) throws RemoteException;
    public List<XaPhuong> getAllXaPhuong() throws RemoteException;
    public List<XaPhuong> getXaPhuongInQuanHuyen(String maQuanHuyen) throws RemoteException;
    public XaPhuong getXaPhuongById(String id) throws RemoteException;

}
