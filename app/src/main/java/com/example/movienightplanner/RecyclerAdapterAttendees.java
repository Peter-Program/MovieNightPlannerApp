package com.example.movienightplanner;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movienightplanner.Models.Attendee;

import java.util.LinkedList;

import static android.app.Activity.RESULT_OK;

public class RecyclerAdapterAttendees extends RecyclerView.Adapter<RecyclerAdapterAttendees.AttendeeViewHolder> {

    static LinkedList<Attendee> attendees;
    public RecyclerAdapterAttendees(LinkedList<Attendee> a) {
        attendees = a;
    }

    // Responsible for each object view
    @NonNull
    @Override
    public RecyclerAdapterAttendees.AttendeeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.attendee_item_recyclerview_layout, viewGroup, false);
        return new RecyclerAdapterAttendees.AttendeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterAttendees.AttendeeViewHolder attendeeViewHolder, int i) {
        Attendee a = attendees.get(i);
        String s = a.getName();
        attendeeViewHolder.getAttendeeName().setText(s);
    }

    @Override
    public int getItemCount() {
        return attendees.size();
    }

    public static class AttendeeViewHolder extends RecyclerView.ViewHolder {

        private final TextView attendeeName;

        public AttendeeViewHolder(View v) {
            super(v);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent(v.getContext().getString(R.string.movie_select));
//                    intent.putExtra(v.getContext().getString(R.string.movie_id), movies.get(getAdapterPosition()).id);
//                    //LocalBroadcastManager.getInstance(v.getContext()).sendBroadcast(intent);
//
//                    ((Activity)v.getContext()).setResult(RESULT_OK, intent);
//                    ((Activity)v.getContext()).finish();
                    Toast.makeText(v.getContext(), "clicked",
                            Toast.LENGTH_SHORT).show();
                    attendees.remove(getAdapterPosition());
                    Intent intent = new Intent();
                    intent.putExtra(v.getContext().getString(R.string.attendee_list), attendees);
                    ((Activity)v.getContext()).setResult(RESULT_OK, intent);
                    ((Activity)v.getContext()).finish();
                }
            });
            attendeeName = v.findViewById(R.id.attendeeName);
        }

        public TextView getAttendeeName() {
            return attendeeName;
        }
    }
}
