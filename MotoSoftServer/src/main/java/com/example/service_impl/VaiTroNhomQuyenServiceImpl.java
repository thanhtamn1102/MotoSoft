package com.example.service_impl;

import com.example.dao.VaiTroNhomQuyenDAO;
import com.example.model.VaiTroNhomQuyen;
import com.example.service.VaiTroNhomQuyenService;

import java.io.Serial;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class VaiTroNhomQuyenServiceImpl extends UnicastRemoteObject implements VaiTroNhomQuyenService, Serializable {

    @Serial
    private static final long serialVersionUID = 2509306127380381647L;

    private VaiTroNhomQuyenDAO vaiTroNhomQuyenDAO;

    public VaiTroNhomQuyenServiceImpl() throws RemoteException {
        vaiTroNhomQuyenDAO = new VaiTroNhomQuyenDAO();
    }

    @Override
    public List<VaiTroNhomQuyen> getAllVaiTroNhomQuyen() throws RemoteException {
        return vaiTroNhomQuyenDAO.getAllRoles();
    }

    @Override
    public boolean addVaiTroNhomQuyen(VaiTroNhomQuyen vaiTro) throws RemoteException {
        return vaiTroNhomQuyenDAO.addVaiTroNhomQuyen(vaiTro);
    }
}
