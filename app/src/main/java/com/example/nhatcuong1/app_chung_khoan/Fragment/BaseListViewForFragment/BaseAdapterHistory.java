package com.example.nhatcuong1.app_chung_khoan.Fragment.BaseListViewForFragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;

import com.example.nhatcuong1.app_chung_khoan.Model.HistoryStockModel;
import com.example.nhatcuong1.app_chung_khoan.Model.ShareModel;
import com.example.nhatcuong1.app_chung_khoan.R;

import java.util.ArrayList;

/**
 * Created by nhatcuong1 on 11/22/15.
 */
public class BaseAdapterHistory extends BaseAdapter {
    private LayoutInflater inflater;
    private Context context;
    private ArrayList<HistoryStockModel> historyStockModelArrayList = new ArrayList<>();
    public BaseAdapterHistory(Context context, ArrayList<HistoryStockModel> historyStockModels){
        this.historyStockModelArrayList = historyStockModels;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return historyStockModelArrayList.size();
    }

    @Override
    public HistoryStockModel getItem(int position) {
        return historyStockModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder myViewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_history, parent, false);
            myViewHolder = new MyViewHolder(convertView);
            convertView.setTag(myViewHolder);
        } else {
            myViewHolder = (MyViewHolder) convertView.getTag();

        }
        HistoryStockModel historyStockModel = getItem(position);
        myViewHolder.edt_history_Exchange.setText(historyStockModel.getExchange()+"");
        myViewHolder.edt_history_Method.setText(historyStockModel.getMethod()+"");
        myViewHolder.edt_history_OldPrice.setText(historyStockModel.getOldprice()+"");
        myViewHolder.edt_history_SID.setText(historyStockModel.getSID()+"");
        myViewHolder.edt_history_Time.setText(historyStockModel.getTime()+"");
        myViewHolder.edt_history_Volume.setText(historyStockModel.getVolume()+"");
       // myViewHolder.edt_history_Method.setText(historyStockModel.getTime()+"");
        if(historyStockModel.getMethod().toString().contains("buying")){
            myViewHolder.edt_history_Percentage.setText("Not available");
        }else{
            myViewHolder.edt_history_Percentage.setText(historyStockModel.getPercentage()+"");
        }


        return convertView;

    }
    private class MyViewHolder{
        EditText edt_history_Time, edt_history_SID, edt_history_Percentage, edt_history_Exchange, edt_history_OldPrice, edt_history_Method, edt_history_Volume;
        public MyViewHolder(View v){
            initComponents(v);
        }
        private void initComponents(View v){
            edt_history_Exchange = (EditText)v.findViewById(R.id.edt_history_Exchange);
            edt_history_Method = (EditText)v.findViewById(R.id.edt_history_Method);
            edt_history_OldPrice = (EditText)v.findViewById(R.id.edt_history_OldPrice);
            edt_history_Percentage = (EditText)v.findViewById(R.id.edt_history_Percentage);
            edt_history_SID = (EditText)v.findViewById(R.id.edt_history_SID);
            edt_history_Time = (EditText)v.findViewById(R.id.edt_history_Time);
            edt_history_Volume = (EditText)v.findViewById(R.id.edt_history_Volume);
        }

    }
}
