package se.air.randomcolor;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION  = 1;
    public static final String DATABASE_NAME  = "ColorsDB";
    public static final String TABLE_COLORS  = "colors";

    public static final String KEY_ID = "_id";
    public static final String KEY_COLOR = "color";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_COLORS + " (" + KEY_ID + " integer primary key, " + KEY_COLOR + " text" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exits " + TABLE_COLORS);

        onCreate(db);
    }
}
