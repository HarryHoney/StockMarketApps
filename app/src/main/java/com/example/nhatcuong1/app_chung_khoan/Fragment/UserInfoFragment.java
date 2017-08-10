package com.example.nhatcuong1.app_chung_khoan.Fragment;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.nhatcuong1.app_chung_khoan.Activity.StartAppActivity;
import com.example.nhatcuong1.app_chung_khoan.Fragment.BaseListViewForFragment.BaseAdapterUserInfo;
import com.example.nhatcuong1.app_chung_khoan.Model.ShareModel;
import com.example.nhatcuong1.app_chung_khoan.Model.StasticShareModel;
import com.example.nhatcuong1.app_chung_khoan.Model.UserModel;
import com.example.nhatcuong1.app_chung_khoan.OpenUrlToGetString;
import com.example.nhatcuong1.app_chung_khoan.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.nhatcuong1.app_chung_khoan.R;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by nhatcuong1 on 11/17/15.
 */
public class UserInfoFragment extends Fragment implements View.OnClickListener{
    private EditText edt_userinfo_username, edt_userinfo_money, edt_userinfo_updatemoney;
    private Button btn_userinfo_saveinfo, btn_user_info_insertmoney, btn_user_info_drawmoney;
    private static final String TAG = "UserInfoFragment";
     //ArrayList<StasticShareModel> stasticShareModels;
    private ListView list_user_stock;
    private int total = 0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.user_info_fragment, container,false);
        btn_userinfo_saveinfo = (Button)v.findViewById(R.id.btn_userinfo_saveinfo);
        btn_userinfo_saveinfo.setOnClickListener(this);
        btn_user_info_drawmoney = (Button)v.findViewById(R.id.btn_drawmoney);
        btn_user_info_drawmoney.setOnClickListener(this);
        btn_user_info_insertmoney = (Button)v.findViewById(R.id.btn_insertmoney);
        btn_user_info_insertmoney.setOnClickListener(this);
        edt_userinfo_updatemoney = (EditText)v.findViewById(R.id.edt_userinfo_moneyupdate);
        edt_userinfo_money = (EditText)v.findViewById(R.id.edt_userinfo_money);
        edt_userinfo_username = (EditText)v.findViewById(R.id.edt_userinfo_username);
        edt_userinfo_username.setText(StartAppActivity.getUser().getUsername()+"");
        edt_userinfo_money.setText(StartAppActivity.getUser().getMoney()+"");
        list_user_stock = (ListView)v.findViewById(R.id.list_info);
        total = StockMarketInfoFragment.arr_Shares.size();
       // updateListView();
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        updateListView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            default: break;
            case R.id.btn_userinfo_saveinfo:
                Log.i(TAG, "vao roi btn save");
                new DoInSaveFile().execute();
                break;
            case R.id.btn_drawmoney:
                if(Double.parseDouble(edt_userinfo_money.getText().toString().trim()) < Double.parseDouble(edt_userinfo_updatemoney.getText().toString().trim())){
                    Toast.makeText(getActivity().getApplicationContext(),"Not enough money to draw",Toast.LENGTH_SHORT).show();
                    return;
                }
                String urlUpdateMoney = "http://oopandroidnhom4.esy.es/updatemoney.php?money=-"+edt_userinfo_updatemoney.getText()+"&uid="+StartAppActivity.getUser().getId();
                new MoneyExecute().execute(urlUpdateMoney,"draw");
                break;
            case R.id.btn_insertmoney:
                String urlUpdateMoney1 = "http://oopandroidnhom4.esy.es/updatemoney.php?money="+edt_userinfo_updatemoney.getText()+"&uid="+StartAppActivity.getUser().getId();
                new MoneyExecute().execute(urlUpdateMoney1,"insert");
                break;
        }
    }
    private void updateListView(){
        Log.i(TAG,"da vao list info roi nhe");
     ArrayList<StasticShareModel> stasticShareModels = new ArrayList<>();
     for(int i = 0; i < total; i++){
         StasticShareModel stasticShareModel = new StasticShareModel();
         stasticShareModel.setVolume(0);
         stasticShareModel.setPercentage(0d);
         stasticShareModel.setAveragePrice(0d);
         stasticShareModel.setCurrentPrice(0d);
         stasticShareModel.setTotalVolume(0);
         int size = HistoryFragment.getHistoryStockModelArrayList().size();
         Log.i(TAG,size + "");
         for(int u = 0;u < size; u++ ){
             if(StockMarketInfoFragment.getArr_Shares().get(i).getId().equals(HistoryFragment.getHistoryStockModelArrayList().get(u).getSID()) == true){
                 Log.i(TAG,"VAo true roi ma");
                 stasticShareModel.setName(StockMarketInfoFragment.getArr_Shares().get(i).getName());
                 stasticShareModel.setCurrentPrice(StockMarketInfoFragment.getArr_Shares().get(i).getPrice());
                 if(HistoryFragment.getHistoryStockModelArrayList().get(u).getMethod().equals("buying")) {
                    // Log.i(TAG, "buying cua " + stasticShareModel.getName());

                   //  stasticShareModel.setAveragePrice((stasticShareModel.getAveragePrice() * stasticShareModel.getVolume()+HistoryFragment.getHistoryStockModelArrayList().get(u).getVolume() * HistoryFragment.getHistoryStockModelArrayList().get(u).getOldprice())/(stasticShareModel.getVolume() + HistoryFragment.getHistoryStockModelArrayList().get(u).getOldprice()));


                     stasticShareModel.setVolume(stasticShareModel.getVolume() + HistoryFragment.getHistoryStockModelArrayList().get(u).getVolume());

                     stasticShareModel.setAveragePrice((stasticShareModel.getAveragePrice() * stasticShareModel.getTotalVolume() + HistoryFragment.getHistoryStockModelArrayList().get(u).getOldprice() * HistoryFragment.getHistoryStockModelArrayList().get(u).getVolume()) / (stasticShareModel.getTotalVolume() + HistoryFragment.getHistoryStockModelArrayList().get(u).getVolume()));
                     stasticShareModel.setTotalVolume(stasticShareModel.getTotalVolume() + HistoryFragment.getHistoryStockModelArrayList().get(u).getVolume());
                 }else{
                   //  stasticShareModel.setTotalVolume(stasticShareModel.getTotalVolume() + HistoryFragment.getHistoryStockModelArrayList().get(u).getVolume());
                     stasticShareModel.setVolume(stasticShareModel.getVolume() - HistoryFragment.getHistoryStockModelArrayList().get(u).getVolume());

                 }

                 }
         }
         if(stasticShareModel.getVolume() !=0 ){
             stasticShareModel.setPercentage((-stasticShareModel.getAveragePrice() + stasticShareModel.getCurrentPrice()) / stasticShareModel.getCurrentPrice() * 100);
             stasticShareModel.setAveragePrice(((double) Math.round(stasticShareModel.getAveragePrice())));
             stasticShareModel.setPercentage(((double) Math.round(stasticShareModel.getPercentage() * 100)) / 100);
             stasticShareModels.add(stasticShareModel);
             //Log.i(TAG,"gia tri percentage cua"+stasticShareModel.getName()+" la "+stasticShareModel.getPercentage());
         }
     }
        Log.i(TAG,"So phan tu van dau stastic"+stasticShareModels.size());

//        int sizeOfArr = stasticShareModels.size()/2;
        String name = stasticShareModels.get(0).getName();
        int dem = 0;
        for(int v = 0 ; v < stasticShareModels.size(); v++){
            if(stasticShareModels.get(v).getName().equals(name)){
                dem ++;
            }
        }
        ArrayList<StasticShareModel> stas = new ArrayList<>();
        for (int a =0; a<stasticShareModels.size()/dem; a++){
            stas.add(stasticShareModels.get(a));
        }
        Log.i(TAG,"so phan tu stasticShare model "+stasticShareModels.size()+"dem == "+dem);
        BaseAdapterUserInfo baseAdapterUserInfo = new BaseAdapterUserInfo(getActivity().getApplication(),stas);
        list_user_stock.setAdapter(baseAdapterUserInfo);


    }
    @Override
    public void onResume() {
        super.onResume();
        if(HistoryFragment.getHistoryStockModelArrayList().size() == 0){
            return;
        }
        Log.i(TAG,"Da vao on resume nhaaaaaaaaaaaaaaa");
        edt_userinfo_username.setText(StartAppActivity.getUser().getUsername() + "");
        edt_userinfo_money.setText(((double)Math.round(StartAppActivity.getUser().getMoney()*100)/100)+"");
        updateListView();
    }



    private class MoneyExecute extends AsyncTask<String,String,String>{
       // String urlUpdateMoney ;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {
           // Double money = Double.parseDouble(params[0]);
            OpenUrlToGetString openUrlToGetString = new OpenUrlToGetString();
            try {
                openUrlToGetString.OpenHttpConnection(params[0]);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            if(params[1].contains("insert")){
                return "insert";
            }else{
                return "draw";
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            UserModel user = new UserModel();
            user = StartAppActivity.getUser();
            if(s == null){
                return;
            }
            else if(s.equals("insert")){
                user.setMoney(user.getMoney()+Double.parseDouble(edt_userinfo_updatemoney.getText().toString().trim()));
                StartAppActivity.setUser(user);
                edt_userinfo_money.setText(((double) Math.round(user.getMoney()*100)/100)+"");
                Toast.makeText(getActivity().getApplicationContext(),"Nap thanh cong "+edt_userinfo_updatemoney.getText().toString()+" vao tai khoan",Toast.LENGTH_SHORT).show();
            }else{
                user.setMoney(user.getMoney()-Double.parseDouble(edt_userinfo_updatemoney.getText().toString().trim()));
                StartAppActivity.setUser(user);
                edt_userinfo_money.setText(((double) Math.round(user.getMoney()*100)/100)+"");
                Toast.makeText(getActivity().getApplicationContext(),"Rut thanh cong "+edt_userinfo_updatemoney.getText().toString()+" tu tai khoan",Toast.LENGTH_SHORT).show();
            }
        }
    }



    private class DoInSaveFile extends AsyncTask<String,String,String>{
        String url = "http://oopandroidnhom4.esy.es/returnhistory.php?uid="+ StartAppActivity.getUser().getId();
        OpenUrlToGetString openUrlToGetString = new OpenUrlToGetString();
        String nameFile = "user_config.txt";
        File pathSave = Environment.getExternalStorageDirectory();
        @Override
        protected String doInBackground(String... params) {
            try {
                String result = openUrlToGetString.OpenHttpConnection(url);
                return result;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i(TAG,"String : "+s);
           // OutputStreamWriter outputStreamWriter = new OutputStreamWriter("txt");
            File f = new File(pathSave,nameFile);
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
           // input.close();
                try {
                    PrintWriter printWriter = new PrintWriter(f);
                    printWriter.print(s);
                    printWriter.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Log.i(TAG,"Loi");
                }

            try {
                Scanner sc = new Scanner(f);
                while(sc.hasNextLine()) {
                    Log.i(TAG, "Reader: " + sc.nextLine());
                }
                sc.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            Log.i(TAG, "copyDB is done...path= " + pathSave + nameFile);
            Toast.makeText(getActivity().getApplicationContext(),"File has been saved",Toast.LENGTH_SHORT).show();

        }

    }
}
//private SQLiteDatabase sqlDB;
//private String pathDB;
//private Context mContext;
//private static final String DB_NAME = "ALTP.sqlite";
//private static final String TAG = "DatabaseManager";
//private ContentValues contentVL = new ContentValues();
//
//    public DatabaseManager(Context context) {
//        Log.i(TAG, "DatabaseManager is created...");
//        mContext = context;
//
//        pathDB = Environment.getDataDirectory() + "/data/"
//                + mContext.getPackageName() + "/databases/";
//        copyDB();
//    }
//
//    private void copyDB() {
//        try {
//            File file = new File(pathDB);
//            file.mkdir();
//
//            file = new File(pathDB + DB_NAME);
//            if (file.exists()) {
//                Log.i(TAG, "Database was exist...");
//                return;
//            }
//            InputStream input = mContext.getAssets().open(DB_NAME);
//
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }