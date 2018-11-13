package iuh.edu.vn.navigationdrawertest1;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import iuh.edu.vn.navigationdrawertest1.fragment.Bookmark_List_Fragment;
import iuh.edu.vn.navigationdrawertest1.fragment.Downloaded_List_Fragment;
import iuh.edu.vn.navigationdrawertest1.fragment.History_List_Fragment;
import iuh.edu.vn.navigationdrawertest1.model.Truyen;
import iuh.edu.vn.navigationdrawertest1.sqlitedb.MyDatabaseHelper;

public class DownloadActivity extends AppCompatActivity {
    private static final String DOWNLOADED_TABLE = "DownloadedTB";
    private static final int REQUEST_ID_WRITE_PERMISSION = 200;
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
                askPermission(REQUEST_ID_WRITE_PERMISSION, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/truyen";
                File dir = new File(path);
                if (dir.isDirectory())
                {
                    String[] children = dir.list();
                    for (int i = 0; i < children.length; i++)
                    {
                        new File(dir, children[i]).delete();
                    }
                }
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
