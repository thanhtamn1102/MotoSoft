package com.example.service;

import com.example.model.ThuongHieu;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ThuongHieuService extends Remote {

    public List<ThuongHieu> getAllThuongHieu()throws RemoteException;

    public ThuongHieu getThuongHieu(int brandId)throws RemoteException;

    boolean addThuongHieu(ThuongHieu thuongHieu)throws RemoteException;

    boolean updateThuongHieu(ThuongHieu thuongHieu)throws RemoteException;

    boolean deleteThuongHieu(ThuongHieu thuongHieu)throws RemoteException;
}
