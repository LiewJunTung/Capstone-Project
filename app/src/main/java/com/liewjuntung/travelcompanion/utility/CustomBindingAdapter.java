package com.liewjuntung.travelcompanion.utility;

import android.databinding.BindingAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.liewjuntung.travelcompanion.R;
import com.squareup.picasso.Picasso;

/**
 * Popular Movie App
 * Created by jtlie on 9/16/2016.
 */

public class CustomBindingAdapter {
    @BindingAdapter("bind:imageUrl")
    public static void loadImage(ImageView imageView, String url) {
        Picasso.with(imageView.getContext())
                .load(url)
                .placeholder(R.drawable.placeholder_grey)
                .error(R.drawable.trip_placeholder)
                .centerCrop().fit()
                .into(imageView);
    }

    @BindingAdapter("bind:drawables")
    public static void loadImage(ImageView imageView, int drawables) {
        Picasso.with(imageView.getContext())
                .load(drawables)
                .error(R.drawable.ic_flag_black_24dp)
                .into(imageView);
    }

    @BindingAdapter("bind:loadWeather")
    public static void loadWeather(ImageView imageView, int weatherCode) {
        if (weatherCode < 0 && weatherCode > 47) {
            return;
        }
        imageView.setContentDescription(WeatherUtility.getWeatherStringFromCode(imageView.getContext(), weatherCode));
        imageView.setImageResource(WeatherUtility.getWeatherDrawables(weatherCode));
    }

    @BindingAdapter("bind:loadWeatherString")
    public static void loadWeatherString(TextView textView, int weatherCode) {
        if (weatherCode < 0 && weatherCode > 47) {
            return;
        }
        textView.setText(WeatherUtility.getWeatherStringFromCode(textView.getContext(), weatherCode));
    }

    @BindingAdapter("bind:loadWeatherTemp")
    public static void loadWeatherTemp(TextView textView, int temperature) {
        textView.setText(textView.getContext().getString(R.string.format_temperature, temperature));
    }

    @BindingAdapter("bind:loadLatLong")
    public static void loadLatLong(TextView textView, double latLong) {
        textView.setText(Double.toString(latLong));
    }
}
