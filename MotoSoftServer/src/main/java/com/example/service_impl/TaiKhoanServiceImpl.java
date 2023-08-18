package com.example.service_impl;

//import com.example.fashionshop.dao.DanhMucDAO;
//import com.example.fashionshop.model.DanhMuc;
//import com.example.fashionshop.service.DanhMucService;
//import javafx.collections.ObservableList;
import com.example.dao.TaiKhoanDAO;
import com.example.model.TaiKhoan;
import com.example.service.TaiKhoanService;

import java.io.Serial;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class TaiKhoanServiceImpl extends UnicastRemoteObject implements TaiKhoanService, Serializable {

    @Serial
    private static final long serialVersionUID = 5349998167054975370L;

    private TaiKhoanDAO taiKhoanDAO;

    public TaiKhoanServiceImpl() throws RemoteException {
        taiKhoanDAO= new TaiKhoanDAO();
    }

    @Override
    public List<TaiKhoan> getAllTaiKhoan() throws RemoteException {
        return taiKhoanDAO.getAllTaiKhoan();
    }

    @Override
    public boolean addTaiKhoan(TaiKhoan taiKhoan)throws RemoteException {
        return taiKhoanDAO.addTaiKhoan(taiKhoan);
    }

    @Override
    public boolean updateTaiKhoan(TaiKhoan taiKhoan)throws RemoteException {
        return taiKhoanDAO.updateTaiKhoan(taiKhoan);
    }

    @Override
    public boolean removeTaiKhoan(TaiKhoan taiKhoan)throws RemoteException {
        return taiKhoanDAO.deleteTaiKhoan(taiKhoan);
    }

    @Override
    public TaiKhoan getTaiKhoanById(String maNhanVien)throws RemoteException {
        return taiKhoanDAO.getTaiKhoan(maNhanVien);
    }
}
