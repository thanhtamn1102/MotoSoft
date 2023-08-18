package com.example.service;

import com.example.model.ChucVu;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ChucVuService extends Remote {
    public List<ChucVu> getAllChucVu() throws RemoteException;;

    public boolean addChucVu(ChucVu chucVu) throws RemoteException;;

    public boolean updateChucVu(ChucVu chucVu) throws RemoteException;;

    public boolean removeChucVu(ChucVu chucVu) throws RemoteException;;


}
