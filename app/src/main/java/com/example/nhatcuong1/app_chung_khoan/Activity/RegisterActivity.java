package com.example.nhatcuong1.app_chung_khoan.Activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nhatcuong1.app_chung_khoan.Model.UserModel;
import com.example.nhatcuong1.app_chung_khoan.OpenUrlToGetString;
import com.example.nhatcuong1.app_chung_khoan.R;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by nhatcuong1 on 11/17/15.
 */
public class RegisterActivity extends Activity implements View.OnClickListener {
    private Button btn_Register_Register;
    private EditText edt_Username_Register, edt_Password_Register, edt_Money_Start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initComponents();
    }
    private void initComponents(){
        edt_Money_Start = (EditText) findViewById(R.id.edt_Money_Start);
        edt_Password_Register = (EditText) findViewById(R.id.edt_Password_Register);
        edt_Username_Register = (EditText) findViewById(R.id.edt_Username_Register);
        btn_Register_Register = (Button) findViewById(R.id.btn_Register_Register);
        btn_Register_Register.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_Register_Register:
                if(edt_Money_Start.getText().toString()=="" || edt_Password_Register.getText().toString() == "" || edt_Username_Register.getText().toString() ==""){
                    Toast.makeText(this,"Please type full three fields",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(edt_Username_Register.getText().toString().startsWith(" ")){
                    Toast.makeText(this,"please type user",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(edt_Username_Register.getText().toString().trim().equals("")){
                    Toast.makeText(this,"please type user",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(edt_Username_Register.getText().toString().trim().contains(" ")){
                    Toast.makeText(this,"please type user",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(checkValidMoney(edt_Money_Start.getText().toString().trim())==false){
                    Toast.makeText(this,"Money is error",Toast.LENGTH_SHORT).show();
                    return;
                }

                new DoInRegister().execute();
                break;
        }
    }
    public boolean checkValidMoney(String money) {
        Matcher matcher = Pattern.compile("[^0-9]").matcher(money);
        return !matcher.find();
    }
    private class DoInRegister extends AsyncTask<String,String,String>{
        final static String TAG = "DoInRegister";
        String url;
        String username;
        String password;
        double money;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            username = edt_Username_Register.getText().toString();
            password = edt_Password_Register.getText().toString();
            money = Double.parseDouble(edt_Money_Start.getText().toString());
            url = "http://www.oopandroidnhom4.esy.es/register.php?username="+username+"&password="+password+"&money="+money;
        }

        @Override
        protected String doInBackground(String... strings) {
            OpenUrlToGetString openUrlToGetString;
            openUrlToGetString = new OpenUrlToGetString();
            try {
                String result =openUrlToGetString.OpenHttpConnection(url);
                if(result.contains("complete")){
                    Log.i(TAG, "Thanh cong");
//                    UserModel user = new UserModel();
//                    user.setMoney(money);
//                    user.setPassword(password);
//                    user.setUsername(username);
//                    String rs = new String(result);
//                    rs = rs.replace("[","");
//                    rs = rs.replace("complete","");
//                    rs = rs.replace("]","");
//                    rs = rs.replace("id","");
//                    rs = rs.replace("{","");
//                    rs = rs.replace("}","");
//                    rs = rs.replace(":","");
//                    rs = rs.replace("\"","");
//                    Log.i(TAG,"Ket qua rs: "+rs);
//                    user.setId(Integer.parseInt(rs));
                    return result;
                }else{
                    Log.i(TAG, "That bai");
                    return result;
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.i(TAG, "That bai");
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s ==null) return;
            if(s.contains("complete")){
                Toast.makeText(getApplicationContext(),"Register is complete",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(),"Register is not complete",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
