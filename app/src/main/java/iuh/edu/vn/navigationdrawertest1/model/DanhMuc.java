package iuh.edu.vn.navigationdrawertest1.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DanhMuc implements Serializable {
    private String maDanhMuc;
    private String tenDanhMuc;

    private List<Truyen> listTruyen=null;
    public DanhMuc(){
    }
    public String getMaDanhMuc() {
        return maDanhMuc;
    }

    public void setMaDanhMuc(String maDanhMuc) {
        this.maDanhMuc = maDanhMuc;
    }

    public String getTenDanhMuc() {
        return tenDanhMuc;
    }

    public void setTenDanhMuc(String tenDanhMuc) {
        this.tenDanhMuc = tenDanhMuc;
    }

    @Override
    public String toString() {
        return "DanhMuc{" +
                "maDanhMuc='" + maDanhMuc + '\'' +
                ", tenDanhMuc='" + tenDanhMuc +"}";
    }

    public boolean isDuplicateTruyen(Truyen tr){
        for(Truyen tr1: listTruyen){
            if(tr1.get_id().trim().equalsIgnoreCase(tr.get_id().trim())){
                return true;
            }
        }
        return false;
    }
    public boolean addTruyen(Truyen tr){
        boolean isDuplicate=isDuplicateTruyen(tr);
        if(!isDuplicate){
            tr.setDanhMuc(this);
            listTruyen.add(tr);
            return true;
        }
        else{
            return false;
        }
    }

    public List<Truyen> getListTruyen() {
        return listTruyen;
    }

    public void setListTruyen(List<Truyen> listTruyen) {
        this.listTruyen = listTruyen;
    }

    public Truyen getTruyen(int i){
        return  listTruyen.get(i);
    }
    public int size(){
        return listTruyen.size();
    }
    public DanhMuc(String maDanhMuc, String tenDanhMuc) {
        this.maDanhMuc = maDanhMuc;
        this.tenDanhMuc = tenDanhMuc;
        this.listTruyen=new ArrayList<>();
    }
}
