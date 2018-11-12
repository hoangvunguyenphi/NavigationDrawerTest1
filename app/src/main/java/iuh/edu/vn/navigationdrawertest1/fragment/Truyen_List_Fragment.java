package iuh.edu.vn.navigationdrawertest1.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.support.v7.widget.SearchView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import iuh.edu.vn.navigationdrawertest1.ChiTietTruyenActivity;
import iuh.edu.vn.navigationdrawertest1.R;
import iuh.edu.vn.navigationdrawertest1.custom_adapter.Truyen_List_Custom_Adapter;
import iuh.edu.vn.navigationdrawertest1.model.DanhMuc;
import iuh.edu.vn.navigationdrawertest1.model.Truyen;

public class Truyen_List_Fragment  extends ListFragment implements SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener {
    private List<Truyen> lisTruyen=null;
    private DanhMuc danhMuc=null;
    Truyen_List_Custom_Adapter truyen_list_custom_adapter;
    SearchView searchView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.truyen_danhsach_fragment,container,false);
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        Bundle bundle=this.getArguments();
        danhMuc= (DanhMuc)bundle.getSerializable("selectedDanhMuc");
        Query query = rootRef.child("allStory").orderByChild("danhMuc").equalTo(danhMuc.get_id());
        lisTruyen=new ArrayList<>();
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    Truyen truyen= new Truyen(ds.child("_id").getValue(String.class),
                            ds.child("tieuDe").getValue(String.class),
                            ds.child("danhMuc").getValue(String.class),
                            ds.child("tacGia").getValue(String.class),
                            ds.child("noiDung").getValue(String.class),
                            ds.child("ngayTao").getValue(String.class));
                    lisTruyen.add(truyen);
                    truyen_list_custom_adapter=new Truyen_List_Custom_Adapter(getContext(), R.layout.truyen_list_custom_adapter, lisTruyen);
                    setListAdapter(truyen_list_custom_adapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        TextView txtTenDanhMuc=(TextView)view.findViewById(R.id.txtTenDanhMuc);
        txtTenDanhMuc.setText(danhMuc.getTenDanhMuc());
        return view;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Truyen tr = lisTruyen.get(position);
        Intent intent = new Intent(getActivity(), ChiTietTruyenActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("selectedTruyen",tr);
        bundle.putString("activityTruoc","DSTruyen");
        intent.putExtra("dataTruyen",bundle);
        startActivity(intent);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_list_truyen,menu);
        MenuItem searchItem=menu.findItem(R.id.action_search);
        searchView= (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("Nhập tên truyện..");
        searchView.clearFocus();
        super.onCreateOptionsMenu(menu, inflater);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        return true;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        searchView.clearFocus();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Log.d("LOGGGG",newText);
        if(newText==null || newText.trim().isEmpty()){
            resetSearch();
            return false;
        }
        ArrayList<Truyen> filterListTruyen = new ArrayList<Truyen>(lisTruyen);
        for(Truyen tr : lisTruyen){
            if(!tr.getTieuDe().trim().toLowerCase().contains(newText.toLowerCase().trim())){
                filterListTruyen.remove(tr);
            }
        }
        truyen_list_custom_adapter = new Truyen_List_Custom_Adapter(getContext(),R.layout.truyen_list_custom_adapter,filterListTruyen);
        setListAdapter(truyen_list_custom_adapter);
        return false;
    }
    public  void  resetSearch(){
        truyen_list_custom_adapter = new Truyen_List_Custom_Adapter(getContext(),R.layout.truyen_list_custom_adapter,lisTruyen);
        setListAdapter(truyen_list_custom_adapter);
    }
}

