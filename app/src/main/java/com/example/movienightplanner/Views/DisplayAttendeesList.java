package com.example.movienightplanner.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.movienightplanner.ContactsManager;
import com.example.movienightplanner.Models.Attendee;
import com.example.movienightplanner.R;
import com.example.movienightplanner.RecyclerAdapterAttendees;

import java.util.ArrayList;
import java.util.LinkedList;

public class DisplayAttendeesList extends AppCompatActivity {
    String TAG = "attendees";
    protected static final int PICK_CONTACTS = 100;

    RecyclerView recyclerView;

    private RecyclerView.LayoutManager layoutManager;
    private RecyclerAdapterAttendees adapter;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendees_list);
        intent = getIntent();
        ArrayList<Attendee> attendeeArrList =
                (ArrayList<Attendee>) intent.getExtras().get(getString(R.string.attendee_list));

        recyclerView = findViewById(R.id.recyclerViewAttendees);
        layoutManager = new GridLayoutManager(this, 1);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerAdapterAttendees(arrListToLink(attendeeArrList));

        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_CONTACTS) {
            if(resultCode == RESULT_OK) {
                Log.i(TAG, "Creating new ContactsManager");
                ContactsManager contactData = new ContactsManager(this, data);
                try {
                    Log.i(TAG, "Getting name and email");
                    String name = contactData.getContactName();
                    String email = contactData.getContactEmail();
                    Log.i(TAG, "Name: " + name);
                    Log.i(TAG, "Email: " + email);

                } catch(ContactsManager.ContactQueryException e) {
                    Log.e(TAG, e.getMessage());
                }
            }
        }
    }

    LinkedList<Attendee> arrListToLink(ArrayList<Attendee> arrayList) {
        LinkedList<Attendee> attendees = new LinkedList<>();
        for (Attendee a: arrayList) {
            attendees.add(a);
        }

        return attendees;
    }
}
