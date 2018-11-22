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
    private static final int DATABASE_VERSION=6;
    private static final String DATABASE_NAME="SQLITE_DB";
    private static final String COLUMN_ID="_id";
    private static final String COLUMN_TIEUDE="tieuDe";
    private static final String COLUMN_TACGIA="tacGia";
    private static final String COLUMN_MOTA="moTa";
    private static final String COLUMN_NOIDUNG="noiDung";
    private static final String COLUMN_NGAYTAO="ngayTao";
    private static final String COLUMN_DANHMUC="danhMuc";
    private static final String COLUMN_TRANG="trangDaXem";
    private static final String COLUMN_TEXTCOLOR="textColor";
    private static final String COLUMN_BACKGROUNDCOLOR="backgroundColor";
    private static final String COLUMN_TEXTSIZE="textSize";

    public MyDatabaseHelper( Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String script="CREATE TABLE " + "HistoryTB" +
                " (" + COLUMN_ID +" NVARCHAR(50) PRIMARY KEY NOT NULL , "
                + COLUMN_TIEUDE + " NVARCHAR(200) NOT NULL ,"
                + COLUMN_TACGIA + " NVARCHAR(100) NOT NULL ,"
                + COLUMN_DANHMUC + " NVARCHAR(100) NOT NULL ,"
                + COLUMN_MOTA + " NVARCHAR(10000) NOT NULL ,"
                + COLUMN_NOIDUNG + " NVARCHAR(100) NOT NULL ,"
                + COLUMN_NGAYTAO + " NVARCHAR(100) NOT NULL ,"
                + COLUMN_TRANG + " INT )" ;
        String script2="CREATE TABLE " + "BookmarkTB" +
                " (" + COLUMN_ID +" NVARCHAR(50) PRIMARY KEY NOT NULL , "
                + COLUMN_TIEUDE + " NVARCHAR(200) NOT NULL ,"
                + COLUMN_TACGIA + " NVARCHAR(100) NOT NULL ,"
                + COLUMN_DANHMUC + " NVARCHAR(100) NOT NULL ,"
                + COLUMN_MOTA + " NVARCHAR(10000) NOT NULL ,"
                + COLUMN_NOIDUNG + " NVARCHAR(100) NOT NULL ,"
                + COLUMN_NGAYTAO + " NVARCHAR(100) NOT NULL ,"
                + COLUMN_TRANG + " INT )" ;
        String script3="CREATE TABLE " + "DownloadedTB" +
                " (" + COLUMN_ID +" NVARCHAR(50) PRIMARY KEY NOT NULL , "
                + COLUMN_TIEUDE + " NVARCHAR(200) NOT NULL ,"
                + COLUMN_TACGIA + " NVARCHAR(100) NOT NULL ,"
                + COLUMN_DANHMUC + " NVARCHAR(100) NOT NULL ,"
                + COLUMN_MOTA + " NVARCHAR(10000) NOT NULL ,"
                + COLUMN_NOIDUNG + " NVARCHAR(100) NOT NULL ,"
                + COLUMN_NGAYTAO + " NVARCHAR(100) NOT NULL ,"
                + COLUMN_TRANG + " INT )" ;
        String script4="CREATE TABLE " + "SettingTB" +
                " (" + COLUMN_ID +" NVARCHAR(50) PRIMARY KEY NOT NULL , "
                + COLUMN_TIEUDE + " NVARCHAR(200) NOT NULL ,"
                + COLUMN_NGAYTAO + " NVARCHAR(100) NOT NULL ,"
                + COLUMN_TRANG + " INT )" ;
        db.execSQL(script);
        db.execSQL(script2);
        db.execSQL(script3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + "HistoryTB");
        db.execSQL("DROP TABLE IF EXISTS " + "BookmarkTB");
        db.execSQL("DROP TABLE IF EXISTS " + "DownloadedTB");
        onCreate(db);
    }

    public long addStory(Truyen truyen ,String table) {
        SQLiteDatabase db=this.getWritableDatabase();
        Truyen tr = getStory(truyen.get_id(),table);
        if(tr==null){
            ContentValues values = new ContentValues();
            values.put(COLUMN_ID,truyen.get_id());
            values.put(COLUMN_TIEUDE,truyen.getTieuDe());
            values.put(COLUMN_TACGIA,truyen.getTacGia());
            values.put(COLUMN_DANHMUC,truyen.getDanhMuc());
            values.put(COLUMN_MOTA,truyen.getMoTa());
            values.put(COLUMN_NOIDUNG,truyen.getNoiDung());
            values.put(COLUMN_NGAYTAO,truyen.getNgayTao());
            values.put(COLUMN_TRANG,0);
            Log.d("EXXADD",truyen.getNoiDung());
            return db.insert(table,null,values);
        }
        else{
            return -5;
        }
    }
    public long updateStory(Truyen truyen,String table){
        long result=-5;
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put(COLUMN_ID,truyen.get_id());
        values.put(COLUMN_TIEUDE,truyen.getTieuDe());
        values.put(COLUMN_TACGIA,truyen.getTacGia());
        values.put(COLUMN_DANHMUC,truyen.getDanhMuc());
        values.put(COLUMN_MOTA,truyen.getMoTa());
        values.put(COLUMN_NOIDUNG,truyen.getNoiDung());
        values.put(COLUMN_NGAYTAO,truyen.getNgayTao());
        values.put(COLUMN_TRANG,truyen.getTrangDaXem());
        result=db.update(table,values,COLUMN_ID+"=?",new String[]{truyen.get_id()});
        Log.d("EXXUPDATE",truyen.getNoiDung());
        return result;
    }

    public Truyen getStory(String id,String table){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.query(table, new String[]{COLUMN_ID,COLUMN_TIEUDE,COLUMN_DANHMUC,COLUMN_TACGIA,COLUMN_MOTA,COLUMN_NOIDUNG,COLUMN_NGAYTAO,COLUMN_TRANG},COLUMN_ID + "=?", new String[]{id},null,null,null,null);
        if(cursor.getCount()>0) {
            cursor.moveToFirst();
            Truyen tr = new Truyen(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(5), cursor.getString(6));
            tr.setMoTa(cursor.getString(4));
            tr.setTrangDaXem(cursor.getInt(7));
            return tr;
        }
        else{
            return null;
        }
    }
    public List<Truyen> getAllStory(String table){
        List<Truyen> listTruyen = new ArrayList<>();
        String queryAll = "SELECT * FROM " + table ;
        SQLiteDatabase database=this.getWritableDatabase();
        Cursor cursor = database.rawQuery(queryAll,null);

        if(cursor.moveToFirst()){
            do{
                Truyen tr= new Truyen(cursor.getString(0),cursor.getString(1),cursor.getString(3),cursor.getString(2),cursor.getString(5),cursor.getString(6));
                tr.setMoTa(cursor.getString(4));
                tr.setTrangDaXem(cursor.getInt(7));
                listTruyen.add(tr);
            }while (cursor.moveToNext());
        }
        database.close();
        return listTruyen;
    }

    public void deleteStory (String id,String table){
        SQLiteDatabase database=this.getWritableDatabase();
        database.delete(table, COLUMN_ID + "=?", new String[] {id});
        database.close();
    }
}
