package com.paulinadelgado.bakinghero;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private Toolbar appbar;
    private DrawerLayout drawerLayout;
    private NavigationView navView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appbar = (Toolbar)findViewById(R.id.appbar);
        setSupportActionBar(appbar);
        getSupportActionBar().setElevation(0);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_drawer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);


        Fragment fragment =null;
        FragmentTransaction t = getFragmentManager().beginTransaction();
        fragment = new ConverterFragment();
        t.replace(R.id.content_frame, fragment);
        t.commit();

        navView = (NavigationView)findViewById(R.id.navview);
        navView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        Fragment fragment =null;
                        FragmentTransaction t = getFragmentManager().beginTransaction();


                        switch (menuItem.getItemId()) {
                            case R.id.convert:

                                fragment = new ConverterFragment();

                                break;
                            case R.id.recipe:
                                fragment = new RecipesFragment();

                                break;
                        }

                        t.replace(R.id.content_frame, fragment);
                        t.commit();

                        menuItem.setChecked(true);
                        getSupportActionBar().setTitle(menuItem.getTitle());
                        drawerLayout.closeDrawers();

                        return true;
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch(item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0 ){
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }



}
