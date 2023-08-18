package com.example.service;

import com.example.model.VaiTroNhomQuyen;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface VaiTroNhomQuyenService extends Remote {
    public List<VaiTroNhomQuyen> getAllVaiTroNhomQuyen() throws RemoteException;

    public boolean addVaiTroNhomQuyen(VaiTroNhomQuyen vaiTro) throws RemoteException;

}
