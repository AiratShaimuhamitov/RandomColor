package se.air.randomcolor;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBHelper extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION  = 1;
    public String databaseName;
    public String tableName;

    public static final String KEY_ID = "_id";
    public static final String KEY_COLOR = "color";

    public DBHelper(Context context, String dbName, String tableName) {
        super(context, dbName, null, DATABASE_VERSION);
        databaseName = dbName;
        this.tableName = tableName;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + tableName + " (" + KEY_ID + " integer primary key, " + KEY_COLOR + " text" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exits " + tableName);
        onCreate(db);
    }

    public int getColumnsCount(SQLiteDatabase db){
        Cursor cursor = db.rawQuery("SELECT COUNT (*) FROM " + tableName, null);
        int count = 0;
        if(null != cursor)
            if(cursor.getCount() > 0){
                cursor.moveToFirst();
                count = cursor.getInt(0);
            }
        cursor.close();
        db.close();
        return count;
    }

    public boolean deleteFirstColor(DBHelper dbHelper) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query(tableName, null, null, null, null, null, null); // курсор для БД

        int idColIndex = cursor.getColumnIndex(DBHelper.KEY_ID);

        if(cursor.moveToFirst()){
            int id = cursor.getInt(idColIndex);
            Log.d("DB", "--- Delete from mytable: ---");
            db.delete(tableName, KEY_ID + " = " + id, null);
            updateIndexes(db);
            return true;
        }
        cursor.close();
        return false;
    }

    private void updateIndexes(SQLiteDatabase db) {
        db.execSQL("update " + tableName + " set " + KEY_ID + " = " + KEY_ID + " - 1 where " + KEY_ID + " > 1");
    }
}
