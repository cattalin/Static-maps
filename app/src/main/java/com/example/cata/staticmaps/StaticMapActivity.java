package com.example.cata.staticmaps;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Cata on 8/24/2016.
 */
public class StaticMapActivity extends AppCompatActivity {


    private String STATIC_MAP_API_ENDPOINT = "http://maps.googleapis.com/maps/api/staticmap?size=230x200&path=";
    String path;
    Bitmap mapThumbnail=null;
    ImageView staticMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        staticMap=(ImageView) findViewById(R.id.map);
        createMapAdress();
        STATIC_MAP_API_ENDPOINT="https://maps.googleapis.com/maps/api/staticmap?center=Herestrau,Bucharest,Romania&size=1920x1080&maptype=hybrid&zoom=15";
        mapThumbnail=getStaticMapThumbnail();
        staticMap.setImageBitmap(mapThumbnail);
    }




    private String createMapAdress()
    {
        try {
            String marker_me = "color:orange|label:1|Brisbane";
            String marker_dest = "color:orange|label:7|Bucharest, Romania";
            marker_me = URLEncoder.encode(marker_me, "UTF-8");

            marker_dest = URLEncoder.encode(marker_dest, "UTF-8");
            path = "weight:3|color:blue|geodesic:true|Brisbane,Australia|Hong Kong|Moscow,Russia|London,UK|Reyjavik,Iceland|New York,USA|Bucharest, Romania";
            path = URLEncoder.encode(path, "UTF-8");


            STATIC_MAP_API_ENDPOINT = STATIC_MAP_API_ENDPOINT + path + "&markers=" + marker_me + "&markers=" + marker_dest;

            Log.d("STATICMAPS", STATIC_MAP_API_ENDPOINT);

        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return STATIC_MAP_API_ENDPOINT;
    }
    private Bitmap getStaticMapThumbnail()
    {
        Bitmap bitmap = null;

            AsyncTask<Void, Void, Bitmap> setImageFromUrl = new AsyncTask<Void, Void, Bitmap>(){
                @Override
                protected Bitmap doInBackground(Void... params) {
                    HttpClient httpclient = new DefaultHttpClient();
                    HttpGet request = new HttpGet(STATIC_MAP_API_ENDPOINT);
                    Bitmap bmp=null;
                    InputStream in = null;
                    try {
                        HttpResponse response = httpclient.execute(request);
                        Log.d("bitmap neprelucrat",response.toString());
                        in = response.getEntity().getContent();
                        Log.d("bitmap neprelucrat",in.toString());
                        bmp = BitmapFactory.decodeStream(in);
                        in.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Log.d("RECEIVEMENT","COMPLETE");
                    return bmp;
                }
                protected void onPostExecute(Bitmap bmp) {
                    if (bmp!=null) {
                        //bitmap = bmp;
                        staticMap.setImageBitmap(bmp);//THIS IS WHERE THE IMAGEVIEW SHOULD BE UPDATED BECAUSE THE BITMAP IS RECEIVED LATER
                                                            // THAN THE EXECUTION OF THE "getStaticMapThumbnail" METHOD

                    }

                }
            };

            setImageFromUrl.execute();

        Log.d("RETURN RESULT","COMPLETE");
        return bitmap;
    }


    private void setMapToImageview(Bitmap map)
    {

    }
}
