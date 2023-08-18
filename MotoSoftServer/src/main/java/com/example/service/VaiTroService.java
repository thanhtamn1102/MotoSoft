package com.example.service;

import com.example.model.VaiTro;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface VaiTroService extends Remote {
    public List<VaiTro> getAllVaiTro() throws RemoteException;

    public boolean addVaiTro(VaiTro vaiTro) throws RemoteException;
    public boolean updateVaiTro(VaiTro vaiTro) throws RemoteException;
    public VaiTro getVaiTroById(int id) throws RemoteException;
}
