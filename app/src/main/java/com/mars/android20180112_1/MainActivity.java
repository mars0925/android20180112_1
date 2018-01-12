package com.mars.android20180112_1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Queue;

//利用 Volley library 抓網路
//載入函式庫 compile 'com.android.volley:volley:1.1.0'
/*
    1.建立Queue
    2.建立request
        參數
            1.網址
            2.成功時要執行的程式
            3.失敗要執行的程式
    3.把2加入1
    4.叫queue開始
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void click1(View view)
    {
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        StringRequest request = new StringRequest("http://data.ntpc.gov.tw/od/data/api/BF90FA7E-C358-4CDA-B579-B6C84ADC96A1?$format=json",
            new Response.Listener<String>() {
                @Override
                //String response 就是抓取到的網頁資料
                public void onResponse(String response) {
                        /*
                        //處理json的資料
                        1.觀察資料的格式:{}大括號代表物件,[]中括號代表陣列
                         */
                    try {
                        /*
                        //方式一 用 JSONarray 以及jsonobject 的方施
                        //觀察之後發現 Json外面是用陣列,然後裡面是用物件裝資料
                        //產生JSONArray
                        JSONArray array = new JSONArray(response);
                        //第一個物件
                        for (int i = 0; i<= array.length();i++)
                        {
                            JSONObject object = array.getJSONObject(i);
                            String str = object.getString("district");
                            Log.d("NET", str);
                        }*/

                        //使用GSON 先載入函數庫
                        //2.要建立一個跟資料屬性類別完全一樣的class檔
                        Gson gson = new Gson();
                        //
                        //這個資料最外面是陣列 ,裡面是物件
                        Animal[] houses = gson.fromJson(response, Animal[].class);

                        for (Animal a : houses)
                        {
                            Log.d("NET", a.district + "," + a.address);
                        }
                    }
                    catch(Exception e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

        queue.add(request);
        queue.start();
        //
    }
}
