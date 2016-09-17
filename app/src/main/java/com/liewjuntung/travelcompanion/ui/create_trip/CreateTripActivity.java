package com.liewjuntung.travelcompanion.ui.create_trip;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.liewjuntung.travelcompanion.R;
import com.liewjuntung.travelcompanion.databinding.ActivityCreateTripBinding;

public class CreateTripActivity extends AppCompatActivity {
    ActivityCreateTripBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_create_trip);
        mBinding.setVm(new CreateTripViewModel(this));
        initToolbar();

    }

    private void initToolbar() {
        setSupportActionBar(mBinding.toolbar.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.text_create_new_trip);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
