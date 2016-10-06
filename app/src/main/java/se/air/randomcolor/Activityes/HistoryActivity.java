package se.air.randomcolor.Activityes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.LinearLayout;

import se.air.randomcolor.R;


public class HistoryActivity extends FavoriteActivity implements NavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.content_history);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout scrollLinearLayout = (LinearLayout) findViewById(R.id.scroll_linear_layout_gen);

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

        fillColors(scrollLinearLayout, dbHelperHis);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        checkedDrawerItemId = id;
        if (id == R.id.nav_favorite) {
            Intent intent = new Intent(HistoryActivity.this, FavoriteActivity.class);
            intent.putExtra("checkedDrawerItemId", id);
            startActivity(intent);
        } else if (id == R.id.nav_main){
            Intent intent = new Intent(HistoryActivity.this, MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_history) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_gen);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
