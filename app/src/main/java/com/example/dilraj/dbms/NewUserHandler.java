package com.example.dilraj.dbms;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteAbortException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

public class NewUserHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 10;
    private static final String DATABASE_NAME = "libDB.db";
    private static final String COLUMN_ID = "_id";

    private static final String TABLE_USERS = "users";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_BRANCH = "branch";
    private static final String COLUMN_ROLL = "roll";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_PSSWD= "passwd";

    private static final String TABLE_ADMINS = "admins";
    private static final String COLUMN_ADMIN_NAME = "admin_name";
    private static final String COLUMN_ADMIN_DESIGNATION = "admin_desig";
    private static final String COLUMN_ADMIN_PHONE = "admin_phone";
    private static final String COLUMN_ADMIN_UID = "admin_uid";
    private static final String COLUMN_ADMIN_PSSWD = "admin_psswd";

    private static final String TABLE_BOOKS = "books";
    private static final String COLUMN_BOOK_ID = "book_id";
    private static final String COLUMN_BOOK_NAME = "book_name";
    private static final String COLUMN_BOOK_AUTHORNAME = "book_authorname";

    private static final String TABLE_RENT = "rents";
    private static final String COLUMN_USERID = "id_rent";
    private static final String COLUMN_BOOKID = "book_id_rent";
    private static final String COLUMN_DATE = "date";

    private static final String TABLE_IDS = "id_table";
    private static final String COLUMN_ID_IN = "id";

    @Override
    public void onCreate(SQLiteDatabase db) {
        String q = "CREATE TABLE IF NOT EXISTS " + TABLE_USERS + "(" +
                COLUMN_ID + " INTEGER AUTO INCREMENT, " +
                COLUMN_NAME + " VARCHAR(200), " +
                COLUMN_BRANCH + " VARCHAR(20), " +
                COLUMN_ROLL + " VARCHAR(20) PRIMARY KEY, " +
                COLUMN_PHONE + " INTEGER, " +
                COLUMN_ADDRESS + " VARCHAR(200), " +
                COLUMN_PSSWD + " VARCHAR(20)" +
                ");";
        db.execSQL(q);
        q = "CREATE TABLE IF NOT EXISTS " + TABLE_ADMINS + "(" +
                COLUMN_ID + " INTEGER AUTO INCREMENT, " +
                COLUMN_ADMIN_NAME + " VARCHAR(200), " +
                COLUMN_ADMIN_DESIGNATION + " VARCHAR(20), " +
                COLUMN_ADMIN_PHONE + " INTEGER, " +
                COLUMN_ADMIN_UID + " VARCHAR(20) PRIMARY KEY, " +
                COLUMN_ADMIN_PSSWD + " VARCHAR(200)" +
                ");";
        db.execSQL(q);
        q = "CREATE TABLE IF NOT EXISTS " + TABLE_BOOKS + "(" +
                COLUMN_ID + " INTEGER AUTO INCREMENT, " +
                COLUMN_BOOK_ID + " VARCHAR(200) PRIMARY KEY, " +
                COLUMN_BOOK_NAME + " VARCHAR(200), " +
                COLUMN_BOOK_AUTHORNAME + " VARCHAR(200)" +
                ");";
        db.execSQL(q);
        q = "CREATE TABLE IF NOT EXISTS " + TABLE_RENT + "(" +
                COLUMN_ID + " INTEGER AUTO INCREMENT, " +
                COLUMN_USERID + " VARCHAR(200), " +
                COLUMN_BOOKID + " VARCHAR(200), " +
                COLUMN_DATE + " VARCHAR(200), " +
                "FOREIGN KEY (" +
                COLUMN_USERID + ") REFERENCES " +
                TABLE_USERS + "(" +
                COLUMN_ROLL + "), " +
                "FOREIGN KEY (" +
                COLUMN_BOOKID + ") REFERENCES " +
                TABLE_USERS + "(" +
                COLUMN_BOOK_ID + ")" +
                ");";
        db.execSQL(q);
        q = "CREATE TABLE IF NOT EXISTS " + TABLE_IDS + "(" +
                COLUMN_ID + " INTEGER AUTO INCREMENT, " +
                COLUMN_ID_IN + " VARCHAR(200) PRIMARY KEY" +
                ");";

    }

    public NewUserHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADMINS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKS);
        onCreate(db);
    }

    public void addUserDB(USers u) throws SQLiteConstraintException, SQLiteAbortException, SQLIntegrityConstraintViolationException{
        try{
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME, u.getName());
            values.put(COLUMN_BRANCH, u.getBranch());
            values.put(COLUMN_ROLL, u.getRoll());
            values.put(COLUMN_PHONE, u.getPhone());
            values.put(COLUMN_ADDRESS, u.getAddress());
            values.put(COLUMN_PSSWD, u.getPsswd());
            SQLiteDatabase db = getWritableDatabase();
            db.insert(TABLE_USERS, null, values);
            db.close();
        }
        catch (SQLiteConstraintException e){
            throw new SQLIntegrityConstraintViolationException(e.toString());
        }
    }

    public ArrayList<USers> test2(){
        ArrayList<USers> ar = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String q = "SELECT * FROM " + TABLE_USERS + " WHERE 1";
        Cursor c = db.rawQuery(q, null);
        c.moveToFirst();
        while(!c.isAfterLast()){
            USers u = new USers(c.getString(c.getColumnIndex(COLUMN_NAME)),
                    c.getString(c.getColumnIndex(COLUMN_BRANCH)),
                    c.getString(c.getColumnIndex(COLUMN_ROLL)),
                    c.getString(c.getColumnIndex(COLUMN_PHONE)),
                    c.getString(c.getColumnIndex(COLUMN_ADDRESS)),
                    c.getString(c.getColumnIndex(COLUMN_PSSWD)));
            ar.add(u);
            u = null;
            System.gc();
            c.moveToNext();
        }
        db.close();
        c.close();
        return ar;
    }

    public ArrayList<String> test(){
        ArrayList<String> ar = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String q = "SELECT * FROM " + TABLE_USERS + " WHERE 1";
        Cursor c = db.rawQuery(q, null);
        c.moveToFirst();
        while(!c.isAfterLast()){
            /*USers u = new USers(c.getString(c.getColumnIndex(COLUMN_NAME)),
                    c.getString(c.getColumnIndex(COLUMN_BRANCH)),
                    c.getString(c.getColumnIndex(COLUMN_ROLL)),
                    c.getString(c.getColumnIndex(COLUMN_PHONE)),
                    c.getString(c.getColumnIndex(COLUMN_ADDRESS)),
                    c.getString(c.getColumnIndex(COLUMN_PSSWD)));*/
            ar.add(c.getString(c.getColumnIndex(COLUMN_ROLL)) + c.getString(c.getColumnIndex(COLUMN_NAME)));
            c.moveToNext();
        }
        db.close();
        c.close();
        return ar;
    }

    public boolean authAdmin(String id, String pass){
        SQLiteDatabase db = getReadableDatabase();
        String q = "SELECT * FROM " + TABLE_ADMINS + " WHERE " + COLUMN_ADMIN_UID + " = \"" + id + "\" AND " + COLUMN_ADMIN_PSSWD + " = \"" + pass + "\"";
        Cursor c = db.rawQuery(q, null);
        c.moveToFirst();
        if(c.getCount()>0){
            c.close();
            return true;
        }
        c.close();
        return false;
    }

    public boolean authUser(String roll, String pass){
        SQLiteDatabase db = getReadableDatabase();
        String q = "SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_ROLL + " = \"" + roll + "\" AND " + COLUMN_PSSWD + " = \"" + pass + "\"";
        Cursor c = db.rawQuery(q, null);
        c.moveToFirst();
        if(c.getCount()>0){
            c.close();
            return true;
        }
        c.close();
        return false;
    }

    public void addAdminDB(Admins a)throws SQLiteConstraintException, SQLiteAbortException, SQLIntegrityConstraintViolationException{
        try{
            ContentValues values = new ContentValues();
            values.put(COLUMN_ADMIN_NAME, a.getName());
            values.put(COLUMN_ADMIN_DESIGNATION, a.getDesig());
            values.put(COLUMN_ADMIN_PHONE, a.getPhone());
            values.put(COLUMN_ADMIN_UID, a.getUid());
            values.put(COLUMN_ADMIN_PSSWD, a.getPsswd());
            SQLiteDatabase db = getWritableDatabase();
            db.insert(TABLE_ADMINS, null, values);
            db.close();
        }
        catch (SQLiteConstraintException e){
            throw new SQLIntegrityConstraintViolationException(e.toString());
        }
    }

    public boolean checkAdmin(String uid){
        SQLiteDatabase db = getReadableDatabase();
        String q = "SELECT * FROM " + TABLE_ADMINS + " WHERE " + COLUMN_ADMIN_UID + " = \"" + uid + "\"";
        Cursor c = db.rawQuery(q, null);
        c.moveToFirst();
        if(c.getCount()>0){
            c.close();
            return true;
        }
        c.close();
        return false;
    }


    public boolean checkUser(String roll){
        SQLiteDatabase db = getReadableDatabase();
        String q = "SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_ROLL + " = \"" + roll + "\"";
        Cursor c = db.rawQuery(q, null);
        c.moveToFirst();
        if(c.getCount()>0){
            c.close();
            return true;
        }
        c.close();
        return false;
    }

    public ArrayList<String> getUsers(){
        ArrayList<String> ar = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String q = "SELECT * FROM " + TABLE_USERS + " WHERE 1";
        Cursor c = db.rawQuery(q, null);
        c.moveToFirst();
        while(!c.isAfterLast()){
            /*USers u = new USers(c.getString(c.getColumnIndex(COLUMN_NAME)),
                    c.getString(c.getColumnIndex(COLUMN_BRANCH)),
                    c.getString(c.getColumnIndex(COLUMN_ROLL)),
                    c.getString(c.getColumnIndex(COLUMN_PHONE)),
                    c.getString(c.getColumnIndex(COLUMN_ADDRESS)),
                    c.getString(c.getColumnIndex(COLUMN_PSSWD)));*/
            ar.add(c.getString(c.getColumnIndex(COLUMN_ROLL)) + " " + c.getString(c.getColumnIndex(COLUMN_NAME)));
            c.moveToNext();
        }
        db.close();
        c.close();
        return ar;
    }

    public void addID(String id) throws SQLIntegrityConstraintViolationException{
        try{
            ContentValues values = new ContentValues();
            values.put(COLUMN_ID_IN, id);
            SQLiteDatabase db = getWritableDatabase();
            db.insert(TABLE_IDS, null, values);
            db.close();
        }
        catch (SQLiteConstraintException e){
            throw new SQLIntegrityConstraintViolationException(e.toString());
        }
    }

    public boolean checkIdIn(String id){
        SQLiteDatabase db = getReadableDatabase();
        String q = "SELECT * FROM " + TABLE_IDS + " WHERE " + COLUMN_ID_IN + " = \"" + id + "\"";
        Cursor c = db.rawQuery(q, null);
        c.moveToFirst();
        if(c.getCount()>0){
            c.close();
            return true;
        }
        c.close();
        return false;
    }

}
