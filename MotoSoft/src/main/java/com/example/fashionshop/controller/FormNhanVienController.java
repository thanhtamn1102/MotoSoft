package com.example.fashionshop.controller;

import com.example.fashionshop.MotoSoftApp;
import com.example.fashionshop.values.StringValues;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import com.example.model.*;
import com.example.service.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;


public class FormNhanVienController implements Initializable {
    public static final int VIEW = 0;
    public static final int ADD = 1;
    public static final int UPDATE = 2;

    @FXML private Label lblTitle;
    @FXML private TextField txtHoTen;
    @FXML private DatePicker txtNgaySinh;
    @FXML private RadioButton rdMale;
    @FXML private RadioButton rdFemale;
    @FXML private ComboBox<ChucVu> cbxChucVu;
    @FXML private ComboBox<VaiTro> cbxVaiTro;
    @FXML private ComboBox<TinhTP> cbxTinhTP;
    @FXML private ComboBox<QuanHuyen> cbxQuanHuyen;
    @FXML private ComboBox<XaPhuong> cbxXaPhuong;
    @FXML private TextField txtCccd;
    @FXML private TextField txtSdt;
    @FXML private TextField txtEmail;
    @FXML private TextField txtDiaChi;
    @FXML private ImageView imageView;
    @FXML private TextField txtImageLink;
    @FXML private Button btnThemHinhAnhTuMayTinh;
    @FXML private Button btnLoadImage;
    @FXML private Button btnLuu, btnThoat, btnXoa, btnCapNhat;
    @FXML private Label lblHoTenError, lblSdtError, lblCccdError, lblNgaySinhError, lblEmailError, lblDiaChiError, lblThanhPhoError, lblQuanError, lblPhuongError;
    @FXML private TextField txtMaNhanVien;
    @FXML private HBox boxImage;


    private int type;
    private String maNV;
    private NhanVien nhanVien;
    private ObservableList<NhanVien> dsNhanVien;
    private ObservableList<VaiTro> dsVaiTro;

    private static Context context ;

    private TinhTPService tinhTPService;        private ObservableList<TinhTP> dsTinhTP;
    private QuanHuyenService quanHuyenService;  private ObservableList<QuanHuyen> dsQuanHuyen;
    private XaPhuongService xaPhuongService;    private ObservableList<XaPhuong> dsXaPhuong;
    private IDService idService;
    private VaiTroService vaiTroService;
    private NhanVienService nhanVienService;
    private TaiKhoanService taiKhoanService;
    private ChucVuService chucVuService;

    public FormNhanVienController(ObservableList<NhanVien> dsNhanVien, NhanVien nhanVien, int type){
        this.type = type;
        this.dsNhanVien = dsNhanVien;
        this.nhanVien = nhanVien;

        try {
            context = new InitialContext();
            tinhTPService = (TinhTPService) context.lookup(StringValues.SERVER_URL + "TinhTPService");
            quanHuyenService = (QuanHuyenService) context.lookup(StringValues.SERVER_URL + "QuanHuyenService");
            xaPhuongService = (XaPhuongService) context.lookup(StringValues.SERVER_URL + "XaPhuongService");
            idService = (IDService) context.lookup(StringValues.SERVER_URL + "IDService");
            vaiTroService = (VaiTroService) context.lookup(StringValues.SERVER_URL + "VaiTroService");
            nhanVienService = (NhanVienService) context.lookup(StringValues.SERVER_URL + "NhanVienService");
            taiKhoanService = (TaiKhoanService) context.lookup(StringValues.SERVER_URL + "TaiKhoanService");
            chucVuService = (ChucVuService) context.lookup(StringValues.SERVER_URL + "ChucVuService");
        }catch (Exception exception){
            exception.printStackTrace();
        }

        dsTinhTP = FXCollections.observableArrayList();
        dsQuanHuyen = FXCollections.observableArrayList();
        dsXaPhuong = FXCollections.observableArrayList();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        init(dsNhanVien);
        addEvents();
        addControls();
        loadData();

        try {
            context = new InitialContext();

            if (type == VIEW) {
                setInputEditable(false);
                btnLuu.setVisible(false);
                boxImage.setVisible(false);
                lblTitle.setText("Thông tin nhân viên");
            } else if (type == ADD) {
                this.maNV = idService.createMaNhanVien();
                txtMaNhanVien.setText(maNV);
                btnXoa.setVisible(false);
                lblTitle.setText("Thêm nhân viên");
                btnCapNhat.setVisible(false);
                rdMale.setSelected(true);
                loadChucVuToComboBox();
                setNoImage();
            } else if (type == UPDATE) {
                btnCapNhat.setVisible(false);
                btnXoa.setVisible(false);
                lblTitle.setText("Cập nhật thông tin nhân viên");
                loadChucVuToComboBox();
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    private void init(ObservableList<NhanVien> dsNhanVien) {
        FXMLLoader fxmlLoader = new FXMLLoader(MotoSoftApp.class.getResource("views/form-nhan-vien.fxml"));
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private void closeStage(MouseEvent mouseEvent){
        Stage stage = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    private void addEvents(){
        btnThoat.setOnMousePressed(mouseEvent -> {
            closeStage(mouseEvent);
        });

        btnLuu.setOnMousePressed(mouseEvent -> {
            String tenNhanVien = txtHoTen.getText().trim();
            ChucVu chucVu = cbxChucVu.getSelectionModel().getSelectedItem();
            LocalDate ngay = txtNgaySinh.getValue();
            Date ngaySinh = null;
            if(ngay != null){
                ngaySinh = new Date(ngay.getYear(), ngay.getMonthValue(), ngay.getDayOfMonth());
            }
            String hinhAnh = txtImageLink.getText().trim();
            String sdt = txtSdt.getText().trim();
            String cccd = txtCccd.getText().trim();
            String diaChi = txtDiaChi.getText().trim();
            String email = txtEmail.getText().trim();
            TinhTP tinhTP = cbxTinhTP.getSelectionModel().getSelectedItem();
            QuanHuyen quanHuyen = cbxQuanHuyen.getSelectionModel().getSelectedItem();
            XaPhuong xaPhuong = cbxXaPhuong.getSelectionModel().getSelectedItem();

            Boolean gioiTinh;
            if(rdMale.isSelected()){
                gioiTinh = true;
            } else {gioiTinh= false;}

            try {
                if(checkInput(tenNhanVien, ngay, cccd, sdt, email, tinhTP, quanHuyen, xaPhuong, diaChi)){
                    NhanVien nhanVien = new NhanVien(
                            idService.createMaNhanVien(),
                            tenNhanVien,
                            ngaySinh,
                            gioiTinh,
                            cccd,
                            sdt,
                            email,
                            new DiaChi(tinhTP, quanHuyen, xaPhuong, diaChi),
                            hinhAnh,
                            chucVu
                    );

                    if(type == ADD){
                        if(addNhanVien(nhanVien)){
                            closeStage(mouseEvent);
                        }
                    }
                    else if(type == UPDATE){
                        nhanVien.setMaNhanVien(this.nhanVien.getMaNhanVien());
                        updateNhanVien(nhanVien, mouseEvent);
                    }
                }
            } catch (Exception ex){
                ex.printStackTrace();
            }

        });

        btnXoa.setOnMouseClicked(mouseEvent -> {
            deleteNhanVien(nhanVien, mouseEvent);
        });

        btnLoadImage.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String imageLink = txtImageLink.getText();
                imageView.setImage(new Image(imageLink, true));
            }
        });

        btnThemHinhAnhTuMayTinh.setOnMouseClicked(mouseEvent -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Chọn hình sản phẩm");
            File file = fileChooser.showOpenDialog((Stage)((Node) mouseEvent.getSource()).getScene().getWindow());
            if(file != null){
                Image image = new Image(file.toURI().toString(), true);
                imageView.setImage(image);
                txtImageLink.setText(file.toURI().toString());
            }
        });

        btnCapNhat.setOnMouseClicked(mouseEvent -> {
            if(btnCapNhat.getText().equalsIgnoreCase("Cập nhật")) {
                btnCapNhat.setText("Hủy cập nhật"); btnCapNhat.setStyle("-fx-background-color: #C4C4C4");
                btnLuu.setVisible(true);
                btnXoa.setVisible(false);
                setInputEditable(true);
                type = UPDATE;
            }
            else {
                btnCapNhat.setText("Cập nhật"); btnCapNhat.setStyle("-fx-background-color: #0C75F5");
                btnLuu.setVisible(false);
                btnXoa.setVisible(true);
                setInputEditable(false);
                type = VIEW;
                loadData();
            }
        });

        cbxTinhTP.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TinhTP>() {
            @Override
            public void changed(ObservableValue<? extends TinhTP> observableValue, TinhTP tinhTP, TinhTP t1) {
//                lblThanhPhoError.setText("");
                if(t1 != null) {
                    try {
                        dsQuanHuyen.setAll(quanHuyenService.getQuanHuyenInTinhTP(t1.getId()));
                        dsXaPhuong.clear();
                    }catch (Exception exception){
                        exception.printStackTrace();
                    }
                }
            }
        });

        cbxQuanHuyen.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<QuanHuyen>() {
            @Override
            public void changed(ObservableValue<? extends QuanHuyen> observableValue, QuanHuyen quanHuyen, QuanHuyen t1) {
//                lblQuanError.setText("");
                if(t1 != null)
                    try {
                        dsXaPhuong.setAll(xaPhuongService.getXaPhuongInQuanHuyen(t1.getId()));
                    }catch (Exception exception){
                        exception.printStackTrace();
                    }
            }
        });

        cbxXaPhuong.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<XaPhuong>() {
            @Override
            public void changed(ObservableValue<? extends XaPhuong> observableValue, XaPhuong xaPhuong, XaPhuong t1) {
//                lblPhuongError.setText("");
            }
        });
    }

    private void addControls() {
        ToggleGroup gender = new ToggleGroup();
        rdMale.setToggleGroup(gender);
        rdFemale.setToggleGroup(gender);

        setTxtError();

    }

    private void loadData() {
        try {
            dsTinhTP.setAll(tinhTPService.getAllTinhTP());
        }catch (Exception exception){
            exception.printStackTrace();
        }
        cbxTinhTP.setItems(dsTinhTP);
        cbxQuanHuyen.setItems(dsQuanHuyen);
        cbxXaPhuong.setItems(dsXaPhuong);

        Image im;
        im = new Image(MotoSoftApp.class.getResource("drawable/no-image_available.png").toString(), true);
        imageView.setImage(im);

        if(nhanVien != null){
            txtMaNhanVien.setText(nhanVien.getMaNhanVien());
            txtHoTen.setText(nhanVien.getHoTen());
            txtDiaChi.setText(nhanVien.getDiaChi().getDiaChiChiTiet());
            txtEmail.setText(nhanVien.getEmail());
            txtSdt.setText(nhanVien.getSdt());
            txtCccd.setText(nhanVien.getCccd());
            LocalDate ngay = LocalDate.of(
                    nhanVien.getNgaySinh().getYear(),
                    nhanVien.getNgaySinh().getMonth(),
                    nhanVien.getNgaySinh().getDay());
            txtNgaySinh.setValue(LocalDate.parse(ngay.toString()));
            txtImageLink.setText(nhanVien.getHinhAnh());
            cbxChucVu.getSelectionModel().select(nhanVien.getChucVu());

            if(nhanVien.isGioiTinh())
                rdMale.setSelected(true);
            else rdFemale.setSelected(true);

            if(nhanVien.getHinhAnh() != null && (!nhanVien.getHinhAnh().isEmpty()))
                im = new Image(nhanVien.getHinhAnh(), true);
            else
                im = new Image(MotoSoftApp.class.getResource("drawable/no-image_available.png").toString(), true);
            imageView.setImage(im);

            cbxTinhTP.getSelectionModel().select(nhanVien.getDiaChi().getTinhTP());
            cbxQuanHuyen.getSelectionModel().select(nhanVien.getDiaChi().getQuanHuyen());
            cbxXaPhuong.getSelectionModel().select(nhanVien.getDiaChi().getXaPhuong());
        }
    }


    private boolean addNhanVien(NhanVien nhanVien) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Add nhanVien");
        alert.setHeaderText(null);

        try {
            if (dsNhanVien.add(nhanVien)) {
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setContentText("Thêm nhân viên thành công");
                alert.show();
                return true;
            } else {
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("Thêm nhân viên không thành công");
                alert.show();
                return false;
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }

        return false;
    }

    private void updateNhanVien(NhanVien nhanVien, MouseEvent mouseEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Cập nhật thông tin nhân viên");
        alert.setHeaderText(null);

        try {
            if (nhanVienService.updateNhanVien(nhanVien)) {
                dsNhanVien.set(dsNhanVien.indexOf(nhanVien), nhanVien);
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setContentText("Cập nhật thông tin nhân viên thành công!");
                alert.show();
                closeStage(mouseEvent);
            } else {
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("Error: Cập nhật thông tin nhân viên không thành công");
                alert.show();
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    private void deleteNhanVien(NhanVien nhanVien, MouseEvent mouseEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete nhanVien");
        alert.setHeaderText(null);
        alert.setContentText("Bạn có chắc chắn muốn xóa nhân viên này không?");
        Optional<ButtonType> optional = alert.showAndWait();

        try {
            if (optional.get() == ButtonType.OK) {
                if (nhanVien.getTaiKhoan() != null)
                    taiKhoanService.removeTaiKhoan(nhanVien.getTaiKhoan());
                if (nhanVienService.deleteNhanVien(nhanVien) && dsNhanVien.remove(nhanVien)) {
                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.setContentText("Xóa nhân viên thành công");
                    closeStage(mouseEvent);
                } else {
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setContentText("Error: Xóa nhân viên không thành công");
                }
                alert.show();
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    private boolean checkInput(String tenNV, LocalDate ngaySinh, String cccd, String sdt, String email,
                               TinhTP tinhTP, QuanHuyen quanHuyen, XaPhuong xaPhuong, String diaChi) {
        setLabelErrorEmpty();

        boolean check = true;

        if(tenNV.isEmpty()){
            lblHoTenError.setText("Tên nhân viên không được để trống");
            check = false;
        }

        if(ngaySinh == null){
            lblNgaySinhError.setText("Ngày sinh không được để trống");
            check = false;
        }
        else if((LocalDate.now().getYear() - ngaySinh.getYear()) < 18){
            lblNgaySinhError.setText("Tuổi của nhân viên phải > 18 tuổi");
            check = false;
        }

        Pattern sdtPattern = Pattern.compile("^0[1-9]{2}[0-9]{7}$");
        if(sdt.isEmpty()){
            lblSdtError.setText("Số điện thoại không được để trống");
            check = false;
        }
        else if(!sdtPattern.matcher(sdt).matches()){
            lblSdtError.setText("Số điện thoại không đúng định dạng");
            check = false;
        }

        Pattern emailPattern = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
        if(email.isEmpty()){
            lblEmailError.setText("Email không được để trống");
            check = false;
        }
        else if(!emailPattern.matcher(email).matches()){
            lblEmailError.setText("Email không đúng dạng");
            check = false;
        }

        Pattern cccdPattern = Pattern.compile("^[0-9]{12}$");
        if(cccd.isEmpty()){
            lblCccdError.setText("Số cccd không được để trống");
            check = false;
        }
        else if(!cccdPattern.matcher(cccd).matches()){
            lblCccdError.setText("Số căn cước công dân phải có 12 số");
            check = false;
        }

        if(tinhTP == null) {
            lblThanhPhoError.setText("Tỉnh thành phố không được để trống");
            check = false;
        }

        if(quanHuyen == null) {
            lblQuanError.setText("Quận huyện không được để trống");
            check = false;
        }

        if(xaPhuong == null) {
            lblPhuongError.setText("Xã phường không được để trống");
            check = false;
        }

        if(diaChi.isEmpty()){
            lblDiaChiError.setText("Địa chỉ không được để trống");
            check = false;
        }

        return true;
    }

    private void loadChucVuToComboBox(){
        if(nhanVien == null)
            cbxChucVu.getSelectionModel().select(2);
        else
            cbxChucVu.getSelectionModel().select(nhanVien.getChucVu());
    }

    private void setInputEditable(boolean b){
        txtHoTen.setEditable(b);
        txtNgaySinh.setDisable(!b);
        cbxChucVu.setDisable(!b);
        txtCccd.setEditable(b);
        txtEmail.setEditable(b);
        txtDiaChi.setEditable(b);
        txtImageLink.setEditable(b);
        btnThemHinhAnhTuMayTinh.setDisable(!b);
        txtSdt.setEditable(b);
        btnLoadImage.setDisable(!b);
        btnThemHinhAnhTuMayTinh.setDisable(!b);
        txtImageLink.setVisible(b);
        boxImage.setVisible(b);
    }

    private void setLabelErrorEmpty(){
        lblHoTenError.setText("");
        lblCccdError.setText("");
        lblEmailError.setText("");
        lblDiaChiError.setText("");
        lblNgaySinhError.setText("");
        lblSdtError.setText("");
    }

    private void setNoImage(){
        Image im = new Image(MotoSoftApp.class.getResource("drawable/no-image_available.png").toString(), true);
        imageView.setImage(im);
    }

    private void setTxtError(){
        txtHoTen.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                lblHoTenError.setText("");
            }
        });

        txtSdt.setOnKeyPressed(keyEvent -> {
            String keyChar = keyEvent.getCode().getChar();
            if(keyChar.matches("[0-9]") || keyEvent.getCode().equals(KeyCode.BACK_SPACE)) {
                txtSdt.setEditable(true);
            }
            else {
                txtSdt.setEditable(false);
            }
        });

        txtCccd.setOnKeyPressed(keyEvent -> {
            String keyChar = keyEvent.getCode().getChar();
            if(keyChar.matches("[0-9]") || keyEvent.getCode().equals(KeyCode.BACK_SPACE)) {
                txtCccd.setEditable(true);
            }
            else {
                txtCccd.setEditable(false);
            }
        });

    }

}
