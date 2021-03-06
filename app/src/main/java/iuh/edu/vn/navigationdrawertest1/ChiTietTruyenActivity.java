package iuh.edu.vn.navigationdrawertest1;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import iuh.edu.vn.navigationdrawertest1.fragment.ChiTietTruyen_Fragment;

public class ChiTietTruyenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_truyen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("selectedTruyen");
        ChiTietTruyen_Fragment cttr = new ChiTietTruyen_Fragment();
        cttr.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.frag_chitiet,cttr).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_truyen,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Fragment frag = getSupportFragmentManager().findFragmentById(R.id.frag_chitiet);
        TextView tv = frag.getActivity().findViewById(R.id.noiDung);
        SeekBar seekBar = frag.getActivity().findViewById(R.id.seekBarSize);
        switch (item.getItemId()){
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
        }
        return super.onOptionsItemSelected(item);
    }
}
