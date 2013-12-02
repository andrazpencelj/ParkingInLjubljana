package edu.andrazpencelj.parkinginljubljana;

import android.util.Log;

/**
 * Created by Andraž on 18.11.2013.
 *
 * parkirišče
 *
 * mId - id parkirišča
 * mName - ime parkirišča
 * mCapacity - kapaciteta parkirišča
 * mDisabledPersonCapacity - število mest namenjenih invalidom
 *
 * mLocation - lokacija parkirišča
 * mOccupancy - zasedenost parkirišča
 */
public class ParkingLot {

    private int mId;
    private String mName;
    private int mCapacity;
    private int mOccupancy;
    private int mDisabledPersonCapacity;
    private ParkingLotLocation mLocation;
    private String mOpen;
    private String mPrice;


    public ParkingLot(){
    }

    public ParkingLot(int id, String name, int capacity, int disabledPersonCapacity, String open, String price){
        this.mId = id;
        this.mCapacity = capacity;
        this.mDisabledPersonCapacity = disabledPersonCapacity;
        this.mName = name;
        this.mOpen = open;
        this.mPrice = price;
    }

    public ParkingLot(int id, String name, int capacity, int disabledPersonCapacity, String open, String price, ParkingLotLocation location, int occupancy){
        this.mId = id;
        this.mCapacity = capacity;
        this.mDisabledPersonCapacity = disabledPersonCapacity;
        this.mName = name;
        this.mOpen = open;
        this.mPrice = price;
        this.mLocation = location;
        this.mOccupancy = occupancy;
    }

    public void setId(int id){
        this.mId = id;
    }

    public void setName(String name){
        this.mName = name;
    }

    public void setCapacity(int capacity){
        this.mCapacity = capacity;
    }

    public void setDisabledPersonCapacity(int capacity){
        this.mDisabledPersonCapacity = capacity;
    }

    public void setOpen(String open){
        this.mOpen = open;
    }

    public void setPrice(String price){
        this.mPrice = price;
    }

    public void setLocation(ParkingLotLocation location){
        this.mLocation = location;
    }

    public void setOccupancy(int occupancy){
        this.mOccupancy = occupancy;
    }

    public int getId(){
        return this.mId;
    }

    public String getName(){
        return this.mName;
    }

    public int getCapacity(){
        return this.mCapacity;
    }

    public int getDisabledPersonCapacity(){
        return this.mDisabledPersonCapacity;
    }

    public String getOpen(){
        return this.mOpen;
    }

    public String getPrice(){
        return this.mPrice;
    }

    public ParkingLotLocation getLocation(){
        return this.mLocation;
    }

    public int getOccupancy(){
        return this.mOccupancy;
    }

    public double getLatitude(){
        return this.mLocation.getLatitude();
    }

    public double getLongitude(){
        return this.mLocation.getLongitude();
    }

}
