package iuh.edu.vn.navigationdrawertest1;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import iuh.edu.vn.navigationdrawertest1.custom_adapter.Truyen_List_Custom_Adapter;
import iuh.edu.vn.navigationdrawertest1.fragment.ChiTietTruyen_Fragment;
import iuh.edu.vn.navigationdrawertest1.model.DanhMuc;
import iuh.edu.vn.navigationdrawertest1.model.Truyen;
import iuh.edu.vn.navigationdrawertest1.sqlitedb.MyDatabaseHelper;

public class ChiTietTruyenActivity extends AppCompatActivity {
    private Truyen truyen;
    Menu mMenu;
    private static final String HISTORY_TABLE="HistoryTB";
    private static final String BOOKMARK_TABLE="BookmarkTB";

    private String activityTruoc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_truyen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        MyDatabaseHelper databaseHelper=new MyDatabaseHelper(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("dataTruyen");
        truyen = (Truyen) bundle.getSerializable("selectedTruyen");
        activityTruoc = bundle.getString("activityTruoc");
        actionBar.setTitle(truyen.getTieuDe());




        long d =databaseHelper.addStory(truyen,HISTORY_TABLE); // Thêm vào lịch sử
        Toast.makeText(this, d+"", Toast.LENGTH_SHORT).show();
        ChiTietTruyen_Fragment cttr = new ChiTietTruyen_Fragment();
        cttr.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.frag_chitiet,cttr).commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_truyen,menu);
        mMenu=menu;
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MyDatabaseHelper databaseHelper=new MyDatabaseHelper(this);
        MenuItem item = mMenu.findItem(R.id.addToBookMark);
        if(databaseHelper.getStory(truyen.get_id(),BOOKMARK_TABLE)!=null){
            item.setIcon(R.drawable.ic_added_bookmark);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Fragment frag = getSupportFragmentManager().findFragmentById(R.id.frag_chitiet);
        TextView tv = frag.getActivity().findViewById(R.id.noiDung);
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        //SQ LITE
        MyDatabaseHelper db=new MyDatabaseHelper(this);
        SeekBar seekBar = frag.getActivity().findViewById(R.id.seekBarSize);
        switch (item.getItemId()){
            case android.R.id.home: //nút back trở lại
                switch (activityTruoc){
                    case "DSTruyen":
                        final Intent intent1 = new Intent(this,DanhSachTruyenActivity.class);
                        final Bundle bundle1 = new Bundle();
                        final ArrayList<DanhMuc> arrDm= new ArrayList<>();
                        Query query = rootRef.child("allCategory").orderByChild("_id").equalTo(truyen.getDanhMuc());
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                for(DataSnapshot ds: dataSnapshot.getChildren()){
                                    DanhMuc danhMuc =new DanhMuc(ds.child("_id").getValue(String.class), ds.child("tenDanhMuc").getValue(String.class) );
                                    arrDm.add(danhMuc);
                                }
                                bundle1.putSerializable("selectedDanhMuc",arrDm.get(0));
                                intent1.putExtra("dataDanhMuc",bundle1);
                                startActivity(intent1);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                        return true;
                    case "DSHistory":
                        Intent i = new Intent(this,HistoryActivity.class);
                        startActivity(i);
                        return true;

                    case "DSBookmark":
                        Intent a = new Intent(this,BookmarkActivity.class);
                        startActivity(a);
                        return true;
                }
            case R.id.light:
                frag.getView().setBackgroundColor(Color.WHITE);
                tv.setTextColor(Color.BLACK);
                return true;
            case R.id.yellow:
                frag.getView().setBackgroundColor(Color.parseColor("#FFFFCC"));
                tv.setTextColor(Color.BLACK);
                return true;
            case R.id.dark:
                frag.getView().setBackgroundColor(Color.BLACK);
                tv.setTextColor(Color.WHITE);
                return true;
            case R.id.optionSize:
                seekBar.setVisibility(View.VISIBLE);
                return true;
            case R.id.addToBookMark:
                MenuItem item1 = mMenu.findItem(R.id.addToBookMark);
                if(db.getStory(truyen.get_id(),BOOKMARK_TABLE)!=null){
                    db.deleteStory(truyen.get_id(),BOOKMARK_TABLE);
                    item.setIcon(R.drawable.ic_menu_bookmark_null);
                    Toast.makeText(this, "Đã xoá khỏi bookmark!", Toast.LENGTH_SHORT).show();
                }
                else{
                    long d = db.addStory(truyen,BOOKMARK_TABLE);
                    if(d>0){
                        item.setIcon(R.drawable.ic_added_bookmark);
                        Toast.makeText(this, "Đã thêm vào bookmark!", Toast.LENGTH_SHORT).show();
                    }
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
