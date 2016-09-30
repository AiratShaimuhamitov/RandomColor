package se.air.randomcolor;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import static android.R.attr.id;

public class FavoriteActivity extends AppCompatActivity {

    private TextView textColors;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.del);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        textColors = (TextView) findViewById(R.id.textColorDB);

        fillColors(textColors);
    }

    private void fillColors(TextView textColors) {
        dbHelper = new DBHelper(this);

        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.query(DBHelper.TABLE_COLORS, null, null, null, null, null, null);

        StringBuilder str = new StringBuilder();
        if(cursor.moveToFirst()){
            int idColIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
            int colorColIndex = cursor.getColumnIndex(DBHelper.KEY_COLOR);

            do{
                str.append(cursor.getInt(idColIndex) + ". ");
                str.append(cursor.getString(colorColIndex)).append("\n");
            }while (cursor.moveToNext());
        }
        textColors.setText(str.toString());
    }

}
