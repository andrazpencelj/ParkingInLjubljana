package edu.andrazpencelj.parkinginljubljana;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by Andraž on 20.11.2013.
 */
public class ParkingLotMapFragment extends Fragment implements GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener, View.OnClickListener {

    private ArrayList<ParkingLot> mParkingLotData;
    private GoogleMap mGoogleMap;
    private View mInfoWindowView;
    private ViewHolder mViewHolder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_parking_lot_map,container,false);
        mInfoWindowView = view.findViewById(R.id.map_detail);
        mViewHolder = createViewHolder(mInfoWindowView);
        return view;
    }

    @Override
    public void onStart(){
        super.onStart();
        if (mGoogleMap == null){
            mGoogleMap = ((SupportMapFragment)getActivity().getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
            //postavimo pozicijo na ljubljano
            LatLng ljubljana = new LatLng(46.056451,14.508070);
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ljubljana, 12));
            //nastavimo tip zemljevida
            mGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            //skrijemo view nad zemljevidom
            mInfoWindowView.setVisibility(View.INVISIBLE);
            //dodamo poslusalca za klik na karto
            mGoogleMap.setOnMapClickListener(this);
            //dodamo poslusalca za view na katerem so podatki o parkiriscu
            mInfoWindowView.setOnClickListener(this);
        }
    }

    private ViewHolder createViewHolder(View view){
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.name = (TextView)view.findViewById(R.id.map_detail_name);
        viewHolder.open = (TextView)view.findViewById(R.id.map_detail_open);
        return  viewHolder;
    }

    public void setParkingLotData(ArrayList<ParkingLot> parkingLotData){
        this.mParkingLotData = parkingLotData;
        addMarkersToMap();
    }

    private void addMarkersToMap(){
        for (ParkingLot parkingLot : mParkingLotData){
            mGoogleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(parkingLot.getLatitude(),parkingLot.getLongitude()))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)))
                    .setSnippet(parkingLot.getName()+"&"+parkingLot.getOpen());
            mGoogleMap.setOnMarkerClickListener(this);
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        showMarkerDetail(marker.getSnippet());
        return false;
    }

    @Override
    public void onMapClick(LatLng latLng) {
        hideMarkerDetail();
    }

    private void showMarkerDetail(String markerSnippet){
        String [] markerData = markerSnippet.split("&");
        mViewHolder.name.setText(markerData[0]);
        mViewHolder.open.setText(markerData[1]);
        mInfoWindowView.setVisibility(View.VISIBLE);
    }

    private void hideMarkerDetail(){
        mInfoWindowView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        mInfoWindowView.setVisibility(View.INVISIBLE);
    }
}
