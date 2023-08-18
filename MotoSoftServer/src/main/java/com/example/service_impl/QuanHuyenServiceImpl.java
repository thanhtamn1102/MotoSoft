package com.example.service_impl;

import com.example.dao.QuanHuyenDAO;
import com.example.model.QuanHuyen;
import com.example.service.QuanHuyenService;

import java.io.Serial;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class QuanHuyenServiceImpl extends UnicastRemoteObject implements QuanHuyenService, Serializable {

    @Serial
    private static final long serialVersionUID = 422048851676820492L;

    private QuanHuyenDAO quanHuyenDAO;

    public QuanHuyenServiceImpl() throws RemoteException {
        quanHuyenDAO = new QuanHuyenDAO();
    }

    @Override
    public boolean addQuanHuyen(QuanHuyen quanHuyen) throws RemoteException {
        return quanHuyenDAO.addQuanHuyen(quanHuyen);
    }

    @Override
    public boolean mergeQuanHuyen(QuanHuyen quanHuyen) throws RemoteException {
        return quanHuyenDAO.mergeQuanHuyen(quanHuyen);
    }

    @Override
    public List<QuanHuyen> getAllQuanHuyen() throws RemoteException {
        return quanHuyenDAO.getAllQuanHuyen();
    }

    @Override
    public List<QuanHuyen> getQuanHuyenInTinhTP(String maTinhTP) throws RemoteException {
        return quanHuyenDAO.getQuanHuyenInTinhTP(maTinhTP);
    }

    @Override
    public QuanHuyen getQuanHuyenById(String id) throws RemoteException {
        return quanHuyenDAO.getQuanHuyenById(id);
    }
}
