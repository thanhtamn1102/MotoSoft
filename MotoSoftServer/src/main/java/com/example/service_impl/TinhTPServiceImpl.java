package com.example.service_impl;

import com.example.dao.TinhTPDAO;
import com.example.model.TinhTP;
import com.example.service.TinhTPService;

import java.io.Serial;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class TinhTPServiceImpl extends UnicastRemoteObject implements TinhTPService, Serializable {

    @Serial
    private static final long serialVersionUID = 1902515750524542443L;

    private TinhTPDAO tinhTPDAO;

    public TinhTPServiceImpl() throws RemoteException {
        tinhTPDAO = new TinhTPDAO();
    }

    @Override
    public boolean addTinhTP(TinhTP tinhTP) throws RemoteException {
        return tinhTPDAO.addTinhTP(tinhTP);
    }

    @Override
    public List<TinhTP> getAllTinhTP() throws RemoteException {
        return tinhTPDAO.getAllTinhTP();
    }

    @Override
    public TinhTP getTinhTPById(String id) throws RemoteException {
        return tinhTPDAO.getTinhTPById(id);
    }
}
