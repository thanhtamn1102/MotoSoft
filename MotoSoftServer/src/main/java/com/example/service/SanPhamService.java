package com.example.service;

import com.example.model.DanhMuc;
import com.example.model.SanPham;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface SanPhamService extends Remote {

    public List<SanPham> getAllSanPham()throws RemoteException;

    public SanPham getSanPham(String maSanPham)throws RemoteException;

    public boolean addSanPham(SanPham sanPham)throws RemoteException;

    public boolean updateSanPham(SanPham sanPham)throws RemoteException;

    public boolean removeSanPham(SanPham sanPham)throws RemoteException;

    public List<SanPham> getAllSanPhamFromDanhMuc(DanhMuc danhMuc, int top)throws RemoteException;

    public List<SanPham> getAllSanPhamFromDanhMuc(DanhMuc danhMuc)throws RemoteException;
}
