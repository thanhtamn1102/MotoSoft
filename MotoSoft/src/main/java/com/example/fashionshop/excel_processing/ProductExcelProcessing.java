package com.example.fashionshop.excel_processing;

//import com.example.fashionshop.model.SanPham;
import com.example.fashionshop.values.StringValues;
import com.example.model.*;
import com.example.service.DanhMucService;
import com.example.service.MauSacService;
import com.example.service.ThuongHieuService;
import javafx.collections.ObservableList;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class ProductExcelProcessing {

    private DanhMucService danhMucService;
    private MauSacService mauSacService;
    private ThuongHieuService thuongHieuService;

    public ProductExcelProcessing() {
        try {
            Context context = new InitialContext();
            danhMucService = (DanhMucService) context.lookup(StringValues.SERVER_URL + "DanhMucService");
            mauSacService = (MauSacService) context.lookup(StringValues.SERVER_URL + "MauSacService");
            thuongHieuService = (ThuongHieuService) context.lookup(StringValues.SERVER_URL + "ThuongHieuService");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private List<String> headers = Arrays.asList(
            "MÃ SẢN PHẨM",
            "TÊN SẢN PHẨM",
            "GIÁ NHẬP",
            "GIÁ BÁN",
            "SỐ LƯỢNG",
            "MÀU SẮC",
            "KÍCH THƯỚC",
            "KHỐI LƯỢNG",
            "KHOẢNG CÁCH TRỤC BÁNH XE",
            "ĐỘ CAO YÊN",
            "KHOẢNG SÁNG GẦM XE",
            "DUNG TÍCH BÌNH XĂNG",
            "KÍCH CỠ LỐP TRƯỚC",
            "KÍCH CỠ LỐP SAU",
            "PHUỘC TRƯỚC",
            "PHUỘC SAU",
            "LOẠI ĐỘNG CƠ",
            "CÔNG SUẤT TỐI ĐA",
            "DUNG TÍCH NHỚT MÁY",
            "MỨC TIÊU THỤ NHIÊN LIỆU",
            "LOẠI TRUYỀN ĐỘNG",
            "HỆ THỐNG KHỞI ĐỘNG",
            "MOMENT CỰC ĐẠI",
            "DUNG TÍCH XY LANH",
            "MÔ TẢ",
            "THƯƠNG HIỆU"
    );

    public boolean generator(File file, ObservableList<SanPham> productList) {
        try {
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet();

            Cell cell; Row row;

            HSSFCellStyle style = createStyleForTitle(workbook);
            row = sheet.createRow(0);

            for (int i = 0; i < headers.size(); i++) {
                cell = row.createCell(i, CellType.STRING);
                cell.setCellValue(headers.get(i));
                cell.setCellStyle(style);
            }

            int rowNum = 0;
            for (SanPham product : productList) {
                rowNum++;
                row = sheet.createRow(rowNum);

                cell = row.createCell(0, CellType.STRING);
                cell.setCellValue(product.getMaSanPham());

                cell = row.createCell(1, CellType.STRING);
                cell.setCellValue(product.getTenSanPham());

                cell = row.createCell(2, CellType.NUMERIC);
                cell.setCellValue(product.getGiaNhap());

                cell = row.createCell(3, CellType.NUMERIC);
                cell.setCellValue(product.getGiaBan());

                cell = row.createCell(4, CellType.NUMERIC);
                cell.setCellValue(product.getSoLuong());

                cell = row.createCell(5, CellType.STRING);
                cell.setCellValue(product.getMauSac().getMauMau() + "-" +  product.getMauSac().getTenMau());

                cell = row.createCell(6, CellType.STRING);
                cell.setCellValue(product.getKichThuoc().toString());

                cell = row.createCell(7, CellType.STRING);
                cell.setCellValue(product.getKhoiLuong());

                cell = row.createCell(8, CellType.NUMERIC);
                cell.setCellValue(product.getKhoangCachTrucBanhXe());

                cell = row.createCell(9, CellType.NUMERIC);
                cell.setCellValue(product.getDoCaoYen());

                cell = row.createCell(10, CellType.NUMERIC);
                cell.setCellValue(product.getKhoangSangGamXe());

                cell = row.createCell(11, CellType.NUMERIC);
                cell.setCellValue(product.getDungTichBinhXang());

                cell = row.createCell(12, CellType.STRING);
                cell.setCellValue(product.getKichCoLopTruoc());

                cell = row.createCell(13, CellType.STRING);
                cell.setCellValue(product.getKichCoLopSau());

                cell = row.createCell(14, CellType.STRING);
                cell.setCellValue(product.getPhuocTruoc());

                cell = row.createCell(15, CellType.STRING);
                cell.setCellValue(product.getPhuocSau());

                cell = row.createCell(16, CellType.STRING);
                cell.setCellValue(product.getLoaiDongCo());

                cell = row.createCell(17, CellType.STRING);
                cell.setCellValue(product.getCongSuatToiDa());

                cell = row.createCell(18, CellType.STRING);
                cell.setCellValue(product.getDungTichNhotMay());

                cell = row.createCell(19, CellType.NUMERIC);
                cell.setCellValue(product.getMucTieuThuNhienLieu());

                cell = row.createCell(20, CellType.STRING);
                cell.setCellValue(product.getLoaiTruyenDong());

                cell = row.createCell(21, CellType.STRING);
                cell.setCellValue(product.getHeThongKhoiDong());

                cell = row.createCell(22, CellType.STRING);
                cell.setCellValue(product.getMomentCucDai());

                cell = row.createCell(23, CellType.STRING);
                cell.setCellValue(product.getDungTichXyLanh());

                cell = row.createCell(24, CellType.STRING);
                cell.setCellValue(product.getMoTa());

                cell = row.createCell(25, CellType.STRING);
                cell.setCellValue(product.getThuongHieu().getMaThuongHieu() + "-" + product.getThuongHieu().getTenThuongHieu());
            }

            FileOutputStream outputStream = new FileOutputStream(file);
            workbook.write(outputStream);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public List<SanPham> parser(File file) {
        List<SanPham> dsSanPham = new ArrayList<>();

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
            HSSFSheet sheet = workbook.getSheetAt(0);

            Iterator<Row> rowIterator = sheet.iterator();
            while(rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if(row.getRowNum() == 0)
                    continue;

                Iterator<Cell> cellIterator = row.cellIterator();

                while(cellIterator.hasNext()) {
                    Cell cell;

                    cell = cellIterator.next();
                    String tenSanPham = cell.getStringCellValue();
                    cell = cellIterator.next();
                    double giaNhap = cell.getNumericCellValue();
                    cell = cellIterator.next();
                    double giaBan = cell.getNumericCellValue();
                    cell = cellIterator.next();
                    int soLuong = (int) cell.getNumericCellValue();
                    cell = cellIterator.next();
                    String mauSac = cell.getStringCellValue();
                    cell = cellIterator.next();
                    String kichThuoc = cell.getStringCellValue();
                    cell = cellIterator.next();
                    double khoiLuong = cell.getNumericCellValue();
                    cell = cellIterator.next();
                    double khoangCachTrucBanhXe = cell.getNumericCellValue();
                    cell = cellIterator.next();
                    double doCaoYen = cell.getNumericCellValue();
                    cell = cellIterator.next();
                    double khoangSangGamXe = cell.getNumericCellValue();
                    cell = cellIterator.next();
                    double dungTichBinhXang = cell.getNumericCellValue();
                    cell = cellIterator.next();
                    String kichCoLopTruoc = cell.getStringCellValue();
                    cell = cellIterator.next();
                    String kichCoLopSau = cell.getStringCellValue();
                    cell = cellIterator.next();
                    String phuocTruoc = cell.getStringCellValue();
                    cell = cellIterator.next();
                    String phuocSau = cell.getStringCellValue();
                    cell = cellIterator.next();
                    String loaiDongCo = cell.getStringCellValue();
                    cell = cellIterator.next();
                    String congSuatToiDa = cell.getStringCellValue();
                    cell = cellIterator.next();
                    String dungTichNhotMay = cell.getStringCellValue();
                    cell = cellIterator.next();
                    double mucTieuThuNhienLieu = cell.getNumericCellValue();
                    cell = cellIterator.next();
                    String loaiTruyenDong = cell.getStringCellValue();
                    cell = cellIterator.next();
                    String heThongKhoiDong = cell.getStringCellValue();
                    cell = cellIterator.next();
                    String momentCucDai = cell.getStringCellValue();
                    cell = cellIterator.next();
                    double dungTichXyLanh = cell.getNumericCellValue();
                    cell = cellIterator.next();
                    String moTa = cell.getStringCellValue();
                    cell = cellIterator.next();
                    String thuongHieu = cell.getStringCellValue();

                    String[] size = kichThuoc.split("x");

                    SanPham sanPham = new SanPham(
                            tenSanPham, giaNhap, giaBan, moTa, soLuong,
                            new KichThuoc(Double.parseDouble(size[0]), Double.parseDouble(size[1]), Double.parseDouble(size[2])),
                            khoiLuong, khoangCachTrucBanhXe, doCaoYen, khoangSangGamXe,
                            dungTichBinhXang, kichCoLopTruoc, kichCoLopSau, phuocTruoc, phuocSau,
                            loaiDongCo, congSuatToiDa, dungTichNhotMay, mucTieuThuNhienLieu, loaiTruyenDong,
                            heThongKhoiDong, momentCucDai, dungTichXyLanh, mauSacService.getMauSac(Integer.parseInt(mauSac.split("-")[0])),
                            thuongHieuService.getThuongHieu(Integer.parseInt(thuongHieu.split("-")[0]))
                    );

                    sanPham.getDanhMucs().add(danhMucService.getCategoryById("DM00000001"));

                    dsSanPham.add(sanPham);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return dsSanPham;
    }

    private static HSSFCellStyle createStyleForTitle(HSSFWorkbook workbook) {
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }

}
