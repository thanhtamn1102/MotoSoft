package com.example.service_impl;

import com.example.dao.PhieuTraHangDAO;
import com.example.model.PhieuTraHang;
import com.example.service.PhieuTraHangService;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class PhieuTraHangServiceImpl extends UnicastRemoteObject implements PhieuTraHangService, Serializable {

    private PhieuTraHangDAO phieuTraHangDAO;

    public PhieuTraHangServiceImpl() throws RemoteException {
        phieuTraHangDAO = new PhieuTraHangDAO();
    }

    @Override
    public boolean addPhieuTraHang(PhieuTraHang phieuTraHang) throws RemoteException {
        return phieuTraHangDAO.addPhieuTraHang(phieuTraHang);
    }

    @Override
    public boolean updatePhieuTraHang(PhieuTraHang phieuTraHang) throws RemoteException {
        return phieuTraHangDAO.updatePhieuTraHang(phieuTraHang);
    }

    @Override
    public boolean deletePhieuTraHang(PhieuTraHang phieuTraHang) throws RemoteException {
        return phieuTraHangDAO.deletePhieuTraHang(phieuTraHang);
    }
}
