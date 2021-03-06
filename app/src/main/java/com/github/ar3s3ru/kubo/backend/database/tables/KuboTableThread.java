package com.github.ar3s3ru.kubo.backend.database.tables;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;

import com.github.ar3s3ru.kubo.backend.database.KuboSQLHelper;
import com.github.ar3s3ru.kubo.backend.models.Modification;
import com.github.ar3s3ru.kubo.backend.models.Reply;
import com.github.ar3s3ru.kubo.backend.net.KuboEvents;

import java.util.TreeSet;

/**
 * Copyright (C) 2016  Danilo Cianfrone
 * <p>
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301  USA
 */

// TODO: whole Javadoc
public class KuboTableThread {

    private static final String TABLE_NAME = "thread";

    private static final String KEY_BOARD   = "board";
    private static final String KEY_NUMBER  = "number";
    private static final String KEY_COMMENT = "comment";
    private static final String KEY_IMAGE   = "image";
    private static final String KEY_EXTENS  = "extens";
    private static final String KEY_LASTUP  = "last_up";
    private static final String KEY_AUTHOR  = "author";

    private static final String DB_BOARD   = KEY_BOARD   + " text not null";
    private static final String DB_NUMBER  = KEY_NUMBER  + " integer not null unique";
    private static final String DB_COMMENT = KEY_COMMENT + " text";
    private static final String DB_IMAGE   = KEY_IMAGE   + " integer not null unique";
    private static final String DB_EXTENS  = KEY_EXTENS  + " text not null";
    private static final String DB_LASTUP  = KEY_LASTUP  + " integer not null";
    private static final String DB_AUTHOR  = KEY_AUTHOR  + " text not null";

    public static final String DB_DROP   = "DROP TABLE IF EXISTS " + TABLE_NAME;
    public static final String DB_CREATE = "create table " + TABLE_NAME
            + " (_id integer primary key autoincrement, "
            + DB_BOARD  + ", " + DB_NUMBER  + ", " + DB_COMMENT + ", " + DB_IMAGE  + ", "
            + DB_EXTENS + ", " + DB_LASTUP  + ", " + DB_AUTHOR  + ");";

    /**
     * Getters
     */

    /**
     * Get followed threads saved into the app database
     * @param helper SQLite application helper instance
     * @return Cursor with all the followed threads of the database
     */
    public static Cursor getFollowedThreads(@NonNull KuboSQLHelper helper) {
        return helper.getReadableDatabase().query(
                TABLE_NAME, null, null, null, null, null, null
        );
    }

    /**
     * Build a TreeSet of followed thread numbers for adapter usage
     * @param helper SQLite application helper instance
     * @return TreeSet with all the followed thread numbers
     */
    public static TreeSet<Integer> getFollowedThreadsSet(@NonNull KuboSQLHelper helper) {
        final Cursor cursor = getFollowedThreads(helper);
        final TreeSet<Integer> set = new TreeSet<>();

        for (int i = 0; i < cursor.getCount(); i++) {
            set.add(getThreadNumber(cursor, i));
        }

        cursor.close();
        return set;
    }

    /**
     * Get Thread board path
     * @param cursor Followed threads' cursor
     * @param position Current cursor position
     * @return Return thread's board path into the cursor at the specified position
     */
    public static String getThreadBoard(@NonNull Cursor cursor, int position) {
        cursor.moveToPosition(position);
        return cursor.getString(cursor.getColumnIndex(KEY_BOARD));
    }

    /**
     * Thread number getter
     * @param cursor Followed threads' cursor
     * @param position Current cursor position
     * @return Return thread number from the cursor at the specified position
     */
    public static int getThreadNumber(@NonNull Cursor cursor, int position) {
        cursor.moveToPosition(position);
        return cursor.getInt(cursor.getColumnIndex(KEY_NUMBER));
    }

    /**
     * Thread comment getter
     * @param cursor Followed threads' cursor
     * @param position Current cursor position
     * @return Return thread comment from the cursor at the specified position
     */
    public static String getThreadComment(@NonNull Cursor cursor, int position) {
        cursor.moveToPosition(position);
        return cursor.getString(cursor.getColumnIndex(KEY_COMMENT));
    }

    /**
     * Thread filename getter
     * @param cursor Followed threads' cursor
     * @param position Current cursor position
     * @return Return thread filename from the cursor at the specified position
     */
    public static long getThreadProperFilename(@NonNull Cursor cursor, int position) {
        cursor.moveToPosition(position);
        return cursor.getLong(cursor.getColumnIndex(KEY_IMAGE));
    }

    /**
     * Thread file extension getter
     * @param cursor Followed threads' cursor
     * @param position Current cursor position
     * @return Return thread file extension from the cursor at the specified position
     */
    public static String getFileExtension(@NonNull Cursor cursor, int position) {
        cursor.moveToPosition(position);
        return cursor.getString(cursor.getColumnIndex(KEY_EXTENS));
    }

    /**
     * Thread last update getter
     * @param cursor Followed threads' cursor
     * @param position Current cursor position
     * @return Return thread last update from the cursor at the specified position
     */
    public static long getLastUpdate(@NonNull Cursor cursor, int position) {
        cursor.moveToPosition(position);
        return cursor.getLong(cursor.getColumnIndex(KEY_LASTUP));
    }

    /**
     * Thread author getter
     * @param cursor Followed threads' cursor
     * @param position Current cursor position
     * @return Return thread author from the cursor at the specified position
     */
    public static String getAuthor(@NonNull Cursor cursor, int position) {
        cursor.moveToPosition(position);
        return cursor.getString(cursor.getColumnIndex(KEY_AUTHOR));
    }

    /**
     * Setters
     */

    /**
     * Set the specified thread as followed
     * @param helper SQLite application helper instance
     * @param thread Thread object (it's Reply extension because we use Reply fields)
     * @param board Board path
     * @return New row id
     */
    public static <T extends Reply> long setFollowingThread(@NonNull KuboSQLHelper helper,
                                                            @NonNull T thread,
                                                            @NonNull String board) {
        final ContentValues cv = new ContentValues();

        cv.put(KEY_BOARD,   board);
        cv.put(KEY_NUMBER,  thread.number);
        cv.put(KEY_COMMENT, thread.comment);
        cv.put(KEY_IMAGE,   thread.properFilename);
        cv.put(KEY_EXTENS,  thread.fileExtension);
        cv.put(KEY_LASTUP,  thread.UNIXtime);
        cv.put(KEY_AUTHOR,  thread.name);

        return helper.getWritableDatabase().insertOrThrow(TABLE_NAME, null, cv);
    }

    /**
     * Delete the specified thread from the database following threads
     * @param helper SQLite application helper instance
     * @param threadNumber Thread number to remove from following
     * @return Number of rows affected by the opertaion
     */
    public static int setUnfollowingThread(@NonNull KuboSQLHelper helper, int threadNumber) {
        return helper.getWritableDatabase().delete(
                TABLE_NAME, KEY_NUMBER + "=" + threadNumber, null
        );
    }

    /**
     * Refresh thread's last update time
     * @param helper SQLite application helper instance
     * @param mod Modification object (which contains thread identification and last update)
     * @return Number of rows affected by the opertaion
     */
    public static int updateLastUpdated(@NonNull KuboSQLHelper helper, @NonNull Modification mod) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_LASTUP, mod.lastModified);

        return helper.getWritableDatabase().update(
            TABLE_NAME, cv, KEY_NUMBER + "=" + mod.threadNumber, null
        );
    }

    /**
     * Notify the application PushService and other components listening that following threads
     * has been updated, therefore all the actual queries needs to be re-done
     * @param context Application context to send local broadcast message
     */
    // TODO: move it
    public static void notifyFollowingThreadsChanged(@NonNull Context context) {
        LocalBroadcastManager.getInstance(context)
                .sendBroadcast(new Intent(KuboEvents.FOLLOWING_UPDATE));
    }
}
