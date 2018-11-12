package iuh.edu.vn.navigationdrawertest1.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import iuh.edu.vn.navigationdrawertest1.R;
import iuh.edu.vn.navigationdrawertest1.model.Truyen;


public class ChiTietTruyen_Fragment extends Fragment {
    TextView noiDung;
    SeekBar seekBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_chi_tiet_truyen, container, false);
        Bundle bundle = this.getArguments();
        Truyen truyen= (Truyen)bundle.getSerializable("selectedTruyen");

        noiDung = view.findViewById(R.id.noiDung);
        seekBar = view.findViewById(R.id.seekBarSize);
        seekBar.setVisibility(View.GONE);
        float maxSize = seekBar.getMax();
        noiDung.setTextSize(TypedValue.COMPLEX_UNIT_DIP,(maxSize/2));
        noiDung.setTextColor(Color.BLACK);
        noiDung.setMovementMethod(new ScrollingMovementMethod());
        noiDung.setText(truyen.getNoiDung());
//        StringBuffer stringBuffer = new StringBuffer();
//        String data = "";
//        InputStream is = this.getResources().openRawResource(R.raw.test);
//        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
//        if(is!=null){
//            try {
//                while ((data = reader.readLine())!=null){
//                    stringBuffer.append("\t"+data + "\n");
//                }
//                noiDung.setText(stringBuffer);
//                is.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
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


}
