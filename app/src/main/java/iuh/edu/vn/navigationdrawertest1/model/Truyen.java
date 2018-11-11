package iuh.edu.vn.navigationdrawertest1.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Truyen implements Serializable {
    private String _id;
    private String tieuDe;
    private String danhMuc;
    private String tacGia;
    private String noiDung;
    private String ngayTao;

    public Truyen() {
    }

    @Override
    public String toString() {
        return "Truyen{" +
                "_id='" + _id + '\'' +
                ", tieuDe='" + tieuDe + '\'' +
                ", danhMuc='" + danhMuc + '\'' +
                ", tacGia='" + tacGia + '\'' +
                ", noiDung='" + noiDung + '\'' +
                ", ngayTao='" + ngayTao + '\'' +
                '}';
    }

    public Truyen(String _id, String tieuDe, String danhMuc, String tacGia, String noiDung, String ngayTao) {
        this._id = _id;
        this.tieuDe = tieuDe;
        this.danhMuc = danhMuc;
        this.tacGia = tacGia;
        this.noiDung = noiDung;
        this.ngayTao = ngayTao;
    }

    public String get_id() {

        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTieuDe() {
        return tieuDe;
    }

    public void setTieuDe(String tieuDe) {
        this.tieuDe = tieuDe;
    }

    public String getDanhMuc() {
        return danhMuc;
    }

    public void setDanhMuc(String danhMuc) {
        this.danhMuc = danhMuc;
    }

    public String getTacGia() {
        return tacGia;
    }

    public void setTacGia(String tacGia) {
        this.tacGia = tacGia;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(String ngayTao) {
        this.ngayTao = ngayTao;
    }
}
