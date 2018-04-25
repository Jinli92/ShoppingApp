package com.example.jinliyu.shoppingapp_1.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jinliyu on 4/16/18.
 */

public class MyDBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "my_db";
    public static final String TABLE_NAME = "cart_table";
    public static  final  String USER_MOBILE = "mobile";
    public static final String PRO_NAME = "productname";
    public static final String PRO_ID = "productid";
    public static final String QUANTITY= "quantity";
    public static final String PRO_PRICE = "price";
    public static final String DESCRIPTION = "description";
    public static final String IMAGE = "image";
    public static final int VERSION = 2;



    public MyDBHelper(Context context) {
        super(context, DB_NAME,null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_table = "CREATE TABLE " + TABLE_NAME + "("
                + "item_id INTEGER primary key autoincrement,"
                + USER_MOBILE + " TEXT,"
                + PRO_ID + " TEXT,"
                + PRO_NAME + " TEXT,"
                + QUANTITY + " TEXT,"
                + PRO_PRICE+ " TEXT,"
                + DESCRIPTION+ " TEXT,"
                + IMAGE+ " TEXT"
                + ")";

        db.execSQL(create_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);

        onCreate(db);
    }
}
