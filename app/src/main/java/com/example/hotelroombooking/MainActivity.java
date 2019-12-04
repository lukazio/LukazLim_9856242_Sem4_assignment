package com.example.hotelroombooking;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText mEtLoginUsername,mEtLoginPassword;
    SharedPreferences mSP;
    public static final String ACCOUNT_PREF = "UserAccountPrefs";
    public static final String LOGIN = "loginKey";
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.title_managerlogin);
        setContentView(R.layout.activity_main);

        mEtLoginUsername = (EditText)findViewById(R.id.etLoginUsername);
        mEtLoginPassword = (EditText)findViewById(R.id.etLoginPassword);

        mSP = getSharedPreferences(ACCOUNT_PREF, Context.MODE_PRIVATE);

        if(mSP.contains(LOGIN)){
            Intent intent = new Intent(this,MenuActivity.class);
            startActivity(intent);
            MainActivity.this.finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu=menu;
        getMenuInflater().inflate(R.menu.app_menu_login,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_info:
                Intent helpIntent = new Intent(this,HelpActivity.class);
                startActivity(helpIntent);
                break;
            case R.id.menu_about:
                Intent aboutIntent = new Intent(this,AboutActivity.class);
                startActivity(aboutIntent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void btnLogin_onClick(View view) {
        if(mEtLoginUsername.getText().toString().contentEquals("admin") && mEtLoginPassword.getText().toString().contentEquals("pwd123456")){
            SharedPreferences.Editor editor = mSP.edit();
            editor.putString(LOGIN,mEtLoginUsername.getText().toString());
            editor.apply();

            Toast.makeText(this,"Login successful",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,MenuActivity.class);
            startActivity(intent);
            MainActivity.this.finish();
        }
        else{
            Toast.makeText(this,"Please enter a valid admin username and password!",Toast.LENGTH_SHORT).show();
        }
    }
}
