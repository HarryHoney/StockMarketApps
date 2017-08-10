package com.example.nhatcuong1.app_chung_khoan.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.nhatcuong1.app_chung_khoan.Activity.DetailActivity;
import com.example.nhatcuong1.app_chung_khoan.Activity.StartAppActivity;
import com.example.nhatcuong1.app_chung_khoan.COMMON;
import com.example.nhatcuong1.app_chung_khoan.Fragment.BaseListViewForFragment.BaseAdapterStockMarket;
import com.example.nhatcuong1.app_chung_khoan.Model.ShareModel;
import com.example.nhatcuong1.app_chung_khoan.OpenUrlToGetString;
import com.example.nhatcuong1.app_chung_khoan.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * Created by nhatcuong1 on 11/17/15.
 */
public class StockMarketInfoFragment extends Fragment{
    private OpenUrlToGetString openUrlToGetString = new OpenUrlToGetString();
    private static final String TAG = "StockMarketInfoFragment";
    private ListView list_share;
    private EditText edt_find_share;
    private Button btn_Add, btn_Refresh;
    public static ArrayList<ShareModel> arr_Shares = new ArrayList<>();
    private BaseAdapterStockMarket adapterStockMarket;
    private Context context;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity().getApplicationContext();
    }

    public static ArrayList<ShareModel> getArr_Shares() {
        return arr_Shares;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(arr_Shares.size() == 0){
            Log.i(TAG,"Onresume.... 0 phan tu nhe" );
            return;
        }
        if(adapterStockMarket == null){
            Log.i(TAG,"Onresume.... adapter null" );
            return;
        }
        adapterStockMarket = new BaseAdapterStockMarket(getActivity().getApplication(),arr_Shares);
        list_share.setAdapter(adapterStockMarket);
        list_share.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity().getApplicationContext(), DetailActivity.class);
                i.putExtra("share", adapterStockMarket.getItem(position));
                startActivity(i);
                return false;
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.stock_market_fragment, container,false);
        list_share = (ListView) view.findViewById(R.id.list_share);

        btn_Add = (Button) view.findViewById(R.id.btn_Add);
        btn_Refresh = (Button) view.findViewById(R.id.btn_Refresh);
        edt_find_share = (EditText) view.findViewById(R.id.edt_id_find_share);
        btn_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Da vao add");
                if(edt_find_share.getText().toString().trim().equals("")){
                    Toast.makeText(getActivity().getApplicationContext(),"Please enter the name of company",Toast.LENGTH_SHORT).show();
                    return ;
                }
                if (edt_find_share.getText().toString().trim().contains(" ")){
                    Toast.makeText(getActivity().getApplicationContext(),"Please enter correct the name of company",Toast.LENGTH_SHORT).show();
                    return ;
                }
                for(int i = 0; i < arr_Shares.size(); i ++){
                   // Toast.makeText(getActivity().getApplicationContext(),"trong for",Toast.LENGTH_SHORT).show();
                    Log.i(TAG, arr_Shares.get(i).getId() + "===?" + edt_find_share.getText().toString().trim());
                    if(arr_Shares.get(i).getId().toString().equals(edt_find_share.getText().toString().trim().toUpperCase())){
                        Log.i(TAG,"Da vao IF");
                        Toast.makeText(getActivity().getApplicationContext(),"Share has been added",Toast.LENGTH_LONG).show();
                        return;
                    }
                }
                new DoInAddShare().execute();
            }
        });
        btn_Refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DoInGetInForStock().execute();
            }
        });
        new DoInGetInForStock().execute();


     //   new DoInGetInForStock().execute();
        return view;
    }
    public boolean readXML(String xmlc2) throws XmlPullParserException, IOException {
        // xmlc2 = "<game><cel>5</cel><val>2</val></game>";
        String str = "";
        try {
            //For String source
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(xmlc2));
            long x = (long) 1.1;
            Log.i(TAG,"Ket qua cua x "+x);
            xpp.next();
            int eventType = xpp.getEventType();
            ShareModel share = new ShareModel();
            while (xpp.getEventType()!=XmlPullParser.END_DOCUMENT) {

                if (xpp.getEventType()==XmlPullParser.START_TAG) {
                    if(xpp.getName().equals(COMMON.STATUS)){
                        if(!xpp.nextText().equals("SUCCESS")){
                            return false;
                        }
                    }
                    if(xpp.getName().equals(COMMON.MESSAGE)){
                        if (xpp.nextText().contains("No")){
                            return false;
                        }
                    }
                    if(xpp.getName().equals(COMMON.NAME)){
                        share.setName(xpp.nextText());
                    }
                    if (xpp.getName().equals(COMMON.ID)) {
                        share.setId(xpp.nextText());
                    }
                    if(xpp.getName().equals(COMMON.PRICE)){
                        //  Log.i(TAG,xpp.nextText());
                        share.setPrice(Double.parseDouble(xpp.nextText()));
                    }
                    if(xpp.getName().equals(COMMON.CHANGE)){
                        share.setChange(Double.parseDouble(xpp.nextText().toString().trim()));
                        //Log.i(TAG,xpp.nextText());
                    }
                    if(xpp.getName().equals(COMMON.VOLUME)){
                        share.setVolume(Double.parseDouble(xpp.nextText().toString().trim()));
                        // Log.i(TAG,xpp.nextText());
                    }
                    if(xpp.getName().equals(COMMON.TIME)){
                        share.setTime(xpp.nextText());
                    }
                    if(xpp.getName().equals(COMMON.HIGH)){
                        share.setHigh(Double.parseDouble(xpp.nextText().toString().trim()));
                        //  Log.i(TAG,xpp.nextText());
                    }
                    if(xpp.getName().equals(COMMON.LOW)){
                        share.setLow(Double.parseDouble(xpp.nextText().toString().trim()));
                        //Log.i(TAG,xpp.nextText());
                    }

                    if(xpp.getName().equals(COMMON.OPEN)){
                        share.setOpen(Double.parseDouble(xpp.nextText().toString().trim()));
                        //   Log.i(TAG,xpp.nextText());
                    }
                }
                xpp.next();
            }
            Log.i(TAG,"Da thoat while");
            arr_Shares.add(share);

        } catch (XmlPullParserException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    private class DoInGetInForStock extends AsyncTask<String,String,String> {
        private static final String TAG = "DoInGetInForStock";
        ArrayList<String> arr_CategoryShare = new ArrayList<>();
        String urlForIdStock = "http://oopandroidnhom4.esy.es/returncategory.php?id=" + StartAppActivity.getUser().getId();
        String url = "http://dev.markitondemand.com/MODApis/Api/v2/Quote?symbol=";
        @Override
        protected String doInBackground(String... params) {
            boolean check = false;
            String result = "";
            while (check == false) {
                arr_CategoryShare = new ArrayList<>();
                try {
                    result = openUrlToGetString.OpenHttpConnection(urlForIdStock);
                    JSONArray arr_js = new JSONArray(result);
                    Log.i(TAG, "Phan tu json " + arr_js.length());
                    for (int i = 0; i < arr_js.length(); i++) {
                        JSONObject js = arr_js.getJSONObject(i);
                        arr_CategoryShare.add(js.getString("SID"));
                        Log.i(TAG, js.getString("SID"));
                    }
                    Log.i(TAG, "So phan tu categoryShare " + arr_CategoryShare.size());
                    for (int i = 0; i < arr_CategoryShare.size(); i++) {
                        String rs = openUrlToGetString.OpenHttpConnection(url + arr_CategoryShare.get(i));
                        readXML(rs);
                    }
                    check = true;

                    //Log.i(TAG,"result : "+result);
                } catch (IOException e) {
                    e.printStackTrace();
                    check = false;
                } catch (JSONException e) {
                    e.printStackTrace();
                    check = false;
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                    check = false;
                }
            }

            return result;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
                Log.i(TAG, arr_Shares.size() + "");
                adapterStockMarket = new BaseAdapterStockMarket(getActivity().getApplicationContext(),arr_Shares);
                list_share.setAdapter(adapterStockMarket);
            list_share.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    Log.i(TAG,"VAo roi nhe.....long");
                    Intent i = new Intent(getActivity().getApplicationContext(), DetailActivity.class);
                    i.putExtra("share", adapterStockMarket.getItem(position));
                    startActivity(i);
                    return false;
                }
            });
            list_share.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Log.i(TAG,"Item thu "+position);
                }
            });
           // list_share.setOnItemClickListener();
            Log.i(TAG,"Xong het ca roi nhe");
        }
    }
        private class DoInAddShare extends AsyncTask<String,String,String>{
      //  String url ="http://dev.markitondemand.com/MODApis/Api/v2/Quote?symbol="+edt_find_share.getText().toString();
            String url = "http://stackoverflow.com/questions/2736389/how-to-pass-object-from-one-activity-to-another-in-android";
            String url1 = "http://dev.markitondemand.com/MODApis/Api/v2/Quote?symbol="+edt_find_share.getText().toString().trim();
            String urlQuantity = "http://oopandroidnhom4.esy.es/addcategory.php?uid=" + StartAppActivity.getUser().getId() + "&sid=" + edt_find_share.getText().toString();
        @Override
        protected String doInBackground(String... params) {
            String rs = "";
            String kq ="";
            boolean check = false;
            while (check == false) {
                try {
                    rs = openUrlToGetString.OpenHttpConnection(url1);
                    if(readXML(rs) == true){
                        openUrlToGetString.OpenHttpConnection(urlQuantity);
                        kq = "complete";
                    }
                    Log.i(TAG, "rs " + rs);
                    check = true;
                } catch (IOException e) {
                    e.printStackTrace();
                    check = false;
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                    check = false;
                }

            }
            return kq;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i(TAG, "Vao");
            adapterStockMarket = new BaseAdapterStockMarket(getActivity().getApplicationContext(),arr_Shares);
            list_share.setAdapter(adapterStockMarket);
            list_share.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent i = new Intent(getActivity().getApplicationContext(), DetailActivity.class);
                    i.putExtra("share", adapterStockMarket.getItem(position));
                    startActivity(i);
                    return false;
                }
            });
            if(s.equals("complete"))
            Toast.makeText(getActivity().getApplicationContext(),"Complete",Toast.LENGTH_SHORT).show();
            else Toast.makeText(getActivity().getApplicationContext(),"Error name company",Toast.LENGTH_SHORT).show();
        }

    }

}
