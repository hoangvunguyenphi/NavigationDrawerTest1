package iuh.edu.vn.navigationdrawertest1.sqlitedb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import iuh.edu.vn.navigationdrawertest1.model.Truyen;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "SQLite";
    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="SQLITE_DB";
    private static final String TABLE_STORY="Story";
    private static final String COLUMN_ID="_id";
    private static final String COLUMN_TIEUDE="tieuDe";
    private static final String COLUMN_TACGIA="tacGia";
    private static final String COLUMN_NOIDUNG="noiDung";
    private static final String COLUMN_DANHDAU="danhDau";
    private static final String COLUMN_NGAYTAO="ngayTao";
    private static final String COLUMN_DANHMUC="danhMuc";



    public MyDatabaseHelper( Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String script="CREATE TABLE " + TABLE_STORY +
                " (" + COLUMN_ID +" NVARCHAR(50) PRIMARY KEY NOT NULL , "
                + COLUMN_TIEUDE + " NVARCHAR(200) NOT NULL ,"
                + COLUMN_TACGIA + " NVARCHAR(100) NOT NULL ,"
                + COLUMN_DANHMUC + " NVARCHAR(100) NOT NULL ,"
                + COLUMN_NOIDUNG + " NVARCHAR(10000) NOT NULL ,"
                + COLUMN_NGAYTAO + " NVARCHAR(100) ,"
                + COLUMN_DANHDAU + " NVARCHAR(100) )";
        db.execSQL(script);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void createDefaultDatabase(){

    }
    public long addStory(Truyen truyen) {
        long result=-5;
        SQLiteDatabase db=this.getWritableDatabase();
        if(getStory(truyen.get_id()) ==null){
            ContentValues values = new ContentValues();
            values.put(COLUMN_ID,truyen.get_id());
            values.put(COLUMN_TIEUDE,truyen.getTieuDe());
            values.put(COLUMN_TACGIA,truyen.getTacGia());
            values.put(COLUMN_DANHMUC,truyen.getDanhMuc());
            values.put(COLUMN_NOIDUNG,truyen.getNoiDung());
            values.put(COLUMN_NGAYTAO,truyen.getNgayTao());
            values.put(COLUMN_DANHDAU,truyen.getDanhDau());
            result=db.insert(TABLE_STORY,null,values);
        }
        db.close();
        return result;
    }

    public Truyen getStory(String id){
        Truyen tr=null;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.query(TABLE_STORY, new String[]{COLUMN_ID,COLUMN_TIEUDE,COLUMN_DANHMUC,COLUMN_TACGIA,COLUMN_NOIDUNG,COLUMN_DANHDAU,COLUMN_NGAYTAO},COLUMN_ID + "=?", new String[]{id},null,null,null,null);
        if (cursor!=null){
            cursor.moveToFirst();
            return tr;
        }
        else {
            Log.d("CUSSSSSS", cursor.getString(5));
            //tr = new Truyen("1", "1", "1", "1", "1", "1", "1");
            tr= new Truyen(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6));
            return tr;
        }
    }
    public List<Truyen> getAllStory(){
        List<Truyen> listTruyen = new ArrayList<>();
        String queryAll = "SELECT * FROM " + TABLE_STORY;
        SQLiteDatabase database=this.getWritableDatabase();
        Cursor cursor = database.rawQuery(queryAll,null);

        if(cursor.moveToFirst()){
            do{
                Truyen tr= new Truyen(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6));
                listTruyen.add(tr);
            }while (cursor.moveToNext());
        }
        database.close();
        return listTruyen;
    }
    public void deleteStory (String id){
        SQLiteDatabase database=this.getWritableDatabase();
        database.delete(TABLE_STORY, COLUMN_ID + "=?", new String[] {id});
        database.close();
    }

}
