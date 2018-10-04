package com.example.davee.footyappv2;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    SectionsPageAdapter sectionsPageAdapter;
    ViewPager viewPager;
    RecyclerView recyclerView;

    boolean pressAgain = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create the adapter that will return a fragment for each of the two primary sections of the activity.
        sectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        // Used to navigate between the fragments
        viewPager = findViewById(R.id.container);
        setupViewPager(viewPager);

        // Create a TabLayout object
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        //navigate between tabs when you click on the tab layout
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    //the method is used to manage the fragments
    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new GeneralNews(), "General News");
        adapter.addFragment(new Following(), "Following");
        viewPager.setAdapter(adapter); // refer to the SectionsPageAdapter class, method addFragment
    }

    @Override
    public void onBackPressed() {
        if (pressAgain) {
            super.onBackPressed();
            Intent intent = new Intent(Intent.ACTION_MAIN); //the first intent when the app is launched
            intent.addCategory(Intent.CATEGORY_HOME); //to home screen
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); //starts activity in a new task; clears the current task
            startActivity(intent);
            finish();
        }

        pressAgain = true;

        Toast.makeText(MainActivity.this, "Click again to exit.", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pressAgain = false;
            }
        }, 2000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else if (item.getItemId() == R.id.about_footy) {
            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
            alert.setTitle("About Footy App");
            alert.setMessage("Created by football fans to follow their favorite teams");
            alert.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            alert.show();
        }
        return super.onOptionsItemSelected(item);
    }

}