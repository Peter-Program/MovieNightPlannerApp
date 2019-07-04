package com.example.movienightplanner.Models;

import android.content.ContentValues;
import android.util.Log;

import com.example.movienightplanner.Database.EventsTable;
import com.example.movienightplanner.EventDateTime;

import java.util.LinkedList;
import java.util.UUID;

// Will need a to and from string of movies and attendees
public class Event {
    String id;
    String title;
    String startDate;
    String endDate;
    String venue; // Name of the location of the event
    String location; // a string representation fo the lat and long of the Venue, -37.345, 70.457
    Movie movie;
    LinkedList<Attendee> attendees = new LinkedList<Attendee>();

    EventDateTime startDateTime;
    EventDateTime endDateTime;

    public Event(String id, String title, String startDate, String endDate, String venue, String location) {
        idConstructor(id);
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.venue = venue;
        this.location = location;

        startDateTime = parseDateTime(startDate);
        endDateTime = parseDateTime(endDate);

    }

    public double getLat() {
        String[] s = location.split(",");
        Log.i("codeRunner", "Split location Lat: " + s[0]);
        Double d = Double.parseDouble(s[0]);
        return d;
    }

    public double getLong() {
        String[] s = location.split(",");
        Log.i("codeRunner", "Split location Long: " + s[1]);
        Double d = Double.parseDouble(s[1]);
        return d;
    }

//    public byte[] writeAttendeeData() {
//        // write to byte array
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        DataOutputStream out = new DataOutputStream(baos);
//        for (Attendee element : attendees) {
//            try {
//                out.write(element.describeContents());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return byte[] bytes = baos.toByteArray();
//    }
//
//    public void readAttendeeData(byte[] byteArr) {
//        // read from byte array
//        ByteArrayInputStream bais = new ByteArrayInputStream(byteArr);
//        DataInputStream in = new DataInputStream(bais);
//        while (in.available() > 0) {
//            Attendee element = null;
//            try {
//                element = in.readUTF();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            System.out.println(element);
//        }
//    }

    public Event() {

    }

    public Event(String id, String title, EventDateTime startDateTime, EventDateTime endDateTime, String venue, String location) {
        idConstructor(id);
        this.title = title;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.venue = venue;
        this.location = location;
    }

    // inside DataItem (AKA event)
    public ContentValues toValues() {
        ContentValues values = new ContentValues(8);

        values.put(EventsTable.COLUMN_ID, id);
        values.put(EventsTable.COLUMN_TITLE, title);
        values.put(EventsTable.COLUMN_START_DATE, startDate);
        values.put(EventsTable.COLUMN_END_DATE, endDate);
        values.put(EventsTable.COLUMN_VENUE, venue);
        values.put(EventsTable.COLUMN_LOCATION, location);
        values.put(EventsTable.COLUMN_MOVIE_ID, movieInfoToString());
        values.put(EventsTable.COLUMN_ATTENDEES, "" + attendees.size());
        // do for each column

        return values;
    }

    private String movieInfoToString() {
        if (movie == null) {
            return "no movie";
        } else {
            return movie.getId();
        }
    }

    private void idConstructor(String id) {
        if (id == null) {
            this.id = UUID.randomUUID().toString();
        } else {
            this.id = id;
        }
    }

    public void setAttendees(LinkedList<Attendee> list) {
        attendees = list;
    }
    void addAttendee(Attendee attendee) {
        attendees.add(attendee);
    }

    int compareTo(Event e) {
        Long time1 = startDateTime.getEntireDateTime();
        Long time2= e.startDateTime.getEntireDateTime();

        if (time1 < time2)
            return -1; // Less
        if (time1 > time2)
            return 1; // Greater
        else
            return 0; // Equal
    }

    int compareTo(EventDateTime time) {
        Long time1 = startDateTime.getEntireDateTime();
        Long time2 = time.getEntireDateTime();

        if (time1 < time2)
            return -1; // Less
        if (time1 > time2)
            return 1; // Greater
        else
            return 0; // Equal
    }

    private EventDateTime parseDateTime(String dateTime) {
        // 2/01/2019 1:00:00 AM
        String regex = "[/]";
        String[] words;
        words = dateTime.split(regex);
        int month = Integer.parseInt(words[0]) - 1;
        int day = Integer.parseInt(words[1]);
        words = words[2].split("\\s");
        int year = Integer.parseInt(words[0]);
        String amOrPm = words[2];
        words = words[1].split(":");
        int hour = Integer.parseInt(words[0]);
        if (amOrPm.equals("PM") && hour < 12) {
            hour += 12;
        }
        //Log.i("Time", "The hour is: " + hour);

        int minute = Integer.parseInt(words[1]);
        EventDateTime eventDateTime = new EventDateTime(day, month, year, hour, minute);
        return eventDateTime;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getVenue() {
        return venue;
    }

    public String getLocation() {
        return location;
    }

    public Movie getMovie() {
        return movie;
    }

    public LinkedList<Attendee> getAttendees() {
        return attendees;
    }

    public EventDateTime getStartDateTime() {
        return startDateTime;
    }

    public EventDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public void setMovieFromId(String id) {
        //Log.i("Event", "Loading movie to Event: " + title +", " + SingletonMovieListModel.getInstance().getMovieOfID(id).getTitle());
        movie = SingletonMovieListModel.getInstance().getMovieOfID(id);
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
        startDateTime = parseDateTime(startDate);
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
        endDateTime = parseDateTime(endDate);
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
