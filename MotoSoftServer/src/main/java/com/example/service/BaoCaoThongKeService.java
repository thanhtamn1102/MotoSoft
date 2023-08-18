package com.example.service;

import com.example.model.BaoCaoThongKeDoanhThu;
import com.example.model.LoaiBaoCaoThongKe;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDate;

public interface BaoCaoThongKeService extends Remote {

    public BaoCaoThongKeDoanhThu getBaoCaoThongKeDoanhThu(LocalDate ngayBatDau, LocalDate ngayKetThuc, LoaiBaoCaoThongKe type) throws RemoteException;

}
