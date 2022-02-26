package com.example.myway.Utils;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myway.Model.ToDoModel;


import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int VERSION = 16;
    private static final String NAME = "toDoListDatabase";

    private static final String TODO_TABLE = "todo";
    private static final String TODO_TABLE_EAT = "todo_eat";
    private static final String TODO_TABLE_NO = "todo_no";

    private static final String ID = "id";

    private static final String ID_EAT = "id_eat";
    private static final String ID_EAT_RAN = "id_eat_ran";

    private static final String ID_NO = "id_no";
    private static final String ID_NO_RAN = "id_no_ran";

    private static final String TASK = "task";
    private static final String EAT = "eat";
    private static final String NO = "noo";

    private static final String STATUS = "status";
    private static final String STATUS_EAT = "status_eat";
    private static final String STATUS_NO = "status_no";

    private static final String DATE = "date";
    private static final String DATE_EAT = "date_eat";
    private static final String DATE_NO = "date_no";

    private static final String CREATE_TODO_TABLE = "CREATE TABLE IF NOT EXISTS " + TODO_TABLE + "(" +
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TASK + " TEXT, " + DATE + " TEXT, "
            + STATUS + " INTEGER)";

    private static final String CREATE_TODO_TABLE_EAT = "CREATE TABLE IF NOT EXISTS " + TODO_TABLE_EAT +
            "(" + ID_EAT + " INTEGER PRIMARY KEY AUTOINCREMENT, " + EAT + " TEXT, " + DATE_EAT + " TEXT, " + ID_EAT_RAN + " INTEGER, "
            + STATUS_EAT + " INTEGER)";

    private static final String CREATE_TODO_TABLE_No = "CREATE TABLE IF NOT EXISTS " + TODO_TABLE_NO +
            "(" + ID_NO + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NO + " TEXT, " + DATE_NO + " TEXT, " + ID_NO_RAN + " INTEGER, "
            + STATUS_NO + " INTEGER)";



    private SQLiteDatabase db;

    //    Создание базы
    public DatabaseHandler(Context context) {
        super(context, NAME, null, VERSION);
    }

    //    Создание Таблицы
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TODO_TABLE);
        db.execSQL(CREATE_TODO_TABLE_EAT);
        db.execSQL(CREATE_TODO_TABLE_No);
    }

    //    Удаление таблицы
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TODO_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TODO_TABLE_EAT);
        db.execSQL("DROP TABLE IF EXISTS " + TODO_TABLE_NO);
        onCreate(db);
    }

    //    Запуск для записи
    public void openDatabase() {
        db = this.getWritableDatabase();
    }

    //    Запись
    public void insertTask(ToDoModel task) {
        ContentValues cv = new ContentValues();
        cv.put(TASK, task.getTask());
        cv.put(DATE, task.getDate());
        cv.put(STATUS, 0);
        db.insert(TODO_TABLE, null, cv);
    }

    //    Запись
    public void insertEat(ToDoModel task) {
        ContentValues cv = new ContentValues();
        cv.put(EAT, task.getEat());
        cv.put(DATE_EAT, task.getDate());
        cv.put(ID_EAT_RAN, task.getId_eat());
        cv.put(STATUS_EAT, 0);
        db.insert(TODO_TABLE_EAT, null, cv);
    }

    public void insertNo(ToDoModel task) {
        ContentValues cv = new ContentValues();
        cv.put(NO, task.getNo());
        cv.put(DATE_NO, task.getDate());
        cv.put(ID_NO_RAN, task.getId_no());
        cv.put(STATUS_NO, 0);
        db.insert(TODO_TABLE_NO, null, cv);
    }

    @SuppressLint("Range")
    public List<ToDoModel> getAllTasks() {
        List<ToDoModel> taskList = new ArrayList<>();
        Cursor cur = null;
        db.beginTransaction();
        try {
            cur = db.query(TODO_TABLE, null, null, null, null, null, null, null);
            if (cur != null) {
                if (cur.moveToFirst()) {
                    do {
                        ToDoModel task = new ToDoModel();
                        task.setId(cur.getInt(cur.getColumnIndex(ID)));
                        task.setTask(cur.getString(cur.getColumnIndex(TASK)));
                        task.setStatus(cur.getInt(cur.getColumnIndex(STATUS)));
                        task.setDate(cur.getString(cur.getColumnIndex(DATE)));
                        taskList.add(task);
                    }
                    while (cur.moveToNext());
                }
            }
        } finally {
            db.endTransaction();
            assert cur != null;
            cur.close();
        }
        return taskList;
    }

    @SuppressLint("Range")
    public List<ToDoModel> getAllEat() {
        List<ToDoModel> taskList = new ArrayList<>();
        Cursor cur = null;
        db.beginTransaction();
        try {
//          Считывания с таблицы
            cur = db.query(TODO_TABLE_EAT, null, null, null, null, null, null, null);
            if (cur != null) {
                if (cur.moveToFirst()) {
                    do {
                        ToDoModel task = new ToDoModel();
                        task.setId_eat_table(cur.getInt(cur.getColumnIndex(ID_EAT)));
                        task.setEat(cur.getString(cur.getColumnIndex(EAT)));
                        task.setStatus_eat(cur.getInt(cur.getColumnIndex(STATUS_EAT)));
                        task.setDate(cur.getString(cur.getColumnIndex(DATE_EAT)));
                        task.setId_eat(cur.getInt(cur.getColumnIndex(ID_EAT_RAN)));
                        taskList.add(task);
                    }
                    while (cur.moveToNext());
                }
            }
        } finally {
            db.endTransaction();
            assert cur != null;
            cur.close();
        }
        return taskList;
    }

    @SuppressLint("Range")
    public List<ToDoModel> getAllNo() {
        List<ToDoModel> taskList = new ArrayList<>();
        Cursor cur = null;
        db.beginTransaction();
        try {
            cur = db.query(TODO_TABLE_NO, null, null, null, null, null, null, null);
            if (cur != null) {
                if (cur.moveToFirst()) {
                    do {
                        ToDoModel task = new ToDoModel();
                        task.setId_no_table(cur.getInt(cur.getColumnIndex(ID_NO)));
                        task.setNo(cur.getString(cur.getColumnIndex(NO)));
                        task.setStatus_no(cur.getInt(cur.getColumnIndex(STATUS_NO)));
                        task.setDate(cur.getString(cur.getColumnIndex(DATE_NO)));
                        task.setId_no(cur.getInt(cur.getColumnIndex(ID_NO_RAN)));
                        taskList.add(task);
                    }
                    while (cur.moveToNext());
                }
            }
        } finally {
            db.endTransaction();
            assert cur != null;
            cur.close();
        }
        return taskList;
    }

    public void updateStatus(int id, int status) {
        ContentValues cv = new ContentValues();
        cv.put(STATUS, status);
        db.update(TODO_TABLE, cv, ID + "= ?", new String[]{String.valueOf(id)});
    }

    public void updateStatusEat(int id, int status) {
        ContentValues cv = new ContentValues();
        cv.put(STATUS_EAT, status);
        db.update(TODO_TABLE_EAT, cv, ID_EAT + "= ?", new String[]{String.valueOf(id)});
    }

    public void updateStatusNo(int id, int status) {
        ContentValues cv = new ContentValues();
        cv.put(STATUS_NO, status);
        db.update(TODO_TABLE_NO, cv, ID_NO + "= ?", new String[]{String.valueOf(id)});
    }

    public void updateTask(int id, String task) {
        ContentValues cv = new ContentValues();
        cv.put(TASK, task);
        db.update(TODO_TABLE, cv, ID + "= ?", new String[]{String.valueOf(id)});
    }

    public void updateTaskEat(int id, String task) {
        ContentValues cv = new ContentValues();
        cv.put(EAT, task);
        db.update(TODO_TABLE_EAT, cv, ID_EAT + "= ?", new String[]{String.valueOf(id)});
    }

    public void updateTaskNo(int id, String task) {
        ContentValues cv = new ContentValues();
        cv.put(NO, task);
        db.update(TODO_TABLE_NO, cv, ID_NO + "= ?", new String[]{String.valueOf(id)});
    }

    public void deleteTask(int id) {
        db.delete(TODO_TABLE, ID + "= ?", new String[]{String.valueOf(id)});
    }

    public void deleteTaskEat(int id) {
        db.delete(TODO_TABLE_EAT, ID_EAT + "= ?", new String[]{String.valueOf(id)});
    }

    public void deleteTaskENo(int id) {
        db.delete(TODO_TABLE_NO, ID_NO + "= ?", new String[]{String.valueOf(id)});
    }
}
