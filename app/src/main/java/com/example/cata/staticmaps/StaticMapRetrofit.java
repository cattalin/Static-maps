package com.example.cata.staticmaps;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;


import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Cata on 8/24/2016.
 */
public class StaticMapRetrofit extends Activity implements Callback<StaticMap> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("Activity", "CREATED");

        InputStream in=null;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/maps/api/")
                .addConverterFactory(BitmapFactory.decodeStream(in))
                .build();


        Log.d("Retrofit object", "BUILT");
        // prepare call in Retrofit 2.0
        StaticMapRequestable stackOverflowAPI = retrofit.create(StaticMapRequestable.class);

        Call<StaticMap> call = stackOverflowAPI.loadMap("640x640");
        //asynchronous call
        Log.d("Call", "SENT");
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<StaticMap> call, Response<StaticMap> response) {
        Log.d("Response", "RECEIVED");
        ImageView staticMap=(ImageView) findViewById(R.id.map);
        staticMap.setImageBitmap(response.body().getThumbnail());
    }

    @Override
    public void onFailure(Call<StaticMap> call, Throwable t) {
        Toast.makeText(StaticMapRetrofit.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }
}
