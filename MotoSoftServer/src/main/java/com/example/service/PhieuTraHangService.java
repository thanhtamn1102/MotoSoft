package com.example.service;

import com.example.model.PhieuTraHang;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PhieuTraHangService extends Remote {

    public boolean addPhieuTraHang(PhieuTraHang phieuTraHang) throws RemoteException;
    public boolean updatePhieuTraHang(PhieuTraHang phieuTraHang) throws RemoteException;
    public boolean deletePhieuTraHang(PhieuTraHang phieuTraHang) throws RemoteException;

}
