package iuh.edu.vn.navigationdrawertest1.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import iuh.edu.vn.navigationdrawertest1.R;
import iuh.edu.vn.navigationdrawertest1.model.Truyen;


public class ChiTietTruyen_Fragment extends Fragment {
    TextView noiDung;
    SeekBar seekBar;
    private StorageReference mStorageRef;
    private static final int REQUEST_ID_READ_PERMISSION = 100;
    private static final int REQUEST_ID_WRITE_PERMISSION = 200;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_chi_tiet_truyen, container, false);
        Bundle bundle = this.getArguments();
        final Truyen truyen= (Truyen)bundle.getSerializable("selectedTruyen");
        mStorageRef = FirebaseStorage.getInstance().getReference();
        noiDung = view.findViewById(R.id.noiDung);
        seekBar = view.findViewById(R.id.seekBarSize);
        seekBar.setVisibility(View.GONE);
        float maxSize = seekBar.getMax();
        noiDung.setTextSize(TypedValue.COMPLEX_UNIT_DIP,(maxSize/2));
        noiDung.setTextColor(Color.BLACK);
        noiDung.setMovementMethod(new ScrollingMovementMethod());
        askPermission(REQUEST_ID_READ_PERMISSION,Manifest.permission.READ_EXTERNAL_STORAGE);
        askPermission(REQUEST_ID_WRITE_PERMISSION,Manifest.permission.WRITE_EXTERNAL_STORAGE);
        File extStore = Environment.getExternalStorageDirectory();
        // ==> /storage/emulated/0/note.txt
        String path = extStore.getAbsolutePath() + "/truyen/" + "book20.txt";
        Log.i("ExternalStorageDemo", "Read file: " + path);

        String s = "";
        String fileContent = "";
        try {
            File myFile = new File(path);
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

        // đường dẫn đến storage firebase folder/file.txt
        //StorageReference riversRef = mStorageRef.child(truyen.getNoiDung());
        StorageReference riversRef = mStorageRef.child("truyen/test.txt");



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
                        noiDung.setText(stringBuffer);
                        String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/truyen";
                        File dir = new File(path);
                        if (!dir.exists()) {
                            dir.mkdirs();
                        }
                        File newFile = new File(path+"/"+truyen.get_id()+".txt");
                        Log.d("MYID:",truyen.get_id());
                        FileOutputStream fos = new FileOutputStream(newFile);
                        OutputStreamWriter myOutWriter = new OutputStreamWriter(fos);
                        myOutWriter.append("Chạy ngon rồi đó hehehe");
                        myOutWriter.close();
                        fos.close();
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                noiDung.setText("Tải truyện thất bại, vui lòng kiểm tra đường truyền!");
            }
        });
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




}
