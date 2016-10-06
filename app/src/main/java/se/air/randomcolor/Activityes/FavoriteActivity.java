package se.air.randomcolor.Activityes;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
        LinearLayout layout;
        TextView textView;

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams( // параметры для linearLayout
                LinearLayout.LayoutParams.MATCH_PARENT, 200);
        layoutParams.setMargins(10, 10, 10, 10);

        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.query(dbHelper.tableName, null, null, null, null, null, null); // курсор для БД

        StringBuilder str;          // TODO refactoring this code pls...
        if(cursor.moveToFirst()){
            int idColIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
            int colorColIndex = cursor.getColumnIndex(DBHelper.KEY_COLOR);

            do{
                layout = new LinearLayout(this);
                layout.setLayoutParams(layoutParams);

                str = new StringBuilder();
                str.append(cursor.getInt(idColIndex)).append(". ");
                str.append(cursor.getString(colorColIndex));

                //layout.setBackgroundColor(Color.parseColor(cursor.getString(colorColIndex))); // set layout color
                layout.setBackgroundResource(R.drawable.border_shadow);

                textView = new TextView(this);
                textView.setTextSize(25);
                textView.setText(str.toString());

                layout.addView(textView);
                linearLayout.addView(layout);
            }while (cursor.moveToNext());
        }
        cursor.close();
    }

    private LinearLayout prepareLayuot(){
        return new LinearLayout(this); // TODO implement
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
}
