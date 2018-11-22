package iuh.edu.vn.navigationdrawertest1.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

import iuh.edu.vn.navigationdrawertest1.ChiTietTruyenActivity;
import iuh.edu.vn.navigationdrawertest1.R;
import iuh.edu.vn.navigationdrawertest1.model.Truyen;
import iuh.edu.vn.navigationdrawertest1.sqlitedb.MyDatabaseHelper;


public class ChiTietTruyen_Fragment extends Fragment {
    TextView noiDung;
    SeekBar seekBar;
    View view;
    ScrollView scrollView;
    private Truyen truyen;
    private String activityTruoc="";
    private StorageReference mStorageRef;
    private static final int REQUEST_ID_READ_PERMISSION = 100;
    private static final int REQUEST_ID_WRITE_PERMISSION = 200;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_chi_tiet_truyen, container, false);
        Bundle bundle = this.getArguments();
        final MyDatabaseHelper databaseHelper=new MyDatabaseHelper(getContext());
        truyen= (Truyen)bundle.getSerializable("selectedTruyen");
        activityTruoc=bundle.getString("activityTruoc");
        mStorageRef = FirebaseStorage.getInstance().getReference();
        //Thannh chỉnh size chữ
        noiDung = view.findViewById(R.id.noiDung);
        scrollView=view.findViewById(R.id.scrlView);
        seekBar = view.findViewById(R.id.seekBarSize);
        seekBar.setVisibility(View.GONE);
        final List<String> sett= databaseHelper.getSetting("1111");
        noiDung.setTextSize(TypedValue.COMPLEX_UNIT_DIP,Integer.parseInt(sett.get(3)));

        seekBar.setProgress(Integer.parseInt(sett.get(3))-10);
        noiDung.setTextColor(Color.parseColor(sett.get(1)));
        view.setBackgroundColor(Color.parseColor(sett.get(2)));

        if(bundle.getString("activityTruoc").equalsIgnoreCase("DSDownloaded")){
            Log.d("EXXXXXX1",truyen.getNoiDung());
            askPermission(REQUEST_ID_READ_PERMISSION,Manifest.permission.READ_EXTERNAL_STORAGE);
            String s = "";
            String fileContent = "";
            try {
                File myFile = new File(truyen.getNoiDung());
                FileInputStream fIn = new FileInputStream(myFile);
                BufferedReader myReader = new BufferedReader(
                        new InputStreamReader(fIn));

                while ((s = myReader.readLine()) != null) {
                    fileContent += s +"\n";
                }
                myReader.close();

                noiDung.setText(fileContent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            Log.d("EXXXXXX",truyen.getNoiDung());
            StorageReference riversRef = mStorageRef.child(truyen.getNoiDung());
            final long ONE_MEGABYTE = 1024 * 1024 ;
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
                            noiDung.setText(stringBuffer);
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Log.d("EXXXXXX",exception.toString());
                    noiDung.setText("Tải truyện thất bại, vui lòng kiểm tra đường truyền!");
                }
            });
        }
        //Xử lý event thanh chinh size
        noiDung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seekBar.setVisibility(View.GONE);
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                noiDung.setTextSize(TypedValue.COMPLEX_UNIT_DIP,progress+10);
                databaseHelper.updateTextSize("1111",progress+10);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        return view;

    }
    public int getTrangDaXem(Truyen truyen1){
        MyDatabaseHelper helper = new MyDatabaseHelper(getContext());
        if(activityTruoc.equalsIgnoreCase("DSBookmark")){
            return helper.getStory(truyen1.get_id(), "BookmarkTB").getTrangDaXem();
        }
        else{
            if(activityTruoc.equalsIgnoreCase("DSDownloaded")){
                return helper.getStory(truyen1.get_id(), "DownloadedTB").getTrangDaXem();
            }
            else{
                if(activityTruoc.equalsIgnoreCase("DSTruyen") || activityTruoc.equalsIgnoreCase("DSHistory") || activityTruoc.equalsIgnoreCase("DSSearch")){
                    return helper.getStory(truyen1.get_id(), "HistoryTB").getTrangDaXem();
                }
            }
            return 0;
        }
    }
    private boolean askPermission(int requestId, String permissionName) {
        if (android.os.Build.VERSION.SDK_INT >= 23) {

            // Check if we have permission
            int permission = ActivityCompat.checkSelfPermission(getActivity(), permissionName);

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
    @Override
    public void onResume() {
        super.onResume();
        final int trang = getTrangDaXem(truyen);
        scrollView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                scrollView.smoothScrollTo(0,trang);
                Handler h = new Handler();
                final ViewTreeObserver.OnGlobalLayoutListener victim = this;
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.getViewTreeObserver().removeOnGlobalLayoutListener(victim);
                    }
                }, 1500);
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        int a=0;
        a=scrollView.getScrollY();
        MyDatabaseHelper databaseHelper=new MyDatabaseHelper(getContext());
        truyen.setTrangDaXem(a);
        if(activityTruoc.equalsIgnoreCase("DSBookmark")){
            databaseHelper.updateStory(truyen,"BookmarkTB");
        }
        else{
            if(activityTruoc.equalsIgnoreCase("DSDownloaded")){
                databaseHelper.updateStory(truyen,"DownloadedTB");
            }
            else{
                if(activityTruoc.equalsIgnoreCase("DSTruyen") || activityTruoc.equalsIgnoreCase("DSHistory") || activityTruoc.equalsIgnoreCase("DSSearch")){
                    databaseHelper.updateStory(truyen,"HistoryTB");
                }
            }

        }
    }


}
