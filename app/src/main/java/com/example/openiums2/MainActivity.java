package com.example.openiums2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import android.content.Intent;

import com.google.android.material.navigation.NavigationView;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    FirebaseAuth mAuth;
    public static final String SHARED_PREFS="sharedprefs";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth=FirebaseAuth.getInstance();
        Toolbar toolbar = findViewById(R.id.toolbar); //Ignore red line errors
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav,
                R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int ItemId = item.getItemId();

        if(ItemId== R.id.nav_home)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
        }
        else if(ItemId== R.id.nav_profile)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SettingsFragment()).commit();
        }
        if(ItemId== R.id.nav_courses)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ShareFragment()).commit();
        }
        if(ItemId== R.id.nav_result)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AboutFragment()).commit();
        }
        if(ItemId== R.id.nav_routine)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RoutineFragment()).commit();

        }
        if(ItemId== R.id.nav_mat)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MaterialFragment()).commit();
        }
        if(ItemId== R.id.nav_advisor)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AdvisorFragment()).commit();
        }
        if(ItemId== R.id.nav_evaluation)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TeacherEvlFragment()).commit();
        }
        if(ItemId== R.id.nav_payments)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PaymentFragment()).commit();
        }
        if(ItemId== R.id.nav_help)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HelpFragment()).commit();
        }
        if(ItemId== R.id.nav_logout)
        {
            //Toast.makeText(this, "Logout!", Toast.LENGTH_SHORT).show();
            mAuth.signOut();
            Intent intent = new Intent(MainActivity.this, LogInPage.class);
            startActivity(intent);
            SharedPreferences sharepreferences=getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
            SharedPreferences.Editor editor=sharepreferences.edit();
            editor.putString("name","");
            editor.apply();
        }



        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}