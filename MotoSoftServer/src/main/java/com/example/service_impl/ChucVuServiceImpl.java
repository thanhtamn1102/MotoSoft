package com.example.service_impl;

import com.example.dao.ChucVuDAO;
import com.example.model.ChucVu;
import com.example.service.ChucVuService;

import java.io.Serial;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class ChucVuServiceImpl extends UnicastRemoteObject implements ChucVuService, Serializable {

    @Serial
    private static final long serialVersionUID = -4053866321561922597L;

    private ChucVuDAO chucVuDAO;

    public ChucVuServiceImpl() throws RemoteException {
        chucVuDAO = new ChucVuDAO();
    }

    @Override
    public List<ChucVu> getAllChucVu() throws RemoteException {
        return chucVuDAO.getAllCChucVu();
    }

    @Override
    public boolean addChucVu(ChucVu chucVu) throws RemoteException {
        return chucVuDAO.addChucVu(chucVu);

    }

    @Override
    public boolean updateChucVu(ChucVu chucVu) throws RemoteException {
        return chucVuDAO.updateChucVu(chucVu);
    }

    @Override
    public boolean removeChucVu(ChucVu chucVu) throws RemoteException {
        return chucVuDAO.removeChucVu(chucVu);
    }
}
