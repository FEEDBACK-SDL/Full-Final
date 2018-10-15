package com.example.fedsev.feedback;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.time.LocalDateTime;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static MyAppDatabase myAppDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Tab1()).commit();
        myAppDatabase = Room.databaseBuilder(getApplicationContext(), MyAppDatabase.class, "recordsdb").allowMainThreadQueries().build();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.fragment_container);
        navigationView.getMenu().getItem(0).setChecked(true);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //Toast.makeText(MainActivity.this,"Helllo",Toast.LENGTH_LONG).show();
            Intent i = new Intent(MainActivity.this, Calling.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    //Java Annotation is a tag that represents the metadata
    // i.e. attached with class, interface, methods or fields to indicate some additional information which can be used by java compiler and JVM.
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.calls) {
            // Handle the camera action
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CallStat()).commit();  //we are creating the object of callstat which
                                                                                                                   //inflates the layout callstats.xml to  view in oncreateview method
        }

        else if (id == R.id.tobedone)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Tab2()).commit();
        }

        else if (id == R.id.stats)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Tab1()).commit();
        }
        else if (id == R.id.profile){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Profile()).commit();

        }
        else if (id == R.id.logout){
            SharedPreferences sharedPreferences = getSharedPreferences("private_data",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            LocalDateTime now = LocalDateTime.now();
            Long et = sharedPreferences.getLong("timeend",0);
            editor.putLong("rt",et - (now.getHour()*60*60*1000 + now.getMinute()*60*1000 + now.getSecond()*1000));
            editor.putString("token","");
            editor.apply();
            Intent i = new Intent(MainActivity.this, Login.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
