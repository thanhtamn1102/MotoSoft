package com.example.service_impl;

import com.example.dao.VaiTroQuyenDAO;
import com.example.model.VaiTroQuyen;
import com.example.service.VaiTroQuyenService;

import java.io.Serial;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class VaiTroQuyenServiceImpl extends UnicastRemoteObject implements VaiTroQuyenService, Serializable {

    @Serial
    private static final long serialVersionUID = -3377993982128904304L;

    private VaiTroQuyenDAO vaiTroQuyenDAO;
    public VaiTroQuyenServiceImpl() throws RemoteException {
        vaiTroQuyenDAO = new VaiTroQuyenDAO();
    }

    @Override
    public List<VaiTroQuyen> getAllVaiTroQuyen() throws RemoteException {
        return vaiTroQuyenDAO.getAllVaiTroQuyen();
    }

    @Override
    public VaiTroQuyen getVaiTroQuyen(String maVaiTroQuyen) throws RemoteException {
        return vaiTroQuyenDAO.getVaiTroQuyen(maVaiTroQuyen);
    }

    @Override
    public boolean addVaiTroQuyen(VaiTroQuyen vaiTroQuyen) throws RemoteException {
        return vaiTroQuyenDAO.addVaiTroQuyen(vaiTroQuyen);
    }

    @Override
    public boolean updateVaiTroQuyen(VaiTroQuyen vaiTroQuyen) throws RemoteException {
        return vaiTroQuyenDAO.updateVaiTroQuyen(vaiTroQuyen);
    }

    @Override
    public boolean removeVaiTroQuyen(VaiTroQuyen vaiTroQuyen) throws RemoteException {
        return vaiTroQuyenDAO.removeVaiTroQuyen(vaiTroQuyen);
    }

}
