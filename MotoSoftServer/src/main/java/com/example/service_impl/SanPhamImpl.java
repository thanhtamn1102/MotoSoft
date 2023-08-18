package com.example.service_impl;

import com.example.dao.SanPhamDAO;
import com.example.model.DanhMuc;
import com.example.model.SanPham;
import com.example.service.SanPhamService;

import java.io.Serial;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class SanPhamImpl extends UnicastRemoteObject implements SanPhamService, Serializable {

    @Serial
    private static final long serialVersionUID = -3434976116720359092L;

    private SanPhamDAO sanPhamDAO;

    public SanPhamImpl() throws RemoteException {
        sanPhamDAO = new SanPhamDAO();
    }

    @Override
    public List<SanPham> getAllSanPham()throws RemoteException {
        return sanPhamDAO.getAllSanPham();
    }

    @Override
    public SanPham getSanPham(String maSanPham)throws RemoteException {
        return sanPhamDAO.getSanPham(maSanPham);
    }

    @Override
    public boolean addSanPham(SanPham sanPham)throws RemoteException {
        return sanPhamDAO.addSanPham(sanPham);
    }

    @Override
    public boolean updateSanPham(SanPham sanPham)throws RemoteException {
        return sanPhamDAO.updateSanPham(sanPham);
    }

    @Override
    public boolean removeSanPham(SanPham sanPham)throws RemoteException {
        return sanPhamDAO.removeSanPham(sanPham);
    }

    @Override
    public List<SanPham> getAllSanPhamFromDanhMuc(DanhMuc danhMuc, int top) throws RemoteException{
        return sanPhamDAO.getAllSanPhamFromDanhMuc(danhMuc, top);
    }

    @Override
    public List<SanPham> getAllSanPhamFromDanhMuc(DanhMuc danhMuc) throws RemoteException{
        return sanPhamDAO.getAllSanPhamFromDanhMuc(danhMuc);
    }
}
