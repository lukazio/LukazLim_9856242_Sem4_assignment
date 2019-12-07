package com.example.hotelroombooking;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class BookingListAdapter extends RecyclerView.Adapter<BookingListAdapter.BookingViewHolder> {

    private LayoutInflater mInflater;
    private ArrayList<Booking> mBookingList;
    private Context mContext;

    public class BookingViewHolder extends RecyclerView.ViewHolder{

        private TextView mName;
        private TextView mUnitNo;
        private TextView mPrice;
        private CheckBox mPaid;
        private Button mDelete;
        final BookingListAdapter mAdapter;
        private BookingDataSource mDataSource;

        public BookingViewHolder(@NonNull View itemView, BookingListAdapter adapter) {
            super(itemView);

            mName = (TextView)itemView.findViewById(R.id.list_tvName);
            mUnitNo = (TextView)itemView.findViewById(R.id.list_tvUnitNo);
            mPrice = (TextView)itemView.findViewById(R.id.list_tvPrice);
            mPaid = (CheckBox)itemView.findViewById(R.id.list_cbPaid);
            mDelete = (Button)itemView.findViewById(R.id.list_btnRemove);
            mAdapter = adapter;
            mDataSource = new BookingDataSource(mContext);
        }
    }

    public BookingListAdapter(Context context, ArrayList<Booking> bookingList){
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mBookingList = bookingList;
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.list_item_unit, viewGroup,false);
        return new BookingViewHolder(view,this);
    }

    @Override
    public void onBindViewHolder(@NonNull final BookingViewHolder bookingViewHolder, final int i) {
        final Booking mCurrent = mBookingList.get(i);
        bookingViewHolder.mName.setText(mCurrent.getOwner());
        bookingViewHolder.mUnitNo.setText("Floor " + mCurrent.getFloor() + " Unit " + mCurrent.getUnit());
        bookingViewHolder.mPrice.setText("RM " + mCurrent.getPrice());

        if(mCurrent.isPaid() == 1){
            bookingViewHolder.mPaid.setChecked(true);
            bookingViewHolder.mPaid.setEnabled(false);
            bookingViewHolder.mDelete.setEnabled(false);

            bookingViewHolder.mPaid.setAlpha(0.5f);
            bookingViewHolder.mDelete.setAlpha(0.5f);
        }
        else{
            bookingViewHolder.mPaid.setChecked(false);
            bookingViewHolder.mPaid.setEnabled(true);
            bookingViewHolder.mDelete.setEnabled(true);

            bookingViewHolder.mPaid.setAlpha(1.0f);
            bookingViewHolder.mDelete.setAlpha(1.0f);
        }

        //On click listener for Paid checkbox
        bookingViewHolder.mPaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                alert.setTitle("Payment Confirmation");
                alert.setMessage("Are you sure you want to mark this booking as paid?");

                //Mark selected booking as paid
                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        bookingViewHolder.mDataSource.open();
                        bookingViewHolder.mDataSource.payBooking(mCurrent.getId());
                        bookingViewHolder.mDataSource.close();

                        bookingViewHolder.mPaid.setEnabled(false);
                        bookingViewHolder.mDelete.setEnabled(false);
                        bookingViewHolder.mPaid.setAlpha(0.5f);
                        bookingViewHolder.mDelete.setAlpha(0.5f);
                    }
                });
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        bookingViewHolder.mPaid.setChecked(false);
                    }
                });
                alert.setCancelable(false);
                alert.show();
            }
        });
        //On click listener for Delete button
        bookingViewHolder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                alert.setTitle("Delete Booking");
                alert.setMessage("Are you sure you want to delete this booking?");

                //Delete selected booking
                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        bookingViewHolder.mDataSource.open();
                        bookingViewHolder.mDataSource.deleteBooking(mCurrent.getId());
                        bookingViewHolder.mDataSource.close();

                        mBookingList.remove(mCurrent);
                        notifyDataSetChanged();
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
        });
    }

    @Override
    public int getItemCount() {
        return mBookingList.size();
    }

}
