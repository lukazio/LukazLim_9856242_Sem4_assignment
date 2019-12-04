package com.example.hotelroombooking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

public class BooklistActivity extends AppCompatActivity {

    private BookingDataSource mDataSource = new BookingDataSource(this);
    private ArrayList<Booking> mBookingList = new ArrayList<>();
    private BookingListAdapter mAdapter;
    private RecyclerView mRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.title_booklist);
        setContentView(R.layout.activity_booklist);

        mDataSource.open();
        mRV = (RecyclerView)findViewById(R.id.rvBookingList);

        mBookingList = mDataSource.getAllBookings();

        mAdapter = new BookingListAdapter(this, mBookingList);
        mRV.setAdapter(mAdapter);
        mRV.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        mDataSource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mDataSource.close();
        super.onPause();
    }

    public void btnShowPaid_onClick(View view) {
        setTitle(R.string.title_booklist_paid);

        mBookingList = mDataSource.getAllBookings();
        ArrayList<Booking> mPaidBookings = new ArrayList<>();

        for(int i=0;i<mBookingList.size();i++){
            if(mBookingList.get(i).isPaid()==1)
                mPaidBookings.add(mBookingList.get(i));
        }

        mAdapter = new BookingListAdapter(this, mPaidBookings);
        mRV.setAdapter(mAdapter);
        mRV.setLayoutManager(new LinearLayoutManager(this));
    }

    public void btnShowUnpaid_onClick(View view) {
        setTitle(R.string.title_booklist_unpaid);

        mBookingList = mDataSource.getAllBookings();
        ArrayList<Booking> mUnpaidBookings = new ArrayList<>();

        for(int i=0;i<mBookingList.size();i++){
            if(mBookingList.get(i).isPaid()==0)
                mUnpaidBookings.add(mBookingList.get(i));
        }

        mAdapter = new BookingListAdapter(this, mUnpaidBookings);
        mRV.setAdapter(mAdapter);
        mRV.setLayoutManager(new LinearLayoutManager(this));
    }
}
