package edu.andrazpencelj.parkinginljubljana;

/**
 * Created by Andraž on 18.11.2013.
 *
 * lokacija parkirišča
 *
 * mLatitude - geografska širina postaje
 * mLongitude - geografska dolžina postaje
 *
 */
public class ParkingLotLocation {

    private double mLatitude;
    private double mLongitude;

    public ParkingLotLocation(){

    }

    public ParkingLotLocation(double latitude,double longitude){
        this.mLatitude = latitude;
        this.mLongitude = longitude;
    }

    public void setLatitude(double latitude){
        this.mLatitude = latitude;
    }

    public void setLongitude(double longitude){
        this.mLongitude = longitude;
    }

    public double getLatitude(){
        return this.mLatitude;
    }

    public double getLongitude(){
        return this.mLongitude;
    }

}
