package com.liewjuntung.travelcompanion;

import com.liewjuntung.travelcompanion.models.Weather;
import com.liewjuntung.travelcompanion.models.yahoo.Channel;
import com.liewjuntung.travelcompanion.models.yahoo.Item;
import com.liewjuntung.travelcompanion.models.yahoo.Result;
import com.liewjuntung.travelcompanion.models.yahoo.YahooQuery;
import com.liewjuntung.travelcompanion.models.yahoo.YahooQueryResult;
import com.liewjuntung.travelcompanion.networks.WeatherService;
import com.liewjuntung.travelcompanion.utility.RetrofitUtility;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class WeatherPlaceUnitTest {

    private YahooQueryResult mQueryResult;
    private WeatherService mWeatherService;


    @Before
    public void setUp() {
        String placeName = "New York";
        mWeatherService = RetrofitUtility.initWeatherService();
        Call<YahooQueryResult> query = RetrofitUtility.getWeatherByPlaceName(mWeatherService, placeName);

        try {
            mQueryResult = query.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void yahooQueryResult_isNotNull() throws Exception {
        YahooQueryResult result = mQueryResult;
        assertNotNull("Result is null", result);
    }


    @Test
    public void yahooQuery_isNotNull() throws Exception {
        YahooQuery result = mQueryResult.getQuery();
        assertNotNull("Result is null", result);
    }

    @Test
    public void result_isNotNull() throws Exception {
        Result result = mQueryResult.getQuery().getResult();
        assertNotNull("Result is null", result);
    }

    @Test
    public void channel_isNotNull() throws Exception {
        Channel channel = mQueryResult.getQuery().getResult().getChannel();
        assertNotNull(channel);
    }

    @Test
    public void item_isNotNull() throws Exception {
        Item item = mQueryResult.getQuery().getResult().getChannel().getItem();
        assertNotNull(item);
    }

    @Test
    public void weather_isNotNull() throws Exception {
        List<Weather> weatherList = mQueryResult.getQuery().getResult().getChannel().getItem().getWeatherList();
        assertNotNull(weatherList);
    }

    @Test
    public void weather_getFromYahooQuery() throws Exception {
        double longitude = 39.9042;
        double latitude = 116.4074;
        YahooQueryResult yahooQueryResult = null;
        try {
            Call<YahooQueryResult> query = RetrofitUtility.getWeatherByLongAndLat(mWeatherService, latitude, longitude);
            yahooQueryResult = query.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (yahooQueryResult != null) {
            List<Weather> weatherList = yahooQueryResult.weathers();
            assertNotNull(weatherList);
            assertNotNull(weatherList.get(0));
            assertNotNull(weatherList.get(0).getCode());
            assertNotNull(weatherList.get(0).getDate());
            assertNotNull(weatherList.get(0).getDay());
            assertNotNull(weatherList.get(0).getHigh());
            assertNotNull(weatherList.get(0).getLow());
            assertNotNull(weatherList.get(0).getText());
        }
    }

    @Test
    public void wrongWeather_getFromYahooQuery() throws Exception {
        double longitude = 39.9011442;
        double latitude = 11416.404174;
        YahooQueryResult yahooQueryResult = null;
        try {
            Call<YahooQueryResult> query = RetrofitUtility.getWeatherByLongAndLat(mWeatherService, latitude, longitude);
            yahooQueryResult = query.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (yahooQueryResult != null) {
            List<Weather> weatherList = yahooQueryResult.weathers();
            assertNull(weatherList);
        }
    }

}