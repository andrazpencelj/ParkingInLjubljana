package edu.andrazpencelj.parkinginljubljana;

import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class MainActivity extends ActionBarActivity {

    private ViewPager mViewPager;
    private ParkingLotPagerAdapter mParkingLotPagerAdapter;
    private RequestQueue mRequestQueue;
    private String mUrl = "http://opendata.si/promet/parkirisca/lpt/";
    private ProgressBar mProgressBar;
    private final String REQUEST_TAG = "PARKING_LOT_REQUEST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_in_ljubljana);
        initViewPager();
        mRequestQueue = Volley.newRequestQueue(this);
        mProgressBar = (ProgressBar)findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(View.VISIBLE);
        downloadParkingLotData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                TextView errorText = (TextView)findViewById(R.id.error_text);
                errorText.setVisibility(View.INVISIBLE);
                downloadParkingLotData();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPause(){
        super.onPause();
        //prekinemo morebitne prenose
        mRequestQueue.cancelAll(REQUEST_TAG);
    }

    private void initViewPager(){
        mViewPager = (ViewPager)findViewById(R.id.pager);
        List<Fragment> fragmentList = new Vector<Fragment>();
        fragmentList.add(new ParkingLotListFragment());
        fragmentList.add(new ParkingLotMapFragment());
        mParkingLotPagerAdapter = new ParkingLotPagerAdapter(getSupportFragmentManager(),fragmentList);
        mViewPager.setAdapter(mParkingLotPagerAdapter);
    }

    private void downloadParkingLotData(){
        //prenos podatkov s streznika (volley knjiznica)
        mProgressBar.setVisibility(View.VISIBLE);
        JsonObjectRequest jsonRequest = new JsonObjectRequest(
                Request.Method.GET,mUrl,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                ArrayList<ParkingLot> parkingLotData = parseJSON(response);
                //podatke posljemo fragmentom
                ParkingLotListFragment listFragment = (ParkingLotListFragment)mParkingLotPagerAdapter.getItem(0);
                listFragment.setParkingLotData(parkingLotData);
                ParkingLotMapFragment mapFragment = (ParkingLotMapFragment)mParkingLotPagerAdapter.getItem(1);
                mapFragment.setParkingLotData(parkingLotData);
                mViewPager.setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.INVISIBLE);
                //skirjemo obvestilo o napaki
                TextView errorText = (TextView)findViewById(R.id.error_text);
                errorText.setVisibility(View.INVISIBLE);
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mViewPager.setVisibility(View.INVISIBLE);
                if (error instanceof TimeoutError){
                    //timeout
                    TextView errorText = (TextView)findViewById(R.id.error_text);
                    errorText.setText(getString(R.string.main_activity_timeout_error));
                    errorText.setVisibility(View.VISIBLE);
                }
                else if (error instanceof NoConnectionError){
                    //ni povezave
                    TextView errorText = (TextView)findViewById(R.id.error_text);
                    errorText.setText(getString(R.string.main_activity_connection_error));
                    errorText.setVisibility(View.VISIBLE);
                }
                else{
                    TextView errorText = (TextView)findViewById(R.id.error_text);
                    errorText.setText(getString(R.string.main_activity_error));
                    errorText.setVisibility(View.VISIBLE);
                }
                mProgressBar.setVisibility(View.INVISIBLE);
            }
        }
        );
        jsonRequest.setTag(REQUEST_TAG);
        mRequestQueue.add(jsonRequest);
    }


    private ArrayList<ParkingLot> parseJSON(JSONObject jsonObject){
        //pretvorba prenesenih podatkov
        ParkingLot parkingLot;
        ArrayList<ParkingLot> parkingLotData = new ArrayList<ParkingLot>();
        try{
            JSONArray allParkingLots = jsonObject.getJSONArray("Parkirisca");
            for (int i=0;i<allParkingLots.length();i++){
                parkingLot = parseJSONObject(allParkingLots.getJSONObject(i));
                if (parkingLot != null){
                    parkingLotData.add(parkingLot);
                }
            }
        }
        catch (Exception e){
            //JSON napaka
        }
        return  parkingLotData;
    }

    private ParkingLot parseJSONObject(JSONObject jsonObject){
        ParkingLot parkingLot = null;
        try{
            if (jsonObject.has("KoordinataX_wgs")&&(jsonObject.has("KoordinataY_wgs"))){
                parkingLot = new ParkingLot();
                parkingLot.setId(Integer.parseInt(jsonObject.getString("ID_Parkirisca")));
                parkingLot.setName(jsonObject.getString("Ime"));
                parkingLot.setCapacity(Integer.parseInt(jsonObject.getString("St_mest")));
                ParkingLotLocation location = new ParkingLotLocation();
                location.setLongitude(Double.parseDouble(jsonObject.getString("KoordinataX_wgs")));
                location.setLatitude(Double.parseDouble(jsonObject.getString("KoordinataY_wgs")));
                parkingLot.setLocation(location);
                parkingLot.setPrice(jsonObject.getString("Opis")); //vsebuje podatke o cenah
                if (jsonObject.getString("Invalidi_St_mest") == "null"){
                    parkingLot.setDisabledPersonCapacity(0);
                }
                else{
                    parkingLot.setDisabledPersonCapacity(Integer.parseInt(jsonObject.getString("Invalidi_St_mest")));
                }

                if (jsonObject.getString("U_delovnik") != "null"){
                    parkingLot.setOpen(jsonObject.getString("U_delovnik"));
                }
                else if (jsonObject.getString("U_splosno") != "null"){
                    parkingLot.setOpen(jsonObject.getString("U_splosno"));
                }
                else{
                    parkingLot.setOpen(null);
                }

                if (jsonObject.has("zasedenost")){
                    JSONObject zasedenostJSON = jsonObject.getJSONObject("zasedenost");
                    parkingLot.setOccupancy(Integer.parseInt(zasedenostJSON.getString("P_kratkotrajniki")));
                }

            }
        }
        catch (Exception e){
            //JSON napaka
            Log.d("MESSAGE",e.toString());

        }
        return parkingLot;
    }
}
