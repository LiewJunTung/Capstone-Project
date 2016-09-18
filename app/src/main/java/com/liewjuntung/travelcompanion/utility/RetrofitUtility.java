package com.liewjuntung.travelcompanion.utility;

import com.liewjuntung.travelcompanion.models.yahoo.YahooQueryResult;
import com.liewjuntung.travelcompanion.networks.ImageService;
import com.liewjuntung.travelcompanion.networks.WeatherService;

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
    public static final String WEATHER_BASE_URL = "https://query.yahooapis.com";
    public static final String PIXABAY_BASE_URL = "https://pixabay.com";

    public static WeatherService initWeatherService(){
        return initRetrofitService(WEATHER_BASE_URL, WeatherService.class);
    }

    public static ImageService initImageService() {
        return initRetrofitService(PIXABAY_BASE_URL, ImageService.class);
    }

    private static <T> T initRetrofitService(String baseUrl, final Class<T> service) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(getInterceptor())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(service);
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


    public static Call<YahooQueryResult> getWeatherByLongAndLat(WeatherService service, double latitude, double longitude) {
        String query;

        query = String.format(Locale.getDefault(),
                "select * from weather.forecast where woeid in (SELECT woeid FROM geo.places WHERE text=\"(%s,%s)\") and u='c'",
                latitude,
                longitude
        );


        return service.yahooQuery(query);
    }

    public static Call<YahooQueryResult> getWeatherByPlaceName(WeatherService service, String place) {
        String query;

        query = String.format(Locale.getDefault(),
                "select * from weather.forecast where woeid in (SELECT woeid FROM geo.places(1) WHERE text=\"%s\") and u='c'",
                place);

        return service.yahooQuery(query);
    }
}
