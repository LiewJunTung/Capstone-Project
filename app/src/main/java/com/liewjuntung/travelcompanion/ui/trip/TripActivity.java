package com.liewjuntung.travelcompanion.ui.trip;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.liewjuntung.travelcompanion.R;
import com.liewjuntung.travelcompanion.databinding.ActivityTripBinding;
import com.liewjuntung.travelcompanion.models.Trip;
import com.liewjuntung.travelcompanion.ui.create_itinerary.CreateItineraryActivity;

public class TripActivity extends AppCompatActivity {
    public static final String TRIP_INTENT = "TRIP";

    ActivityTripBinding mBinding;
    private Trip mTrip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        mTrip = intent.getParcelableExtra(TRIP_INTENT);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_trip);
        mBinding.setTrip(mTrip);
        initToolbar();
        mBinding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(TripActivity.this, CreateItineraryActivity.class);
                intent1.putExtra(CreateItineraryActivity.TRIP_DATE_ID, mTrip.getId());
                intent1.putExtra(CreateItineraryActivity.TRIP_DATE_FROM, mTrip.getDateFrom());
                intent1.putExtra(CreateItineraryActivity.TRIP_DATE_UNTIL, mTrip.getDateUntil());
                startActivity(intent1);
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
    }

    private void initToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle(mTrip.getName());
        }
    }

}
