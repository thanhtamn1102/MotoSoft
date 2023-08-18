package com.example.service;

import com.example.model.KhachHang;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface KhachHangService extends Remote {
    public List<KhachHang> getAllCustomer() throws RemoteException;;

    public long addCustomer(KhachHang khachHang) throws RemoteException;;

    public boolean updateCustomer(KhachHang khachHang) throws RemoteException;;

    public boolean removeCustomer(KhachHang khachHang) throws RemoteException;;


}
