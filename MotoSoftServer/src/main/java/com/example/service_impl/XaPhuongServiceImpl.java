package com.example.service_impl;

import com.example.dao.XaPhuongDAO;
import com.example.model.XaPhuong;
import com.example.service.XaPhuongService;

import java.io.Serial;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class XaPhuongServiceImpl extends UnicastRemoteObject implements XaPhuongService, Serializable {

    @Serial
    private static final long serialVersionUID = 4901011413592057579L;

    private XaPhuongDAO xaPhuongDAO;

    public XaPhuongServiceImpl() throws RemoteException {
        xaPhuongDAO = new XaPhuongDAO();
    }

    @Override
    public boolean addXaPhuong(XaPhuong xaPhuong) throws RemoteException {
        return xaPhuongDAO.addXaPhuong(xaPhuong);
    }

    @Override
    public List<XaPhuong> getAllXaPhuong() throws RemoteException {
        return xaPhuongDAO.getAllXaPhuong();
    }

    @Override
    public List<XaPhuong> getXaPhuongInQuanHuyen(String maQuanHuyen) throws RemoteException {
        return xaPhuongDAO.getXaPhuongInQuanHuyen(maQuanHuyen);
    }

    @Override
    public XaPhuong getXaPhuongById(String id) throws RemoteException {
        return xaPhuongDAO.getXaPhuongById(id);
    }
}
