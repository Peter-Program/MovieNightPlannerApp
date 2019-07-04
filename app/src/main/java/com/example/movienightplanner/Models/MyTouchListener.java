package com.example.movienightplanner.Models;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public abstract class MyTouchListener implements View.OnTouchListener {
    private static final String TAG = "MyTouchListener";

    private GestureDetector gestureDetector;

    MyTouchListener(Context c) {
        gestureDetector = new GestureDetector(c, new MyGestureListener());
    }

    public boolean onTouch(final View view, final MotionEvent motionEvent) {
        return gestureDetector.onTouchEvent(motionEvent);
    }

    private final class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        // The return of onDown must return true for the gestures to work
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            onTap();
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            onLongTap();
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            onDoublePress();
            return true;
        }

        // Determine the fling direction
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            float diffY = e2.getY() - e1.getY(); // Getting the different Y position from the end pos minus the start pos
            float diffX = e2.getX() - e1.getX(); // Getting the different X position from the end pos minus the start pos

            // Checking if moved in the X direction
            if (Math.abs(diffX) > Math.abs(diffY)) {
                // Checking if the movement is greater then the specified thresholds (we don't want to have this trigger
                // from slight movements of the finger)
                if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    // Checking if moved to the Right else it would have to be to the Left
                    if (diffX > 0) {
                        onSwipeRight();
                    } else {
                        onSwipeLeft();
                    }
                }
                // Must have moved in the Y direction instead
            } else {
                // Checking if the movement is greater then the specified thresholds
                if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    // Must have moved Down, else Must have move Up
                    if (diffY > 0) {
                        onSwipeDown();
                    } else {
                        onSwipeUp();
                    }
                }
            }

            return result;
        }
    }

    // These are the methods that you want to Override when having this class with the setOnTouchListener for your View
    void onSwipeRight() {
        Log.i(TAG, "Swiped Right");
    }

    void onSwipeLeft() {
        Log.i(TAG, "Swiped Left");
    }

    void onSwipeUp() {
        Log.i(TAG, "Swiped Up");
    }

    void onSwipeDown() {
        Log.i(TAG, "Swiped Down");
    }

    void onTap() {
        Log.i(TAG, "Tap");
    }

    void onLongTap() {
        Log.i(TAG, "Long Tap");
    }

    void onDoublePress() {
        Log.i(TAG, "Double Press");
    }
}
