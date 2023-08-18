package com.example.service_impl;


import com.example.dao.NhaCungCapDAO;
import com.example.model.NhaCungCap;
import com.example.service.NhaCungCapService;

import java.io.Serial;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class NhaCungCapImpl extends UnicastRemoteObject implements NhaCungCapService, Serializable {

    @Serial
    private static final long serialVersionUID = -7561617464556182414L;

    private NhaCungCapDAO nhaCungCapDAO;

    public NhaCungCapImpl() throws RemoteException {
        nhaCungCapDAO = new NhaCungCapDAO();
    }

    @Override
    public List<NhaCungCap> getAllNhaCungCap()throws RemoteException  {
        return nhaCungCapDAO.getAllNhaCungCap();
    }

    @Override
    public boolean addNhaCungCap(NhaCungCap nhaCungCap)throws RemoteException  {
        return nhaCungCapDAO.addNhaCungCap(nhaCungCap);
    }

    @Override
    public boolean updateNhaCungCap(NhaCungCap nhaCungCap)throws RemoteException  {
        return nhaCungCapDAO.updateNhaCungCap(nhaCungCap);
    }

    @Override
    public boolean deleteNhaCungCap(NhaCungCap nhaCungCap)throws RemoteException  {
        return nhaCungCapDAO.deleteNhaCungCap(nhaCungCap);
    }
}
