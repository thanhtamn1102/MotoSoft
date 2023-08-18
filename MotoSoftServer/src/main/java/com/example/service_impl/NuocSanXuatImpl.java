package com.example.service_impl;


import com.example.dao.NuocSanXuatDAO;
import com.example.model.NuocSanXuat;
import com.example.service.NuocSanXuatService;

import java.io.Serial;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class NuocSanXuatImpl extends UnicastRemoteObject implements NuocSanXuatService, Serializable {

    @Serial
    private static final long serialVersionUID = -1157468214934495448L;

    private NuocSanXuatDAO nuocSanXuatDAO;

    public NuocSanXuatImpl() throws RemoteException {
        nuocSanXuatDAO= new NuocSanXuatDAO();
    }

    @Override
    public List<NuocSanXuat> getAllNuocSanXuat()throws RemoteException {
        return nuocSanXuatDAO.getAllNuocSanXuat();
    }

    @Override
    public NuocSanXuat getNuocSanXuat(int brandId)throws RemoteException {
        return nuocSanXuatDAO.getNuocSanXuat(brandId);
    }

    @Override
    public boolean addNuocSanXuat(NuocSanXuat nuocSanXuat)throws RemoteException{
        return nuocSanXuatDAO.addNuocSanXuat(nuocSanXuat);
    }

    @Override
    public boolean updateNuocSanXuat(NuocSanXuat nuocSanXuat)throws RemoteException{
        return nuocSanXuatDAO.updateNuocSanXuat(nuocSanXuat);
    }
    @Override
    public boolean deleteNuocSanXuat(NuocSanXuat nuocSanXuat)throws RemoteException{
        return nuocSanXuatDAO.deleteNuocSanXuat(nuocSanXuat);
    }
}
