package com.example.service_impl;

import com.example.dao.NhanVienDAO;
import com.example.model.NhanVien;
import com.example.service.NhanVienService;

import java.io.Serial;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class NhanVienServiceImpl extends UnicastRemoteObject implements NhanVienService, Serializable {

    @Serial
    private static final long serialVersionUID = -5415817559736554449L;

    private final NhanVienDAO nhanVienDAO;

    public NhanVienServiceImpl() throws RemoteException {
        nhanVienDAO = new NhanVienDAO();
    }

    @Override
    public List<NhanVien> getAllNhanVien() throws RemoteException{
        return nhanVienDAO.getAllNhanVien();
    }

    @Override
    public NhanVien getNhanVien(String maNhanVien) throws RemoteException{
        return nhanVienDAO.getNhanVien(maNhanVien);
    }

    @Override
    public boolean addNhanVien(NhanVien nhanVien) throws RemoteException {
        return nhanVienDAO.addNhanVien(nhanVien);
    }

    @Override
    public boolean updateNhanVien(NhanVien nhanVien) throws RemoteException {
        return nhanVienDAO.updateNhanVien(nhanVien);
    }

    @Override
    public boolean deleteNhanVien(NhanVien nhanVien) throws RemoteException {
        return nhanVienDAO.deleteNhanVien(nhanVien);
    }
}
