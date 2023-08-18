package com.example.service;

import com.example.model.TaiKhoan;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface TaiKhoanService extends Remote {

    public List<TaiKhoan> getAllTaiKhoan() throws RemoteException;

    public boolean addTaiKhoan(TaiKhoan taiKhoan) throws RemoteException;

    public boolean updateTaiKhoan(TaiKhoan taiKhoan) throws RemoteException;

    public boolean removeTaiKhoan(TaiKhoan taiKhoan) throws RemoteException;

    public TaiKhoan getTaiKhoanById(String maNhanVien)throws RemoteException;


}
