package com.example;

import com.example.dao.DonNhapHangDAO;
import com.example.model.*;
import com.example.service.*;
import com.example.service_impl.*;
import com.example.utlis.StringUtils;
import com.google.gson.Gson;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.stream.JsonParser;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Server {
    private static ChucVuService chucVuService;
    private static DanhMucService danhMucService;
    private static DonNhapHangService donNhapHangService;
    private static KhachHangService khachHangService;
    private static MauSacService mauSacService;
    private static NhaCungCapService nhaCungCapService;
    private static NhanVienService nhanVienService;
    private static NuocSanXuatService nuocSanXuatService;
    private static PhieuKiemKeService phieuKiemKeService;
    private static QuanHuyenService quanHuyenService ;
    private static SanPhamService sanPhamService ;
    private static ThuongHieuService thuongHieuService ;
    private static TinhTPService tinhTPService;
    private static DonHangService donHangService;
    private static PhieuTraHangService phieuTraHangService;
    private static XaPhuongService xaPhuongService ;
    private static TaiKhoanService taiKhoanService ;
    private static VaiTroService vaiTroService ;
    private static NhomQuyenService nhomQuyenService ;
    private static QuyenService quyenService ;
    private static VaiTroNhomQuyenService vaiTroNhomQuyenService ;
    private static VaiTroQuyenService vaiTroQuyenService ;
    private static IDService idService;
    private static BaoCaoThongKeService baoCaoThongKeService;
    private static final String RES = "rmi://localhost:1102/FashionShop/";


    public static void main(String[] args) {
        try {
            idService = new IDServiceImpl();
            taiKhoanService = new TaiKhoanServiceImpl();
            chucVuService = new ChucVuServiceImpl();
            danhMucService = new DanhMucImpl();
            donNhapHangService = new DonNhapHangImpl();
            khachHangService = new KhachHangServiceImpl();
            mauSacService = new MauSacImpl();
            nhaCungCapService = new NhaCungCapImpl();
            donHangService = new DonHangServiceImpl();
            phieuTraHangService = new PhieuTraHangServiceImpl();
            nhanVienService = new NhanVienServiceImpl();
            nuocSanXuatService = new NuocSanXuatImpl();
            phieuKiemKeService = new PhieuKiemKeImpl();
            nhomQuyenService = new NhomQuyenServiceImpl();
            quanHuyenService = new QuanHuyenServiceImpl();
            sanPhamService = new SanPhamImpl();
            thuongHieuService = new ThuongHieuImpl();
            tinhTPService = new TinhTPServiceImpl();
            xaPhuongService = new XaPhuongServiceImpl();
            vaiTroService = new VaiTroServiceImpl();
            quyenService = new QuyenServiceImpl();
            vaiTroNhomQuyenService=  new VaiTroNhomQuyenServiceImpl();
            vaiTroQuyenService = new VaiTroQuyenServiceImpl();
            baoCaoThongKeService = new BaoCaoThongKeServiceImpl();

            importData();
            LocateRegistry.createRegistry(1102);

            Context context = new InitialContext();
            context.bind(RES + "IDService", idService);
            context.bind(RES + "ChucVuService", chucVuService);
            context.bind(RES + "DanhMucService", danhMucService);
            context.bind(RES + "DonNhapHangService", donNhapHangService);
            context.bind(RES + "KhachHangService", khachHangService);
            context.bind(RES + "MauSacService", mauSacService);
            context.bind(RES + "NhaCungCapService", nhaCungCapService);
            context.bind(RES + "NhanVienService", nhanVienService);
            context.bind(RES + "NuocSanXuatService", nuocSanXuatService);
            context.bind(RES + "PhieuKiemKeService", phieuKiemKeService);
            context.bind(RES + "QuanHuyenService", quanHuyenService);
            context.bind(RES + "SanPhamService", sanPhamService);
            context.bind(RES + "ThuongHieuService", thuongHieuService);
            context.bind(RES + "TinhTPService", tinhTPService);
            context.bind(RES + "DonHangService", donHangService);
            context.bind(RES + "PhieuTraHangSerivce", phieuTraHangService);
            context.bind(RES + "XaPhuongService", xaPhuongService);
            context.bind(RES + "TaiKhoanService", taiKhoanService);
            context.bind(RES + "VaiTroService", vaiTroService);
            context.bind(RES + "NhomQuyenService", nhomQuyenService);
            context.bind(RES + "QuyenService", quyenService);
            context.bind(RES + "VaiTroNhomQuyenService", vaiTroNhomQuyenService);
            context.bind(RES + "VaiTroQuyenService", vaiTroQuyenService);
            context.bind(RES + "BaoCaoThongKeService", baoCaoThongKeService);

            System.out.println("Server đã sẵn sàng...");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private static void importData() throws Exception {
        importDLTinhTPVietNam();

        KhachHang khachHang1 = new KhachHang(
                "Nguyễn Thị Thu Mơ",
                "0966002637",
                "thumo@gmail.com",
                new DiaChi(
                        tinhTPService. getTinhTPById("87"),
                        quanHuyenService.getQuanHuyenById("875"),
                        xaPhuongService.getXaPhuongById("30190"),
                        "4/10 Nguyễn Văn Bản"
                ));
        KhachHang khachHang2 = new KhachHang("Nguyễn Thanh Tùng", "0398161252", "thanhtung@gmail.com",
                new DiaChi(
                        tinhTPService.getTinhTPById("87"),
                        quanHuyenService.getQuanHuyenById("875"),
                        xaPhuongService.getXaPhuongById("30190"),
                        "4/10 Nguyễn Văn Bản"
                ));
        KhachHang khachHang3 = new KhachHang("Trần Thị An", "0396010594", "antran@gmail.com",
                new DiaChi(
                        tinhTPService.getTinhTPById("87"),
                        quanHuyenService.getQuanHuyenById("875"),
                        xaPhuongService.getXaPhuongById("30190"),
                        "4/10 Nguyễn Văn Bản"
                ));

        KhachHang khachHang4 = new KhachHang("Lê Minh Thư", "0338123511", "khoc@gmail.com",
                new DiaChi(
                        tinhTPService.getTinhTPById("87"),
                        quanHuyenService.getQuanHuyenById("875"),
                        xaPhuongService.getXaPhuongById("30190"),
                        "4/10 Nguyễn Văn Bản"
                ));
        KhachHang khachHang5 = new KhachHang("Hoài Thị Lan", "0378956218", "thoimeta@gmail.com",
                new DiaChi(
                        tinhTPService.getTinhTPById("87"),
                        quanHuyenService.getQuanHuyenById("875"),
                        xaPhuongService.getXaPhuongById("30190"),
                        "4/10 Nguyễn Văn Bản"
                ));
        KhachHang khachHang6 = new KhachHang("Trần Tuấn Anh", "0390942132", "tuânnanhtran@gmail.com",
                new DiaChi(
                        tinhTPService.getTinhTPById("87"),
                        quanHuyenService.getQuanHuyenById("875"),
                        xaPhuongService.getXaPhuongById("30190"),
                        "4/10 Nguyễn Văn Bản"
                ));
        KhachHang khachHang7 = new KhachHang("Nguyễn Lan Khuê", "0389102523", "khue921@gmail.com",
                new DiaChi(
                        tinhTPService.getTinhTPById("87"),
                        quanHuyenService.getQuanHuyenById("875"),
                        xaPhuongService.getXaPhuongById("30190"),
                        "4/10 Nguyễn Văn Bản"
                ));
        KhachHang khachHang8 = new KhachHang("Đồng Văn Ngợ", "03984201321", "ngo123@gmail.com",
                new DiaChi(
                        tinhTPService.getTinhTPById("87"),
                        quanHuyenService.getQuanHuyenById("875"),
                        xaPhuongService.getXaPhuongById("30190"),
                        "4/10 Nguyễn Văn Bản"
                ));
        KhachHang khachHang9 = new KhachHang("Lương Cao Trúc", "07891235215", "caobang@gmail.com",
                new DiaChi(
                        tinhTPService.getTinhTPById("87"),
                        quanHuyenService.getQuanHuyenById("875"),
                        xaPhuongService.getXaPhuongById("30190"),
                        "4/10 Nguyễn Văn Bản"
                ));
        KhachHang khachHang10 = new KhachHang("Hồ Ngọc Hà", "02391234562", "ngohan@gmail.com",
                new DiaChi(
                        tinhTPService.getTinhTPById("87"),
                        quanHuyenService.getQuanHuyenById("875"),
                        xaPhuongService.getXaPhuongById("30190"),
                        "4/10 Nguyễn Văn Bản"
                ));

        List<KhachHang> dsKhachHang = Arrays.asList(
                khachHang1, khachHang2, khachHang3, khachHang4, khachHang5, khachHang6, khachHang7, khachHang8, khachHang9, khachHang10
        );

        for(KhachHang kh : dsKhachHang)
            khachHangService.addCustomer(kh);

        NhomQuyen nhomQuyen1 = new NhomQuyen("EMPLOYEES_PERMISSION", "Quản lý nhân viên");
        NhomQuyen nhomQuyen2 = new NhomQuyen("SELL_PERMISSION", "Bán hàng");
        NhomQuyen nhomQuyen3 = new NhomQuyen("BILL_PERMISSION", "Quản lý hóa đơn");
        NhomQuyen nhomQuyen4 = new NhomQuyen("STATISTIC_PERMISSION", "Báo cáo thống kê");
        NhomQuyen nhomQuyen5 = new NhomQuyen("CUSTOMER_PERMISSION", "Quản lý khách hàng");
        NhomQuyen nhomQuyen6 = new NhomQuyen("PRODUCT_PERMISSION", "Quản lý sản phẩm");
        NhomQuyen nhomQuyen7 = new NhomQuyen("CATEGORY_PERMISSION", "Quản lý danh mục");
        NhomQuyen nhomQuyen8 = new NhomQuyen("ROLE_SETTING_PERMISSION", "Thiết lập vai trò");
        NhomQuyen nhomQuyen9 = new NhomQuyen("EMPLOYEES_SUPER_PERMISSION", "Nhân viên");
        NhomQuyen nhomQuyen10 = new NhomQuyen("POSITION_PERMISISON", "Quản lý chức vụ");
        NhomQuyen nhomQuyen11 = new NhomQuyen("INVENTORY_PERMISSION", "Kiểm kê");
        NhomQuyen nhomQuyen12 = new NhomQuyen("SUPPLIER_PERMISSION", "Quản lý nhà cung cấp");
        NhomQuyen nhomQuyen13 = new NhomQuyen("SELL_SUPER_PERMISSION", "Bán hàng");
        NhomQuyen nhomQuyen14 = new NhomQuyen("PRODUCT_SUPER_PERMISSION", "Sản phẩm");
        NhomQuyen nhomQuyen15 = new NhomQuyen("STORE_MANAGER_PERMISSION", "Quản lý cửa hàng");

        List<NhomQuyen> dsNhomQuuyen = Arrays.asList(
                nhomQuyen1, nhomQuyen2, nhomQuyen3, nhomQuyen4, nhomQuyen5, nhomQuyen6, nhomQuyen7, nhomQuyen8,
                nhomQuyen9, nhomQuyen10, nhomQuyen11, nhomQuyen12, nhomQuyen13, nhomQuyen14, nhomQuyen15
        );

        for(NhomQuyen nq : dsNhomQuuyen)
            nhomQuyenService.addNhomQuyen(nq);

        Quyen quyen1 = new Quyen("CREATE_CUSTOMER", "Thêm khách hàng", nhomQuyen5);
        Quyen quyen2 = new Quyen("CREATE_EMPLOYEE", "Thêm nhân viên", nhomQuyen1);
        Quyen quyen3 = new Quyen("CREATE_ORDER", "Tạo don hàng", nhomQuyen2);
        Quyen quyen4 = new Quyen("CREATE_PRODCUT", "Thêm sản phẩm", nhomQuyen6);
        Quyen quyen5 = new Quyen("DELETE_CUSTOMER", "Xóa khách hàng", nhomQuyen5);
        Quyen quyen6 = new Quyen("DELETE_EMPLOYEE", "Xóa nhân viên", nhomQuyen1);
        Quyen quyen7 = new Quyen("DELETE_PRODUCT", "Xóa sản phẩm", nhomQuyen6);
        Quyen quyen8 = new Quyen("READ_CUSTOMER", "Xem thông tin khách hàng", nhomQuyen5);
        Quyen quyen9 = new Quyen("READ_EMPLOYEE", "Xem thông tin nhân viên", nhomQuyen1);
        Quyen quyen10 = new Quyen("READ_ORDER", "Xem thông tin don hàng", nhomQuyen2);
        Quyen quyen11 = new Quyen("READ_PRODUCT", "Xem thông tin sản phẩm", nhomQuyen6);
        Quyen quyen12 = new Quyen("UPDATE_CUSTOMER", "Cập nhật thông tin khách hàng", nhomQuyen5);
        Quyen quyen13 = new Quyen("UPDATE_EMPLOYEE", "Cập nhật thông tin nhân viên", nhomQuyen1);
        Quyen quyen14 = new Quyen("UPDATE_PRODUCT", "Cập nhật thông tin sản phẩm", nhomQuyen6);
        Quyen quyen15 = new Quyen("INVENTORY", "Thực hiện kiểm kê", nhomQuyen11);
        Quyen quyen16 = new Quyen("CREATE_INVENTORY_SHEET", "Thêm phiếu kiểm kê", nhomQuyen11);
        Quyen quyen17 = new Quyen("UPDATE_INVENTORY_SHEET", "Cập nhật phiếu kiểm kê", nhomQuyen11);
        Quyen quyen18 = new Quyen("DELETE_INVENTORY_SHEET", "Xóa phiếu kiểm kê", nhomQuyen11);
        Quyen quyen19 = new Quyen("REVENUE_STATISTICS_PERMISSION", "Thống kê doanh thu", nhomQuyen4);

        List<Quyen> dsQuyen = Arrays.asList(
                quyen1, quyen2, quyen3, quyen4, quyen5, quyen6, quyen7, quyen8, quyen9, quyen10, quyen11,
                quyen12, quyen13, quyen14, quyen15, quyen16, quyen17, quyen18, quyen19
        );

        for(Quyen q : dsQuyen)
            quyenService.addQuyen(q);

        VaiTro vaiTro1 = new VaiTro("Chủ cửa hàng");
        VaiTro vaiTro2 = new VaiTro("Quản lý");
        VaiTro vaiTro3 = new VaiTro("Nhân viên bán hàng");

        List<VaiTro> dsVaiTro = Arrays.asList(vaiTro1, vaiTro2, vaiTro3);
        for(VaiTro vt : dsVaiTro)
            vaiTroService.addVaiTro(vt);

        VaiTroNhomQuyen vaiTroNhomQuyen101 = new VaiTroNhomQuyen(vaiTro1, nhomQuyen1, true);
        VaiTroNhomQuyen vaiTroNhomQuyen102 = new VaiTroNhomQuyen(vaiTro1, nhomQuyen2, true);
        VaiTroNhomQuyen vaiTroNhomQuyen103 = new VaiTroNhomQuyen(vaiTro1, nhomQuyen3, true);
        VaiTroNhomQuyen vaiTroNhomQuyen104 = new VaiTroNhomQuyen(vaiTro1, nhomQuyen4, true);
        VaiTroNhomQuyen vaiTroNhomQuyen105 = new VaiTroNhomQuyen(vaiTro1, nhomQuyen5, true);
        VaiTroNhomQuyen vaiTroNhomQuyen106 = new VaiTroNhomQuyen(vaiTro1, nhomQuyen6, true);
        VaiTroNhomQuyen vaiTroNhomQuyen107 = new VaiTroNhomQuyen(vaiTro1, nhomQuyen7, true);
        VaiTroNhomQuyen vaiTroNhomQuyen108 = new VaiTroNhomQuyen(vaiTro1, nhomQuyen8, true);
        VaiTroNhomQuyen vaiTroNhomQuyen109 = new VaiTroNhomQuyen(vaiTro1, nhomQuyen9, true);
        VaiTroNhomQuyen vaiTroNhomQuyen110 = new VaiTroNhomQuyen(vaiTro1, nhomQuyen10, true);
        VaiTroNhomQuyen vaiTroNhomQuyen111 = new VaiTroNhomQuyen(vaiTro1, nhomQuyen11, true);
        VaiTroNhomQuyen vaiTroNhomQuyen112 = new VaiTroNhomQuyen(vaiTro1, nhomQuyen12, true);
        VaiTroNhomQuyen vaiTroNhomQuyen113 = new VaiTroNhomQuyen(vaiTro1, nhomQuyen13, true);
        VaiTroNhomQuyen vaiTroNhomQuyen114 = new VaiTroNhomQuyen(vaiTro1, nhomQuyen14, true);
        VaiTroNhomQuyen vaiTroNhomQuyen115 = new VaiTroNhomQuyen(vaiTro1, nhomQuyen15, true);

        VaiTroNhomQuyen vaiTroNhomQuyen301 = new VaiTroNhomQuyen(vaiTro3, nhomQuyen1, false);
        VaiTroNhomQuyen vaiTroNhomQuyen302 = new VaiTroNhomQuyen(vaiTro3, nhomQuyen2, true);
        VaiTroNhomQuyen vaiTroNhomQuyen303 = new VaiTroNhomQuyen(vaiTro3, nhomQuyen3, true);
        VaiTroNhomQuyen vaiTroNhomQuyen304 = new VaiTroNhomQuyen(vaiTro3, nhomQuyen4, true);
        VaiTroNhomQuyen vaiTroNhomQuyen305 = new VaiTroNhomQuyen(vaiTro3, nhomQuyen5, true);
        VaiTroNhomQuyen vaiTroNhomQuyen306 = new VaiTroNhomQuyen(vaiTro3, nhomQuyen6, true);
        VaiTroNhomQuyen vaiTroNhomQuyen307 = new VaiTroNhomQuyen(vaiTro3, nhomQuyen7, true);
        VaiTroNhomQuyen vaiTroNhomQuyen308 = new VaiTroNhomQuyen(vaiTro3, nhomQuyen8, false);
        VaiTroNhomQuyen vaiTroNhomQuyen309 = new VaiTroNhomQuyen(vaiTro3, nhomQuyen9, false);
        VaiTroNhomQuyen vaiTroNhomQuyen310 = new VaiTroNhomQuyen(vaiTro3, nhomQuyen10, false);
        VaiTroNhomQuyen vaiTroNhomQuyen311 = new VaiTroNhomQuyen(vaiTro3, nhomQuyen11, true);
        VaiTroNhomQuyen vaiTroNhomQuyen312 = new VaiTroNhomQuyen(vaiTro3, nhomQuyen12, false);
        VaiTroNhomQuyen vaiTroNhomQuyen313 = new VaiTroNhomQuyen(vaiTro3, nhomQuyen13, true);
        VaiTroNhomQuyen vaiTroNhomQuyen314 = new VaiTroNhomQuyen(vaiTro3, nhomQuyen14, true);
        VaiTroNhomQuyen vaiTroNhomQuyen315 = new VaiTroNhomQuyen(vaiTro3, nhomQuyen15, false);

        List<VaiTroNhomQuyen> dsVaiTroNhomQuyen = Arrays.asList(
                vaiTroNhomQuyen101, vaiTroNhomQuyen102, vaiTroNhomQuyen103, vaiTroNhomQuyen104, vaiTroNhomQuyen105,
                vaiTroNhomQuyen106, vaiTroNhomQuyen107, vaiTroNhomQuyen108, vaiTroNhomQuyen109, vaiTroNhomQuyen110,
                vaiTroNhomQuyen111, vaiTroNhomQuyen112, vaiTroNhomQuyen113, vaiTroNhomQuyen114, vaiTroNhomQuyen115,
                vaiTroNhomQuyen301, vaiTroNhomQuyen302, vaiTroNhomQuyen303, vaiTroNhomQuyen304, vaiTroNhomQuyen305,
                vaiTroNhomQuyen306, vaiTroNhomQuyen307, vaiTroNhomQuyen308, vaiTroNhomQuyen309, vaiTroNhomQuyen310,
                vaiTroNhomQuyen311, vaiTroNhomQuyen312, vaiTroNhomQuyen313, vaiTroNhomQuyen314, vaiTroNhomQuyen315
        );

        for(VaiTroNhomQuyen vtnq : dsVaiTroNhomQuyen)
            vaiTroNhomQuyenService.addVaiTroNhomQuyen(vtnq);

        VaiTroQuyen vaiTroQuyen101 = new VaiTroQuyen(vaiTroNhomQuyen105, quyen1, true);
        VaiTroQuyen vaiTroQuyen102 = new VaiTroQuyen(vaiTroNhomQuyen101, quyen2, true);
        VaiTroQuyen vaiTroQuyen103 = new VaiTroQuyen(vaiTroNhomQuyen102, quyen3, true);
        VaiTroQuyen vaiTroQuyen104 = new VaiTroQuyen(vaiTroNhomQuyen106, quyen4, true);
        VaiTroQuyen vaiTroQuyen105 = new VaiTroQuyen(vaiTroNhomQuyen105, quyen5, true);
        VaiTroQuyen vaiTroQuyen106 = new VaiTroQuyen(vaiTroNhomQuyen101, quyen6, true);
        VaiTroQuyen vaiTroQuyen107 = new VaiTroQuyen(vaiTroNhomQuyen106, quyen7, true);
        VaiTroQuyen vaiTroQuyen108 = new VaiTroQuyen(vaiTroNhomQuyen105, quyen8, true);
        VaiTroQuyen vaiTroQuyen109 = new VaiTroQuyen(vaiTroNhomQuyen101, quyen9, true);
        VaiTroQuyen vaiTroQuyen110 = new VaiTroQuyen(vaiTroNhomQuyen102, quyen10, true);
        VaiTroQuyen vaiTroQuyen111 = new VaiTroQuyen(vaiTroNhomQuyen106, quyen11, true);
        VaiTroQuyen vaiTroQuyen112 = new VaiTroQuyen(vaiTroNhomQuyen105, quyen12, true);
        VaiTroQuyen vaiTroQuyen113 = new VaiTroQuyen(vaiTroNhomQuyen101, quyen13, true);
        VaiTroQuyen vaiTroQuyen114 = new VaiTroQuyen(vaiTroNhomQuyen106, quyen14, true);
        VaiTroQuyen vaiTroQuyen115 = new VaiTroQuyen(vaiTroNhomQuyen111, quyen15, true);
        VaiTroQuyen vaiTroQuyen116 = new VaiTroQuyen(vaiTroNhomQuyen111, quyen16, true);
        VaiTroQuyen vaiTroQuyen117 = new VaiTroQuyen(vaiTroNhomQuyen111, quyen17, true);
        VaiTroQuyen vaiTroQuyen118 = new VaiTroQuyen(vaiTroNhomQuyen111, quyen18, true);
        VaiTroQuyen vaiTroQuyen119 = new VaiTroQuyen(vaiTroNhomQuyen104, quyen19, true);

        VaiTroQuyen vaiTroQuyen301 = new VaiTroQuyen(vaiTroNhomQuyen305, quyen1, true);
        VaiTroQuyen vaiTroQuyen302 = new VaiTroQuyen(vaiTroNhomQuyen301, quyen2, false);
        VaiTroQuyen vaiTroQuyen303 = new VaiTroQuyen(vaiTroNhomQuyen302, quyen3, true);
        VaiTroQuyen vaiTroQuyen304 = new VaiTroQuyen(vaiTroNhomQuyen306, quyen4, true);
        VaiTroQuyen vaiTroQuyen305 = new VaiTroQuyen(vaiTroNhomQuyen305, quyen5, true);
        VaiTroQuyen vaiTroQuyen306 = new VaiTroQuyen(vaiTroNhomQuyen301, quyen6, false);
        VaiTroQuyen vaiTroQuyen307 = new VaiTroQuyen(vaiTroNhomQuyen306, quyen7, true);
        VaiTroQuyen vaiTroQuyen308 = new VaiTroQuyen(vaiTroNhomQuyen305, quyen8, true);
        VaiTroQuyen vaiTroQuyen309 = new VaiTroQuyen(vaiTroNhomQuyen301, quyen9, false);
        VaiTroQuyen vaiTroQuyen310 = new VaiTroQuyen(vaiTroNhomQuyen302, quyen10, true);
        VaiTroQuyen vaiTroQuyen311 = new VaiTroQuyen(vaiTroNhomQuyen306, quyen11, true);
        VaiTroQuyen vaiTroQuyen312 = new VaiTroQuyen(vaiTroNhomQuyen305, quyen12, true);
        VaiTroQuyen vaiTroQuyen313 = new VaiTroQuyen(vaiTroNhomQuyen301, quyen13, false);
        VaiTroQuyen vaiTroQuyen314 = new VaiTroQuyen(vaiTroNhomQuyen306, quyen14, true);
        VaiTroQuyen vaiTroQuyen315 = new VaiTroQuyen(vaiTroNhomQuyen311, quyen15, true);
        VaiTroQuyen vaiTroQuyen316 = new VaiTroQuyen(vaiTroNhomQuyen311, quyen16, false);
        VaiTroQuyen vaiTroQuyen317 = new VaiTroQuyen(vaiTroNhomQuyen311, quyen17, false);
        VaiTroQuyen vaiTroQuyen318 = new VaiTroQuyen(vaiTroNhomQuyen311, quyen18, false);
        VaiTroQuyen vaiTroQuyen319 = new VaiTroQuyen(vaiTroNhomQuyen304, quyen19, false);

        List<VaiTroQuyen> dsVaiTroQuyen = Arrays.asList(
                vaiTroQuyen101, vaiTroQuyen102, vaiTroQuyen103, vaiTroQuyen104, vaiTroQuyen105, vaiTroQuyen106,
                vaiTroQuyen107, vaiTroQuyen108, vaiTroQuyen109, vaiTroQuyen110, vaiTroQuyen111, vaiTroQuyen112,
                vaiTroQuyen113, vaiTroQuyen114, vaiTroQuyen115, vaiTroQuyen116, vaiTroQuyen117, vaiTroQuyen118,
                vaiTroQuyen119,
                vaiTroQuyen301, vaiTroQuyen302, vaiTroQuyen303, vaiTroQuyen304, vaiTroQuyen305, vaiTroQuyen306,
                vaiTroQuyen307, vaiTroQuyen308, vaiTroQuyen309, vaiTroQuyen310, vaiTroQuyen311, vaiTroQuyen312,
                vaiTroQuyen313, vaiTroQuyen314, vaiTroQuyen315, vaiTroQuyen316, vaiTroQuyen317, vaiTroQuyen318,
                vaiTroQuyen319
        );

        for(VaiTroQuyen vtq : dsVaiTroQuyen)
            vaiTroQuyenService.addVaiTroQuyen(vtq);

        ChucVu chucVu1 = new ChucVu("Chủ cửa hàng");
        ChucVu chucVu2 = new ChucVu("Quản lý");
        ChucVu chucVu3 = new ChucVu("Nhân viên");


        List<ChucVu> dsChucVu = Arrays.asList(chucVu1, chucVu2, chucVu3);

        NhanVien nhanVien1 = new NhanVien(
                "20019561",
                "Nguyễn Văn A",
                new Date(2022, 1, 1),
                true,
                "123456789012",
                "1234567890",
                "nguyenvana@gmail.com",
                new DiaChi(
                        tinhTPService.getTinhTPById("87"),
                        quanHuyenService.getQuanHuyenById("875"),
                        xaPhuongService.getXaPhuongById("30190"),
                        "4/10 Nguyễn Văn Bản"
                ),
                "",
                chucVu1
        );

        NhanVien nhanVien2 = new NhanVien(
                "12345678",
                "Vo Thi Cam Xuan",
                new Date(2022, 1, 1),
                false,
                "123456789012",
                "1234567890",
                "nguyenthithumo@gmail.com",
                new DiaChi(
                        tinhTPService.getTinhTPById("87"),
                        quanHuyenService.getQuanHuyenById("875"),
                        xaPhuongService.getXaPhuongById("30190"),
                        "4/10 Nguyễn Văn Bản"
                ),
                "",
                chucVu3
        );

        for(ChucVu cv : dsChucVu)
            chucVuService.addChucVu(cv);

        List<NhanVien> dsNhanVien = Arrays.asList(nhanVien1, nhanVien2);

        for(NhanVien nhanVien : dsNhanVien) {
            nhanVienService.addNhanVien(nhanVien);
        }

        String pass1 = "admin";
        String chuoiRiengTu1 = StringUtils.createUniqueString();
        String hashPass1 = StringUtils.createHashString(pass1 + chuoiRiengTu1);
        TaiKhoan taiKhoan1 = new TaiKhoan(
                nhanVien1,
                hashPass1,
                chuoiRiengTu1,
                true,
                vaiTro1
        );

        String pass2 = "admin";
        String chuoiRiengTu2 = StringUtils.createUniqueString();
        String hashPass2 = StringUtils.createHashString(pass2 + chuoiRiengTu2);
        TaiKhoan taiKhoan2 = new TaiKhoan(
                nhanVien2,
                hashPass2,
                chuoiRiengTu2,
                true,
                vaiTro3
        );

        List<TaiKhoan> dsTaiKhoan = Arrays.asList(taiKhoan1, taiKhoan2);
        for(TaiKhoan tk : dsTaiKhoan)
            taiKhoanService.addTaiKhoan(tk);

        DanhMuc danhMuc1 = new DanhMuc(idService.createMaDanhMuc(), "Tất cả", true);
        DanhMuc danhMuc2 = new DanhMuc(idService.createMaDanhMuc(), "Xe tay ga", true);
        DanhMuc danhMuc3 = new DanhMuc(idService.createMaDanhMuc(), "Xe số", true);
        DanhMuc danhMuc4 = new DanhMuc(idService.createMaDanhMuc(), "Xe côn tay", true);
        DanhMuc danhMuc5 = new DanhMuc(idService.createMaDanhMuc(), "Xe mô tô", true);

        List<DanhMuc> dsDanhMuc = Arrays.asList(
                danhMuc1, danhMuc2, danhMuc3, danhMuc4, danhMuc5
        );

        for(DanhMuc dm : dsDanhMuc) {
            dm.setMaDanhMuc(idService.createMaDanhMuc());
            danhMucService.addCategory(dm);
        }

        MauSac mauDen = new MauSac("Đen", "#000000");
        MauSac mauTrang = new MauSac("Trắng", "#FFFFFF");
        MauSac mauVang = new MauSac("Vàng", "#7BFF00");
        MauSac mauXanh = new MauSac("Xanh", "#4C9CFF");
        MauSac mauXanhLuc = new MauSac("Xanh lục", "#889144");
        MauSac mauCam = new MauSac("Cam", "#CC8B11");
        MauSac mauDo = new MauSac("Đỏ", "#FF0004");


        List<MauSac> dsMauSac = Arrays.asList(
                mauDen, mauTrang, mauVang, mauXanh, mauXanhLuc, mauCam, mauDo
        );
        for(MauSac ms : dsMauSac)
            mauSacService.addMauSac(ms);

        ThuongHieu thuongHieuHonda = new ThuongHieu("Honda");
        ThuongHieu thuongHieuYamaha = new ThuongHieu("Yamaha");
        ThuongHieu thuongHieuSYM = new ThuongHieu("SYM");
        ThuongHieu thuongHieuVinfast = new ThuongHieu("Vinfast");
        ThuongHieu thuongHieuDucati = new ThuongHieu("Ducati");
        ThuongHieu thuongHieuSuzuki = new ThuongHieu("Suzuki");

        List<ThuongHieu> dsThuongHieu = Arrays.asList(
                thuongHieuHonda, thuongHieuYamaha, thuongHieuSYM, thuongHieuVinfast,
                thuongHieuDucati, thuongHieuSuzuki
        );
        for(ThuongHieu th : dsThuongHieu)
            thuongHieuService.addThuongHieu(th);

        SanPham sanPham1 = new SanPham(
                "Honda Winner X", 46160000, 50000000, "", 2,
                new KichThuoc(2019,727,1104),
                122, 1278, 795, 151, 4.5,
                "90/80-17M/C 46P", "120/70-17M/C 58P",
                "Ống lồng, giảm chấn thủy lực", "Lò xo trụ đơn",
                "PGM-FI, 4 kỳ, DOHC, xy-lanh đơn, côn 6 số, làm mát bằng dung dịch",
                "11,5kW/9.000 vòng/phút", "1,1 lít khi thay nhớt, 1,3 lít khi rã máy",
                1.99, "Cơ khí", "Điện", "Điện",
                149.1, mauDo, thuongHieuHonda
        );
        sanPham1.setMoTa("Cuộc đời là một cuộc phiêu lưu tràn đầy những điều bất ngờ và mỗi người đều có một vạch đích của riêng mình. Hãy tự tin tạo những cú kích để vút xa và tạo dấu ấn riêng biệt cùng Honda WINNER X mới. Đánh dấu sự chuyển mình mạnh mẽ hướng tới hình ảnh một mẫu siêu xe thể thao cỡ nhỏ hàng đầu tại Việt Nam cùng những trang bị hiện đại và tối tân, WINNER X mới sẵn sàng cùng các tay lái bứt tốc trên mọi hành trình khám phá.");
        sanPham1.getDanhMucs().add(danhMuc1);
        sanPham1.getHinhAnhs().add("https://cdn.honda.com.vn/motorbikes/December2021/c6Dgnczm9zqvHI2xdBkP.png");
        sanPham1.getHinhAnhs().add("https://cdn.honda.com.vn/motorbike-strong-points/December2021/eUKep4O2Ccr3OizFH7B2.png");
        sanPham1.getHinhAnhs().add("https://cdn.honda.com.vn/motorbike-strong-points/December2021/AsIsd85eH2wDOjTews9F.png");
        sanPham1.getHinhAnhs().add("https://cdn.honda.com.vn/motorbike-strong-points/December2021/okEk00cfgvukJ5T27Pz8.png");
        sanPham1.getHinhAnhs().add("https://cdn.honda.com.vn/motorbike-strong-points/December2021/SIoZlOWSZD5lacwW3JJ3.png");


        SanPham sanPham2 = new SanPham(
                "Sh mode 125cc",56641091, 61648363, "", 3,
                new KichThuoc(1950, 669, 1100),
                116, 1304, 765, 130, 5.6,
                "80/90-16M/C 43P", "100/90-14M/C 57P",
                "Ống lồng, giảm chấn thủy lực", "Phuộc đơn",
                "4 van, 4 kỳ, DOHC, làm mát bằng dung dịch",
                "8,2kW/8.500 vòng/phút", "0,8 lít khi thay nhớt, 0,9 lít khi rã máy",
                2.16, "Tự động Vô cấp", "Điện",
                "11,7 N.m/5000 vòng/phút",8, mauDo, thuongHieuHonda
        );
        sanPham2.setMoTa("Thuộc phân khúc xe ga cao cấp và thừa hưởng thiết kế sang trọng nổi tiếng của dòng xe SH, Sh mode luôn được đánh giá cao nhờ kiểu dáng sang trọng, tinh tế tới từng đường nét, động cơ tiên tiến và các tiện nghi cao cấp xứng tầm phong cách sống thời thượng, đẳng cấp.");
        sanPham2.getDanhMucs().add(danhMuc1);
        sanPham2.getHinhAnhs().add("https://cdn.honda.com.vn/motorbike-strong-points/October2022/zUnfFfiGdz46ogxNuyKU.png");
        sanPham2.getHinhAnhs().add("https://cdn.honda.com.vn/motorbike-versions/October2022/AvewE7i9BJl2eQ7gykis.png");


        SanPham sanPham3 = new SanPham(
                "Air Blade 125", 41324727, 42502909, "", 4,
                new KichThuoc(1887, 687, 1092),
                113, 1286, 775, 141, 4.4,
                "80/90", "90/90",
                "Ống lồng, giảm chấn thủy lực", "Lò xò trụ, giảm chấn thủy lực",
                "Xăng, 4 kỳ, 1 xy-lanh, làm mát bằng dung dịch",
                "8,75kW/8.500 vòng/phút", "0,8 lít khi thay nhớt, 0,9 lít khi rã máy",
                2.26, "Vô cấp”, “Dây biến, biến thiên vô cấp", "Điện",
                "Air Blade 125: 11,3Nm/6.500 vòng/phút",8, mauDen, thuongHieuHonda
        );
        sanPham3.setMoTa("Xứng danh mẫu xe tay ga thể thao tầm trung hàng đầu trong suốt hơn một thập kỷ qua, AIR BLADE hoàn toàn mới nay được nâng cấp động cơ eSP+ 4 van độc quyền, tiên tiến nhất giúp mang trong mình mãnh lực tiên phong.");
        sanPham3.getDanhMucs().add(danhMuc1);
        sanPham3.getHinhAnhs().add("https://cdn.honda.com.vn/motorbike-versions/May2022/UDvt2b8oUaEjVwt3fY1q.png");
        sanPham3.getHinhAnhs().add("https://cdn.honda.com.vn/motorbike-strong-points/May2022/5XuDeCZU1ItWHg2346a3.jpg");


        SanPham sanPham4 = new SanPham(
                "CB150R The Streetster", 105500000, 106000000, "", 2,
                new KichThuoc(1973, 882, 1053),
                126, 1295, 802, 139, 8.5,
                "110/70R17", "150/60R17",
                "Ống lồng, giảm chấn thủy lực", "Lò xò trụ đơn ",
                "4 kỳ, xy-lanh đơn, làm mát bằng dung dịch",
                "12kW/9.500 vòng/phút", "1,5 lít khi thay nhớt, 1,2 lít khi rã máy",
                2.79, "Côn tay 6 số, truyền động băng xích tải", "Điện", "13,6 Nm",
                149.2, mauDen, thuongHieuHonda
        );
        sanPham4.setMoTa("CB150R là sự pha trộn hoàn hảo giữa cổ điển và đương đại, nam tính và đầy bản lĩnh với màu sắc.");
        sanPham4.getDanhMucs().add(danhMuc1);
        sanPham4.getHinhAnhs().add("https://cdn.honda.com.vn/motorbike-versions/May2022/x8ZCNtM5g2QRGQl4udo8.png");
        sanPham4.getHinhAnhs().add("https://cdn.honda.com.vn/motorbikes/May2022/2jmCWBQTySgeYBdfaqNm.jpg");


        SanPham sanPham5 = new SanPham(
                "CBR500R 2022", 192490000, 192600000, "", 2,
                new KichThuoc(2080,760,1145),
                192, 1410, 785, 130, 17.1,
                "120/70ZR17", "160/60ZR17",
                "Hành trình ngược Showa SFF-BP 41mm", "Lò xo trụ đơn, giảm chấn thủy lực, 5 cấp độ điều chỉnh tải trước lò xo",
                "4 kỳ, 2 xy-lanh, làm mát bằng chất lỏng",
                "35 kW tại 8.600 vòng/phút", "1,1 lít khi thay nhớt, 1,3 lít khi rã máy",
                3.59, "6 cấp", " Côn tay 6 số", "Điện",
                471, mauDo, thuongHieuHonda
        );
        sanPham5.setMoTa("CBR500R - UY LỰC TỐC ĐỘ, SẴN SÀNG TIẾN TỚI Cho dù bạn đang lựa chọn chiếc xe sport đầu tiên để bắt đầu hành trình đam mê hay bạn là tay lái nhiều kinh nghiệm đang tìm kiếm cảm giác phấn khích từ một chiếc xe nhỏ hơn, CBR500R đều có thể thỏa mãn nhu cầu của bạn. Với kích thước nhỏ gọn và thiết kế thể thao, CBR500R thỏa sức để bạn khám phá khả năng cầm lái trên những cung đường đèo uốn lượn. Động cơ phản hồi nhanh nhạycới từng cú vặn ga và bất kì tác động nào từ người điều khiển, cho bạn tận hưởng từng khoảnh khắc sống động mỗi khi cầm lái.");
        sanPham5.getDanhMucs().add(danhMuc1);
        sanPham5.getHinhAnhs().add("https://cdn.honda.com.vn/motorbike-strong-points/September2022/YhEjOLDkhOJFYocwtH4b.jpg");
        sanPham5.getHinhAnhs().add("https://cdn.honda.com.vn/motorbike-versions/September2022/0fuVUozl9BEsLqJwjCJE.jpg");


        SanPham sanPham6 = new SanPham(
                "CB500F 2022", 184490000, 185600000, "", 2,
                new KichThuoc(2080,800,1060),
                182, 1410, 785, 145, 17.1,
                "120/70ZR17", "160/60ZR17",
                "Hành trình ngược Showa SFF-BP 41mm", "Lò xo trụ đơn, giảm chấn thủy lực, 5 cấp độ điều chỉnh tải trước lò xo",
                "4 kỳ, 2 xy-lanh, làm mát bằng chất lỏng",
                "35 kW tại 8.600 vòng/phút", "Sau khi xả 2,5 lít, Sau khi xả và thay lọc nhớt động cơ 2,7 lít, Sau khi rã máy: 3,2 lít",
                3.59, "6 cấp", " Côn tay 6 số", "Điện",
                471, mauDo, thuongHieuHonda
        );
        sanPham6.setMoTa("CB500F - GIẢI PHÓNG ĐAM MÊ TRONG BẠN CB500F - mẫu xe vượt ra khỏi mọi định nghĩa về phương tiện di chuyển thông thường. Một dòng xe mang đến sự khác biệt, đánh dấu cá tính đặc trưng của bạn, và cho bạn khám phá những trải nghiệm lái mới: linh hoạt trên đường đô thị mã vẫn mượt mà trên mọi cung đường trường. Với CB500F, đã đến lúc bạn có thể quên đi các rào cản, phương tiện công cộng khiến bạn bức bách, hay tắc nghẽn giao thông bó buộc đôi chân. Thiết kế trọng lượng khung xe của CB500F nhẹ nhàng, mang đến cảm giác lái nhanh nhẹn, thỏa mãn đam mê bứt phá. Phản ứng động cơ nhạy bén, cho bạn tận hưởng cảm giác hưng phấn trong từng chuyến đi. CB500F xứng danh là người bạn đồng hành hoàn hảo trong những chuyến hành trình của bạn, dù đó là hành trình trong thành phố hay những chuyến touring đường dài.");
        sanPham6.getDanhMucs().add(danhMuc1);
        sanPham6.getHinhAnhs().add("https://cdn.honda.com.vn/motorbike-strong-points/September2022/ElPCOfZA7Xgt2z2dY94r.jpg");
        sanPham6.getHinhAnhs().add("https://cdn.honda.com.vn/motorbike-versions/September2022/R9ElSY1IKhYil9vXOaDG.jpg");


        SanPham sanPham7 = new SanPham(
                "Vision (Phiên bản Cá tính)", 34942909, 35000000, "", 4,
                new KichThuoc(1925,686,1126),
                102, 1277, 785, 130, 4.9,
                "80/90-16M/C 43P", "90/90-14M/C 46P",
                "Ống lồng, giảm chấn thủy lực", "Lò xo trụ đơn, giảm chấn thủy lực ",
                "Xăng, 4 kỳ, 1 xy-lanh, làm mát bằng chất lỏng",
                "6,59kW/7.500 vòng/phút", "0,65 lít khi thay dầu, 0,8 lít khi rã máy",
                1.83, "Đai", "Điện", "Điện",
                109.5, mauDo, thuongHieuHonda
        );
        sanPham7.setMoTa("Thuộc phân khúc xe tay ga giá thấp, Vision luôn là mẫu xe được ưa chuộng trong giới trẻ và có số lượng bán ra lớn nhất tại thị trường Việt Nam suốt nhiều năm qua nhờ kiểu dáng trẻ trung, thanh lịch và nhỏ gọn. Tiếp tục kế thừa nét ưu việt từ những phiên bản trước, Vision 2020 nay đã được nâng cấp toàn diện cả về ngoại hình thời trang và mạnh mẽ, phong cách sang trọng cùng những tiện ích và công nghê hiện đại, mang đến thêm lựa chọn đa dạng, giúp khách hàng tự tin khẳng định cá tính.");
        sanPham7.getDanhMucs().add(danhMuc1);
        sanPham7.getHinhAnhs().add("https://cdn.honda.com.vn/motorbikes/December2020/tvsGv5RA4cWKldDPuiXj.png");
        sanPham7.getHinhAnhs().add("https://cdn.honda.com.vn/motorbike-versions/December2020/osAEdqYtGQdNqAyZp64i.png");


        SanPham sanPham8 = new SanPham(
                "LEAD 125cc", 39066545, 40000000, "", 4,
                new KichThuoc(1844,680,1130),
                113, 1273, 760, 120, 6.0,
                "90/90-12 44JJ", " 100/90-10 56J",
                "Ống lồng, giảm chấn thủy lực", "Lò xo trụ đơn, giảm chấn thủy lực ",
                "Xăng, 4 kỳ, làm mát bằng chất lỏng",
                "8,22 kw / 8.500 vòng/phút", "0,8 lít khi thay dầu, 0,9 lít khi rã máy",
                2.16, "Tự động", "Vô cấp", "Điện",
                124.8, mauDo, thuongHieuHonda
        );
        sanPham8.setMoTa("Kế thừa ngôn ngữ thiết kế hiện đại cùng nhiều tiện ích vượt trội vốn có, xe LEAD 125cc mới nay được nâng tầm với động cơ thế hệ mới nhất của Honda eSP+ như trên các mẫu xe ga cao cấp, màu sắc mới hợp xu hướng, cổng sạc tiện lợi, thiết kế phía trước tinh tế, tem xe nổi bật giúp nâng tầm phong cách và tối đa trải nghiệm lái xe cho người sở hữu.");
        sanPham8.getDanhMucs().add(danhMuc1);
        sanPham8.getHinhAnhs().add("https://cdn.honda.com.vn/motorbikes/December2021/PxbOtPG619Vte84CQHPg.png");
        sanPham8.getHinhAnhs().add("https://cdn.honda.com.vn/motorbike-strong-points/December2021/COLWmyw9vpbechnaIgBL.png");


        SanPham sanPham11 = new SanPham(
                "Future 125 FI", 30328363, 31100000, "", 4,
                new KichThuoc(1931,711,1083),
                104, 1258, 756, 133, 4.6,
                "70/90 - 17 M/C 38P", "80/90 - 17 M/C 50P",
                "Ống lồng, giảm chấn thủy lực", "Lò xo trụ đơn, giảm chấn thủy lực ",
                "Xăng, làm mát bằng không khí, 4 kỳ, 1 xy-lanh",
                "6,83 kW/7.500 vòng/phút", "0,7 lít khi thay dầu, 0,9 lít khi rã máy",
                1.54, "4 số tròn", "Điện và đạp chân", "Điện",
                124.9, mauDo, thuongHieuHonda
        );
        sanPham11.setMoTa("Honda Future 125 FI với thiết kế trẻ trung, lịch lãm và hiện đại được bổ sung màu mới, tạo những điểm nhấn ấn tượng, thu hút mọi ánh nhìn. Cùng với vị thế là mẫu xe số cao cấp hàng đầu phân khúc tại Việt Nam, Future 125 FI cho bạn tự tin thể hiện phong cách, phẩm chất của mình trên mọi hành trình.");
        sanPham11.getDanhMucs().add(danhMuc1);
        sanPham11.getHinhAnhs().add("https://cdn.honda.com.vn/motorbikes/October2021/q9ElGvGXqy8Kvz4v3eJp.jpg");
        sanPham11.getHinhAnhs().add("https://cdn.honda.com.vn/motorbike-strong-points/October2021/Q1UICdPkMMgHUClZhwzc.png");


        SanPham sanPham9 = new SanPham(
                "SH350i", 148990000, 149000000, "", 2,
                new KichThuoc(2160,743,1162),
                172, 1450, 805, 132, 9.3,
                "110/70-16 M/C 52S", "130/70R16 M/C 61S",
                "", "",
                "SOHC, 4 kỳ, xy-lanh đơn 4 van, làm mát bằng chất lỏng; đáp ứng Euro 3",
                "21,5 kW/7.500 vòng/phút", "Sau khi xả 1,4 lít, Sau khi xả và vệ sinh lưới lọc 1,5 lít, Sau khi rã máy 1,85 lít",
                3.54, "", "Biến thiên vô cấp", "",
                329.6, mauDo, thuongHieuHonda
        );
        sanPham9.setMoTa("Trải qua hành trình hơn 37 năm phát triển từ mẫu xe đầu tiên SH50 vào năm 1984 cho đến các phiên bản cao cấp hơn như SH125/150, SH300i, dòng xe SH của Honda đã và đang trở thành sự lựa chọn tối ưu của khách hàng trên toàn thế giới. Tại Việt Nam, hình ảnh mẫu xe SH từ lâu đã trở thành biểu tượng cho tính đẳng cấp, sang trọng và sự hoàn hảo. Kế thừa những nét đặc trưng đó, mẫu xe SH350i mới tiếp tục gây ấn tượng mạnh mẽ với vẻ đẹp đậm chất 'hiện đại công nghệ' và “bề thề”. Được cải tiến và nâng cấp toàn bộ từ thiết kế, động cơ, công nghệ và trang bị tiện nghi, SH350i phô diễn được sức mạnh và sự năng động khi di chuyển, xứng đáng với vị trí ông hoàng trong phân khúc xe tay ga cao cấp tại Việt Nam. Bằng việc ra mắt phiên bản thể thao, ngoài sự sang trọng vốn có, mẫu xe SH350i hoàn toàn mới còn đem đến sự trẻ trung, năng động cho người sở hữu.");
        sanPham9.getDanhMucs().add(danhMuc1);
        sanPham9.getHinhAnhs().add("https://cdn.honda.com.vn/motorbikes/August2021/aZjXabSDNavTrFbXWCe8.jpg");
        sanPham9.getHinhAnhs().add("https://cdn.honda.com.vn/motorbike-versions/August2021/j7dtMXFqKNJJX98rrTQW.jpg");


        SanPham sanPham10 = new SanPham(
                "Super Cub C125", 85801091, 86320000, "", 3,
        new KichThuoc(1910,718,1002),
                109, 1243, 780, 136, 3.7,
                "70/90-17M/C 38P", "80/90-17M/C 50P",
        "Ống lồng, giảm chấn thủy lực", "Lò xo trụ đơn",
                "PGM-FI, 4 kỳ, xy-lanh đơn, làm mát bằng không khí",
                "6,87kW/7.500 vòng/phút", "0,8 lít khi thay nhớt, 1,0 lít khi rã máy",
                1.5, "Cơ khí", "Điện", "",
                123.94, mauDo, thuongHieuHonda
        );
        sanPham10.setMoTa("Thiết kế cổ điển, thanh lịch đậm chất Super CUB, tư thế lái xe thoải mái.");
        sanPham10.getDanhMucs().add(danhMuc1);
        sanPham10.getHinhAnhs().add("https://cdn.honda.com.vn/motorbike-strong-points/October2021/WwL6LIRJq466Z2Z5irlr.png");
        sanPham10.getHinhAnhs().add("https://cdn.honda.com.vn/motorbikes/October2021/nuRedtB9JREnqcxDDvY3.png");


        SanPham sanPham12 = new SanPham(
                "Vision", 30230182, 31899370, "", 5,
        new KichThuoc(1871,686,1101),
                96, 1255, 761, 120, 4.9,
                "80/90-14M/C 40P", "90/90-14M/C 46P",
                "Ống lồng, giảm chấn thủy lực", "Lò xo trụ đơn, giảm chấn thủy lực ",
                "Xăng, 4 kỳ, 1 xi-lanh, làm mát bằng không khí",
                "6,59kW/7.500 vòng/phút", "0,65 lít khi thay nhớt, 0,8 lít khi rã máy",
                1.88, "Đai", "Điện", "",
                109.5, mauDo, thuongHieuHonda
        );
        sanPham12.setMoTa("Thuộc phân khúc xe tay ga giá thấp, Vision luôn là mẫu xe được ưa chuộng trong giới trẻ và có số lượng bán ra lớn nhất tại thị trường Việt Nam suốt nhiều năm qua nhờ kiểu dáng trẻ trung, thanh lịch và nhỏ gọn. Sau 6 năm kể từ lần thay đổi lớn gần nhất vào năm 2014, chiếc xe Vision 2020 đã được nâng cấp toàn diện cả về kiểu dáng thời trang cùng những tiện ích và công nghệ hiện đại, hứa hẹn mang đến những trải nghiệm vượt xa kỳ vọng cho những người trẻ năng động và luôn dẫn đầu xu hướng.");
        sanPham12.getDanhMucs().add(danhMuc1);
        sanPham12.getHinhAnhs().add("https://cdn.honda.com.vn/motorbikes/December2020/Bf4EFMSz9Q70ZZj3BVm1.png");
        sanPham12.getHinhAnhs().add("https://cdn.honda.com.vn/motorbikes/December2020/xWSrVFuVMXjxl4PIdaza.png");


        SanPham sanPham13 = new SanPham(
                "Wave RSX FI 110", 21688363, 24567579, "", 5,
        new KichThuoc(1921,709,1081),
                99, 1227, 760, 135, 4.0,
                "70/90 - 17 M/C 38P", "80/90 - 17 M/C 50P",
                "Ống lồng, giảm chấn thủy lực", "Lò xo trụ đơn, giảm chấn thủy lực ",
                "Xăng, 4 kỳ, 1 xi-lanh, làm mát bằng không khí",
                "6,46kW/7.500 vòng/phút", "0,8 lít khi thay nhớt, 1,0 lít khi rã máy",
                1.7, "Cơ khí, 4 số tròn", "Điện, đạp chân", "",
                109.2, mauDo, thuongHieuHonda
        );
        sanPham13.setMoTa("Wave RSX Fi 110 mang diện mạo hoàn toàn mới, với thiết kế thể thao đặc trưng vốn có, nay mạnh mẽ và khỏe khoắn hơn, phù hợp phong cách của các bạn trẻ.");
        sanPham13.getDanhMucs().add(danhMuc1);
        sanPham13.getHinhAnhs().add("https://cdn.honda.com.vn/motorbikes/November2020/hH9D5e4x75nRQAmex6sx.png");
        sanPham13.getHinhAnhs().add("https://cdn.honda.com.vn/motorbikes/November2020/eHjhxG7nuazXsRuqc67v.png");


        SanPham sanPham14 = new SanPham(
                "CBR650R phiên bản 2021", 254490000, 258675790, "", 5,
        new KichThuoc(2130,750,1150),
                208, 1450, 810, 130, 15.4,
                "120/70ZR17 M/C", "180/55ZR17 M/C",
                "Giảm xóc hành trình ngược Showa SFF-BP, 41mm", "Lò trụ đơn với tải trước lò xo có 10 cấp điều chỉnh",
                "Động cơ 4 xy-lanh, 4 kỳ làm mát bằng chất lỏng, 16 van DOHC",
                "70 kW/12.000 vòng/phút", "2,3 lít khi thay nhớt, 2,6 lít khi thay nhớt và bộ lọc, 3,0 lít khi rã máy",
                4.58, "6 cấp", "Điện ", "",
                649, mauDo, thuongHieuHonda
        );
        sanPham14.setMoTa("CBR650R - 'chiến mã' mạnh mẽ được thiết kế kỳ công để trở thành nhà vô địch trong mọi hành trình. Với kiểu dáng khỏe khoắn, năng động, đậm chất Racing và sức mạnh động cơ tập trung tại dải vòng tua tầm trung, mang lại khả năng bứt tốc hoàn hảo, CBR650R sẽ mang lại những trải nghiệm tốc độ tuyệt đỉnh, đốt cháy mãnh lực nhà vô địch bên trong bạn, dù bạn đang trên đường phố hay trên đường đua.");
        sanPham14.getDanhMucs().add(danhMuc1);
        sanPham14.getHinhAnhs().add("https://cdn.honda.com.vn/motorbikes/December2021/hvj48WyZogC7RL8gX66K.png");
        sanPham14.getHinhAnhs().add("https://cdn.honda.com.vn/motorbike-generation/December2021/y5KtSOxq1luXjsBd9G51.png");


        SanPham sanPham15 = new SanPham(
                "Wave Alpha 110cc", 17859273, 18500000, "", 5,
                new KichThuoc(21914,688,1075),
                97, 1224, 769, 138, 3.7,
                "70/90-17M/C 38P", "80/90-17M/C 50P",
                "Ống lồng, giảm chấn thủy lực", "Lò xo trụ đơn",
                "4 kỳ, 1 xilanh, làm mát bằng không khí",
                "6,12kW/7.500 vòng/phút", "0,8 lít khi thay nhớt, 1,0 lít khi rã máy",
                1.90, "Cơ khí", "Điện", "Điện",
                109.1, mauDo, thuongHieuHonda
        );
        sanPham15.setMoTa("Wave Alpha phiên bản 2023 trẻ trung và năng động với màu đen mờ hoàn toàn mới cùng thiết kế bộ tem mới ấn tượng, thu hút ánh nhìn, cho bạn tự tin thể hiện cá tính của mình trên mọi hành trình.");
        sanPham15.getDanhMucs().add(danhMuc1);
        sanPham15.getHinhAnhs().add("https://cdn.honda.com.vn/motorbikes/August2022/Mxog3bmELura9ZBr4lVz.png");
        sanPham15.getHinhAnhs().add("https://cdn.honda.com.vn/motorbike-strong-points/December2021/eUKep4O2Ccr3OizFH7B2.png");
        sanPham15.getHinhAnhs().add("https://cdn.honda.com.vn/motorbikes/August2022/UZRmrbvdNm1isCkzbd6E.png");


        List<SanPham> dsSanPham = Arrays.asList(
                sanPham1, sanPham2, sanPham3, sanPham4, sanPham5, sanPham6, sanPham7, sanPham8, sanPham9, sanPham10, sanPham11,
                sanPham12,sanPham13, sanPham14, sanPham15
        );

        for(SanPham sp : dsSanPham) {
            sp.setMaSanPham(idService.createMaSanPham());
            sanPhamService.addSanPham(sp);
        }

        NhaCungCap nhaCungCap1 = new NhaCungCap("Honda VietNam", "1800 8001", "cr@honda.com.vn",
                new DiaChi(
                        tinhTPService.getTinhTPById("79"),
                        quanHuyenService.getQuanHuyenById("771"),
                        xaPhuongService.getXaPhuongById("27172"),
                        "Tầng 26, Tòa nhà Trụ Sở Điều Hành Và Trung Tâm Thương Mại Viettel, 285 Cách Mạng Tháng Tám"
                ));
        NhaCungCap nhaCungCap2 = new NhaCungCap("Yamaha VietNam", "18001588", "cskh@yamaha-motor.com.vn",
                new DiaChi(
                        tinhTPService.getTinhTPById("79"),
                        quanHuyenService.getQuanHuyenById("770"),
                        xaPhuongService.getXaPhuongById("27139"),
                        "186A Nam Kỳ Khởi Nghĩa"
                ));

        List<NhaCungCap> dsNhaCungCap = Arrays.asList(
                nhaCungCap1, nhaCungCap2
        );

        for(NhaCungCap ncc : dsNhaCungCap) {
            nhaCungCapService.addNhaCungCap(ncc);
        }


        PhieuKiemKe phieuKiemKe1 = new PhieuKiemKe(
                LocalDateTime.now(),
                PhieuKiemKeStatus.TAO_MOI_CHO_KIEM_KE,
                nhanVien1
        );

        ChiTietPhieuKiem chiTietPhieuKiem2 = new ChiTietPhieuKiem(phieuKiemKe1, sanPham2);
        chiTietPhieuKiem2.setSoLuongThucTe(chiTietPhieuKiem2.getSoLuongHeThong());
        ChiTietPhieuKiem chiTietPhieuKiem3 = new ChiTietPhieuKiem(phieuKiemKe1, sanPham3);
        chiTietPhieuKiem3.setSoLuongThucTe(chiTietPhieuKiem3.getSoLuongHeThong());

        phieuKiemKe1.setChiTietPhieuKiems(
                chiTietPhieuKiem2, chiTietPhieuKiem3
        );

        List<PhieuKiemKe> dsPhieuKiemKe = Arrays.asList(phieuKiemKe1);

        for(PhieuKiemKe pkk : dsPhieuKiemKe) {
            pkk.setMaPhieuKiemKe(idService.createMaPhieuKiemKe());
            phieuKiemKeService.addPhieuKiemKe(pkk);
        }


        DonNhapHang donNhapHang1 = new DonNhapHang(
                LocalDateTime.now(),
                DonNhapHangStatus.TAO_MOI,
                nhanVien1,
                nhaCungCap1
        );

        ChiTietDonNhapHang chiTietDonNhapHang1 = new ChiTietDonNhapHang(donNhapHang1, sanPham1, 2);
        ChiTietDonNhapHang chiTietDonNhapHang2 = new ChiTietDonNhapHang(donNhapHang1, sanPham2, 5);
        ChiTietDonNhapHang chiTietDonNhapHang3 = new ChiTietDonNhapHang(donNhapHang1, sanPham3, 1);

        donNhapHang1.getChiTietDonNhapHangs().addAll(Arrays.asList(chiTietDonNhapHang1, chiTietDonNhapHang2, chiTietDonNhapHang3));
        donNhapHang1.tinhTongTien();
        donNhapHang1.tinhTongThanhToan();
        donNhapHang1.tinhCongNo();
        donNhapHang1.tinhTongSoLuong();
        donNhapHang1.tinhSoLuongConLai();
        donNhapHang1.setVAT(8);
        donNhapHang1.setPhiVanChuyen(100000);
        donNhapHang1.setDaThanhToan(0);
        donNhapHang1.setGiamGia(0);
        donNhapHang1.setPhuThu(0);

        List<DonNhapHang> dsDonNhapHang = Arrays.asList(
                donNhapHang1
        );

        for(DonNhapHang dnh : dsDonNhapHang) {
            dnh.setMaDonNhapHang(idService.createMaDonNhapHang());
            donNhapHangService.addDonNhapHang(dnh);
        }


        DonHang donHang1 = new DonHang(
                LocalDateTime.of(2022, 10, 27, 13, 10),
                100000,
                0,
                8,
                0,
                "Giao hang som giup minh",
                nhanVien1,
                khachHang1
        );
        ChiTietDonHang chiTietDonHang1 = new ChiTietDonHang(
                donHang1,
                sanPham1,
                sanPham1.getGiaBan(),
                2
        );
        ChiTietDonHang chiTietDonHang2 = new ChiTietDonHang(
                donHang1,
                sanPham2,
                sanPham2.getGiaBan(),
                1
        );

        donHang1.getChiTietDonHangs().addAll(Arrays.asList(chiTietDonHang1, chiTietDonHang2));
        donHang1.tinhTongTien();
        donHang1.tinhTongThanhTien();


        DonHang donHang2 = new DonHang(
                "DH00000002",
                LocalDateTime.of(2022, 10, 28, 13, 10),
                0,
                0,
                8,
                0,
                "Đơn hàng 2",
                nhanVien2,
                khachHang3
        );

        ChiTietDonHang chiTietDonHang21 = new ChiTietDonHang(
                donHang2,
                sanPham4,
                sanPham4.getGiaBan(),
                2
        );
        ChiTietDonHang chiTietDonHang22 = new ChiTietDonHang(
                donHang2,
                sanPham3,
                sanPham3.getGiaBan(),
                1
        );

        donHang2.getChiTietDonHangs().addAll(Arrays.asList(chiTietDonHang21, chiTietDonHang22));
        donHang2.tinhTongTien();
        donHang2.tinhTongThanhTien();


        DonHang donHang3 = new DonHang(
                "DH00000003",
                LocalDateTime.of(2022, 10, 13, 13, 10),
                50000,
                10000,
                8,
                0,
                "",
                nhanVien2,
                khachHang4
        );

        ChiTietDonHang chiTietDonHang31 = new ChiTietDonHang(
                donHang3,
                sanPham7,
                sanPham7.getGiaBan(),
                2
        );

        donHang3.getChiTietDonHangs().addAll(Arrays.asList(chiTietDonHang31));
        donHang3.tinhTongTien();
        donHang3.tinhTongThanhTien();


        DonHang donHang4 = new DonHang(
                "DH00000004",
                LocalDateTime.of(2022, 10, 26, 13, 10),
                0,
                0,
                8,
                0,
                "",
                nhanVien1,
                khachHang7
        );

        ChiTietDonHang chiTietDonHang41 = new ChiTietDonHang(
                donHang4,
                sanPham7,
                sanPham7.getGiaBan(),
                1
        );

        donHang4.getChiTietDonHangs().addAll(Arrays.asList(chiTietDonHang41));
        donHang4.tinhTongTien();
        donHang4.tinhTongThanhTien();


        DonHang donHang5 = new DonHang(
                "DH00000005",
                LocalDateTime.of(2022, 10, 27, 13, 10),
                70000,
                10000,
                8,
                0,
                "",
                nhanVien1,
                khachHang2
        );

        ChiTietDonHang chiTietDonHang51 = new ChiTietDonHang(
                donHang5,
                sanPham6,
                sanPham6.getGiaBan(),
                2
        );
        ChiTietDonHang chiTietDonHang52 = new ChiTietDonHang(
                donHang5,
                sanPham4,
                sanPham4.getGiaBan(),
                1
        );
        ChiTietDonHang chiTietDonHang53 = new ChiTietDonHang(
                donHang5,
                sanPham1,
                sanPham1.getGiaBan(),
                1
        );

        donHang5.getChiTietDonHangs().addAll(Arrays.asList(chiTietDonHang51, chiTietDonHang52, chiTietDonHang53));
        donHang5.tinhTongTien();
        donHang5.tinhTongThanhTien();


        DonHang donHang6 = new DonHang(
                "DH00000006",
                LocalDateTime.of(2022, 10, 27, 13, 10),
                0,
                20000,
                8,
                0,
                "Upppppppp",
                nhanVien2,
                khachHang5
        );

        ChiTietDonHang chiTietDonHang61 = new ChiTietDonHang(
                donHang6,
                sanPham1,
                sanPham1.getGiaBan(),
                2
        );
        ChiTietDonHang chiTietDonHang62 = new ChiTietDonHang(
                donHang6,
                sanPham9,
                sanPham9.getGiaBan(),
                1
        );
        ChiTietDonHang chiTietDonHang63 = new ChiTietDonHang(
                donHang6,
                sanPham11,
                sanPham11.getGiaBan(),
                2
        );
        ChiTietDonHang chiTietDonHang64 = new ChiTietDonHang(
                donHang6,
                sanPham15,
                sanPham15.getGiaBan(),
                3
        );

        donHang6.getChiTietDonHangs().addAll(Arrays.asList(chiTietDonHang61, chiTietDonHang62, chiTietDonHang63, chiTietDonHang64));
        donHang6.tinhTongTien();
        donHang6.tinhTongThanhTien();


        DonHang donHang7 = new DonHang(
                "DH00000007",
                LocalDateTime.of(2022, 10, 25, 13, 10),
                0,
                0,
                8,
                0,
                "",
                nhanVien2,
                khachHang8
        );

        ChiTietDonHang chiTietDonHang71 = new ChiTietDonHang(
                donHang7,
                sanPham5,
                sanPham5.getGiaBan(),
                3
        );

        donHang7.getChiTietDonHangs().addAll(Arrays.asList(chiTietDonHang71));
        donHang7.tinhTongTien();
        donHang7.tinhTongThanhTien();


        DonHang donHang8 = new DonHang(
                "DH00000008",
                LocalDateTime.of(2022, 9, 30, 13, 10),
                30000,
                0,
                8,
                20000,
                "",
                nhanVien1,
                khachHang2
        );

        ChiTietDonHang chiTietDonHang81 = new ChiTietDonHang(
                donHang8,
                sanPham10,
                sanPham10.getGiaBan(),
                1
        );

        donHang8.getChiTietDonHangs().addAll(Arrays.asList(chiTietDonHang81));
        donHang8.tinhTongTien();
        donHang8.tinhTongThanhTien();


        DonHang donHang9 = new DonHang(
                "DH00000009",
                LocalDateTime.of(2022, 10, 20, 13, 10),
                0,
                0,
                8,
                50000,
                "",
                nhanVien1,
                khachHang4
        );

        ChiTietDonHang chiTietDonHang91 = new ChiTietDonHang(
                donHang9,
                sanPham9,
                sanPham9.getGiaBan(),
                4
        );

        donHang9.getChiTietDonHangs().addAll(Arrays.asList(chiTietDonHang91));
        donHang9.tinhTongTien();
        donHang9.tinhTongThanhTien();


        DonHang donHang10 = new DonHang(
                "DH00000010",
                LocalDateTime.of(2022, 10, 29, 13, 10),
                0,
                5000,
                8,
                0,
                "",
                nhanVien2,
                khachHang5
        );

        ChiTietDonHang chiTietDonHang101 = new ChiTietDonHang(
                donHang10,
                sanPham6,
                sanPham6.getGiaBan(),
                4
        );
        ChiTietDonHang chiTietDonHang102 = new ChiTietDonHang(
                donHang10,
                sanPham3,
                sanPham3.getGiaBan(),
                1
        );

        donHang10.getChiTietDonHangs().addAll(Arrays.asList(chiTietDonHang101, chiTietDonHang102));
        donHang10.tinhTongTien();
        donHang10.tinhTongThanhTien();

        DonHang donHang11 = new DonHang(
                "DH00000011",
                LocalDateTime.of(2022, 10, 20, 13, 10),
                0,
                5000,
                8,
                0,
                "",
                nhanVien2,
                khachHang5
        );

        ChiTietDonHang chiTietDonHang111 = new ChiTietDonHang(
                donHang11,
                sanPham5,
                sanPham6.getGiaBan(),
                2
        );
        ChiTietDonHang chiTietDonHang112 = new ChiTietDonHang(
                donHang11,
                sanPham3,
                sanPham3.getGiaBan(),
                2
        );

        donHang11.getChiTietDonHangs().addAll(Arrays.asList(chiTietDonHang111, chiTietDonHang112));
        donHang11.tinhTongTien();
        donHang11.tinhTongThanhTien();


        List<DonHang> dsDonHang = Arrays.asList(
                donHang1, donHang2, donHang3, donHang4, donHang5, donHang6, donHang7, donHang8, donHang9, donHang10, donHang11
        );

        for(DonHang dh : dsDonHang) {
            dh.setMaDonHang(idService.createMaDonHang());
            donHangService.addDonHang(dh);
        }

        PhieuTraHang phieuTraHang1 = new PhieuTraHang(
                donHang4.getMaDonHang().replace("DH", "PTH"),
                donHang4,
                LocalDateTime.of(2022, 10, 27, 10, 51),
                100, 0,
                "", nhanVien1);
        phieuTraHang1.tinhTienHoanLai();
        phieuTraHang1.tinhTongTienHoan();

        PhieuTraHang phieuTraHang2 = new PhieuTraHang(
                donHang9.getMaDonHang().replace("DH", "PTH"),
                donHang9,
                LocalDateTime.of(2022, 10, 20, 15, 00),
                90, 0,
                "", nhanVien1
        );
        phieuTraHang2.tinhTienHoanLai();
        phieuTraHang2.tinhTongTienHoan();

        List<PhieuTraHang> dsPhieuTraHang = Arrays.asList(
                phieuTraHang1, phieuTraHang2
        );

        for(PhieuTraHang phieuTraHang : dsPhieuTraHang)
            phieuTraHangService.addPhieuTraHang(phieuTraHang);
    }
    private static void importDLTinhTPVietNam() throws FileNotFoundException, RemoteException {
        List<TinhTP> dsTinhTP = new ArrayList<>();
        List<QuanHuyen> dsQuanHuyen = new ArrayList<>();
        List<XaPhuong> dsXaPhuong = new ArrayList<>();

        Gson gson = new Gson();

        JsonParser parser = Json.createParser(new FileInputStream("data/tinh_tp.json"));

        String key = null;
        while(parser.hasNext()) {
            JsonParser.Event event = parser.next();
            if(event == JsonParser.Event.KEY_NAME) {
                key = parser.getString();
            }
            if(event == JsonParser.Event.START_OBJECT && key != null) {
                JsonObject jsonObject = parser.getObject();
                TinhTP tinhTP = gson.fromJson(jsonObject.toString(), TinhTP.class);
                dsTinhTP.add(tinhTP);
            }
        }

        JsonParser parser1 = Json.createParser(new FileInputStream("data/quan_huyen.json"));
        String key1 = null;
        while(parser1.hasNext()) {
            JsonParser.Event event = parser1.next();
            if(event == JsonParser.Event.KEY_NAME) {
                key1 = parser1.getString();
            }
            if(event == JsonParser.Event.START_OBJECT && key1 != null) {
                JsonObject jsonObject = parser1.getObject();
                QuanHuyen quanHuyen = gson.fromJson(jsonObject.toString(), QuanHuyen.class);
                String parentCode = jsonObject.getString("parent_code");
                quanHuyen.setTinhTP(new TinhTP(parentCode));
                dsQuanHuyen.add(quanHuyen);
            }
        }

        JsonParser parser2 = Json.createParser(new FileInputStream("data/xa_phuong.json"));
        String key2 = null;
        while(parser2.hasNext()) {
            JsonParser.Event event = parser2.next();
            if(event == JsonParser.Event.KEY_NAME) {
                key2 = parser2.getString();
            }
            if(event == JsonParser.Event.START_OBJECT && key2 != null) {
                JsonObject jsonObject = parser2.getObject();
                XaPhuong xaPhuong = gson.fromJson(jsonObject.toString(), XaPhuong.class);
                String parentCode = jsonObject.getString("parent_code");
                xaPhuong.setQuanHuyen(new QuanHuyen(parentCode));
                dsXaPhuong.add(xaPhuong);
            }
        }

        for(TinhTP tinhTP : dsTinhTP)
            tinhTPService.addTinhTP(tinhTP);
        for(QuanHuyen quanHuyen : dsQuanHuyen)
            quanHuyenService.addQuanHuyen(quanHuyen);
        for(XaPhuong xaPhuong : dsXaPhuong)
            xaPhuongService.addXaPhuong(xaPhuong);
    }
}