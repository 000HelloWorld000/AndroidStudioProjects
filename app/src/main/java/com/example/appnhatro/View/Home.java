package com.example.appnhatro.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.appnhatro.Adapter.AdapterViewPagerHome;
import com.example.appnhatro.R;

public class Home extends AppCompatActivity implements ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener {

    ViewPager vp_fragment;
    RadioButton rd_odau,rd_timgi;
    RadioGroup radioGroup_odau_timgi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        vp_fragment = findViewById(R.id.vp_fragment);
        AdapterViewPagerHome adapterViewPagerHome = new AdapterViewPagerHome(getSupportFragmentManager());
        vp_fragment.setAdapter(adapterViewPagerHome);
        rd_odau = findViewById(R.id.rd_odau);
        rd_timgi = findViewById(R.id.rd_timgi);
        radioGroup_odau_timgi = findViewById(R.id.rdgroup);

        radioGroup_odau_timgi.setOnCheckedChangeListener(this);
        vp_fragment.addOnPageChangeListener(this);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position)
        {
            case 0:
                rd_odau.setChecked(true);
                break;
            case 1:
                rd_timgi.setChecked(true);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkId) {
        switch (checkId)
        {
            case R.id.rd_odau:
                vp_fragment.setCurrentItem(0);
                break;
            case R.id.rd_timgi:
                vp_fragment.setCurrentItem(1);
                break;
        }
    }
}
