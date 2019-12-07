package com.example.hotelroombooking;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class AboutActivity extends AppCompatActivity {

    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.title_about);
        setContentView(R.layout.activity_about);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabLayoutAbout);
        tabLayout.addTab(tabLayout.newTab().setText("About"));
        tabLayout.addTab(tabLayout.newTab().setText("Contact"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager vp = (ViewPager)findViewById(R.id.viewPagerAbout);
        PagerAdapterAbout mPA = new PagerAdapterAbout(getSupportFragmentManager(),tabLayout.getTabCount());
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu=menu;
        getMenuInflater().inflate(R.menu.app_menu_about,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Uri uri;

        switch(item.getItemId()){
            case R.id.menu_contact: //Dial contact number in phone dialler
                uri = Uri.parse("tel:+6049111337");
                Intent contactIntent = new Intent(Intent.ACTION_DIAL,uri);

                if(contactIntent.resolveActivity(getPackageManager()) != null)
                    startActivity(contactIntent);
                else
                    Log.d("DialImplicitIntent","Contact intent failed!");

                break;
            case R.id.menu_email:   //Compose email in another email app
                uri = Uri.fromParts("mailto","dancecardo@bookappa.my",null);
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO,uri);

                if(emailIntent.resolveActivity(getPackageManager()) != null)
                    startActivity(Intent.createChooser(emailIntent,"Send email..."));
                else
                    Log.d("EmailImplicitIntent","Email intent failed!");

                break;
            case R.id.menu_location:    //View headquarters location in mapping/location app
                String location = "XDSoft Headquarters (doesn't exist, just to show implicit intent)";
                uri = Uri.parse("geo:0,0?q=" + location);
                Intent locationIntent = new Intent(Intent.ACTION_VIEW,uri);

                if(locationIntent.resolveActivity(getPackageManager()) != null)
                    startActivity(Intent.createChooser(locationIntent,"View location..."));
                else
                    Log.d("LocationImplicitIntent","Location intent failed!");

                break;
            case R.id.menu_share:   //Share contact info as text to another app
                String shareText = "XDSoft\n\nPhone: +6049111337\nEmail: dancecardo@bookappa.my\nAddress: 666 Meme Street, Lmoa Town";
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT,shareText);

                if(shareIntent.resolveActivity(getPackageManager()) != null)
                    startActivity(Intent.createChooser(shareIntent,"Share contact info..."));
                else
                    Log.d("ShareImplicitIntent","Share intent failed!");

                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
