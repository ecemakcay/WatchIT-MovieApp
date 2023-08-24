package com.eakcay.watchit.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//  This class is used to connect to the API using the Retrofit library.
//  Specifies the API key and API URL, and creates the connection to the API.
public class RetrofitClient {
    private static Retrofit retrofit;
    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
