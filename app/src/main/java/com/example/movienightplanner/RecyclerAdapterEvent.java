package com.example.movienightplanner;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.movienightplanner.Models.Event;

import java.util.ArrayList;

public class RecyclerAdapterEvent extends RecyclerView.Adapter<RecyclerAdapterEvent.EventViewHolder> {

    private static ArrayList<Event> events;

    public RecyclerAdapterEvent(ArrayList<Event> e) {
        events = e;
    }

    // Responsible for each object view
    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.event_item_recyclerview_layout, viewGroup, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder eventViewHolder, int i) {
        Event e = events.get(i);
        String s = e.getStartDateTime().toStringDate()
                + " at" + e.getStartDateTime().toStringTime() + ", " + e.getTitle()
                + ", " + e.getVenue();
        eventViewHolder.getEventTitle().setText(s);
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    static class EventViewHolder extends RecyclerView.ViewHolder {

        private final TextView eventTitle;

        EventViewHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), AddEventInfo.class);
                    intent.putExtra(v.getContext().getString(R.string.edit_event_id),
                            events.get(getAdapterPosition()).getId());
                    v.getContext().startActivity(intent);
                }
            });
            eventTitle = v.findViewById(R.id.eventTitle);
        }

        TextView getEventTitle() {
            return eventTitle;
        }
    }
}
