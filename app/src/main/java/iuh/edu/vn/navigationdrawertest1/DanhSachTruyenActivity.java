package iuh.edu.vn.navigationdrawertest1;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import iuh.edu.vn.navigationdrawertest1.fragment.Truyen_List_Fragment;
import iuh.edu.vn.navigationdrawertest1.model.DanhMuc;

public class DanhSachTruyenActivity extends AppCompatActivity {
    Fragment fragment=null;
    FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_truyen);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Danh sách truyện");
        Intent intent=getIntent();
        Bundle bundle=intent.getBundleExtra("dataDanhMuc");
        DanhMuc danhMuc=(DanhMuc) bundle.getSerializable("selectedDanhMuc");
        Bundle bundle1=new Bundle();
        bundle1.putSerializable("selectedDanhMuc",danhMuc);
        Truyen_List_Fragment truyen_list_fragment=new Truyen_List_Fragment();
        truyen_list_fragment.setArguments(bundle1);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentListTruyen,truyen_list_fragment).commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list_truyen,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                startActivity(new Intent(this,MainActivity.class));
                return true;
            case R.id.action_reload:
                Intent intent=getIntent();
                Bundle bundle=intent.getBundleExtra("dataDanhMuc");
                DanhMuc danhMuc=(DanhMuc) bundle.getSerializable("selectedDanhMuc");
                Bundle bundle1=new Bundle();
                bundle1.putSerializable("selectedDanhMuc",danhMuc);
                Truyen_List_Fragment truyen_list_fragment=new Truyen_List_Fragment();
                truyen_list_fragment.setArguments(bundle1);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentListTruyen,truyen_list_fragment).commit();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
