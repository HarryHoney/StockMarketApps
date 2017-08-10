package com.example.nhatcuong1.app_chung_khoan.Fragment.BaseListViewForFragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;

import com.example.nhatcuong1.app_chung_khoan.Model.ShareModel;
import com.example.nhatcuong1.app_chung_khoan.R;

import java.util.ArrayList;

/**
 * Created by nhatcuong1 on 11/18/15.
 */
public class BaseAdapterStockMarket extends BaseAdapter {
    private ArrayList<ShareModel> arr_Shares = new ArrayList<>();
    private Context context;
    private LayoutInflater layoutInflater;
    public BaseAdapterStockMarket(Context context, ArrayList<ShareModel> arr_Shares){
        this.context = context;
        this.arr_Shares = arr_Shares;
        layoutInflater = LayoutInflater.from(this.context);
    }
    @Override
    public int getCount() {
        return arr_Shares.size();
    }

    @Override
    public ShareModel getItem(int position) {

        return arr_Shares.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder myViewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.share_item, parent, false);
            myViewHolder = new MyViewHolder(convertView);
            convertView.setTag(myViewHolder);
        } else {
            myViewHolder = (MyViewHolder) convertView.getTag();

        }
        ShareModel share = arr_Shares.get(position);
        myViewHolder.edt_ShareID.setText(share.getId()+"");
        myViewHolder.edt_ShareName.setText(share.getName()+"");
        myViewHolder.edt_SharePrice.setText(share.getPrice()+"");
        return convertView;

    }

    private class MyViewHolder{
        EditText edt_ShareID, edt_ShareName, edt_SharePrice;
        public MyViewHolder(View view){
            edt_ShareID = (EditText) view.findViewById(R.id.edt_ShareID);
            edt_ShareName = (EditText) view.findViewById(R.id.edt_ShareName);
            edt_SharePrice = (EditText) view.findViewById(R.id.edt_SharePrice);
        }
    }
}
