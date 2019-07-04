package com.example.movienightplanner.Views;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.example.movienightplanner.BaseActivity;
import com.example.movienightplanner.Models.SingletonEventsListModel;
import com.example.movienightplanner.R;
import com.example.movienightplanner.RecyclerAdapterEvent;

public class DisplayEventsList extends BaseActivity {

    ToggleButton sortOrderToggleBtn;

    RecyclerView recyclerView;
    private RecyclerAdapterEvent adapter;

    SingletonEventsListModel eventsListModel = SingletonEventsListModel.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_events_list);

        sortOrderToggleBtn = findViewById(R.id.displayEventsToggleOrderBtn);

        recyclerView = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        if (sortOrderToggleBtn.isChecked()) {
            adapter = new RecyclerAdapterEvent(eventsListModel.getEventsArrayReverseOrder());
        } else
            adapter = new RecyclerAdapterEvent(eventsListModel.getSortedEventsArray());

        recyclerView.setAdapter(adapter);

        sortOrderToggleBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled then reverse order
                    adapter = new RecyclerAdapterEvent(eventsListModel.getEventsArrayReverseOrder());
                } else {
                    // The toggle is disabled then normal order
                    adapter = new RecyclerAdapterEvent(eventsListModel.getSortedEventsArray());
                }
                recyclerView.setAdapter(adapter);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sortOrderToggleBtn.isChecked()) {
            adapter = new RecyclerAdapterEvent(eventsListModel.getEventsArrayReverseOrder());
        } else
            adapter = new RecyclerAdapterEvent(eventsListModel.getSortedEventsArray());

        recyclerView.setAdapter(adapter);

    }
}
