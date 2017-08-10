package com.example.nhatcuong1.app_chung_khoan.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nhatcuong1.app_chung_khoan.COMMON;
import com.example.nhatcuong1.app_chung_khoan.Model.HistoryStockModel;
import com.example.nhatcuong1.app_chung_khoan.Model.ShareModel;
import com.example.nhatcuong1.app_chung_khoan.Model.UserModel;
import com.example.nhatcuong1.app_chung_khoan.OpenUrlToGetString;
import com.example.nhatcuong1.app_chung_khoan.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by nhatcuong1 on 11/20/15.
 */
public class DetailActivity extends Activity implements View.OnClickListener{
    private EditText edt_detail_ShareId, edt_detail_ShareName, edt_detail_ShareVolume, edt_detail_ShareLow, edt_detail_ShareOpen, edt_detail_ShareTime, edt_detail_ShareHigh, edt_detail_SharePrice, edt_detail_ShareChange, edt_detail_quantity, edt_detail_currentmoney;
    private ShareModel share = new ShareModel();
    private Button btn_detail_buy;
    private Button btn_detail_sell;
    private EditText edt_detail_buyorsell;
    private static final String TAG = "DetailActivity";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_share_activity);
        Intent i = getIntent();
        share = (ShareModel) i.getSerializableExtra("share");
        Log.i(TAG, "ShareID " + share.getId() + share.getName() + share.getTime() + share.getChange() + share.getHigh() + share.getOpen() + share.getVolume() + share.getLow());
        initComponents();
        new DoInAddQuantity().execute();

    }
    private void initComponents(){
        edt_detail_buyorsell = (EditText)findViewById(R.id.edt_detail_buyorsell);
        edt_detail_ShareChange = (EditText)findViewById(R.id.edt_detail_sharechange);
        edt_detail_ShareHigh = (EditText)findViewById(R.id.edt_detail_sharehigh);
        edt_detail_ShareId = (EditText)findViewById(R.id.edt_detail_shareid);
        edt_detail_ShareLow = (EditText)findViewById(R.id.edt_detail_sharelow);
        edt_detail_ShareName = (EditText)findViewById(R.id.edt_detail_sharename);
        edt_detail_ShareOpen = (EditText)findViewById(R.id.edt_detail_shareopen);
        edt_detail_SharePrice = (EditText)findViewById(R.id.edt_detail_shareprice);
        edt_detail_ShareVolume = (EditText)findViewById(R.id.edt_detail_sharevolume);
        edt_detail_ShareTime = (EditText)findViewById(R.id.edt_detail_sharetime);
        edt_detail_quantity = (EditText)findViewById(R.id.edt_detail_quantity);
        edt_detail_currentmoney = (EditText)findViewById(R.id.edt_detail_currentmoney);
        edt_detail_ShareTime.setText(share.getTime()+"");
        edt_detail_ShareVolume.setText(share.getVolume()+"");
        edt_detail_SharePrice.setText(share.getPrice()+"");
        edt_detail_ShareOpen.setText(share.getOpen()+"");
        edt_detail_ShareChange.setText(share.getChange()+"");
        edt_detail_ShareId.setText(share.getId()+"");
        edt_detail_ShareLow.setText(share.getLow()+"");
        edt_detail_ShareHigh.setText(share.getHigh()+"");
        edt_detail_ShareName.setText(share.getName()+"");
        edt_detail_currentmoney.setText(((double)Math.round(StartAppActivity.getUser().getMoney() * 100)/100)+"$");
        btn_detail_buy = (Button)findViewById(R.id.btn_detail_buy);
        btn_detail_sell =(Button)findViewById(R.id.btn_detail_sell);
        btn_detail_sell.setOnClickListener(this);
        btn_detail_buy.setOnClickListener(this);




    }
    public boolean checkValidMoney(String money) {
        Matcher matcher = Pattern.compile("[^0-9]").matcher(money);
        return !matcher.find();
    }

    @Override
    public void onClick(View v) {
        String uid = StartAppActivity.getUser().getId()+"";
        String sid = share.getId();
        switch (v.getId()){
            case R.id.btn_detail_buy:
                if(checkValidMoney(edt_detail_buyorsell.getText().toString().trim()) == false){
                    Toast.makeText(this,"Invalid money",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(check_quantity(edt_detail_buyorsell.getText().toString().trim())==false){
                    Toast.makeText(this,"Invalid money",Toast.LENGTH_SHORT).show();
                    return;
                }
                String quantity ="";
                String method="";
                method ="buy";
                quantity = edt_detail_buyorsell.getText()+"";
                Log.i(TAG,"Quantity: "+quantity);
                String urlbuy = "http://oopandroidnhom4.esy.es/addquantity.php?sid="+sid+"&uid="+uid+"&quantity="+quantity;
                new DoInBuyOrSellQuantity().execute(urlbuy,edt_detail_quantity.getText().toString(), edt_detail_buyorsell.getText().toString(),method);
                break;
            case R.id.btn_detail_sell:
                if(checkValidMoney(edt_detail_buyorsell.getText().toString().trim()) == false){
                    Toast.makeText(this,"Invalid money",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(check_quantity(edt_detail_buyorsell.getText().toString().trim())==false){
                    Toast.makeText(this,"Invalid money",Toast.LENGTH_SHORT).show();
                    return;
                }
                String quantity1 ="";
                String method1="";
                method1 ="sell";
                quantity1 = edt_detail_buyorsell.getText()+"";
                Log.i(TAG,"Quantity: "+quantity1);
                int quantityCurrent = Integer.parseInt(edt_detail_quantity.getText().toString());
                int quantitySell = Integer.parseInt(edt_detail_buyorsell.getText().toString());
                if(quantityCurrent < quantitySell){
                    Toast.makeText(this,"Not have enough quantity for selling",Toast.LENGTH_SHORT).show();
                    return;
                }
                String urlsell = "http://oopandroidnhom4.esy.es/addquantity.php?sid="+sid+"&uid="+uid+"&quantity=-"+quantity1;
                new DoInBuyOrSellQuantity().execute(urlsell,edt_detail_quantity.getText().toString(),edt_detail_buyorsell.getText().toString(),method1);
                break;
        }
    }
    private boolean check_quantity(String quantity){
        if(Integer.parseInt(quantity) == 0 ){
            return false;
        }else{
            return true;
        }
    }


    private class DoInAddQuantity extends AsyncTask<String,String,String>{
        String url = "http://oopandroidnhom4.esy.es/returnquantity.php?uid="+StartAppActivity.getUser().getId()+"&sid="+share.getId();
        @Override
        protected String doInBackground(String... params) {
            OpenUrlToGetString openUrlToGetString = new OpenUrlToGetString();
            boolean check = false;
            while (check == false) {
                try {
                    String result = openUrlToGetString.OpenHttpConnection(url);
                    Log.i(TAG, "result truoc: " + result);
                    result = result.substring(result.indexOf("["), result.indexOf("]") + 1);
                    Log.i(TAG, "result sau " + result);
                    JSONArray arr_js = new JSONArray(result);
                    Log.i(TAG, "SO phan tu " + arr_js.length());
                    if (arr_js.length() == 0) {
                        Log.i(TAG, "CHuoi json rong nhe");
                        return null;
                    }
                    JSONObject js = arr_js.getJSONObject(0);
                    check = true;
                    return js.getString("Quantity");
                } catch (IOException e) {
                    e.printStackTrace();
                    check = false;
                } catch (JSONException e) {
                    e.printStackTrace();
                    check = false;
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s == null) {
                Log.i(TAG, "null roi nhe");
                return;
            }
            Log.i(TAG,"String s = "+s);
            edt_detail_quantity.setText(s + "");
        }
    }
    private class DoInBuyOrSellQuantity extends AsyncTask<String,String,String>{
        String url;
        String urlUpdateMoney;
        String urlAddHistory;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            share.setPrice(Double.parseDouble(edt_detail_SharePrice.getText().toString().trim()));
        }

        @Override
        protected String doInBackground(String... params) {

            url = params[0];
            int quantity = Integer.parseInt(params[1]);
            int quantityBuyOrSell = Integer.parseInt(params[2]);
            String method = params[3];
            double exchange = quantityBuyOrSell * share.getPrice();
            OpenUrlToGetString openUrlToGetString = new OpenUrlToGetString();
            try {

                   // Log.i(TAG, "Thanh cong roi nhe");
                    if(method.equals("buy")){
                        int total = quantity + quantityBuyOrSell;
                        UserModel user = new UserModel();
                        user = StartAppActivity.getUser();

                        if(user.getMoney() < quantityBuyOrSell * share.getPrice()){
                            Log.i(TAG,"So tien minh dang co la "+ user.getMoney()+"  so tien phai tra "+ quantityBuyOrSell * share.getPrice());
                            return null;
                        }
                        user.setMoney(user.getMoney() - quantityBuyOrSell * share.getPrice());
                        StartAppActivity.setUser(user);
                        urlUpdateMoney = "http://oopandroidnhom4.esy.es/updatemoney.php?money=-"+quantityBuyOrSell*share.getPrice()+"&uid="+StartAppActivity.getUser().getId();
                        urlAddHistory = "http://oopandroidnhom4.esy.es/addhistory.php?uid="+StartAppActivity.getUser().getId()+"&sid="+share.getId()+"&method=buying&oldprice="+share.getPrice()+"&volume="+quantityBuyOrSell+"&exchange="+exchange+"&percentage=10000000";
                        openUrlToGetString.OpenHttpConnection(urlUpdateMoney);
                        //add to history
                        String kq= openUrlToGetString.OpenHttpConnection(urlAddHistory);
                        openUrlToGetString.OpenHttpConnection(url);
                        Log.i(TAG, "URL add quantity " + url);
                        Log.i(TAG, "KQ = "+kq);
                        return total + "";
                    }else if(method.equals("sell")){
                        int total = quantity - quantityBuyOrSell;
                        UserModel user = new UserModel();
                        user = StartAppActivity.getUser();
                        user.setMoney(user.getMoney() + quantityBuyOrSell * share.getPrice());
                        StartAppActivity.setUser(user);
                        urlUpdateMoney = "http://oopandroidnhom4.esy.es/updatemoney.php?money="+quantityBuyOrSell*share.getPrice()+"&uid="+StartAppActivity.getUser().getId();
                        openUrlToGetString.OpenHttpConnection(urlUpdateMoney);
                        /// add to history
                        String urlReturnBuyingHistory = "http://oopandroidnhom4.esy.es/returnbuyinghistory.php?sid="+share.getId()+"&uid="+StartAppActivity.getUser().getId();
                        String rs = openUrlToGetString.OpenHttpConnection(urlReturnBuyingHistory);
                        Log.i(TAG,"rs cua buying: "+rs +"shareId= "+share.getId()+"UID "+StartAppActivity.getUser().getId());
                        JSONArray arr_js_buying = new JSONArray(rs);
                        ArrayList<HistoryStockModel> arr_buying_share = new ArrayList<>();
                        for(int i =0 ;i < arr_js_buying.length(); i++){
                            JSONObject js = arr_js_buying.getJSONObject(i);
                            HistoryStockModel historyStockModel = new HistoryStockModel();
                            historyStockModel.setOldprice(Double.parseDouble(js.getString(COMMON.HISTORY_OLDPRICE)));
                            historyStockModel.setVolume(Integer.parseInt(js.getString(COMMON.HISTORY_VOLUME)));
                            Log.i(TAG, Double.parseDouble(js.getString(COMMON.HISTORY_OLDPRICE)) + "");
                            Log.i(TAG,Double.parseDouble(js.getString(COMMON.HISTORY_VOLUME))+"");
                            arr_buying_share.add(historyStockModel);
                        }
                        double meanOfOldPrice = 0;
                        double totalOldprice = 0;
                        int sumOfVolume = 0;
                        for (int i = 0;i < arr_buying_share.size(); i++){
                            totalOldprice = totalOldprice + (double) arr_buying_share.get(i).getVolume() * arr_buying_share.get(i).getOldprice();
                            sumOfVolume = sumOfVolume + arr_buying_share.get(i).getVolume();
                        }
                        Log.i(TAG,"TotalOldprice"+totalOldprice+"  sumofvolume"+sumOfVolume);
                        meanOfOldPrice = totalOldprice/sumOfVolume;
                        double percentage = (share.getPrice() - meanOfOldPrice) / (meanOfOldPrice);
                        Log.i(TAG,"Percentage "+percentage+"meanofOldprice: "+meanOfOldPrice);
                        urlAddHistory = "http://oopandroidnhom4.esy.es/addhistory.php?uid="+StartAppActivity.getUser().getId()+"&sid="+share.getId()+"&method=selling&oldprice="+share.getPrice()+"&volume="+quantityBuyOrSell+"&exchange="+exchange+"&percentage="+percentage;;
                        openUrlToGetString.OpenHttpConnection(urlAddHistory);
                        Log.i(TAG,"URL add quantity "+url);
                        openUrlToGetString.OpenHttpConnection(url);
                        return total + "";
                    }
                    //edt_detail_quantity.setText();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s == null) {
                Toast.makeText(getApplicationContext(), "Transaction is not complete", Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(getApplicationContext(), "Transaction is complete", Toast.LENGTH_SHORT).show();
            edt_detail_quantity.setText(s);
            edt_detail_currentmoney.setText(((double)Math.round(StartAppActivity.getUser().getMoney()*100)/100 )+"$");
        }
    }
}
