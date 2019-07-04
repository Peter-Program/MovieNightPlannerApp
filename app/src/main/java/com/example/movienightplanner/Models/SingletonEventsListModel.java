package com.example.movienightplanner.Models;

import android.util.Log;

import com.example.movienightplanner.EventDateTime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

public class SingletonEventsListModel {
//    private static final LinkedList<Event> eventsList = new LinkedList();
    private static final HashMap lst = new HashMap();
    private static SingletonEventsListModel myObj;
//    private int id = 100;
    private boolean sorted = false;

    // Get instance of class
    public static SingletonEventsListModel getInstance(){
        if(myObj == null){
            myObj = new SingletonEventsListModel();
        }
        return myObj;
    }

    public void addEvent(Event event) {
        if (lst.get(event.getId()) == null) {
            lst.put(event.getId(), event);
            Log.i("dataBase", "put event in system");
        } else
            Log.i("dataBase", "event already in system");

//        eventsList.add(event);
        sorted = false;
    }

    public EventDateTime getTestDate() {
        EventDateTime time = new EventDateTime(3, 30, 2019, 8, 05);
        return time;
    }

    public LinkedList<Event> getNearest3Events(EventDateTime currentTime) {
        LinkedList<Event> nearestEvents = new LinkedList<>();
        Iterator<Event> iterator = getSortedEventsArray().iterator();
        int count = 0;
        while (iterator.hasNext() && count < 3) {
            Event e = iterator.next();
            Log.i("codeRunner", "Event is: " + e.getTitle());
            if (e.compareTo(currentTime) >= 0) {
                nearestEvents.add(e);
                count++;
            }
        }

        return nearestEvents;
    }

    public EventDateTime getCurrentTime() {
        Date d = Calendar.getInstance().getTime();
        int day = d.getDate();
        int month = d.getMonth();
        int year = d.getYear() + 1900;
        int hour = d.getHours();
        int minute = d.getMinutes();
        EventDateTime currentTime = new EventDateTime(day, month, year, hour, minute);

        Log.i("codeRunner", "current time: " + currentTime.getEntireDateTime());
        return currentTime;
    }

    public ArrayList<Event> getSortedEventsArray() {
        Iterator<Event> iterator = lst.values().iterator();
        ArrayList<Event> arrayList = new ArrayList<>();
        while (iterator.hasNext()) {
            Event e = iterator.next();
            arrayList.add(e);
        }

        return quickSort(arrayList, 0, arrayList.size() - 1);
    }

    public ArrayList<Event> getEventsArrayReverseOrder() {
        ArrayList<Event> reverseOrder = new ArrayList<>();
        ArrayList<Event> list = getSortedEventsArray();
        int length = list.size();
        for (int i = 0; i < length; i++) {
            reverseOrder.add(list.get(length - i - 1));
        }
        return reverseOrder;
    }

    int partition(ArrayList<Event> list, int low, int high) {
        Event pivot = list.get(high);
        int i = (low-1); // index of smaller element
        for (int j=low; j<high; j++) {
            // If current element is smaller than or
            // equal to pivot
            if (list.get(j).compareTo(pivot) <= 0) {
                i++;

                // swap arr[i] and arr[j]
                Event temp = list.get(i);
                list.set(i, list.get(j));
                list.set(j, temp);
            }
        }

        // swap arr[i+1] and arr[high] (or pivot)
        Event temp = list.get(i+1);
        list.set(i+1, list.get(high));
        list.set(high, temp);

        return i+1;
    }


    /* The main function that implements QuickSort()
      arr[] --> Array to be sorted,
      low  --> Starting index,
      high  --> Ending index */
    ArrayList<Event> quickSort(ArrayList<Event> list, int low, int high) {
        if (low < high) {
            /* pi is partitioning index, arr[pi] is
              now at right place */
            int pi = partition(list, low, high);

            // Recursively sort elements before
            // partition and after partition
            quickSort(list, low, pi-1);
            quickSort(list, pi+1, high);
        }
        sorted = true;
        return list;
    }

//    public String getNewId() {
//        return "" + id++;
//    }

    public void removeEventOfID(String id) {
        lst.remove(id);
    }

    Event getEventOfID(String id) {
        return (Event) lst.get(id);
    }

}
