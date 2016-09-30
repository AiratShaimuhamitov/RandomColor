package se.air.randomcolor;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ScrollingView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import static android.R.attr.color;
import static android.R.attr.id;

public class FavoriteActivity extends AppCompatActivity {

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.content_favorite);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        ScrollView scrollView = new ScrollView(this);
        linearLayout.addView(scrollView);

        LinearLayout scrollLinearLayout = new LinearLayout(this);
        scrollLinearLayout.setOrientation(LinearLayout.VERTICAL);
        scrollLinearLayout.setLayoutParams(layoutParams);
        scrollView.addView(scrollLinearLayout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.del);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        fillColor(scrollLinearLayout);
    }

    private void fillColor(LinearLayout linearLayout){
        LinearLayout layout;
        TextView textView;
        ViewGroup.LayoutParams lpView = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200); // параметры для linearLayout

        dbHelper = new DBHelper(this);

        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.query(DBHelper.TABLE_COLORS, null, null, null, null, null, null); // курсор для БД

        StringBuilder str;
        if(cursor.moveToFirst()){
            int idColIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
            int colorColIndex = cursor.getColumnIndex(DBHelper.KEY_COLOR);

            do{
                str = new StringBuilder();
                layout = new LinearLayout(this);
                layout.setLayoutParams(lpView);
                textView = new TextView(this);
                textView.setTextSize(25);
                str.append(cursor.getInt(idColIndex)).append(". ");
                str.append(cursor.getString(colorColIndex));
                layout.setBackgroundColor(Color.parseColor(cursor.getString(colorColIndex))); // set layout color
                textView.setText(str.toString());
                layout.addView(textView);
                linearLayout.addView(layout);
            }while (cursor.moveToNext());
        }
    }


}
