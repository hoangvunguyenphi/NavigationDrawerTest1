package iuh.edu.vn.navigationdrawertest1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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
    private static final String DOWNLOADED_TABLE = "DownloadedTB";
    private static final int REQUEST_ID_READ_PERMISSION = 100;
    private static final int REQUEST_ID_WRITE_PERMISSION = 200;
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

        if(activityTruoc.equalsIgnoreCase("DSTruyen") || activityTruoc.equalsIgnoreCase("DSSearch")  ){
            databaseHelper.addStory(truyen,HISTORY_TABLE); // Thêm vào lịch sử
        }
        ChiTietTruyen_Fragment cttr = new ChiTietTruyen_Fragment();
        cttr.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.frag_chitiet,cttr).commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(activityTruoc.equalsIgnoreCase("DSDownloaded")){
            getMenuInflater().inflate(R.menu.menu_download,menu);
            mMenu=menu;
            return true;
        }
        else{
            getMenuInflater().inflate(R.menu.menu_truyen,menu);
            mMenu=menu;
            return true;
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MyDatabaseHelper databaseHelper=new MyDatabaseHelper(this);
        if(!activityTruoc.equalsIgnoreCase("DSDownloaded")){
            MenuItem item = mMenu.findItem(R.id.addToBookMark);
            if(databaseHelper.getStory(truyen.get_id(),BOOKMARK_TABLE)!=null){
                item.setIcon(R.drawable.ic_added_bookmark);
            }
        }
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Fragment frag = getSupportFragmentManager().findFragmentById(R.id.frag_chitiet);
        final TextView tv = frag.getActivity().findViewById(R.id.noiDung);
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
        //SQ LITE
        final MyDatabaseHelper db=new MyDatabaseHelper(this);
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
                        startActivity(new Intent(this,HistoryActivity.class));
                        return true;

                    case "DSBookmark":
                        startActivity(new Intent(this,BookmarkActivity.class));
                        return true;
                    case "DSDownloaded":
                        startActivity(new Intent(this,DownloadActivity.class));
                        return true;

                    case "DSSearch":
                        startActivity(new Intent(this,SearchActivity.class));
                        return true;
                }
            case R.id.light:
                frag.getView().setBackgroundColor(Color.WHITE);
                tv.setTextColor(Color.BLACK);
                return true;
            case R.id.yellow:
                frag.getView().setBackgroundColor(Color.parseColor("#dfdcc3"));
                tv.setTextColor(Color.parseColor("#121212"));
                return true;
            case R.id.dark:
                frag.getView().setBackgroundColor(Color.BLACK);
                tv.setTextColor(Color.parseColor("#818181"));
                return true;
            case R.id.optionSize:
                seekBar.setVisibility(View.VISIBLE);
                return true;
            case R.id.action_deleteDownloaded:
                String path = truyen.getNoiDung();
                File file = new File(path);
                file.delete();
                MyDatabaseHelper databaseHelper=new MyDatabaseHelper(this);
                ArrayList<Truyen> listr = new ArrayList<>();
                databaseHelper.deleteStory(truyen.get_id(),DOWNLOADED_TABLE);
                Toast.makeText(ChiTietTruyenActivity.this, "Đã xoá", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ChiTietTruyenActivity.this,DownloadActivity.class));
                return true;
            case R.id.action_download:
                StorageReference riversRef = mStorageRef.child(truyen.getNoiDung());
                final long ONE_MEGABYTE = 1024 * 1024;
                riversRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
                        BufferedReader reader = new BufferedReader(new InputStreamReader(bis));
                        StringBuffer stringBuffer = new StringBuffer();
                        String data = "";
                        if(bis!=null) {
                            try {
                                while ((data = reader.readLine()) != null) {
                                    stringBuffer.append("\t" + data + "\n");
                                }
                                askPermission(REQUEST_ID_WRITE_PERMISSION,Manifest.permission.WRITE_EXTERNAL_STORAGE);
                                String linkTruyen = Environment.getExternalStorageDirectory().getAbsolutePath() + "/truyen/" + truyen.get_id()+".txt";
                                Truyen tr=new Truyen(truyen.get_id(),truyen.getTieuDe(),truyen.getDanhMuc(),truyen.getTacGia(),truyen.getNoiDung(),truyen.getNgayTao());
                                tr.setMoTa(truyen.getMoTa());
                                tr.setNoiDung(linkTruyen);
                                Log.d("EXXX",tr.getNoiDung());
                                long check = db.addStory(tr,"DownloadedTB");

                                String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/truyen";
                                File dir = new File(path);
                                if (!dir.exists()) {
                                    dir.mkdirs();
                                }
                                File newFile = new File(path+"/"+truyen.get_id()+".txt");
                                FileOutputStream fos = new FileOutputStream(newFile);
                                OutputStreamWriter myOutWriter = new OutputStreamWriter(fos);
                                myOutWriter.append(stringBuffer);
                                myOutWriter.close();
                                fos.close();
                                bis.close();
                                if(check<0){
                                    Toast.makeText(ChiTietTruyenActivity.this, "Đã tồn tại, muốn tải lại hãy xoá trong danh sách tải xuống!", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(ChiTietTruyenActivity.this, "Đã tải xuống !", Toast.LENGTH_SHORT).show();
                                }

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        tv.setText("Tải truyện thất bại, vui lòng kiểm tra đường truyền!");
                    }
                });
                return false;
            case R.id.addToBookMark:
                MenuItem item1 = mMenu.findItem(R.id.addToBookMark);
                if(db.getStory(truyen.get_id(),BOOKMARK_TABLE)!=null){
                    db.deleteStory(truyen.get_id(),BOOKMARK_TABLE);
                    item1.setIcon(R.drawable.ic_menu_bookmark_null);
                    Toast.makeText(ChiTietTruyenActivity.this, "Đã xoá khỏi bookmark!", Toast.LENGTH_SHORT).show();
                    return false;
                }
                else{
                    long d = db.addStory(truyen,BOOKMARK_TABLE);
                    if(d>0){
                        item1.setIcon(R.drawable.ic_added_bookmark);
                        Toast.makeText(ChiTietTruyenActivity.this, "Đã thêm vào bookmark!", Toast.LENGTH_SHORT).show();
                    }
                    return false;
                }

        }
        return super.onOptionsItemSelected(item);
    }
    private boolean askPermission(int requestId, String permissionName) {
        if (android.os.Build.VERSION.SDK_INT >= 23) {

            // Check if we have permission
            int permission = ActivityCompat.checkSelfPermission(this, permissionName);


            if (permission != PackageManager.PERMISSION_GRANTED) {
                // If don't have permission so prompt the user.
                this.requestPermissions(
                        new String[]{permissionName},
                        requestId
                );
                return false;
            }
        }
        return true;
    }
}
