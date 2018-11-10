package iuh.edu.vn.navigationdrawertest1.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import iuh.edu.vn.navigationdrawertest1.ChiTietTruyenActivity;
import iuh.edu.vn.navigationdrawertest1.R;
import iuh.edu.vn.navigationdrawertest1.custom_adapter.Truyen_List_Custom_Adapter;
import iuh.edu.vn.navigationdrawertest1.model.DanhMuc;
import iuh.edu.vn.navigationdrawertest1.model.Truyen;

public class Truyen_List_Fragment  extends ListFragment {
    private List<Truyen> lisTruyen=null;
    private DanhMuc danhMuc=null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.truyen_danhsach_fragment,container,false);
        getListTruyen();
        Truyen_List_Custom_Adapter truyen_list_custom_adapter=new Truyen_List_Custom_Adapter(getContext(), R.layout.truyen_list_custom_adapter,lisTruyen);
        setListAdapter(truyen_list_custom_adapter);
        TextView txtTenDanhMuc=(TextView)view.findViewById(R.id.txtTenDanhMuc);
        txtTenDanhMuc.setText(danhMuc.getTenDanhMuc());
        return view;
    }
    public void getListTruyen(){
        Bundle bundle=this.getArguments();
        danhMuc=(DanhMuc)bundle.getSerializable("selectedDanhMuc");
        lisTruyen=danhMuc.getListTruyen();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Truyen tr = lisTruyen.get(position);
        Intent intent = new Intent(getActivity(), ChiTietTruyenActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("selectedTruyen",tr);
        intent.putExtra("dataTruyen",bundle);
        startActivity(intent);
    }
}
