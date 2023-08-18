package com.example.service;

import com.example.model.VaiTroQuyen;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface VaiTroQuyenService extends Remote {

    public List<VaiTroQuyen> getAllVaiTroQuyen()throws RemoteException;

    public VaiTroQuyen getVaiTroQuyen(String maVaiTroQuyen)throws RemoteException;

    public boolean addVaiTroQuyen(VaiTroQuyen vaiTroQuyen)throws RemoteException;

    public boolean updateVaiTroQuyen(VaiTroQuyen vaiTroQuyen)throws RemoteException;

    public boolean removeVaiTroQuyen(VaiTroQuyen vaiTroQuyen)throws RemoteException;

}
