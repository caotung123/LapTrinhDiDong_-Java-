package com.example.app_sothuchi;

import androidx.annotation.FractionRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import Model.MyDataBase;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        anhXa();
        loadFragmentDefault();
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

    }

    public void anhXa()
    {
        bottomNavigationView = findViewById(R.id.bottom_navigation);
    }

    public void loadFragmentDefault()
    {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment sellectFragment = null;

            switch (item.getItemId())
            {
                case R.id.nav_home:
                {
                    sellectFragment = new HomeFragment();
                    break;
                }
                case R.id.nav_wallet:
                {
                    sellectFragment = new TaiKhoanFragment();
                    break;
                }
                case R.id.nav_add:
                {
                    sellectFragment = new ThemFragment();
                    break;
                }
                case R.id.nav_report:
                {
                    sellectFragment = new BaoCaoFragment();
                    break;
                }
                case R.id.nav_other:
                {
                    sellectFragment = new KhacFragment();
                    break;
                }
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,sellectFragment).commit();

            return true;
        }
    };

}