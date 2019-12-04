package com.example.hotelroombooking;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.ArrayList;

public class SummaryActivity extends AppCompatActivity {

    private TextView mTvUnitsBooked,mTvUnpaid,mTvPaid,mTvEarnings,mTvPotentialGain;
    private BookingDataSource mDataSource = new BookingDataSource(this);
    private ArrayList<Booking> mBookingList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.title_summary);
        setContentView(R.layout.activity_summary);

        mDataSource.open();

        mTvUnitsBooked = (TextView)findViewById(R.id.tvUnitsBooked);
        mTvUnpaid = (TextView)findViewById(R.id.tvUnpaid);
        mTvPaid = (TextView)findViewById(R.id.tvPaid);
        mTvEarnings = (TextView)findViewById(R.id.tvEarnings);
        mTvPotentialGain = (TextView)findViewById(R.id.tvPotentialGain);
        mBookingList = mDataSource.getAllBookings();

        mTvUnitsBooked.setText(mBookingList.size() + " units (40 max)");

        int unpaid=0,paid=0,potential=0,earnings=0;
        for(int i=0;i<mBookingList.size();i++){
            switch(mBookingList.get(i).isPaid()){
                case 0:
                    unpaid++;
                    potential += mBookingList.get(i).getPrice();
                    break;
                case 1:
                    paid++;
                    earnings += mBookingList.get(i).getPrice();
                    break;
            }
        }
        mTvUnpaid.setText(unpaid + " units");
        mTvPaid.setText(paid + " units");
        mTvPotentialGain.setText("RM " + potential);
        mTvEarnings.setText("RM " + earnings);

        mDataSource.close();
    }
}
