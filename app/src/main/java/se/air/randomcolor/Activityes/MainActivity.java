package se.air.randomcolor.Activityes;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import se.air.randomcolor.DBHelper;
import se.air.randomcolor.R;
import se.air.randomcolor.Services.ColorService;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnTouchListener {

    protected int checkedDrawerItemId; // для остлеживания выделения названия в NavigationView
    private TextView colorHEXTextView;
    private TextView colorRGBTextView;
    private RelativeLayout relativeLayout;
    public ColorService colorService;
    public se.air.randomcolor.Models.Color color;
    protected DBHelper dbHelperFav;
    protected DBHelper dbHelperHis;
    protected NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbHelperFav = new DBHelper(this, "FavoritesDB", "fav_colors");
        dbHelperHis = new DBHelper(this, "HistoryDB", "his_colors");

        final SQLiteDatabase database = dbHelperFav.getWritableDatabase();

        final ContentValues contentValues = new ContentValues();
        final Context context = this.getBaseContext();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contentValues.put(DBHelper.KEY_COLOR, color.getColorHEX());
                database.insert(dbHelperFav.tableName, null, contentValues);
                Toast toast = Toast.makeText(context, "HEX was saved to favorites", Toast.LENGTH_LONG);
                toast.show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        checkedDrawerItemId = R.id.nav_main;

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        relativeLayout = (RelativeLayout) findViewById(R.id.content_main);
        colorHEXTextView = (TextView) findViewById(R.id.colorHexView);
        colorRGBTextView = (TextView) findViewById(R.id.colorRGBView);

        relativeLayout.setOnTouchListener(this);

        colorService = new ColorService();
        setColor();
    }

    @Override
    protected void onResume() {
        super.onResume();

        navigationView.getMenu().findItem(R.id.nav_main).setChecked(true);
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

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        navigationView.getMenu().clear();
        navigationView.inflateMenu(R.menu.activity_main_drawer);

        navigationView.getMenu().findItem(checkedDrawerItemId).setChecked(true);

        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();
        checkedDrawerItemId = id;

        if (id == R.id.nav_favorite) {
            Intent intent = new Intent(MainActivity.this, FavoriteActivity.class);
            intent.putExtra("checkedDrawerItemId", id);
            startActivity(intent);
            this.onPause();

        } else if (id == R.id.nav_main){

        } else if (id == R.id.nav_history) {
            Intent intent = new Intent(MainActivity.this, HistoryActivity.class); //TODO implement History activity
            intent.putExtra("checkedDrawerItemId", id);
            startActivity(intent);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    @Override
    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
        color.setColorHEX(state.getString("colorHEX"));
        int[] rgb = new int[]{state.getInt("r"), state.getInt("g"), state.getInt("b")};
        color.setRgb(rgb);
        setColor(color.getColorHEX(), color.toStringRGB());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("colorHEX", color.getColorHEX());
        int[] rgb = color.getRGB();
        outState.putInt("r", rgb[0]);
        outState.putInt("g", rgb[1]);
        outState.putInt("b", rgb[2]);
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
                saveColorInHistory();
                break;
            case MotionEvent.ACTION_CANCEL:
        }
        return true;
    }

    private void saveColorInHistory() {
        SQLiteDatabase db = dbHelperHis.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.KEY_COLOR, color.getColorHEX());
        db.insert(dbHelperHis.tableName, null, contentValues);

        if(dbHelperHis.getColumnsCount(db) > 25){
            dbHelperHis.deleteFirstColor(dbHelperHis);
        }
    }


    private void setColor(){
        color = colorService.getRandomColor();
        color.setRgb(colorService.hexToRqb(color.getColorHEX()));

        colorHEXTextView.setText(color.getColorHEX());
        colorRGBTextView.setText(color.toStringRGB());

        int colorInt = Color.parseColor(color.getColorHEX());
        relativeLayout.setBackgroundColor(colorInt);
        relativeLayout.invalidate();
    }

    private void setColor(String colorHex,  String colorRGB){
        colorHEXTextView.setText(colorHex);
        colorRGBTextView.setText(colorRGB);

        int colorInt = Color.parseColor(colorHex);

        relativeLayout.setBackgroundColor(colorInt);
        relativeLayout.invalidate();
    }

    public void copyClick(MenuItem item) {
        Context context = this.getBaseContext();
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("", color.getColorHEX());
        clipboardManager.setPrimaryClip(clip);
        Toast toast = Toast.makeText(context, "Hex code saved to clipboard", Toast.LENGTH_SHORT);
        toast.show();
    }
}