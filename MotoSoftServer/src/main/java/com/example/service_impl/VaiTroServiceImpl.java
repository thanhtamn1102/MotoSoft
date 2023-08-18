package com.example.service_impl;

import com.example.dao.VaiTroDAO;
import com.example.model.VaiTro;
import com.example.service.VaiTroService;

import java.io.Serial;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class VaiTroServiceImpl extends UnicastRemoteObject implements VaiTroService, Serializable {

    @Serial
    private static final long serialVersionUID = 2001191476317028532L;

    private VaiTroDAO vaiTroDAO;

    public VaiTroServiceImpl() throws RemoteException {
        vaiTroDAO = new VaiTroDAO();
    }

    @Override
    public List<VaiTro> getAllVaiTro() throws RemoteException {
        return vaiTroDAO.getAllRoles();
    }

    @Override
    public boolean addVaiTro(VaiTro vaiTro) throws RemoteException {
        return vaiTroDAO.addVaiTro(vaiTro);
    }

    @Override
    public boolean updateVaiTro(VaiTro vaiTro) throws RemoteException {
        return vaiTroDAO.updateVaiTro(vaiTro);
    }

    @Override
    public VaiTro getVaiTroById(int id) throws RemoteException {
        return vaiTroDAO.getRoleById(id);
    }
}
