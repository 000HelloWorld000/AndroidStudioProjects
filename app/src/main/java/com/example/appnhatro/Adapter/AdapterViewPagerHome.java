package com.example.appnhatro.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.appnhatro.View.Fragment.FragmentODau;
import com.example.appnhatro.View.Fragment.FragmentTimGi;

public class AdapterViewPagerHome extends FragmentPagerAdapter {
    FragmentODau fragmentODau;
    FragmentTimGi fragmentTimGi;

    public AdapterViewPagerHome(FragmentManager fm) {
        super(fm);
        fragmentODau = new FragmentODau();
        fragmentTimGi = new FragmentTimGi();

    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return fragmentODau;
            case 1:
                return fragmentTimGi;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
