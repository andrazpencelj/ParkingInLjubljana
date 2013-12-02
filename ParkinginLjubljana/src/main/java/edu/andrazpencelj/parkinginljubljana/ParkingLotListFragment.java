package edu.andrazpencelj.parkinginljubljana;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Andraž on 20.11.2013.
 */
public class ParkingLotListFragment extends ListFragment {

    private ArrayList<ParkingLot> mParkingLotData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_parking_lot_list,container,false);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        Intent detailsIntent = new Intent(getActivity(),ParkingLotDetailsActivity.class);
        detailsIntent.putExtra("name",mParkingLotData.get((int)id).getName());
        detailsIntent.putExtra("open",mParkingLotData.get((int)id).getOpen());
        detailsIntent.putExtra("capacity",""+mParkingLotData.get((int)id).getCapacity());
        detailsIntent.putExtra("occupancy",""+mParkingLotData.get((int)id).getOccupancy());
        detailsIntent.putExtra("disabled",""+mParkingLotData.get((int)id).getDisabledPersonCapacity());
        detailsIntent.putExtra("price",mParkingLotData.get((int)id).getPrice());
        startActivity(detailsIntent);
    }

    public void setParkingLotData(ArrayList<ParkingLot> parkingLotData){
        this.mParkingLotData = parkingLotData;
        createList();
    }

    private void createList(){
        //ustvarimo seznam
        ParkingLotArrayListAdapter listAdapter = new ParkingLotArrayListAdapter(getActivity(),R.layout.parking_lot_list_item,mParkingLotData);
        ListView list = (ListView)getActivity().findViewById(android.R.id.list);
        list.setAdapter(listAdapter);
    }


}
