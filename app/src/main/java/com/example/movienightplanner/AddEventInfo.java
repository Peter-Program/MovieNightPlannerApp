package com.example.movienightplanner;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movienightplanner.Models.Attendee;
import com.example.movienightplanner.Models.Event;
import com.example.movienightplanner.Models.SingletonEventsListModel;
import com.example.movienightplanner.Models.SingletonMovieListModel;
import com.example.movienightplanner.Views.DatePickerPopup;
import com.example.movienightplanner.Views.DisplayAttendeesList;
import com.example.movienightplanner.Views.DisplayMoviesList;
import com.example.movienightplanner.Views.TimePickerPopup;

import java.util.ArrayList;
import java.util.LinkedList;

public class AddEventInfo extends BaseActivity {

    String TAG = "addEventInfo";
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    protected static final int PICK_CONTACTS = 1009;

    int SET_START_DATE_REQUEST = 1000;
    int SET_START_TIME_REQUEST = 1001;
    int SET_END_DATE_REQUEST = 1002;
    int SET_END_TIME_REQUEST = 1003;
    int SET_MOVIE_REQUEST = 1004;
    int REMOVE_ATTENDEE_REQUEST = 1005;

    TextView setDateTextView;
    TextView setTimeTextView;
    TextView endDateTextView;
    TextView endTimeTextView;
    TextView titleText;
    TextView venueText;
    TextView movieTitle;
    TextView latAndLong;

    Button setStartDateBtn;
    Button setStartTimeBtn;
    Button setEndDateBtn;
    Button setEndTimeBtn;
    Button saveBtn;
    Button setMovieBtn;
    Button addAttendees;
    Button removeAttendeesBtn;
    Button delBtn;

    EventDateTime startDateTime = new EventDateTime();
    EventDateTime endDateTime = new EventDateTime();

    Boolean newEvent;
    Event event = new Event();
    LinkedList<Attendee> attendeeLinkedList = new LinkedList<>();
    String movieID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event_info);

        Intent intent = getIntent();
        newEvent = intent.getBooleanExtra(getString(R.string.new_Event), false);

        initializeViews();
        setOnClick();

        if (!newEvent) {
            delBtn.setVisibility(View.VISIBLE);
            String eventID = intent.getStringExtra(getString(R.string.edit_event_id));
            for (Event e: SingletonEventsListModel.getInstance().getSortedEventsArray()) {
                if (e.getId().equals(eventID)) {
                    event = e;
                    titleText.setText(event.getTitle());
                    venueText.setText(event.getVenue());
                    setDateTextView.setText(event.getStartDateTime().toStringDate());
                    setTimeTextView.setText(event.getStartDateTime().toStringTime());
                    endDateTextView.setText(event.getEndDateTime().toStringDate());
                    endTimeTextView.setText(event.getEndDateTime().toStringTime());
                    startDateTime = event.getStartDateTime();
                    endDateTime = event.getEndDateTime();
                    latAndLong.setText(event.getLocation());
                    attendeeLinkedList = event.getAttendees();
                    if (event.getMovie() != null)
                        movieTitle.setText(event.getMovie().getTitle());
                }
            }
        }
//        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
//                new IntentFilter(getString(R.string.movie_select)));
    }

    private void initializeViews() {
        titleText = findViewById(R.id.addEventTitleText);
        venueText = findViewById(R.id.addEventVenueText);
        setDateTextView = findViewById(R.id.addEventStartDateTextView);
        setTimeTextView = findViewById(R.id.addEventStartTimeTextView);
        endDateTextView = findViewById(R.id.addEventEndDateTextView);
        endTimeTextView = findViewById(R.id.addEventEndTimeTextView);
        movieTitle = findViewById(R.id.addEventMovieTitleText);
        latAndLong = findViewById(R.id.addEventInfoLocation);

        setStartDateBtn = findViewById(R.id.addEventSetDateBtn);
        setStartTimeBtn = findViewById(R.id.addEventSetStartTimeBtn);
        setEndDateBtn = findViewById(R.id.addEventSetEndDateBtn);
        setEndTimeBtn = findViewById(R.id.addEventSetEndTimeBtn);
        saveBtn = findViewById(R.id.addEventInfoSaveBtn);
        setMovieBtn = findViewById(R.id.addEventMovieBtn);
        addAttendees = findViewById(R.id.addEventAddAttenBtn);
        removeAttendeesBtn = findViewById(R.id.addEventInfoRemoveAttenBtn);
        delBtn = findViewById(R.id.addEventDelBtn);
    }

    private void setOnClick() {
        setStartDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DatePickerPopup.class);
                startActivityForResult(intent, SET_START_DATE_REQUEST);
            }
        });

        setStartTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TimePickerPopup.class);
                startActivityForResult(intent, SET_START_TIME_REQUEST);
            }
        });

        setEndDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DatePickerPopup.class);
                startActivityForResult(intent, SET_END_DATE_REQUEST);
            }
        });

        setEndTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TimePickerPopup.class);
                startActivityForResult(intent, SET_END_TIME_REQUEST);
            }
        });

        setMovieBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DisplayMoviesList.class);
                startActivityForResult(intent, SET_MOVIE_REQUEST);
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                Log.i(TAG, "Saving Event");
                // Need to Work with time not being set
                if (titleText.getText().length() == 0)
                    Toast.makeText(v.getContext(), "Must have a Title",
                            Toast.LENGTH_LONG).show();
                else if (venueText.getText().length() == 0)
                    Toast.makeText(v.getContext(), "Must have a Venue",
                            Toast.LENGTH_LONG).show();
                else if (!endDateTime.laterThen(startDateTime)) {
                    Toast.makeText(v.getContext(), "End Time must be Later then the Start time",
                            Toast.LENGTH_LONG).show();
                } else if (latAndLong.getText().length() == 0) {
                    Toast.makeText(v.getContext(), "Must Have a Location",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(v.getContext(), "All Good", Toast.LENGTH_LONG).show();
                    // Num of Attend

                    saveEvent();
                }
            }
        });

        addAttendees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                        ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(contactPickerIntent, PICK_CONTACTS);
            }
        });

        removeAttendeesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DisplayAttendeesList.class);
                if (attendeeLinkedList.size() == 0) {
                    Toast.makeText(v.getContext(), "No Attendees To Remove",
                            Toast.LENGTH_SHORT).show();
                } else {
                    intent.putExtra(getString(R.string.attendee_list), attendeeLinkedList);
                    startActivityForResult(intent, REMOVE_ATTENDEE_REQUEST);
                }
            }
        });

        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingletonEventsListModel.getInstance().removeEventOfID(event.getId());
                finish();
            }
        });
    }

    private void saveEvent() {
        if (!newEvent) {
            SingletonEventsListModel.getInstance().removeEventOfID(event.getId());
        }
        Event e =
                new Event(null,
                        titleText.getText().toString(),
                        startDateTime,
                        endDateTime,
                        venueText.getText().toString(),
                        latAndLong.getText().toString());
        e.setAttendees(attendeeLinkedList);
        e.setMovieFromId(movieID);
        SingletonEventsListModel.getInstance().addEvent(e);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SET_START_DATE_REQUEST) {
            if (resultCode == RESULT_OK) {
                int day = data.getIntExtra(getString(R.string.day), 1);
                int month = data.getIntExtra(getString(R.string.month), 1);
                int year = data.getIntExtra(getString(R.string.year), 2000);

                //String s = " " + day + "-" + (month + 1) + "-" + year;
                startDateTime.setDay(day);
                startDateTime.setMonth(month);
                startDateTime.setYear(year);
                setDateTextView.setText(startDateTime.toStringDate());
            }
        }

        if (requestCode == SET_START_TIME_REQUEST) {
            if (resultCode == RESULT_OK) {
                int hour = data.getIntExtra(getString(R.string.timeHour), 0);
                int minute = data.getIntExtra(getString(R.string.timeMinute), 0);

                //String s = " " + hour + ":" + minute;
                startDateTime.setHour(hour);
                startDateTime.setMinute(minute);
                setTimeTextView.setText(startDateTime.toStringTime());
            }
        }

        if (requestCode == SET_END_DATE_REQUEST) {
            if (resultCode == RESULT_OK) {
                int day = data.getIntExtra(getString(R.string.day), 1);
                int month = data.getIntExtra(getString(R.string.month), 1);
                int year = data.getIntExtra(getString(R.string.year), 2000);

                //String s = " " + day + "-" + (month + 1) + "-" + year;
                endDateTime.setDay(day);
                endDateTime.setMonth(month);
                endDateTime.setYear(year);
                endDateTextView.setText(endDateTime.toStringDate());
            }
        }

        if (requestCode == SET_END_TIME_REQUEST) {
            if (resultCode == RESULT_OK) {
                int hour = data.getIntExtra(getString(R.string.timeHour), 0);
                int minute = data.getIntExtra(getString(R.string.timeMinute), 0);

                //String s = " " + hour + ":" + minute;
                endDateTime.setHour(hour);
                endDateTime.setMinute(minute);
                endTimeTextView.setText(endDateTime.toStringTime());
            }
        }

        if (requestCode == SET_MOVIE_REQUEST) {
            if (resultCode == RESULT_OK) {
                // update movie title
                movieID = data.getStringExtra(getString(R.string.movie_id));
                event.setMovie(SingletonMovieListModel.getInstance().
                        getMovieOfID(data.getStringExtra(getString(R.string.movie_id))));
                Log.i(TAG, event.getMovie().getId());
                movieTitle.setText(event.getMovie().getTitle());
            }
        }

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
                    Attendee attendee = new Attendee(name, email);
                    attendeeLinkedList.add(attendee);

                } catch(ContactsManager.ContactQueryException e) {
                    Log.e(TAG, e.getMessage());
                }
            }
        }

        if (requestCode == REMOVE_ATTENDEE_REQUEST) {
            if (resultCode == RESULT_OK) {
                Log.i(TAG, "Remove Attendee");
                ArrayList<Attendee> attendeeArrList =
                        (ArrayList<Attendee>) data.getExtras().
                                get(getString(R.string.attendee_list));
                LinkedList<Attendee> a = new LinkedList<>(attendeeArrList);
                attendeeLinkedList = a;
            }
        }
    }

    private void showContacts() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                checkSelfPermission(Manifest.permission.READ_CONTACTS)
                        != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},
                    PERMISSIONS_REQUEST_READ_CONTACTS);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            // Android version is lesser than 6.0 or the permission is already granted.
            //Do stuff

        }
    }

    // More of Getting Permission
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                showContacts();
            } else {
                Toast.makeText(this,
                        "Until you grant the permission, we canot display the names",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

//    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            // Get extra data included in the Intent
//            String movieID = intent.getStringExtra(context.getString(R.string.movie_id));
//            Movie movie = SingletonMovieListModel.getInstance().getMovieOfID(movieID);
//            movieTitle.setText(movie.title);
//        }
//    };
}
