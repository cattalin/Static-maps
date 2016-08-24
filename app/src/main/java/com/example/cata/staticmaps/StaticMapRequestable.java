package com.example.cata.staticmaps;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
/**
 * Created by Cata on 8/24/2016.
 */
public interface StaticMapRequestable {
    @GET("staticmap?center=Herestrau,Bucharest,Romania")
    Call<StaticMap> loadMap(@Query("size") String size);
}
