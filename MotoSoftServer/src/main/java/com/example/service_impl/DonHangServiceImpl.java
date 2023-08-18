package com.example.service_impl;

import com.example.dao.DonHangDAO;
import com.example.model.DonHang;
import com.example.service.DonHangService;

import java.io.Serial;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class DonHangServiceImpl extends UnicastRemoteObject implements DonHangService, Serializable {

    @Serial
    private static final long serialVersionUID = 4483620507218382438L;

    private DonHangDAO donHangDAO;

    public DonHangServiceImpl() throws RemoteException {
        donHangDAO = new DonHangDAO();
    }

    @Override
    public List<DonHang> getAllDonHang() throws RemoteException {
        return donHangDAO.getAllDonHang();
    }

    @Override
    public boolean addDonHang(DonHang donHang) throws RemoteException {
        return donHangDAO.addDonHang(donHang);
    }

    @Override
    public boolean updateDonHang(DonHang donHang) throws RemoteException {
        return donHangDAO.updateDonHang(donHang);
    }

    @Override
    public boolean deleteDonHang(DonHang donHang) throws RemoteException {
        return donHangDAO.removeDonHang(donHang);
    }
}
