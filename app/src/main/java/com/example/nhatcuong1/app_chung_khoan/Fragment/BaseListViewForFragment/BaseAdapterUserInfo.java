package com.example.nhatcuong1.app_chung_khoan.Fragment.BaseListViewForFragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;

import com.example.nhatcuong1.app_chung_khoan.Model.StasticShareModel;
import com.example.nhatcuong1.app_chung_khoan.R;

import java.util.ArrayList;

/**
 * Created by nhatcuong1 on 12/3/15.
 */
public class BaseAdapterUserInfo extends BaseAdapter {
    private ArrayList<StasticShareModel> stasticShareModels = new ArrayList<>();
    private Context context;
    private LayoutInflater inflater;
    public BaseAdapterUserInfo(Context context, ArrayList<StasticShareModel> stasticShareModels){
        this.stasticShareModels =stasticShareModels;
        this.context =context;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return stasticShareModels.size();
    }

    @Override
    public StasticShareModel getItem(int position) {
        return stasticShareModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder myViewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_user_info_stock, parent, false);
            myViewHolder = new MyViewHolder(convertView);
            convertView.setTag(myViewHolder);
        } else {
            myViewHolder = (MyViewHolder) convertView.getTag();

        }
        StasticShareModel stasticShareModel = getItem(position);
        myViewHolder.edt_stastic_averageprice.setText(stasticShareModel.getAveragePrice()+"$");
        myViewHolder.edt_stastic_currentprice.setText(stasticShareModel.getCurrentPrice()+"$");
        myViewHolder.edt_stastic_name.setText(stasticShareModel.getName()+"");
        myViewHolder.edt_stastic_percentagge.setText(stasticShareModel.getPercentage()+"%");
        myViewHolder.edt_stastic_volume.setText(stasticShareModel.getVolume()+"");
        return convertView;
    }

    private class MyViewHolder{
        EditText edt_stastic_name, edt_stastic_percentagge, edt_stastic_currentprice, edt_stastic_volume, edt_stastic_averageprice;
        public MyViewHolder(View view){
            edt_stastic_averageprice = (EditText)view.findViewById(R.id.edt_stastic_averageprice);
            edt_stastic_currentprice = (EditText)view.findViewById(R.id.edt_stastic_currentprice);
            edt_stastic_name = (EditText)view.findViewById(R.id.edt_stastic_name);
            edt_stastic_percentagge = (EditText)view.findViewById(R.id.edt_stastic_percentage);
            edt_stastic_volume = (EditText)view.findViewById(R.id.edt_stastic_volume);
        }
    }
}
