package edu.andrazpencelj.parkinginljubljana;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Andraž on 21.11.2013.
 */
public class ParkingLotArrayListAdapter extends ArrayAdapter<ParkingLot> {

    private ArrayList<ParkingLot> mList;
    private Context mContext;
    private int mResource;

    public ParkingLotArrayListAdapter(Context context, int resource, ArrayList<ParkingLot> list) {
        super(context, resource, list);
        this.mList = list;
        this.mContext = context;
        this.mResource = resource; //layout posameznega elementa seznama
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder viewHolder;
        ParkingLot parkingLot = getItem(position);
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mResource,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView)convertView.findViewById(R.id.parking_lot_list_name);
            viewHolder.open = (TextView)convertView.findViewById(R.id.parking_lot_list_open);
            viewHolder.image1 = (ImageView) convertView.findViewById(R.id.parking_lot_image1);
            viewHolder.image2 = (ImageView) convertView.findViewById(R.id.parking_lot_image2);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.name.setText(getItem(position).getName());
        if (getItem(position).getOpen() != null){
            viewHolder.open.setText(getItem(position).getOpen());
        }
        //prikazemo sliko ce parkirisce vsebuje mesta za invalide
        if (getItem(position).getDisabledPersonCapacity() == 0){
            viewHolder.image1.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }
}
