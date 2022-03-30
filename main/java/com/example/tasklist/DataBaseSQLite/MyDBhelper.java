
//package com.example.tasklist.DataBaseSQLite;
//
//import android.content.Context;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//
//import androidx.annotation.Nullable;
//
//import java.util.Calendar;
//import java.util.Date;
//
//public class MyDBhelper extends SQLiteOpenHelper {
//
//    // Database Information
//    static final String DB_NAME = "TaskListDatabase.db";
//    // crate the name of the Table -
//    public static final String TABLE_NAME = "TaskTable";
//    // database version
//    static final int DB_VERSION = 1;
//    //list of our required columns information
//    /* 1. */ public static final String TASK_ID = "id";
//    /* 2. */ public static final String TASKS_STATUS = "status";
//    /* 3. */ public static final String TASK = "description";
//    /* 4. */ public static final String date = "date";
//    /* 5. */ public static final String urgency = "urgency";
//
//    // Creating The table in run-time !!
//    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + TASK_ID
//            + " INTEGER PRIMARY KEY AUTOINCREMENT," + TASK + " TEXT, " + date + " TEXT, " + urgency + "INTEGER" + TASKS_STATUS + " INTEGER  )";
//
//    public MyDBhelper(@Nullable Context context) {
//        super(context, DB_NAME, null, DB_VERSION);
//    }
//
//
////    public void onCreate(SQLiteDatabase db) {
////        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, Title char(20), Description char(255), Date date(10), New_Date date(10), Urgency INTEGER,  IsDone INTEGER)");
////    }
//
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        db.execSQL(CREATE_TABLE);
//    }
//
//    @Override
//    // Creating The table in run-time !!
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        // if the old table exits drop him
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
//        // crate the table again ...
//        onCreate(db);
//    }
//}
package com.example.tasklist.DataBaseSQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.format.DateFormat;

import androidx.annotation.Nullable;

import java.util.Calendar;

public class MyDBhelper extends SQLiteOpenHelper {

    // Database Information
    static final String DB_NAME = "TaskListDatabase";
    // crate the name of the Table -
    public static final String TABLE_NAME = "TaskTable";
    // database version
    static final int DB_VERSION = 1;

    // crate the table col ->
    /* 1. */ public static final String TASK_ID = "id";
    /* 2. */ public static final String TASKS_STATUS = "status";
    /* 3. */ public static final String TASK = "description";
    /* 4. */ public static final String DATE = "date";
    /* 5. */ public static final String URGENCY = "urgency";


    // Creating The table in run-time !!
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + TASK_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, DATE date(10)" + TASK + " TEXT, " + TASKS_STATUS + " INTEGER  )";



        private static final String CREATE_TABLE2 = "create table " + TABLE_NAME + "(" + TASK_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT," + TASK + " TEXT, " + DATE + " TEXT, " + URGENCY + " INTEGER, " + TASKS_STATUS + " INTEGER  )";

    public MyDBhelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE2);

    }

    @Override
    // Creating The table in run-time !!
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // if the old table exits drop him
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        // crate the table again ...
        onCreate(db);
    }

}
