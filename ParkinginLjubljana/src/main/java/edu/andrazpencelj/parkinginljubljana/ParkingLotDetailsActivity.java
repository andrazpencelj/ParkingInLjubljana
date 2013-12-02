package edu.andrazpencelj.parkinginljubljana;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.toolbox.Volley;
import com.google.android.gms.internal.ex;

/**
 * Created by Andraž on 22.11.2013.
 */
public class ParkingLotDetailsActivity extends ActionBarActivity {

    private ParkingLot mParkingLot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_parking_in_ljubljana_details);
        setData(getIntent().getExtras());
        setDataOnView();
    }

    private void setData(Bundle extras){
        mParkingLot = new ParkingLot();
        mParkingLot.setName(extras.getString("name"));
        mParkingLot.setOpen(extras.getString("open"));
        mParkingLot.setCapacity(Integer.parseInt(extras.getString("capacity")));
        mParkingLot.setOccupancy(Integer.parseInt(extras.getString("occupancy")));
        mParkingLot.setDisabledPersonCapacity(Integer.parseInt(extras.getString("disabled")));
        mParkingLot.setPrice(extras.getString("price"));
    }

    private void setDataOnView(){
        TextView textName = (TextView)findViewById(R.id.activity_details_name);
        textName.setText(mParkingLot.getName());
        TextView textOpen = (TextView)findViewById(R.id.activity_details_open);
        textOpen.setText(mParkingLot.getOpen());
        TextView capacityText = (TextView)findViewById(R.id.activity_details_capacity);
        TextView capacityTitle = (TextView)findViewById(R.id.activity_details_parking_lots);
        if ( mParkingLot.getOccupancy() <= mParkingLot.getCapacity()){
            int free = mParkingLot.getCapacity() - mParkingLot.getOccupancy();
            capacityTitle.setText(getString(R.string.details_occupacy));
            capacityText.setText(""+free);
        }
        else{
            capacityTitle.setText(getString(R.string.details_all));
            capacityText.setText(""+mParkingLot.getCapacity());
        }
        TextView disabledText = (TextView)findViewById(R.id.activity_details_disabled);
        disabledText.setText(""+mParkingLot.getDisabledPersonCapacity());
        TextView priceText = (TextView)findViewById(R.id.activity_details_price);
        priceText.setText(mParkingLot.getPrice());

    }

}
