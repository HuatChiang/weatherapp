package com.example.huatchan.weatherapp;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

/**
 * Created by Huat Chan on 7/25/2016.
 */
public class DBAdapter extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 7;
    private static String DB_PATH = "/data/data/com.example.huatchan.weatherapp/databases/";
    private static String DB_NAME = "myDBName";
    private static final String TABLE_WEATHER_DAY = "weatherDay";
    private static final String KEY_DAY = "day";
    private static final String KEY_CONDITION = "condition";
    private static final String KEY_TEMP = "temp";
    private static final String KEY_DATE = "date";
    private SQLiteDatabase myDataBase;
    private final Context myContext;


    public DBAdapter(Context context) {

        super(context, DB_NAME, null, DATABASE_VERSION);
        this.myContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_WEATHER_DAY
                + "(" + KEY_DATE + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_TEMP + " INTEGER," + KEY_DAY + " TEXT,"
                + KEY_CONDITION + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
        Log.e("ERROR", "horray the onCreate() method was executed");
    }


    public void createDataBase() throws IOException {

        boolean dbExist = checkDataBase();

        if(dbExist){
            //do nothing - database already exist
        }else{
            this.getReadableDatabase();

            try {

                copyDataBase();

            } catch (IOException e) {

                throw new Error("Error copying database");

            }
        }

    }


    private boolean checkDataBase(){

        SQLiteDatabase checkDB = null;

        try{
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        }catch(SQLiteException e){
            Log.e("ERROR", "There is no database");

        }

        if(checkDB != null){

            checkDB.close();

        }

        return checkDB != null ? true : false;
    }


    private void copyDataBase() throws IOException{

        //Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public void openDataBase() throws SQLException {


        //Open the database
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        this.getReadableDatabase();  //Need this for the onUpgrade method to be executed
    }

    @Override
    public synchronized void close() {

        if(myDataBase != null)
            myDataBase.close();

        super.close();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {  // Upgrades database when version is changed
        Log.e("ERROR", "UPGRADING DATABASE");
        //Drop if exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WEATHER_DAY);
        //Make it again
        onCreate(db);
    }

    public void addWeatherDay(weatherDay day){
        //Add a weather day
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TEMP, day.getTempereature());
        values.put(KEY_DAY, day.getDayOfWeek());
        values.put(KEY_CONDITION, day.getCondition());
        //Insert row
        db.insert(TABLE_WEATHER_DAY, null, values);
        db.close();

    }

    public weatherDay getDay(int date){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_WEATHER_DAY, new String[]{KEY_DATE, KEY_TEMP, KEY_DAY ,KEY_CONDITION, }, KEY_DATE + "=?"
        ,new String[] { String.valueOf(date) }, null, null, null, null);
        if(cursor != null);
        cursor.moveToFirst();
        weatherDay day = new weatherDay(Integer.parseInt(cursor.getString(0)), Integer.parseInt(cursor.getString(1)), cursor.getString(2), cursor.getString(3), null , null);
        return day;
    }
}