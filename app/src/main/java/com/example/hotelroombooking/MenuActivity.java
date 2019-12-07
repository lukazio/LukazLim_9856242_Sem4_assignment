package com.example.hotelroombooking;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {

    SharedPreferences mSP;
    public static final String ACCOUNT_PREF = "UserAccountPrefs";
    public static final String LOGIN = "loginKey";

    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.title_mainmenu);
        setContentView(R.layout.activity_menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.app_menu_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_about:   //Go to about activity
                Intent aboutIntent = new Intent(this,AboutActivity.class);
                startActivity(aboutIntent);
                break;
            case R.id.menu_logout:  //Logout user
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("Logout");
                alert.setMessage("Are you sure you want to logout?");

                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mSP = getSharedPreferences(ACCOUNT_PREF, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = mSP.edit();
                        editor.clear();
                        editor.apply();

                        Intent logoutIntent = new Intent(MenuActivity.this,MainActivity.class);
                        startActivity(logoutIntent);
                        MenuActivity.this.finish();
                        Toast.makeText(MenuActivity.this,"You have logged out",Toast.LENGTH_SHORT).show();
                    }
                });
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                alert.setCancelable(false);
                alert.show();

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    //Go to Booking Activity
    public void btnBookUnit_onClick(View view) {
        Intent intent = new Intent(this,BookingActivity.class);
        startActivity(intent);
    }

    //Go to Booking List Activity
    public void btnBookings_onClick(View view) {
        Intent intent = new Intent(this,BooklistActivity.class);
        startActivity(intent);
    }

    //Go to Summary Activity
    public void btnSummary_onClick(View view) {
        Intent intent = new Intent(this,SummaryActivity.class);
        startActivity(intent);
    }
}
