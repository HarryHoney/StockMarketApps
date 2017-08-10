package com.example.nhatcuong1.app_chung_khoan.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.nhatcuong1.app_chung_khoan.Activity.StartAppActivity;
import com.example.nhatcuong1.app_chung_khoan.Fragment.BaseListViewForFragment.BaseAdapterHistory;
import com.example.nhatcuong1.app_chung_khoan.Model.HistoryStockModel;
import com.example.nhatcuong1.app_chung_khoan.OpenUrlToGetString;
import com.example.nhatcuong1.app_chung_khoan.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by nhatcuong1 on 11/17/15.
 */
public class HistoryFragment extends Fragment {
    private static final String TAG = "HistoryFragment";
    private static ArrayList<HistoryStockModel> historyStockModelArrayList = new ArrayList<>();
    private ListView listHistory;
    private EditText edt_history_search;
    private Button btn_history_search;
    private BaseAdapterHistory adapterHistory;
    public ArrayList<HistoryStockModel> searchStock = new ArrayList<>();

    public static ArrayList<HistoryStockModel> getHistoryStockModelArrayList() {
        return historyStockModelArrayList;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       // return super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.history_fragment, container, false);
        listHistory = (ListView)v.findViewById(R.id.list_history);
        edt_history_search = (EditText)v.findViewById(R.id.edt_history_search);
        //btn_history_search = (Button)v.findViewById(R.id.btn_history_search);
      //  btn_history_search.setOnClickListener(this);
        historyStockModelArrayList = new ArrayList<>();
        new DoInAddHistory().execute();
        edt_history_search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_UP){
                Log.i(TAG,"SU kien bat la "+(char)keyCode);
                    searchStock = new ArrayList<>();
                    String search = edt_history_search.getText().toString().trim().toLowerCase();
                    for( int i = 0; i <historyStockModelArrayList.size(); i++ ){
                        if(historyStockModelArrayList.get(i).getSID().toLowerCase().startsWith(search) == true){
                            searchStock.add(historyStockModelArrayList.get(i));
                        }
                    }
                    adapterHistory = new BaseAdapterHistory(getActivity().getApplicationContext(),searchStock);
                    listHistory.setAdapter(adapterHistory);
                    return false;
                }

                return false;
            }

        });
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG,"Chua co gi ca ?? "+historyStockModelArrayList.size());
        new DoInAddHistory().execute();
        Log.i(TAG,"Onresume.......So luong phan tu" +historyStockModelArrayList.size());
    }

    private class DoInAddHistory extends AsyncTask<String,String,String>{
        String url = "http://oopandroidnhom4.esy.es/returnhistory.php?uid="+ StartAppActivity.getUser().getId();
        OpenUrlToGetString openUrlToGetString;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //historyStockModelArrayList = new ArrayList<>();
            openUrlToGetString = new OpenUrlToGetString();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String result = openUrlToGetString.OpenHttpConnection(url);
                if (result.equals("null")){
                    return null;
                }
                else
                {
                    historyStockModelArrayList = new ArrayList<>();
                    JSONArray arr_js = new JSONArray(result);
                    Log.i(TAG,"TOng so arr history = "+arr_js.length());
                    for(int i = 0; i < arr_js.length(); i++){
                        JSONObject js = arr_js.getJSONObject(i);
                        HistoryStockModel historyStockModel = new HistoryStockModel();
                        historyStockModel.setUID(Integer.parseInt(js.getString("uid")));
                        historyStockModel.setVolume(Integer.parseInt(js.getString("volume")));
                        historyStockModel.setOldprice(Double.parseDouble(js.getString("oldprice")));
                        historyStockModel.setExchange(Double.parseDouble(js.getString("exchange")));
                        historyStockModel.setMethod(js.getString("method"));
                        historyStockModel.setPercentage(Double.parseDouble(js.getString("percentage")));
                        historyStockModel.setSID(js.getString("sid"));
                        historyStockModel.setTime(js.getString("time"));
                        historyStockModelArrayList.add(historyStockModel);
                    }
                    Log.i(TAG,"SO phan tu add vao model arr :"+historyStockModelArrayList.size());
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return "ok";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s == null){
                Toast.makeText(getActivity().getApplicationContext(),"S bi null",Toast.LENGTH_SHORT).show();
                return;
            }
            adapterHistory = new BaseAdapterHistory(getActivity().getApplicationContext(),historyStockModelArrayList);
            Log.i(TAG,"OnPost.......So luong phan tu" +historyStockModelArrayList.size());
            listHistory.setAdapter(adapterHistory);
            listHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
        }
    }

}
