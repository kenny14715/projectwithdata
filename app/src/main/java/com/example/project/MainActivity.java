package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.room.Room;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.example.project.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    static FragmentManager fragmentManager;
    static AccDatabase accDatabase;
    AccDao accDao;
    LiveData<List<Account>> allAccsLive;
    AppBarConfiguration appBarConfiguration;
    NavController navController;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    myDatabase dbhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbhelper = new myDatabase(this); //初始化資料庫
        accDatabase = Room.databaseBuilder(this, AccDatabase.class, "account_database")
                .allowMainThreadQueries()
                .build();
        accDao = accDatabase.getAccDao();
        //Log.d(TAG, "onCreate: " + dbhelper.searchByname("忠孝夜市"));
        Account admin = new Account();
        admin.setAcc("admin");
        admin.setPwd("admin");
        admin.setPhone("0000000000");
        admin.setEmail("admin");
        admin.setUsername("admin");
        accDao.insertAccs(admin);
        allAccsLive = accDao.getAllAccsLives();

        //////////////////////////////
        //
    }

    @Override
    protected void onStart() {
        super.onStart();
        navController = Navigation.findNavController(this, R.id.fragment);
        navigationView = (NavigationView)findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawerLayout);
        appBarConfiguration = new AppBarConfiguration.Builder(R.id.guideFragment,R.id.settingsFragment).setOpenableLayout(drawerLayout).build();
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout);
        NavigationUI.setupWithNavController(navigationView, navController);

    }

    @Override
    public boolean onSupportNavigateUp() {
        navController = Navigation.findNavController(this, R.id.fragment);
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }
}