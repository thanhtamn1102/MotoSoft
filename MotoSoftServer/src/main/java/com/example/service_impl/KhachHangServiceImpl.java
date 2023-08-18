package com.example.service_impl;

import com.example.dao.KhachHangDAO;
import com.example.model.KhachHang;
import com.example.service.KhachHangService;

import java.io.Serial;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class KhachHangServiceImpl extends UnicastRemoteObject implements KhachHangService, Serializable {

    @Serial
    private static final long serialVersionUID = -506504883904625037L;

    private KhachHangDAO khachHangDAO;
    public KhachHangServiceImpl() throws RemoteException {
        khachHangDAO = new KhachHangDAO();
    }

    @Override
    public List<KhachHang> getAllCustomer() throws RemoteException {
        return khachHangDAO.getAllCustomer();
    }

    @Override
    public long addCustomer(KhachHang khachHang) throws RemoteException {
        return khachHangDAO.addCustomer(khachHang);

    }

    @Override
    public boolean updateCustomer(KhachHang khachHang) throws RemoteException {
        return khachHangDAO.updateCustomer(khachHang);
    }

    @Override
    public boolean removeCustomer(KhachHang khachHang) throws RemoteException {
        return khachHangDAO.removeCustomer(khachHang);
    }
}
