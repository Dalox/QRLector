package com.app.dalox.qrlector;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ASUS on 10/03/2018.
 */

public class AdminSQLiteHelper extends SQLiteOpenHelper {

    public AdminSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table obras(codigo integer primary key AUTOINCREMENT, nombre text, telefono text, email text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists obras");
        db.execSQL("create table obras(codigo integer primary key AUTOINCREMENT, nombre text, telefono text, email text)");
    }
}
