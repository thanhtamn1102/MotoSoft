package com.example.service_impl;

import com.example.dao.QuyenDAO;
import com.example.model.Quyen;
import com.example.service.QuyenService;

import java.io.Serial;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class QuyenServiceImpl extends UnicastRemoteObject implements QuyenService, Serializable {

    @Serial
    private static final long serialVersionUID = 8101601336047277465L;

    private QuyenDAO quyenDAO;

    public QuyenServiceImpl() throws RemoteException {
        quyenDAO = new QuyenDAO();
    }

    @Override
    public List<Quyen> getAllQuyen() throws RemoteException {
        return quyenDAO.getAllQuyen();
    }

    @Override
    public boolean addQuyen(Quyen quyen) throws RemoteException {
        return quyenDAO.addQuyen(quyen);
    }

    @Override
    public boolean updateQuyen(Quyen quyen) throws RemoteException {
        return quyenDAO.updateQuyen(quyen);
    }

    @Override
    public boolean deleteQuyen(Quyen quyen) throws RemoteException {
        return quyenDAO.removeQuyen(quyen);
    }
}
