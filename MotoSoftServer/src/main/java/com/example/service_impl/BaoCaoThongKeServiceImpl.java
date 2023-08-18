package com.example.service_impl;

import com.example.dao.BaoCaoThongKeDAO;
import com.example.model.BaoCaoThongKeDoanhThu;
import com.example.model.LoaiBaoCaoThongKe;
import com.example.service.BaoCaoThongKeService;
import com.example.service.ChucVuService;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;

public class BaoCaoThongKeServiceImpl extends UnicastRemoteObject implements BaoCaoThongKeService, Serializable {

    private BaoCaoThongKeDAO baoCaoThongKeDAO;

    public BaoCaoThongKeServiceImpl() throws RemoteException {
        baoCaoThongKeDAO = new BaoCaoThongKeDAO();
    }

    @Override
    public BaoCaoThongKeDoanhThu getBaoCaoThongKeDoanhThu(LocalDate ngayBatDau, LocalDate ngayKetThuc, LoaiBaoCaoThongKe type) throws RemoteException {
        return baoCaoThongKeDAO.getBaoCaoThongKeDoanhThu(ngayBatDau, ngayKetThuc, type);
    }
}
