package com.liewjuntung.travelcompanion.ui.trip;

import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liewjuntung.travelcompanion.R;
import com.liewjuntung.travelcompanion.databinding.ItemMainTitleBinding;
import com.liewjuntung.travelcompanion.databinding.ItemTripBinding;
import com.liewjuntung.travelcompanion.models.Itinerary;
import com.liewjuntung.travelcompanion.utility.TravelCompanionUtility;

import java.util.List;

/**
 * Popular Movie App
 * Created by jtlie on 9/16/2016.
 */

public class ItineraryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TITLE = 0;
    public static final int ITINERARY_MAIN = 1;

    List<Object> mainList;

    int[] titleIndex;

    ItineraryClickListener mItineraryClickListener;
    View emptyView;

    public ItineraryAdapter(Cursor cursor, View emptyView) {
        setMainListCursor(cursor);
        this.emptyView = emptyView;
    }


    public void setMainListCursor(Cursor cursor) {
        this.mainList = TravelCompanionUtility.initItineraryList(cursor);
        notifyDataSetChanged();
    }

    public void setItineraryClickListener(ItineraryClickListener itineraryClickListener) {
        mItineraryClickListener = itineraryClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        Object object = mainList.get(position);
        if (object instanceof String) {
            return TITLE;
        } else {
            return ITINERARY_MAIN;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TITLE) {
            ItemMainTitleBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_main_title, parent, false);
            return new TextViewHolder(binding);
        } else {
            ItemTripBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_trip, parent, false);
            return new CurrentItemViewHolder(binding);
        }
    }

    private void clickTrip(Itinerary itinerary) {
        if (mItineraryClickListener != null) {
            mItineraryClickListener.clickItinerary(itinerary);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TextViewHolder) {
            ((TextViewHolder) holder).binding.setTitle((String) mainList.get(position));
        } else {
            final Itinerary itinerary = (Itinerary) mainList.get(position);
            ((CurrentItemViewHolder) holder).binding.setItinerary(itinerary);
            ((CurrentItemViewHolder) holder).binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickTrip(itinerary);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        int size = mainList.size();
        if (emptyView != null) {
            if (size > 0) {
                emptyView.setVisibility(View.GONE);
            } else {
                emptyView.setVisibility(View.VISIBLE);
            }
        }
        return size;
    }

    private class TextViewHolder extends RecyclerView.ViewHolder {
        ItemMainTitleBinding binding;

        TextViewHolder(ItemMainTitleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private class CurrentItemViewHolder extends RecyclerView.ViewHolder {
        ItemTripBinding binding;

        CurrentItemViewHolder(ItemTripBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
