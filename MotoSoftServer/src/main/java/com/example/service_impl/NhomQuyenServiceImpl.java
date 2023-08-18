package com.example.service_impl;

import com.example.dao.NhomQuyenDAO;
import com.example.model.NhomQuyen;
import com.example.service.NhomQuyenService;

import java.io.Serial;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class NhomQuyenServiceImpl extends UnicastRemoteObject implements NhomQuyenService, Serializable {

    @Serial
    private static final long serialVersionUID = 8607746710232497892L;

    private NhomQuyenDAO nhomQuyenDAO;

    public NhomQuyenServiceImpl() throws RemoteException {
        nhomQuyenDAO = new NhomQuyenDAO();
    }

    @Override
    public List<NhomQuyen> getAllNhomQuyen() throws RemoteException {
        return nhomQuyenDAO.getAllPermissionGroup();
    }

    @Override
    public NhomQuyen getNhomQuyen(int brandId) throws RemoteException {
        return nhomQuyenDAO.getPermissionGroupById(brandId);
    }

    @Override
    public boolean addNhomQuyen(NhomQuyen nhomQuyen) throws RemoteException {
        return nhomQuyenDAO.addNhomQuyen(nhomQuyen);
    }

    @Override
    public boolean updateNhomQuyen(NhomQuyen nhomQuyen) throws RemoteException {
        return nhomQuyenDAO.updateNhomQuyen(nhomQuyen);
    }

    @Override
    public boolean deleteNhomQuyen(NhomQuyen nhomQuyen) throws RemoteException {
        return false;
    }
}
