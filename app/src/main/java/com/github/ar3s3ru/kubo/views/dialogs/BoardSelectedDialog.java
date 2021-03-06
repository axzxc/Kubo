package com.github.ar3s3ru.kubo.views.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.github.ar3s3ru.kubo.R;

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

public class BoardSelectedDialog extends DialogFragment implements DialogInterface.OnClickListener {

    /** Arguments keys */
    private static final String ID    = "com.github.ar3s3ru.kubo.views.dialogs.boards.id";
    private static final String POS   = "com.github.ar3s3ru.kubo.views.dialogs.boards.pos";
    private static final String STAR  = "com.github.ar3s3ru.kubo.views.dialogs.boards.star";
    private static final String TITLE = "com.github.ar3s3ru.kubo.views.dialogs.boards.title";

    public static final String TAG = "BoardSelectedDialog";

    /** Members variables */
    private boolean  isBoardFavorited;
    private String   mTitle;

    private int mID;
    private int mPosition;

    public static BoardSelectedDialog newInstance(int id, int position, boolean favorite,
                                                  @NonNull String title) {

        BoardSelectedDialog dialog = new BoardSelectedDialog();
        Bundle args = new Bundle();

        // Adding arguments
        args.putInt(ID, id);
        args.putInt(POS, position);
        args.putBoolean(STAR, favorite);
        args.putString(TITLE, title);

        // Setting up arguments
        dialog.setArguments(args);

        return dialog;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(ID, mID);
        outState.putInt(POS, mPosition);
        outState.putBoolean(STAR, isBoardFavorited);
        outState.putString(TITLE, mTitle);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle takeArgs = getArguments();
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        if (savedInstanceState != null) {
            takeArgs = savedInstanceState;
        }

        // Get arguments
        mID           = takeArgs.getInt(ID);
        mTitle        = takeArgs.getString(TITLE);
        mPosition     = takeArgs.getInt(POS);
        isBoardFavorited = takeArgs.getBoolean(STAR);

        builder.setTitle(mTitle)
                .setNegativeButton(R.string.text_close, null);

        if (isBoardFavorited) {
            // Select starred board options
            builder.setItems(R.array.favorite_board_selected_actions, this);
        }
        else {
            // Select unstarred board options
            builder.setItems(R.array.unfavorite_board_selected_actions, this);
        }

        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        // BoardsActivity MUST implement Listener
        final Listener mListener = (Listener) getActivity();

        if (which == 0) {
            // Open selected board
            mListener.onGoToSelected(mTitle, isBoardFavorited, mPosition);
        } else if (isBoardFavorited) {
            // Unstarring
            mListener.onUnfavoriteSelected(mID, mPosition);
        } else {
            // Starring
            mListener.onFavoriteSelected(mID, mPosition);
        }
    }

    public interface Listener {
        void onGoToSelected(String title, boolean favorited, int position);
        void onFavoriteSelected(int id, int position);
        void onUnfavoriteSelected(int id, int position);
    }
}
