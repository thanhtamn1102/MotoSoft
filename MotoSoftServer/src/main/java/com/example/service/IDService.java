package com.example.service;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IDService extends Remote {

    public String createMaSanPham() throws RemoteException;;
    public String createMaDanhMuc() throws RemoteException;;
    public String createMaPhieuKiemKe() throws RemoteException;;
    public String createMaDonNhapHang() throws RemoteException;;
    public  String createMaPhieuNhapHang() throws RemoteException;;
    public  String createMaDonHang() throws RemoteException;;
    public  String createMaNhanVien() throws RemoteException;;

}
