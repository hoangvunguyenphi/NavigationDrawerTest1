package iuh.edu.vn.navigationdrawertest1.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import iuh.edu.vn.navigationdrawertest1.R;


public class ChiTietTruyen_Fragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chi_tiet_truyen, container, false);
        Bundle bundle = this.getArguments();
        TextView noiDung = view.findViewById(R.id.noiDung);
        noiDung.setTextColor(Color.BLACK);
        noiDung.setMovementMethod(new ScrollingMovementMethod());
        StringBuffer stringBuffer = new StringBuffer();
        String data = "";
        InputStream is = this.getResources().openRawResource(R.raw.test);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        if(is!=null){
            try {
                while ((data = reader.readLine())!=null){
                    stringBuffer.append("\t"+data + "\n");
                }
                noiDung.setText(stringBuffer);
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return view;

    }


}
