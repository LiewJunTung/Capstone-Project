package com.liewjuntung.travelcompanion.ui.main;

import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.liewjuntung.travelcompanion.R;
import com.liewjuntung.travelcompanion.databinding.ItemMainCurrentBinding;
import com.liewjuntung.travelcompanion.databinding.ItemMainTitleBinding;
import com.liewjuntung.travelcompanion.databinding.ItemMainUpcomingBinding;
import com.liewjuntung.travelcompanion.models.Trip;
import com.liewjuntung.travelcompanion.utility.TravelCompanionUtility;

import java.util.List;

/**
 * Popular Movie App
 * Created by jtlie on 9/16/2016.
 */

public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TITLE = 0;
    public static final int TRIP_MAIN = 1;
    public static final int TRIP_SUB = 2;

    List<Object> mainList;

    int[] titleIndex;

    public MainAdapter(Cursor cursor) {
        setMainListCursor(cursor);
    }


    public void setMainListCursor(Cursor cursor) {
        this.mainList = TravelCompanionUtility.initMainList(cursor);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        Object object = mainList.get(position);
        if (object instanceof String) {
            return TITLE;
        } else {
            Trip trip = (Trip) object;
            return trip.isFuture() ?
                    TRIP_SUB :
                    TRIP_MAIN;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TITLE) {
            ItemMainTitleBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_main_title, parent, false);
            return new TextViewHolder(binding);
        } else if (viewType == TRIP_MAIN) {
            ItemMainCurrentBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_main_current, parent, false);
            return new CurrentItemViewHolder(binding);
        } else {
            ItemMainUpcomingBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_main_upcoming, parent, false);
            return new UpcomingItemViewHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TextViewHolder) {
            ((TextViewHolder) holder).binding.setTitle((String) mainList.get(position));
        } else if (holder instanceof UpcomingItemViewHolder) {
            ((UpcomingItemViewHolder) holder).binding.setTrip((Trip) mainList.get(position));
        } else {
            ((CurrentItemViewHolder) holder).binding.setTrip((Trip) mainList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return mainList.size();
    }

    private class TextViewHolder extends RecyclerView.ViewHolder {
        ItemMainTitleBinding binding;

        TextViewHolder(ItemMainTitleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private class UpcomingItemViewHolder extends RecyclerView.ViewHolder {
        ItemMainUpcomingBinding binding;

        UpcomingItemViewHolder(ItemMainUpcomingBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private class CurrentItemViewHolder extends RecyclerView.ViewHolder {
        ItemMainCurrentBinding binding;

        CurrentItemViewHolder(ItemMainCurrentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
