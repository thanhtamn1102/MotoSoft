package com.example.service_impl;

import com.example.dao.DonNhapHangDAO;
import com.example.model.DonNhapHang;
import com.example.service.DonNhapHangService;

import java.io.Serial;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class DonNhapHangImpl extends UnicastRemoteObject implements DonNhapHangService, Serializable {

    @Serial
    private static final long serialVersionUID = 7722230768824217058L;

    private DonNhapHangDAO donNhapHangDAO;

    public DonNhapHangImpl() throws RemoteException {
        donNhapHangDAO = new DonNhapHangDAO();
    }

    @Override
    public List<DonNhapHang> getAllDonNhapHang() throws RemoteException {
        return donNhapHangDAO.getAllDonNhapHang();
    }

    @Override
    public boolean addDonNhapHang(DonNhapHang donNhapHang)throws RemoteException {
        return donNhapHangDAO.addDonNhapHang(donNhapHang);
    }

    @Override
    public boolean updateDonNhapHang(DonNhapHang donNhapHang)throws RemoteException {
        return donNhapHangDAO.updateDonNhapHang(donNhapHang);
    }

    @Override
    public boolean deleteDonNhapHang(DonNhapHang donNhapHang)throws RemoteException {
        return donNhapHangDAO.deleteDonNhapHang(donNhapHang);
    }

}
