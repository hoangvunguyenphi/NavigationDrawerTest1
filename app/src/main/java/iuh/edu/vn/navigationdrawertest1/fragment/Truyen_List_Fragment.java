package iuh.edu.vn.navigationdrawertest1.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.widget.Button;
import android.widget.ListView;
import android.support.v7.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import iuh.edu.vn.navigationdrawertest1.ChiTietTruyenActivity;
import iuh.edu.vn.navigationdrawertest1.R;
import iuh.edu.vn.navigationdrawertest1.custom_adapter.Truyen_List_Custom_Adapter;
import iuh.edu.vn.navigationdrawertest1.model.DanhMuc;
import iuh.edu.vn.navigationdrawertest1.model.Truyen;

public class Truyen_List_Fragment  extends ListFragment implements SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener {
    private List<Truyen> lisTruyen;
    private DanhMuc danhMuc;
    private View view;
    Truyen_List_Custom_Adapter truyen_list_custom_adapter;
    SearchView searchView;
    public static final int SOTRUYEN = 10;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.truyen_danhsach_fragment,container,false);
        final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        Bundle bundle=this.getArguments();
        danhMuc= (DanhMuc)bundle.getSerializable("selectedDanhMuc");
        if(!isNetworkAvailable(getContext())){
            Toast.makeText(getActivity(), "Lỗi : vui lòng kiểm tra kết nối internet!", Toast.LENGTH_LONG).show();
        }
        lisTruyen=new ArrayList<>();
        rootRef.child("allStory").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Query query = rootRef.child("allStory").orderByChild("danhMuc").equalTo(danhMuc.get_id());
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
                            truyen.setMoTa(ds.child("moTa").getValue(String.class));
                            lisTruyen.add(truyen);

                        }
                        phanTrangList(lisTruyen);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getActivity(), "Có gián đoạn, vui lòng tải lại !", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Có gián đoạn, vui lòng tải lại !", Toast.LENGTH_SHORT).show();
            }
        });


        TextView txtTenDanhMuc=(TextView)view.findViewById(R.id.txtTenDanhMuc);
        txtTenDanhMuc.setText(danhMuc.getTenDanhMuc());

        return view;
    }

    public static boolean isNetworkAvailable(Context con) {
        try {
            ConnectivityManager cm = (ConnectivityManager) con
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
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
        phanTrangList(filterListTruyen);
        return false;
    }
    public  void  resetSearch(){
        truyen_list_custom_adapter = new Truyen_List_Custom_Adapter(getContext(),R.layout.truyen_list_custom_adapter,lisTruyen);
        setListAdapter(truyen_list_custom_adapter);
    }

    public void phanTrangList(final List<Truyen> listTruyen){
        final Button previous,next,first,last;
        final TextView recur,max;

        max = (TextView)view.findViewById(R.id.maxTR);
        recur = (TextView)view.findViewById(R.id.recurTR);
        previous = view.findViewById(R.id.previousTR);
        next = view.findViewById(R.id.nextTR);
        first = view.findViewById(R.id.firstTR);
        last = view.findViewById(R.id.lastTR);
        int size = listTruyen.size();
        int imax = size/SOTRUYEN;
        if( size % SOTRUYEN !=0 || imax==0)
            imax+=1;
        max.setText(imax+"");
        recur.setText(1+"");
        final ArrayList<Truyen> firstList = new ArrayList<Truyen>();
        if(listTruyen.size()<=SOTRUYEN){
            for(int i=0;i<listTruyen.size();i++){
                firstList.add(listTruyen.get(i));
            }
        }else{
            for(int i=0;i<SOTRUYEN;i++){
                firstList.add(listTruyen.get(i));
            }
        }
        truyen_list_custom_adapter=new Truyen_List_Custom_Adapter(view.getContext(), R.layout.truyen_list_custom_adapter, firstList);
        setListAdapter(truyen_list_custom_adapter);
        checkTrang(1,imax,next,previous);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int irecur = Integer.parseInt(recur.getText()+"");
                int imax = Integer.parseInt(max.getText()+"");
                irecur = irecur-1;
                recur.setText(irecur+"");
                ArrayList<Truyen> phanList = new ArrayList<Truyen>();
                for(int i=irecur*SOTRUYEN -SOTRUYEN;i<irecur*SOTRUYEN;i++){
                    phanList.add(listTruyen.get(i));
                }
                truyen_list_custom_adapter = new Truyen_List_Custom_Adapter(view.getContext(),R.layout.truyen_list_custom_adapter,phanList);
                setListAdapter(truyen_list_custom_adapter);
                checkTrang(irecur,imax,next,previous);
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int irecur = Integer.parseInt(recur.getText()+"");
                int imax = Integer.parseInt(max.getText()+"");
                irecur = irecur+1;
                recur.setText(irecur+"");
                ArrayList<Truyen> phanList = new ArrayList<Truyen>();
                if(irecur==imax){
                    for(int i=irecur*SOTRUYEN -SOTRUYEN;i<listTruyen.size();i++){
                        phanList.add(listTruyen.get(i));
                    }
                }else{
                    for(int i=irecur*SOTRUYEN-SOTRUYEN;i<irecur*SOTRUYEN;i++){
                        phanList.add(listTruyen.get(i));
                    }
                }
                truyen_list_custom_adapter = new Truyen_List_Custom_Adapter(view.getContext(),R.layout.truyen_list_custom_adapter,phanList);
                setListAdapter(truyen_list_custom_adapter);
                checkTrang(irecur,imax,next,previous);
            }
        });
        final int finalImax = imax;
        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Truyen> firstListClick = new ArrayList<Truyen>();
                if(listTruyen.size()<=SOTRUYEN){
                    for(int i=0;i<listTruyen.size();i++){
                        firstListClick.add(listTruyen.get(i));
                    }
                }else{
                    for(int i=0;i<SOTRUYEN;i++){
                        firstListClick.add(listTruyen.get(i));
                    }
                }
                recur.setText(1+"");
                truyen_list_custom_adapter=new Truyen_List_Custom_Adapter(view.getContext(), R.layout.truyen_list_custom_adapter, firstListClick);
                setListAdapter(truyen_list_custom_adapter);
                checkTrang(1, finalImax,next,previous);
            }
        });
        last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int imax = Integer.parseInt(max.getText()+"");
                recur.setText(imax+"");
                ArrayList<Truyen> phanList = new ArrayList<Truyen>();
                for(int i=imax*SOTRUYEN -SOTRUYEN;i<listTruyen.size();i++){
                    phanList.add(listTruyen.get(i));
                }
                truyen_list_custom_adapter = new Truyen_List_Custom_Adapter(view.getContext(),R.layout.truyen_list_custom_adapter,phanList);
                setListAdapter(truyen_list_custom_adapter);
                checkTrang(imax,imax,next,previous);
            }
        });
    }
    public void checkTrang(int recur, int max, Button next, Button previous){
        if(recur==max)
            next.setEnabled(false);
        else
            next.setEnabled(true);
        if(recur==1)
            previous.setEnabled(false);
        else
            previous.setEnabled(true);
    }
}

