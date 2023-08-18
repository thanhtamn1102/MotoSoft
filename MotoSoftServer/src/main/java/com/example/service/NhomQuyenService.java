package com.example.service;

import com.example.model.NhomQuyen;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface NhomQuyenService extends Remote {

    public List<NhomQuyen> getAllNhomQuyen()throws RemoteException;;

    public NhomQuyen getNhomQuyen(int brandId)throws RemoteException;;

    boolean addNhomQuyen(NhomQuyen nhomQuyen)throws RemoteException;;

    boolean updateNhomQuyen(NhomQuyen nhomQuyen)throws RemoteException;;

    boolean deleteNhomQuyen(NhomQuyen nhomQuyen)throws RemoteException;;
}
