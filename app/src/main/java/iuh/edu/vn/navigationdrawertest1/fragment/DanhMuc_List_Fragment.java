package iuh.edu.vn.navigationdrawertest1.fragment;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import iuh.edu.vn.navigationdrawertest1.DanhSachTruyenActivity;
import iuh.edu.vn.navigationdrawertest1.MainActivity;
import iuh.edu.vn.navigationdrawertest1.R;
import iuh.edu.vn.navigationdrawertest1.custom_adapter.DanhMuc_Custom_Adapter;
import iuh.edu.vn.navigationdrawertest1.model.DanhMuc;
import iuh.edu.vn.navigationdrawertest1.model.Truyen;

public class DanhMuc_List_Fragment extends ListFragment {
    private List<DanhMuc> danhMucList;
    private ArrayList<DanhMuc> arrDanhMuc;
    private DatabaseReference rootRef ;
    ProgressBar progressBar;
    DatabaseReference danhMucRef;
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        final View view=inflater.inflate(R.layout.danhmuc_list_fragment,container,false);
        rootRef = FirebaseDatabase.getInstance().getReference();
        danhMucRef=rootRef.child("allCategory");
        danhMucRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                danhMucList=new ArrayList<>();
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    DanhMuc dm=new DanhMuc(ds.child("_id").getValue(String.class), ds.child("tenDanhMuc").getValue(String.class) );
                    danhMucList.add(dm);
                }
                DanhMuc_Custom_Adapter danhMuc_custom_adapter=new DanhMuc_Custom_Adapter(getContext(),
                        R.layout.danhmuc_custom_adapter,danhMucList);
                setListAdapter(danhMuc_custom_adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(getActivity().getClass().getSimpleName(),"Failed to read value.",databaseError.toException());
            }
        });
        return view;
    }
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        DanhMuc dm=danhMucList.get(position);;
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
