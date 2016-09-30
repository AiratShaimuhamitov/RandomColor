package se.air.randomcolor;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnTouchListener {

    private TextView colorName;
    private RelativeLayout relativeLayout;
    private String color;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbHelper = new DBHelper(this);

        final SQLiteDatabase database = dbHelper.getWritableDatabase();

        final ContentValues contentValues = new ContentValues();

        final Context context = this.getBaseContext();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contentValues.put(DBHelper.KEY_COLOR, color);
                database.insert(DBHelper.TABLE_COLORS, null, contentValues);
                Toast toast = Toast.makeText(context, "HEX was saved to favorites", Toast.LENGTH_LONG);
                toast.show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        dbHelper = new DBHelper(this);

        relativeLayout = (RelativeLayout) findViewById(R.id.content_main);
        colorName = (TextView) findViewById(R.id.color_name);

        assert relativeLayout != null;
        relativeLayout.setOnTouchListener(this);

        setColor();
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


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_favorite) {
            Intent intent = new Intent(this, FavoriteActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_history) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        color = savedInstanceState.getString("color");
        setColor(color);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("color", color);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN: // нажатие
                break;
            case MotionEvent.ACTION_MOVE: // движение
                break;
            case MotionEvent.ACTION_UP: // отпускание
                setColor();
                break;
            case MotionEvent.ACTION_CANCEL:
        }
        return true;
    }

    private void setColor(){
        color = getRandomColor();
        colorName.setText(color);
        int colorInt = Color.parseColor(color);
        relativeLayout.setBackgroundColor(colorInt);
        relativeLayout.invalidate();
    }

    private void setColor(String color){
        colorName.setText(color);
        int colorInt = Color.parseColor(color);
        relativeLayout.setBackgroundColor(colorInt);
        relativeLayout.invalidate();
    }

    private String getRandomColor() {  // возвращает HEX код
        Random random = new Random();
        String letters = "0123456789abcdef";
        StringBuilder color = new StringBuilder();
        color.append('#');
        for(int i = 0; i < 6; i++){
            color.append(letters.charAt(random.nextInt(letters.length())));
        }
        return color.toString();
    }

    public void copyClick(MenuItem item) {
        Context context = this.getBaseContext();
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("", color);
        clipboardManager.setPrimaryClip(clip);
        Toast toast = Toast.makeText(context, "Hex code saved to clipboard", Toast.LENGTH_LONG);
        toast.show();
    }
}

