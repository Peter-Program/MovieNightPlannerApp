package com.example.movienightplanner.Models;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

public class FileInputReader {

    // Works for both events and movies
    public static void readFile(String fileName) {
        Event event;
        Movie movie;

        String line;
        Pattern p = Pattern.compile("^[/].*");                                                      // The Pattern for the specified Regex
        String[] words;
        String filePath =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                        + "/" + fileName;
        String TAG = "FileInputReader";

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader =
                    new FileReader(filePath);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {

                if (!p.matcher(line).matches()) {
                    //System.out.println(line);
                    words = line.split(",");
                    for(int i = 0; i < words.length; i++) {
                        words[i] = words[i].replaceAll("\"", "");
                        //System.out.println(i + " " + str);
                    }
                    String id = words[0];
                    String title = words[1];
                    // if the file being read is the events then do this
                    if (fileName.compareTo("events.txt") == 0) {
                        Log.i(TAG, "Adding Event from file " + title);
                        String startDate = words[2];
                        String endDate = words[3];
                        String venue = words[4];
                        String location = words[5] + ", " + words[6];
                    /*
                    Format after parsing file
                    0 - id
                    1 - Title
                    2 - Start date
                    3 - end date
                    4 - Venue title
                    5 - Lat
                    6 - Long
                     */
                        // for 0 to 6 we are setting up the event constructor
                        event = new Event(id, title, startDate, endDate, venue, location);
                        // now add this event to the event list
                        SingletonEventsListModel.getInstance().addEvent(event);
                    } else {
                        Log.i(TAG, "Adding Movie from file " + title);
                        String year = words[2];
                        String poster = words[3];

                        movie = new Movie(id, title, year, poster);
                        SingletonMovieListModel.getInstance().addMovie(movie);
                    }
                }
            }
            // Always close files.
            bufferedReader.close();
            fileReader.close();

        } catch(FileNotFoundException ex) {
            Log.i(TAG, "Unable to open file '" + fileName + "'");
        } catch(IOException ex) {
            Log.i(TAG, "Error reading file '" + fileName + "'");
        }
    }

}
