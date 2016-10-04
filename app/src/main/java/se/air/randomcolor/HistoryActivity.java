package se.air.randomcolor;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.view.MenuItem;


public class HistoryActivity extends MainActivity implements NavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }
}
