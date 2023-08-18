package com.example.service_impl;


import com.example.dao.ThuongHieuDAO;
import com.example.model.ThuongHieu;
import com.example.service.ThuongHieuService;

import java.io.Serial;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class ThuongHieuImpl extends UnicastRemoteObject implements ThuongHieuService, Serializable {

    @Serial
    private static final long serialVersionUID = -4312261344556600001L;

    private ThuongHieuDAO thuongHieuDAO;

    public ThuongHieuImpl() throws RemoteException {
        thuongHieuDAO = new ThuongHieuDAO();
    }

    @Override
    public List<ThuongHieu> getAllThuongHieu()throws RemoteException  {
        return thuongHieuDAO.getAllThuongHieu();
    }

    @Override
    public ThuongHieu getThuongHieu(int brandId)throws RemoteException  {
        return thuongHieuDAO.getThuongHieu(brandId);
    }

    @Override
    public boolean addThuongHieu(ThuongHieu thuongHieu)throws RemoteException {
        return thuongHieuDAO.addThuongHieu(thuongHieu);
    }

    @Override
    public boolean updateThuongHieu(ThuongHieu thuongHieu)throws RemoteException {
        return thuongHieuDAO.updateThuongHieu(thuongHieu);
    }
    @Override
    public boolean deleteThuongHieu(ThuongHieu thuongHieu)throws RemoteException {
        return thuongHieuDAO.deleteThuongHieu(thuongHieu);
    }
}
