package iuh.edu.vn.navigationdrawertest1;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import iuh.edu.vn.navigationdrawertest1.fragment.History_List_Fragment;
import iuh.edu.vn.navigationdrawertest1.fragment.Truyen_List_Fragment;
import iuh.edu.vn.navigationdrawertest1.model.Truyen;
import iuh.edu.vn.navigationdrawertest1.sqlitedb.MyDatabaseHelper;

public class HistoryActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Lịch sử");
        History_List_Fragment history_list_fragment=new History_List_Fragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentListHistory,history_list_fragment).commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_history,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_clear:
                MyDatabaseHelper databaseHelper=new MyDatabaseHelper(this);
                ArrayList<Truyen> listr = new ArrayList<>();
                List<Truyen> list= databaseHelper.getAllStory();
                listr.addAll(list);
                if(listr.size()>0) {
                    for (Truyen tr : listr) {
                        databaseHelper.deleteStory(tr.get_id());
                    }
                    Toast.makeText(this, "Đã xoá !", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(this, "Không có gì để xoá !", Toast.LENGTH_SHORT).show();
                }
                History_List_Fragment history_list_fragment=new History_List_Fragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentListHistory,history_list_fragment).commit();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}


