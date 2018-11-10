package iuh.edu.vn.navigationdrawertest1.fragment;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import iuh.edu.vn.navigationdrawertest1.DanhSachTruyenActivity;
import iuh.edu.vn.navigationdrawertest1.R;
import iuh.edu.vn.navigationdrawertest1.custom_adapter.DanhMuc_Custom_Adapter;
import iuh.edu.vn.navigationdrawertest1.model.DanhMuc;
import iuh.edu.vn.navigationdrawertest1.model.Truyen;

public class DanhMuc_List_Fragment extends ListFragment {
    private List<DanhMuc> danhMucList;
    private ArrayList<DanhMuc> arrDanhMuc;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        khoiTaoDataDanhMucList();
        View view=inflater.inflate(R.layout.danhmuc_list_fragment,container,false);
        DanhMuc_Custom_Adapter danhMuc_custom_adapter=new DanhMuc_Custom_Adapter(getContext(),
                R.layout.danhmuc_custom_adapter,danhMucList);
        setListAdapter(danhMuc_custom_adapter);

        return view;
    }
    public void khoiTaoDataDanhMucList(){
        danhMucList=new ArrayList<>();
        arrDanhMuc =new ArrayList<>();
        DanhMuc danhMuc1=new DanhMuc("dm1","Truyện vui");
        DanhMuc danhMuc2=new DanhMuc("dm2","Truyện kinh dị");
        DanhMuc danhMuc3=new DanhMuc("dm3","Truyện vãi lol");
        DanhMuc danhMuc4=new DanhMuc("dm4","Truyện nhạt vl");

        Truyen tr1=new Truyen("tr1", "Cô giáo thảo 2018", "hoangleo", "Chưa cập nhật", "25/10/2018","");
        Truyen tr2=new Truyen("tr2", "Cô giáo thảo 2018", "hoangleo", "Chưa cập nhật", "25/10/2018","");
        Truyen tr3=new Truyen("tr3", "Cô giáo thảo 2018", "hoangleo", "Chưa cập nhật", "25/10/2018","");
        Truyen tr4=new Truyen("tr4", "Cô giáo thảo 2018", "hoangleo", "Chưa cập nhật", "25/10/2018","");
        Truyen tr6=new Truyen("tr5", "Cô giáo thảo 2018", "hoangleo", "Chưa cập nhật", "25/10/2018","");
        Truyen tr5=new Truyen("tr6", "Cô giáo thảo 2018", "hoangleo", "Chưa cập nhật", "25/10/2018","");
        Truyen tr7=new Truyen("tr7", "Cô giáo thảo 2018", "hoangleo", "Chưa cập nhật", "25/10/2018","");

        danhMuc1.addTruyen(tr1);
        danhMuc1.addTruyen(tr2);
        danhMuc1.addTruyen(tr3);
        danhMuc1.addTruyen(tr4);
        danhMuc2.addTruyen(tr5);
        danhMuc3.addTruyen(tr6);
        danhMuc4.addTruyen(tr7);

        danhMucList.add(danhMuc1);
        danhMucList.add(danhMuc2);
        danhMucList.add(danhMuc3);
        danhMucList.add(danhMuc4);
        arrDanhMuc.addAll(danhMucList);
    }
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        DanhMuc dm=danhMucList.get(position);
        Intent intent = new Intent(getActivity(),DanhSachTruyenActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("selectedDanhMuc",dm);
        intent.putExtra("dataDanhMuc",bundle);
        startActivity(intent);
    }

//
//    public void filter(String charText){
//        charText = charText.toLowerCase(Locale.getDefault());
//        danhMucList.clear();
//        if (charText.length()==0){
//            danhMucList.addAll(arrDanhMuc);
//        }
//        else {
//            for (DanhMuc dm : arrDanhMuc){
//                if (dm.getTenDanhMuc().toLowerCase(Locale.getDefault())
//                        .contains(charText)){
//                    danhMucList.add(dm);
//                }
//            }
//        }
//    }
}
