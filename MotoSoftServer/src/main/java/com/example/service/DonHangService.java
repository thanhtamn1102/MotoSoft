package com.example.service;

//import com.example.fashionshop.model.DonNhapHang;
//import javafx.collections.ObservableList;

import com.example.model.DonHang;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface DonHangService extends Remote {

    public List<DonHang> getAllDonHang()throws RemoteException;;

    public boolean addDonHang(DonHang donHang)throws RemoteException;;

    public boolean updateDonHang(DonHang donHang)throws RemoteException;;

    public boolean deleteDonHang(DonHang donHang)throws RemoteException;;

}
