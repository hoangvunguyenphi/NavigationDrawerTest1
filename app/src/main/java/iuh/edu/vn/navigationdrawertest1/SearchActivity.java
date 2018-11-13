package iuh.edu.vn.navigationdrawertest1;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import iuh.edu.vn.navigationdrawertest1.custom_adapter.DanhMuc_Custom_Adapter;
import iuh.edu.vn.navigationdrawertest1.custom_adapter.Truyen_List_Custom_Adapter;
import iuh.edu.vn.navigationdrawertest1.model.DanhMuc;
import iuh.edu.vn.navigationdrawertest1.model.Truyen;

public class SearchActivity extends AppCompatActivity  implements SearchView.OnQueryTextListener{
    private ListView listViewSearch;
    private Truyen_List_Custom_Adapter truyen_list_custom_adapter;
    private ArrayList<Truyen> resultList=null;
    private ArrayList<Truyen> nullResult=null;

    private SearchView searchView;
    DatabaseReference storyRef;
    private DatabaseReference rootRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        nullResult=new ArrayList<>();
        listViewSearch= (ListView) findViewById(R.id.listSearchResult);
        rootRef = FirebaseDatabase.getInstance().getReference();
        storyRef=rootRef.child("allStory");
        resultList=new ArrayList<>();
        storyRef.addValueEventListener(new ValueEventListener() {
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
                    resultList.add(truyen);
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(SearchActivity.this, databaseError.toException().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        truyen_list_custom_adapter = new Truyen_List_Custom_Adapter(SearchActivity.this,R.layout.truyen_list_custom_adapter,nullResult);
        listViewSearch.setAdapter(truyen_list_custom_adapter);
        listViewSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Truyen tr = resultList.get(position);
                Intent intent = new Intent(SearchActivity.this, ChiTietTruyenActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("selectedTruyen",tr);
                bundle.putString("activityTruoc","DSSearch");
                intent.putExtra("dataTruyen",bundle);
                startActivity(intent);
            }
        });
        searchView = (SearchView)findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(this);
        searchView.onActionViewExpanded();
    }

    @Override
    public boolean onQueryTextSubmit(String s) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        if(s==null || s.trim().isEmpty()){
            resetSearch();
            return false;
        }
        ArrayList<Truyen> filterList = new ArrayList<Truyen>(resultList);
        for(Truyen tr : resultList){
            if(!tr.getTieuDe().trim().toLowerCase().contains(s.toLowerCase().trim())){
                filterList.remove(tr);
            }
        }
        truyen_list_custom_adapter = new Truyen_List_Custom_Adapter(SearchActivity.this,R.layout.truyen_list_custom_adapter,filterList);
        listViewSearch.setAdapter(truyen_list_custom_adapter);
        return true;
    }

    public  void  resetSearch(){
        truyen_list_custom_adapter = new Truyen_List_Custom_Adapter(SearchActivity.this,R.layout.truyen_list_custom_adapter,nullResult);
        listViewSearch.setAdapter(truyen_list_custom_adapter);
    }
}
