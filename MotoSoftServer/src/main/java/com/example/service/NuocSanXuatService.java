package com.example.service;

import com.example.model.NuocSanXuat;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface NuocSanXuatService extends Remote {

    public List<NuocSanXuat> getAllNuocSanXuat()throws RemoteException;;

    public NuocSanXuat getNuocSanXuat(int brandId)throws RemoteException;;

    boolean addNuocSanXuat(NuocSanXuat nuocSanXuat)throws RemoteException;;

    boolean updateNuocSanXuat(NuocSanXuat nuocSanXuat)throws RemoteException;;

    boolean deleteNuocSanXuat(NuocSanXuat nuocSanXuat)throws RemoteException;;
}
