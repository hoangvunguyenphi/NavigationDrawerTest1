package iuh.edu.vn.navigationdrawertest1.sqlitedb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import iuh.edu.vn.navigationdrawertest1.model.Truyen;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "SQLite";
    private static final int DATABASE_VERSION=4;
    private static final String DATABASE_NAME="SQLITE_DB";
    private static final String COLUMN_ID="_id";
    private static final String COLUMN_TIEUDE="tieuDe";
    private static final String COLUMN_TACGIA="tacGia";
    private static final String COLUMN_NOIDUNG="noiDung";;
    private static final String COLUMN_NGAYTAO="ngayTao";
    private static final String COLUMN_DANHMUC="danhMuc";



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
                + COLUMN_NOIDUNG + " NVARCHAR(10000) NOT NULL ,"
                + COLUMN_NGAYTAO + " NVARCHAR(100) )";
        String script2="CREATE TABLE " + "BookmarkTB" +
                " (" + COLUMN_ID +" NVARCHAR(50) PRIMARY KEY NOT NULL , "
                + COLUMN_TIEUDE + " NVARCHAR(200) NOT NULL ,"
                + COLUMN_TACGIA + " NVARCHAR(100) NOT NULL ,"
                + COLUMN_DANHMUC + " NVARCHAR(100) NOT NULL ,"
                + COLUMN_NOIDUNG + " NVARCHAR(10000) NOT NULL ,"
                + COLUMN_NGAYTAO + " NVARCHAR(100) )";
        String script3 = "CREATE TABLE FirstTimeStory ( linkTruyen NVARCHAR(10000) PRIMARY KEY NOT NULL )";
        db.execSQL(script);
        db.execSQL(script2);
        db.execSQL(script3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + "HistoryTB");
        db.execSQL("DROP TABLE IF EXISTS " + "BookmarkTB");
        db.execSQL("DROP TABLE IF EXISTS " + "FirstTimeStory");
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
            values.put(COLUMN_NOIDUNG,truyen.getNoiDung());
            values.put(COLUMN_NGAYTAO,truyen.getNgayTao());
            return db.insert(table,null,values);
        }
        else{
            return -5;
        }
    }
    public  long updateStory(Truyen truyen,String table){
        long result=-5;
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put(COLUMN_ID,truyen.get_id());
        values.put(COLUMN_TIEUDE,truyen.getTieuDe());
        values.put(COLUMN_TACGIA,truyen.getTacGia());
        values.put(COLUMN_DANHMUC,truyen.getDanhMuc());
        values.put(COLUMN_NOIDUNG,truyen.getNoiDung());
        values.put(COLUMN_NGAYTAO,truyen.getNgayTao());
        result=db.update(table,values,COLUMN_ID+"=?",new String[]{truyen.get_id()});
        Log.d("AAAAA",result+"");
        return result;
    }

    public Truyen getStory(String id,String table){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.query(table, new String[]{COLUMN_ID,COLUMN_TIEUDE,COLUMN_DANHMUC,COLUMN_TACGIA,COLUMN_NOIDUNG,COLUMN_NGAYTAO},COLUMN_ID + "=?", new String[]{id},null,null,null,null);
        if(cursor.getCount()>0) {
            cursor.moveToFirst();
            Truyen tr = new Truyen(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));
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
                Truyen tr= new Truyen(cursor.getString(0),cursor.getString(1),cursor.getString(3),cursor.getString(2),cursor.getString(4),cursor.getString(5));
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
    public boolean firstTimeLamChuyenAy(String link){
        SQLiteDatabase writedb = this.getWritableDatabase();
        ArrayList<String> check = new ArrayList<String>();
        String sql = "SELECT * FROM FirstTimeStory WHERE linkTruyen is '"+link+"'";
        Cursor cursor = writedb.rawQuery(sql,null);

        if(cursor.moveToFirst()){
            do{
                String tr = cursor.getString(0);
                Log.d("LINKNE",tr);
                check.add(tr);
            }while (cursor.moveToNext());
        }
        if(check.isEmpty()){
            ContentValues contentValues = new ContentValues();
            contentValues.put("linkTruyen",link);
            writedb.insert("FirstTimeStory",null,contentValues);
            writedb.close();
            return true;
        }
        else{
            writedb.close();
            return false;
        }
    }
}
