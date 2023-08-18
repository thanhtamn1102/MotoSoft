package com.example.service;

//import com.example.fashionshop.model.DonNhapHang;
//import javafx.collections.ObservableList;

import com.example.model.DonNhapHang;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface DonNhapHangService extends Remote {

    public List<DonNhapHang> getAllDonNhapHang()throws RemoteException;;

    public boolean addDonNhapHang(DonNhapHang donNhapHang)throws RemoteException;;

    public boolean updateDonNhapHang(DonNhapHang donNhapHang)throws RemoteException;;

    public boolean deleteDonNhapHang(DonNhapHang donNhapHang)throws RemoteException;;

}
