package iuh.edu.vn.navigationdrawertest1.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@IgnoreExtraProperties
public class DanhMuc implements Serializable {
    private String _id;
    private String tenDanhMuc;
    private long count;
    public DanhMuc(){
    }
    public DanhMuc(String _id, String tenDanhMuc) {
        this._id = _id;
        this.tenDanhMuc = tenDanhMuc;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTenDanhMuc() {
        return tenDanhMuc;
    }

    public void setTenDanhMuc(String tenDanhMuc) {
        this.tenDanhMuc = tenDanhMuc;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "DanhMuc{" +
                "_id='" + _id + '\'' +
                ", tenDanhMuc='" + tenDanhMuc +"}";
    }
//    public boolean isDuplicateTruyen(Truyen tr){
//        for(Truyen tr1: listTruyen){
//            if(tr1.get_id().trim().equalsIgnoreCase(tr.get_id().trim())){
//                return true;
//            }
//        }
//        return false;
//    }
//    public boolean addTruyen(Truyen tr){
//        boolean isDuplicate=isDuplicateTruyen(tr);
//        if(!isDuplicate){
//            tr.setDanhMuc(this);
//            listTruyen.add(tr);
//            return true;
//        }
//        else{
//            return false;
//        }
//    }
//    public List<Truyen> getListTruyen() {
//        return listTruyen;
//    }
//
//    public void setListTruyen(List<Truyen> listTruyen) {
//        this.listTruyen = listTruyen;
//    }
//    public Truyen getTruyen(int i){
//        return  listTruyen.get(i);
//    }
//    public int size(){
//        return listTruyen.size();
//    }

}
