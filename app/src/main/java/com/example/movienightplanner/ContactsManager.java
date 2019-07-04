package com.example.movienightplanner;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

public class ContactsManager {


    private static final String LOG_TAG = "ContactsManager";
    private Context context;
    private Intent intent;

    public class ContactQueryException extends Exception {
        private static final long serialVersionUID = 1L;

        public ContactQueryException(String message) {
            super(message);
        }
    }

    public ContactsManager(Context aContext, Intent anIntent) {
        this.context = aContext;
        this.intent = anIntent;
    }

    public String getContactName() throws ContactQueryException {
        Cursor cursor = null;
        String name = null;
        try {
            cursor = context.getContentResolver().query(intent.getData(), null,
                    null, null, null);
            if (cursor.moveToFirst())
                name = cursor.getString(cursor
                        .getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));

        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
            throw new ContactQueryException(e.getMessage());
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return name;
    }

    public String getContactEmail() throws ContactQueryException {
        Cursor cursor = null;
        String email = null;
        try {

            cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                    null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + "=?",
                    new String[]{intent.getData().getLastPathSegment()},
                    null);

            if (cursor.moveToFirst())
                email = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));

        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
            throw new ContactQueryException(e.getMessage());
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return email;
    }
}
