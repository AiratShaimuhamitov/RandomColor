package se.air.randomcolor;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class FavoriteActivity extends MainActivity implements NavigationView.OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.content_favorite);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                                                            ViewGroup.LayoutParams.MATCH_PARENT); // параметры Layout

        ScrollView scrollView = new ScrollView(this); // ScrollView для прокрутки LinearLayout's
        linearLayout.addView(scrollView);

        LinearLayout scrollLinearLayout = new LinearLayout(this); /* ScrollView работает только с одним Layout,
                                                                     поэтому добовляем еще один внутрь ScrollView */

        scrollLinearLayout.setOrientation(LinearLayout.VERTICAL);
        scrollLinearLayout.setLayoutParams(layoutParams);
        scrollView.addView(scrollLinearLayout);                    /* Иерархия Layout элементов: LinearLayout -> ScrollView ->
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


        fillColors(scrollLinearLayout);
    }

    private void fillColors(LinearLayout linearLayout){ // заполняет переданный Layout цветами (LinearLayout's)
        LinearLayout layout;
        TextView textView;
        ViewGroup.LayoutParams lpView = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200); // параметры для linearLayout

        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.query(DBHelper.TABLE_COLORS, null, null, null, null, null, null); // курсор для БД

        StringBuilder str;          // TODO refactoring this code pls...
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

    private LinearLayout prepareLayuot(){
        return new LinearLayout(this); // TODO implement
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_favorite) {

        } else if (id == R.id.nav_main){
            checkedDrawerItemId = id;
            Intent intent = new Intent(FavoriteActivity.this, MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_history) {

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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
