package com.liewjuntung.travelcompanion.ui.create_trip;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.liewjuntung.travelcompanion.R;
import com.liewjuntung.travelcompanion.databinding.ActivityCreateTripBinding;

public class CreateTripActivity extends AppCompatActivity {
    ActivityCreateTripBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_create_trip);
        mBinding.setVm(new CreateTripViewModel(this));
        setSupportActionBar(mBinding.toolbar.toolbar);
    }

}
