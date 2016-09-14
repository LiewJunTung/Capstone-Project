package com.liewjuntung.travelcompanion;

import com.liewjuntung.travelcompanion.models.YahooQuery;
import com.liewjuntung.travelcompanion.networks.RetrofitUtility;
import com.liewjuntung.travelcompanion.networks.WeatherService;

import org.junit.Test;

import java.io.IOException;

import retrofit2.Call;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void weathe_isNotNull(){
        WeatherService weatherService = RetrofitUtility.initWeatherService();
        Call<YahooQuery> query = RetrofitUtility.getWeatherByLongAndLat(weatherService, 40.7141667,-74.0063889);
        assert query != null;
        try {
            assertEquals("Wed, 14 Sep 2016 10:00 AM AKDT", query.execute().body().getPubDate());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}