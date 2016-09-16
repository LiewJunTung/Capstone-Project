package com.liewjuntung.travelcompanion.utility;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

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
}
