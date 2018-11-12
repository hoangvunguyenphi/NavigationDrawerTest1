package iuh.edu.vn.navigationdrawertest1;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import iuh.edu.vn.navigationdrawertest1.fragment.Bookmark_List_Fragment;
import iuh.edu.vn.navigationdrawertest1.fragment.Downloaded_List_Fragment;
import iuh.edu.vn.navigationdrawertest1.fragment.History_List_Fragment;
import iuh.edu.vn.navigationdrawertest1.model.Truyen;
import iuh.edu.vn.navigationdrawertest1.sqlitedb.MyDatabaseHelper;

public class DownloadActivity extends AppCompatActivity {
    private static final String DOWNLOADED_TABLE = "DownloadedTB";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Tải xuống");
        Downloaded_List_Fragment downloaded_list_fragment=new Downloaded_List_Fragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentListDownloaded,downloaded_list_fragment).commit();

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
                List<Truyen> list= databaseHelper.getAllStory(DOWNLOADED_TABLE);
                listr.addAll(list);
                if(listr.size()>0) {
                    for (Truyen tr : listr) {

                        databaseHelper.deleteStory(tr.get_id(),DOWNLOADED_TABLE);

                    }
                    Toast.makeText(this, "Đã xoá !", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(this, "Không có gì để xoá !", Toast.LENGTH_SHORT).show();
                }
                Downloaded_List_Fragment downloaded_list_fragment=new Downloaded_List_Fragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentListDownloaded,downloaded_list_fragment).commit();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
