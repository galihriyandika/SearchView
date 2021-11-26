package com.example.searchview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.example.searchview.fragment.EnterDataFragment;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
        showEnterDataFragment();
    }
    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
    }

    private void showEnterDataFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, new EnterDataFragment())
                .commit();
    }

}