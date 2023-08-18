package com.example.service;

//import com.example.fashionshop.model.DanhMuc;
//import javafx.collections.ObservableList;

import com.example.model.DanhMuc;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface DanhMucService extends Remote {

    public List<DanhMuc> getAllCategory() throws RemoteException;;

    public boolean addCategory(DanhMuc danhMuc) throws RemoteException;;

    public boolean updateCategory(DanhMuc danhMuc) throws RemoteException;;

    public boolean removeCategory(DanhMuc danhMuc) throws RemoteException;;

    public DanhMuc getCategoryById(String category_id)throws RemoteException;;

}
