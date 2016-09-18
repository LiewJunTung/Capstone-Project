package com.liewjuntung.travelcompanion.utility;

import android.content.Context;
import android.support.annotation.DrawableRes;

import com.liewjuntung.travelcompanion.R;

import static com.liewjuntung.travelcompanion.R.string.snow;

/**
 * Popular Movie App
 * Created by jtlie on 9/18/2016.
 */

public class WeatherUtility {
    public static String getWeatherStringFromCode(Context context, int weatherCode) {
        switch (weatherCode) {
            case 0:
                return context.getString(R.string.tornado);
            case 1:
                return context.getString(R.string.tropical_storm);
            case 2:
                return context.getString(R.string.hurricane);
            case 3:
                return context.getString(R.string.severe_thunderstorms);
            case 4:
                return context.getString(R.string.thunderstorms);
            case 5:
                return context.getString(R.string.mixed_rain_and_snow);
            case 6:
                return context.getString(R.string.mixed_rain_and_sleet);
            case 7:
                return context.getString(R.string.mixed_snow_and_sleet);
            case 8:
                return context.getString(R.string.freezing_drizzle);
            case 9:
                return context.getString(R.string.drizzle);
            case 10:
                return context.getString(R.string.freezing_rain);
            case 11:
                return context.getString(R.string.showers);
            case 12:
                return context.getString(R.string.showers2);
            case 13:
                return context.getString(R.string.snow_flurries);
            case 14:
                return context.getString(R.string.light_snow_showers);
            case 15:
                return context.getString(R.string.blowing_snow);
            case 16:
                return context.getString(snow);
            case 17:
                return context.getString(R.string.hail);
            case 18:
                return context.getString(R.string.sleet);
            case 19:
                return context.getString(R.string.dust);
            case 20:
                return context.getString(R.string.foggy);
            case 21:
                return context.getString(R.string.haze);
            case 22:
                return context.getString(R.string.smoky);
            case 23:
                return context.getString(R.string.blustery);
            case 24:
                return context.getString(R.string.windy);
            case 25:
                return context.getString(R.string.cold);
            case 26:
                return context.getString(R.string.cloudy);
            case 27:
                return context.getString(R.string.mostly_cloudy_night);
            case 28:
                return context.getString(R.string.mostly_cloudy_day);
            case 29:
                return context.getString(R.string.partly_cloudy_night);
            case 30:
                return context.getString(R.string.partly_cloudy_day);
            case 31:
                return context.getString(R.string.clear_night);
            case 32:
                return context.getString(R.string.sunny);
            case 33:
                return context.getString(R.string.fair_night);
            case 34:
                return context.getString(R.string.fair_day);
            case 35:
                return context.getString(R.string.mixed_rain_and_hail);
            case 36:
                return context.getString(R.string.hot);
            case 37:
                return context.getString(R.string.isolated_thunderstorms);
            case 38:
                return context.getString(R.string.scattered_thunderstorms);
            case 39:
                return context.getString(R.string.scattered_thunderstorms2);
            case 40:
                return context.getString(R.string.scattered_showers);
            case 41:
                return context.getString(R.string.heavy_snow);
            case 42:
                return context.getString(R.string.scattered_snow_showers);
            case 43:
                return context.getString(R.string.heavy_snow2);
            case 44:
                return context.getString(R.string.partly_cloudy);
            case 45:
                return context.getString(R.string.thundershowers);
            case 46:
                return context.getString(R.string.snow_showers);
            case 47:
                return context.getString(R.string.isolated_thundershowers);
            default:
                return context.getString(R.string.weather_not_available);
        }
    }

    public static
    @DrawableRes
    int getWeatherDrawables(int weatherCode) {
        if ((weatherCode >= 0 && weatherCode < 5)) {
            return R.drawable.weather_lightning_rainy;
        } else if (weatherCode >= 5 && weatherCode < 8) {
            return R.drawable.weather_snowy_rainy;
        } else if (weatherCode >= 8 && weatherCode < 13) {
            return R.drawable.weather_rainy;
        } else if (weatherCode >= 13 && weatherCode < 17) {
            return R.drawable.weather_snowy;
        } else if (weatherCode == 17 || weatherCode == 35) {
            //hail
            return R.drawable.weather_hail;
        } else if (weatherCode >= 18 && weatherCode < 24) {
            return R.drawable.weather_windy_variant;
        } else if (weatherCode >= 24 && weatherCode < 26) {
            return R.drawable.weather_windy;
        } else if (weatherCode >= 26 && weatherCode < 29) {
            return R.drawable.ic_weather_cloudy;
        } else if (weatherCode >= 29 && weatherCode < 30) {
            return R.drawable.ic_weather_partlycloudy;
        } else if (weatherCode >= 30 && weatherCode < 35) {
            return R.drawable.weather_sunny;
        } else if (weatherCode == 36) {
            return R.drawable.ic_hot_black_24dp;
        } else if (weatherCode >= 37 && weatherCode < 41) {
            return R.drawable.weather_snowy;
        } else if (weatherCode >= 41 && weatherCode <= 47) {
            return R.drawable.weather_lightning_rainy;
        } else {
            return R.drawable.weather_sunny;
        }
    }
}
