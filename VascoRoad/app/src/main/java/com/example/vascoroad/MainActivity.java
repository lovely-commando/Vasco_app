package com.example.vascoroad;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    Button bt;
    Retrofit retrofit;
    RetrofitExService retroService;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        retrofit = new Retrofit.Builder().baseUrl(RetrofitExService.URL).addConverterFactory(GsonConverterFactory.create()).build();
        retroService = retrofit.create(RetrofitExService.class);
        textView = (TextView)findViewById(R.id.textView);
        bt = (Button)findViewById(R.id.bt);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("버튼클릭됨@@@@@@@@@@@");
                retroService.getData().enqueue(new Callback<ArrayList<Mperson>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Mperson>> call, Response<ArrayList<Mperson>> response) {
                        System.out.println("onResponse 호출!!!!!!@@@@");
                        ArrayList<Mperson> items = response.body();

                        for(int i =0;i<items.size();i++){
                            System.out.println(items.get(i).getP_id());
                            System.out.println(items.get(i).getP_name());
                            System.out.println(items.get(i).getP_age());
                        }

                    }

                    @Override
                    public void onFailure(Call<ArrayList<Mperson>> call, Throwable t){
                        System.out.println("onFailure 호출!!!!!@@@@@@");
                        System.out.println(t);
                    }
                });
            }
        });
    }

    public interface RetrofitExService{ //interface 선언
        public static final String URL = "http://13.125.95.139:9000/";
        @GET("mperson")
        Call<ArrayList<Mperson>> getData();
    }
}
