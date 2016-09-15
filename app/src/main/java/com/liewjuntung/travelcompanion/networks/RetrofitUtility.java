package com.liewjuntung.travelcompanion.networks;

import com.liewjuntung.travelcompanion.models.yahoo.YahooQueryResult;

import java.io.IOException;
import java.util.Locale;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Popular Movie App
 * Created by jtlie on 9/15/2016.
 */

public class RetrofitUtility {
    public static WeatherService initWeatherService(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://query.yahooapis.com")
                .client(getInterceptor())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(WeatherService.class);
    }

    private static HttpLoggingInterceptor loggingInterceptor() {
        return new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    private static OkHttpClient getInterceptor(){
        return new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor())
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        if (chain.request().method().equals("GET")){
                            HttpUrl url = chain
                                    .request()
                                    .url()
                                    .newBuilder()
                                    .addQueryParameter("format", "json")
                                    .build();
                            return chain.proceed(chain.request().newBuilder().url(url).build());
                        }
                        return chain.proceed(chain.request());
                    }
                }).build();
    }


    public static Call<YahooQueryResult> getWeatherByLongAndLat(WeatherService service, double longitude, double latitude) {
        String query;

            query = String.format(Locale.getDefault(),
                            "select * from weather.forecast where woeid in (SELECT woeid FROM geo.places WHERE text=\"(%s,%s)\")",
                            longitude,
                            latitude);


        return service.yahooQuery(query);
    }
}
