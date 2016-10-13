package se.air.randomcolor.Activityes;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import se.air.randomcolor.DBHelper;
import se.air.randomcolor.R;

public class FavoriteActivity extends MainActivity implements NavigationView.OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.content_history);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        // ScrollView для прокрутки LinearLayout's

        LinearLayout scrollLinearLayout = (LinearLayout) findViewById(R.id.scroll_linear_layout_gen); /* ScrollView работает только с одним Layout,
                                                                     поэтому добовляем еще один внутрь ScrollView */
        /* Иерархия Layout элементов: LinearLayout -> ScrollView ->
           LinearLayout -> n * LinearLayout (где n = кол-во цветов) */

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_gen);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        checkedDrawerItemId = getIntent().getIntExtra("checkedDrawerItemId", checkedDrawerItemId);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fillColors(scrollLinearLayout, dbHelperFav);
    }

    protected void fillColors(LinearLayout linearLayout, DBHelper dbHelper){ // заполняет переданный Layout цветами (LinearLayout's)
        ArrayList<LinearLayout> linearLayouts = new ArrayList<>();

        RelativeLayout layout;

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams( // параметры для linearLayout
                LinearLayout.LayoutParams.MATCH_PARENT, 200);
        layoutParams.setMargins(0, 10, 0, 10);

        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.query(dbHelper.tableName, null, null, null, null, null, null); // курсор для БД

        if(cursor.moveToFirst()){
            int colorColIndex = cursor.getColumnIndex(DBHelper.KEY_COLOR);

            do{
                String color = cursor.getString(colorColIndex);
                layout = new RelativeLayout(this);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { // TODO REIMPLEMENTATION this code
                    layout.setBackgroundTintList(new ColorStateList(
                            new int[][]{new int[]{android.R.attr.state_pressed},
                                    new int[]{}
                            },
                            new int[]{Color.GREEN, Color.parseColor(color)}
                    ));
                    layout.setBackgroundTintMode(PorterDuff.Mode.SRC_OVER);
                }
                layout.setLayoutParams(layoutParams);
                prepareLayout(layout, color);

                linearLayout.addView(layout);
            }while (cursor.moveToNext());
        }
        cursor.close();
    }

    private void prepareLayout(RelativeLayout layout, String color){
        layout.setBackgroundResource(R.drawable.border_shadow);

        TextView textView = new TextView(this);
        textView.setText(color);
        textView.setTextSize(35);

        RelativeLayout.LayoutParams textViewParams = new  RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        textViewParams.addRule(RelativeLayout.CENTER_VERTICAL);
        textViewParams.addRule(RelativeLayout.ALIGN_PARENT_START);
        textViewParams.setMarginStart(30);

        ImageButton imageButton = new ImageButton(this);
        imageButton.setBackgroundResource(R.drawable.ic_info_black_48dp);

        RelativeLayout.LayoutParams imageButtonParams =  new RelativeLayout.LayoutParams(96, 96);
        imageButtonParams.addRule(RelativeLayout.CENTER_VERTICAL);
        imageButtonParams.addRule(RelativeLayout.ALIGN_PARENT_END);
        imageButtonParams.setMarginEnd(50);

        layout.addView(textView, textViewParams);
        layout.addView(imageButton, imageButtonParams);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        checkedDrawerItemId = id;

        if (id == R.id.nav_favorite) {

        } else if (id == R.id.nav_main){
            Intent intent = new Intent(FavoriteActivity.this, MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_history) {
            Intent intent = new Intent(FavoriteActivity.this, HistoryActivity.class); //TODO implement History activity
            intent.putExtra("checkedDrawerItemId", id);
            startActivity(intent);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_gen);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Метод который связывает ToolBar и макет меню (xml файл)
        return true;
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_gen);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            this.finish();
        }
    }

    public void testClick(View view){

    }

}
