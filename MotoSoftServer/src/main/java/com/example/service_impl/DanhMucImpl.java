package com.example.service_impl;

import com.example.dao.DanhMucDAO;
import com.example.model.DanhMuc;
import com.example.service.DanhMucService;

import java.io.Serial;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class DanhMucImpl extends UnicastRemoteObject implements DanhMucService, Serializable {

    @Serial
    private static final long serialVersionUID = 3424801871483195051L;

    private DanhMucDAO danhMucDAO;

    public DanhMucImpl() throws RemoteException {
        danhMucDAO= new DanhMucDAO();
    }

    @Override
    public List<DanhMuc> getAllCategory() throws RemoteException {
        return danhMucDAO.getAllCategory();
    }

    @Override
    public boolean addCategory(DanhMuc danhMuc)throws RemoteException {
        return danhMucDAO.addCategory(danhMuc);
    }

    @Override
    public boolean updateCategory(DanhMuc danhMuc)throws RemoteException {
        return danhMucDAO.updateCategory(danhMuc);
    }

    @Override
    public boolean removeCategory(DanhMuc danhMuc)throws RemoteException {
        return danhMucDAO.updateCategory(danhMuc);
    }

    @Override
    public DanhMuc getCategoryById(String category_id)throws RemoteException {
        return danhMucDAO.getCategoryById(category_id);
    }
}
