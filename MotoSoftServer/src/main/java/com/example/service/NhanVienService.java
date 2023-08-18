package com.example.service;

import com.example.model.NhanVien;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface NhanVienService extends Remote {

    public List<NhanVien> getAllNhanVien() throws RemoteException;;

    public NhanVien getNhanVien(String maNhanVien) throws RemoteException;;

    public boolean addNhanVien(NhanVien nhanVien) throws RemoteException;;

    public boolean updateNhanVien(NhanVien nhanVien) throws RemoteException;;

    public boolean deleteNhanVien(NhanVien nhanVien) throws RemoteException;;

}
