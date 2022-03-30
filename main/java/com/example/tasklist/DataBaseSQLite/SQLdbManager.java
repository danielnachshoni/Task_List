package com.example.tasklist.DataBaseSQLite;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.example.tasklist.Model.MyTaskListModel;

import java.util.ArrayList;
import java.util.List;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.format.DateFormat;

import androidx.annotation.Nullable;

import java.util.Calendar;

public class SQLdbManager {



    private MyDBhelper dbHelper;
    private final Context context;
    private String pathWay;
    private SQLiteDatabase database;

    public SQLdbManager(String pathWay, Context context) {
        this.context = context;
        this.pathWay = pathWay;
    }
    public SQLdbManager( Context context) {
        this.context = context;

    }

//    public com.example.tasklist.DataBaseSQLite.SQLdbManager open() {
//        dbHelper = new MyDBhelper(context);
//        database = SQLiteDatabase.openDatabase(pathWay, null, SQLiteDatabase.OPEN_READWRITE);
//        database = dbHelper.getWritableDatabase();
//        return this;
//    }
//    public void openDatabase() {
//        dbHelper = new MyDBhelper(context);
//        database = dbHelper.getWritableDatabase();
//
//    }
    public SQLdbManager openDatabase() {
        dbHelper = new MyDBhelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }
    public void closeDB() {
        dbHelper.close();
    }

    public void insertAnewTask(MyTaskListModel task) {
        ContentValues contentValue = new ContentValues();
//        contentValue.put(MyDBhelper.TASK_ID, count);
        contentValue.put(MyDBhelper.TASK, task.getStringTask());
        contentValue.put(MyDBhelper.TASKS_STATUS, 0);
        contentValue.put(MyDBhelper.DATE, task.getDate());
        contentValue.put(MyDBhelper.URGENCY, task.getImportance());
        database.insert(MyDBhelper.TABLE_NAME, null, contentValue);
    }

    @SuppressLint("Range")
    public List<MyTaskListModel> getAllTasks() {
        List<MyTaskListModel> toReturn = new ArrayList<>();
        Cursor cursor=null;
        database.beginTransaction();
        try {
             cursor = database.query(MyDBhelper.TABLE_NAME, null, null, null, null, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    // to get the first element use in Do while loop
                    do {
                        MyTaskListModel task = new MyTaskListModel();
                        task.setTaskID(cursor.getInt(cursor.getColumnIndex(MyDBhelper.TASK_ID)));
                        task.setStringTask(cursor.getString(cursor.getColumnIndex(MyDBhelper.TASK)));
                        task.setImportance(cursor.getInt(cursor.getColumnIndex(MyDBhelper.URGENCY)));
                        task.setTaskStatus(cursor.getInt(cursor.getColumnIndex(MyDBhelper.TASKS_STATUS)));
                        task.setDate(cursor.getString(cursor.getColumnIndex(MyDBhelper.DATE)));
                        toReturn.add(task);
                    } while (cursor.moveToNext());
                }

            }
        } catch (SQLiteException e) {
        System.err.println("some problem with the SQL");
    } finally {
        database.endTransaction();
        assert cursor != null;
        cursor.close();
    }

        return toReturn;
    }

    public void updateTaskStatus(int id, int taskStatus) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(MyDBhelper.TASKS_STATUS, taskStatus);
        database.update(MyDBhelper.TABLE_NAME, contentValues, MyDBhelper.TASK_ID + "=?", new String[]{String.valueOf(id)});
    }
    public void updateTaskUrgency(int id, int Importance) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MyDBhelper.URGENCY, Importance);
        database.update(MyDBhelper.TABLE_NAME, contentValues, MyDBhelper.TASK_ID + "=?", new String[]{String.valueOf(id)});
    }
    public void updateDate(int id, String Date) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MyDBhelper.DATE,Date);
        database.update(MyDBhelper.TABLE_NAME, contentValues, MyDBhelper.TASK_ID + "=?", new String[]{String.valueOf(id)});
    }
    public void updateTaskDesc(int id, String taskDesc) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MyDBhelper.TASK, taskDesc);
        database.update(MyDBhelper.TABLE_NAME, contentValues, MyDBhelper.TASK_ID + "=?", new String[]{String.valueOf(id)});
    }
    public void deleteTask(int id){
        database.delete(MyDBhelper.TABLE_NAME, MyDBhelper.TASK_ID + "=?", new String[]{String.valueOf(id)});
    }

//
//    public Cursor fetch() {
//        String[] columns = new String[]{dbHelper.TASK_ID, dbHelper.TASKS_STATUS, dbHelper.TASK};
//
//        Cursor cursor = database.query(dbHelper.TABLE_NAME, columns, null, null, null, null, null);
//        if (cursor != null) {
//            cursor.moveToFirst();
//        }
//        return cursor;
//    }
//
//    public int update(long _id, String name, String desc) {
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(dbHelper.TASKS_STATUS, name);
//        contentValues.put(dbHelper.TASK, desc);
//        int i = database.update(dbHelper.TABLE_NAME, contentValues, dbHelper.TASK_ID + " = " + _id, null);
//        return i;
//    }

//    String getAllTask() {
//
////        String allTaskk= "select count(*) as Total"
////                +"form dbHelper.TABLE_NAME"+ "where recID>?"+" and name=? " ;
//
//        String sql = "select * FROM dbHelper.TABLE_NAME";
//        Cursor allTask = database.rawQuery(sql, null);
//        allTask.moveToPosition(-1);
//        StringBuilder allTaskinRow = new StringBuilder(new String("All Task\n"));
//        while (allTask.moveToNext()) {
//            allTaskinRow.append(allTask.getString(0)).append("\t");
//            allTaskinRow.append(allTask.getString(1)).append("\t");
//            allTaskinRow.append(allTask.getString(2)).append("\t");
//        }
//        String aa = allTask.toString();
//        return aa;
//    }

//    String getAllTask2() {
//        String[] columns = new String[]{dbHelper.TASK_ID, dbHelper.TASKS_STATUS, dbHelper.TASK};
//        Cursor cursor = database.query(dbHelper.TABLE_NAME, columns, null, null, null, null, null);
//        cursor.moveToFirst();
//        StringBuilder allTaskinRow = new StringBuilder(new String("All Task\n"));
//
//        while (cursor.moveToNext()) {
//            allTaskinRow.append(cursor.getString(0)).append("\n");
//        }
//        String allTaskk = "select count(*) as Total"
//                + "form dbHelper.TABLE_NAME" + "where recID>?" + " and name=? ";
//
//        Cursor allTask = database.rawQuery(dbHelper.TABLE_NAME, null);
//        String aa = allTask.toString();
//        return aa;
//    }


    public void delete(long _id) {
        database.delete(dbHelper.TABLE_NAME, dbHelper.TASK_ID + "=" + _id, null);
    }


}


