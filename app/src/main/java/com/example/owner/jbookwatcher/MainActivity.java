package com.example.owner.jbookwatcher;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.owner.jbookwatcher.adapters.PageFragAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout tl = findViewById(R.id.tabs_main);
        ViewPager vp = findViewById(R.id.viewpager_main);

        PageFragAdapter pfa = new PageFragAdapter(getSupportFragmentManager());
        vp.setAdapter(pfa);
        tl.setupWithViewPager(vp);
    }
}
