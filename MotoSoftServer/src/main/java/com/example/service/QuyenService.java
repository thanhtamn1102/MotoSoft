package com.example.service;

import com.example.model.Quyen;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface QuyenService extends Remote {

    public List<Quyen> getAllQuyen()throws RemoteException;

    public  boolean addQuyen(Quyen quyen)throws RemoteException;

    public boolean updateQuyen(Quyen quyen)throws RemoteException;

    public boolean deleteQuyen(Quyen quyen)throws RemoteException;
}
