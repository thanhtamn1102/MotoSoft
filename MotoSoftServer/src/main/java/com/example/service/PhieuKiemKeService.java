package com.example.service;

import com.example.model.PhieuKiemKe;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface PhieuKiemKeService extends Remote {

    public List<PhieuKiemKe> getAllPhieuKiemKe()throws RemoteException;;

    public boolean addPhieuKiemKe(PhieuKiemKe phieuKiemKe)throws RemoteException;;

    public boolean updatePhieuKiemKe(PhieuKiemKe phieuKiemKe)throws RemoteException;;

    public boolean deletePhieuKiemKe(PhieuKiemKe phieuKiemKe)throws RemoteException;;

}
