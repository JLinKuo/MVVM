package com.heaven.common.manager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by neusoft on 2016/4/28.
 */
public class DataBaseHelper extends SQLiteOpenHelper {
    private static String DB_NAME = "heaven";
    private static final int VERSION = 1;
    private String TABLE_NAME = "user";
    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                          int version) {
        super(context, name, factory, version);

    }
    public DataBaseHelper(Context context, String name, int version){
        this(context, name, null, version);
    }

    public DataBaseHelper(Context context){
        this(context, DB_NAME, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists " + TABLE_NAME + "(user_name varchar(20),password varchar(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void updateTable(String userName, String password) {
        String sql = "Update "+ TABLE_NAME + " set password = '"+password+"' where user_name = '"+ userName +"'";
        this.getWritableDatabase().execSQL(sql);
    }

    public void delete(String userName) {

    }
}
