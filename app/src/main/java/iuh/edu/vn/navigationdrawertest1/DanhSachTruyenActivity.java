package iuh.edu.vn.navigationdrawertest1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import iuh.edu.vn.navigationdrawertest1.fragment.Truyen_List_Fragment;
import iuh.edu.vn.navigationdrawertest1.model.DanhMuc;

public class DanhSachTruyenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_truyen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent=getIntent();
        Bundle bundle=intent.getBundleExtra("dataDanhMuc");
        DanhMuc danhMuc= (DanhMuc) bundle.getSerializable("selectedDanhMuc");
        Bundle bundle1=new Bundle();
        bundle1.putSerializable("selectedDanhMuc",danhMuc);
        Truyen_List_Fragment truyen_list_fragment=new Truyen_List_Fragment();
        truyen_list_fragment.setArguments(bundle1);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentListTruyen,truyen_list_fragment).commit();
    }

}
