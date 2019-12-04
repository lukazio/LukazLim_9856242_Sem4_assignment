package com.example.hotelroombooking;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class BookingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.title_booking);
        setContentView(R.layout.activity_booking);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabLayoutBooking);
        tabLayout.addTab(tabLayout.newTab().setText("Add Booking"));
        tabLayout.addTab(tabLayout.newTab().setText("Prices"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager vp = (ViewPager)findViewById(R.id.viewPagerBooking);
        PagerAdapterBooking mPA = new PagerAdapterBooking(getSupportFragmentManager(),tabLayout.getTabCount());
        vp.setAdapter(mPA);

        vp.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vp.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
