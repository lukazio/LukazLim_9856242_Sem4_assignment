package com.example.hotelroombooking;


import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TabFragmentBooking extends Fragment {

    private Context mContext;
    private EditText mEtFloor,mEtUnit,mEtName;
    private TextView mTvBookingCheck;
    private Button mBtnCheckBooking,mBtnSubmitBooking;
    private BookingDataSource mDataSource;

    public TabFragmentBooking() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragment_booking, container, false);

        mContext = (Context)getContext();
        mEtFloor = (EditText)view.findViewById(R.id.etFloor);
        mEtUnit = (EditText)view.findViewById(R.id.etUnit);
        mEtName = (EditText)view.findViewById(R.id.etName);
        mTvBookingCheck = (TextView)view.findViewById(R.id.tvBookingCheck);
        mBtnCheckBooking = (Button)view.findViewById(R.id.btnCheckBooking);
        mBtnSubmitBooking = (Button)view.findViewById(R.id.btnSubmitBooking);
        mDataSource = new BookingDataSource(mContext);

        mEtFloor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTvBookingCheck.setText("");
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        mEtUnit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTvBookingCheck.setText("");
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mBtnCheckBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataSource.open();
                int floor,unit;

                if(mEtFloor.getText().toString().isEmpty() || mEtUnit.getText().toString().isEmpty()) {
                    Toast.makeText(mContext,"Please fill in both floor and unit fields!", Toast.LENGTH_SHORT).show();
                    return;
                }

                try{
                    floor = Integer.parseInt(mEtFloor.getText().toString());
                    unit = Integer.parseInt(mEtUnit.getText().toString());
                }
                catch(NumberFormatException e){
                    Toast.makeText(mContext,"Invalid input, only integers allowed!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(floor<1 || floor>10 || unit<1 || unit>4)
                    Toast.makeText(mContext,"Values must be within the accepted range shown above!", Toast.LENGTH_SHORT).show();
                else{
                    ArrayList<Booking> bookings = mDataSource.getAllBookings();

                    for(int i=0;i<bookings.size();i++){
                        //If a booking exists for requested unit
                        if(bookings.get(i).getFloor()==floor && bookings.get(i).getUnit()==unit){
                            mTvBookingCheck.setText("Not Available");
                            mTvBookingCheck.setTextColor(Color.RED);
                            return;
                        }
                    }
                    //If unit is vacant
                    mTvBookingCheck.setText("Available");
                    mTvBookingCheck.setTextColor(Color.GREEN);
                }

                mDataSource.close();
            }
        });

        mBtnSubmitBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataSource.open();
                final int floor,unit;
                final String owner;

                if(mEtFloor.getText().toString().isEmpty() || mEtUnit.getText().toString().isEmpty() || mEtName.getText().toString().trim().isEmpty()) {
                    Toast.makeText(mContext,"Please fill in all fields!", Toast.LENGTH_SHORT).show();
                    return;
                }

                try{
                    floor = Integer.parseInt(mEtFloor.getText().toString());
                    unit = Integer.parseInt(mEtUnit.getText().toString());
                    owner = mEtName.getText().toString().trim();
                }
                catch(NumberFormatException e){
                    Toast.makeText(mContext,"Invalid input, only integers allowed in floor and unit!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(floor<1 || floor>10 || unit<1 || unit>4)
                    Toast.makeText(mContext,"Floor and unit must be within the accepted range shown above!", Toast.LENGTH_SHORT).show();
                else if(owner.length()>30)
                    Toast.makeText(mContext,"Name is too long, please enter 30 characters or less!", Toast.LENGTH_SHORT).show();
                else{
                    ArrayList<Booking> bookings = mDataSource.getAllBookings();

                    for(int i=0;i<bookings.size();i++){
                        //If a booking exists for requested unit
                        if(bookings.get(i).getFloor()==floor && bookings.get(i).getUnit()==unit){
                            Toast.makeText(mContext,"Booking already exists for this unit, please enter choose another!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    //If unit is vacant
                    AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                    alert.setTitle("Add Booking");
                    alert.setMessage("Are you sure you want to book this unit?\n\nOwner: " + owner + "\nFloor " + floor + " Unit " + unit);

                    alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mDataSource.open();

                            int price = 420000+(69000*(floor-1));
                            mDataSource.createBooking(owner,0,floor,unit,price);
                            Toast.makeText(mContext,"Booking successfully created for " + owner + "!", Toast.LENGTH_SHORT).show();

                            mEtFloor.setText("");
                            mEtUnit.setText("");
                            mEtName.setText("");
                        }
                    });
                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    alert.setCancelable(false);
                    alert.show();
                }

                mDataSource.close();
            }
        });

        //Inflate the layout for this fragment
        return view;
    }

}
