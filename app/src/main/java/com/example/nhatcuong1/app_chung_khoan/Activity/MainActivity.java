package com.example.nhatcuong1.app_chung_khoan.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nhatcuong1.app_chung_khoan.Model.ShareModel;
import com.example.nhatcuong1.app_chung_khoan.Model.UserModel;
import com.example.nhatcuong1.app_chung_khoan.OpenUrlToGetString;
import com.example.nhatcuong1.app_chung_khoan.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MainActivity extends Activity implements View.OnClickListener{
    private static final String TAG = "MAIN_ACTIVITY" ;
    private  Button btn_Register, btn_Login;
    private EditText edt_Username, edt_Password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();

    }
    //khoi tao cac doi tuong
    private void initComponents(){
        btn_Login = (Button) findViewById(R.id.btn_Login);
        btn_Register = (Button) findViewById(R.id.btn_Register);
        edt_Password = (EditText) findViewById(R.id.edt_Password);
        edt_Username = (EditText) findViewById(R.id.edt_Username);
        btn_Register.setOnClickListener(this);
        btn_Login.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        Intent myIntent;
        switch (view.getId()){
            default: break;
            case R.id.btn_Register:
                //Log.i(TAG,"Vao register roi...");
                myIntent = new Intent(this,RegisterActivity.class);
                startActivity(myIntent);
                break;
            case R.id.btn_Login:
                if(edt_Username.getText().toString().startsWith(" ")){
                    Toast.makeText(this,"please type user",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(edt_Username.getText().toString().trim().equals("")){
                    Toast.makeText(this,"please type user",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(edt_Username.getText().toString().trim().contains(" ")){
                    Toast.makeText(this,"please type user",Toast.LENGTH_SHORT).show();
                    return;
                }


                new DoLogin().execute();
                break;
        }
    }
    private class DoLogin extends AsyncTask<String,String,String>{
        String urlID;
        String url;
        String password;
        String username;
        UserModel user = new UserModel();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            password = edt_Password.getText().toString();
            username = edt_Username.getText().toString();
            url = "http://oopandroidnhom4.esy.es/login.php?username="+username+"&password="+password;
            urlID = "http://oopandroidnhom4.esy.es/returnuser.php?username="+username;
        }

        @Override
        protected String doInBackground(String... strings) {
            String kq = "";
            OpenUrlToGetString openUrlToGetString = new OpenUrlToGetString();
            try {
                String result = openUrlToGetString.OpenHttpConnection(url);
                if (result.contains("1")){
                    Log.i(TAG,"Tai khoan chua ton tai: "+result);
                    kq = "noexist";
                } else if (result.contains("2")){
                    Log.i(TAG,"Sai password"+result);
                    kq="wrongpass";
                } else {
                    Log.i(TAG,"Thanh cong"+result);
                    kq="complete";
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            try {
                String result = openUrlToGetString.OpenHttpConnection(urlID);
                JSONArray arr_js = new JSONArray(result);
                JSONObject js = arr_js.getJSONObject(0);
                Log.i(TAG,js.getString("username"));
                user.setUsername(js.getString("username"));
                user.setPassword(js.getString("password"));
                user.setId(Integer.parseInt(js.getString("id")));
                user.setMoney(Double.parseDouble(js.getString("money")));
                Log.i(TAG,"ID user la "+user.getId());

            } catch (IOException e) {
                e.printStackTrace();
                return kq;
            } catch (JSONException e) {
                e.printStackTrace();
                return kq;
            }
            return kq;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s == null) {
                Toast.makeText(getApplicationContext(),"error login",Toast.LENGTH_SHORT).show();
                return;
            }
           // if(!s.equals("OK")) return;
            if(s.equals("noexist")){
                Toast.makeText(getApplicationContext(),"Your account has not been created",Toast.LENGTH_SHORT).show();
                return;
            }
            if(s.equals("wrongpass")){
                Toast.makeText(getApplicationContext(),"Wrong password",Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(getApplicationContext(), StartAppActivity.class);
            intent.putExtra("user",user);
            Log.i(TAG,"Den phan start activity roi");
            startActivity(intent);
        }
    }
}
