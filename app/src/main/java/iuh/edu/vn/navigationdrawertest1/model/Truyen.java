package iuh.edu.vn.navigationdrawertest1.model;

import java.io.Serializable;

public class Truyen implements Serializable {
    private String _id;
    private String tieuDe;
    private DanhMuc danhMuc;
    private String tacGia;
    private String noiDung;
    private String ngayTao;
    private String hashTag;
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

    public DanhMuc getDanhMuc() {
        return danhMuc;
    }

    public void setDanhMuc(DanhMuc danhMuc) {
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

    public String getHashTag() {
        return hashTag;
    }

    public void setHashTag(String hashTag) {
        this.hashTag = hashTag;
    }

    @Override
    public String toString() {
        return "Truyen{" +
                "_id='" + _id + '\'' +
                ", tieuDe='" + tieuDe + '\'' +
                ", danhMuc=" + danhMuc.getTenDanhMuc() +
                ", tacGia='" + tacGia + '\'' +
                ", noiDung='" + noiDung + '\'' +
                ", ngayTao='" + ngayTao + '\'' +
                ", hashTag='" + hashTag + '\'' +
                '}';
    }

    public Truyen(String _id, String tieuDe, DanhMuc danhMuc, String tacGia, String noiDung, String ngayTao, String hashTag) {

        this._id = _id;
        this.tieuDe = tieuDe;
        this.danhMuc = danhMuc;
        this.tacGia = tacGia;
        this.noiDung = noiDung;
        this.ngayTao = ngayTao;
        this.hashTag = hashTag;
    }
    public Truyen(String _id, String tieuDe, String tacGia, String noiDung, String ngayTao, String hashTag) {

        this._id = _id;
        this.tieuDe = tieuDe;
        this.tacGia = tacGia;
        this.noiDung = noiDung;
        this.ngayTao = ngayTao;
        this.hashTag = hashTag;
    }
    public Truyen(){

    }
}
