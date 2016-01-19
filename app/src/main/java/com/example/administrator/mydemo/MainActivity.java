package com.example.administrator.mydemo;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.mydemo.MyKeyListener;
import com.example.administrator.mydemo.R;

public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";
    private MyKeyListener homeKeyListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        homeKeyListener = new MyKeyListener(this);
        homeKeyStart(); //处理方法
        homeKeyListener.startHomeListener(); //开启监听
    }
    /**
     * Home键开始监听
     */
    private void homeKeyStart(){

        homeKeyListener.setOnHomePressedListener(new MyKeyListener.OnHomePressedListener() {

            @Override
            public void onHomePressed() {
                Toast.makeText(MainActivity.this,"onHomePressed",Toast.LENGTH_LONG).show();
                // 这里获取到home键按下事件
                Log.i("lock", "onHomePressed ========================================");
            }
            @Override
            public void onHomeLongPressed() {

                Log.i("lock", "onHomeLongPressed ========================================");

            }
            @Override
            public void onScreenPressed() {
                // TODO Auto-generated method stub
                Toast.makeText(MainActivity.this,"dianjile",Toast.LENGTH_LONG).show();
            }
            @Override
            public void offScreenPressed() {
                // TODO Auto-generated method stub
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(homeKeyListener != null){
            homeKeyListener.stopHomeListener(); //关闭监听
        }
    }
}