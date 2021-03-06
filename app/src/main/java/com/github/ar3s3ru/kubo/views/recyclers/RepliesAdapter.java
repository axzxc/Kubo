package com.github.ar3s3ru.kubo.views.recyclers;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.ar3s3ru.kubo.R;
import com.github.ar3s3ru.kubo.backend.models.RepliesList;
import com.github.ar3s3ru.kubo.backend.models.Reply;
import com.github.ar3s3ru.kubo.utils.KuboUtilities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

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

public class RepliesAdapter extends RecyclerView.Adapter<RepliesAdapter.ViewHolder> {

    private static final int VIEWTYPE_THREAD = 0;
    private static final int VIEWTYPE_POST   = 1;

    private final List<Reply> mList = new ArrayList<>();

    private static String mBoard;

    public RepliesAdapter(@NonNull RepliesList list, @NonNull String board) {
        mList.addAll(list.replies);
        mBoard = board;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? VIEWTYPE_THREAD : VIEWTYPE_POST;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEWTYPE_THREAD:
                return new ThreadViewHolder(
                        LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.layout_reply_thread, parent, false)
                );
            case VIEWTYPE_POST:
                return new PostViewHolder(
                        LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.layout_reply_post, parent, false)
                );
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.onBindViewHolder(mList.get(position));
    }

    /**
     * Swap adapter dataset with a new list
     * @param list New adapter dataset
     */
    public void swapList(@NonNull RepliesList list) {
        mList.clear();
        mList.addAll(list.replies);
        // Notify changes to superclass observers
        notifyDataSetChanged();
    }

    /**
     * Update board path
     * @param board New board path
     */
    public void updateBoard(@NonNull String board) {
        mBoard = board;
    }

    /**
     * Abstract class for RepliesAdapter ViewHolder.
     */
    static abstract class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        /**
         * Abstract method for ViewHolder binding (executed into adapter's onBindViewHolder)
         * @param reply Reply object to bind
         */
        abstract void onBindViewHolder(@NonNull Reply reply);
    }

    /**
     * Thread ViewHolder (first element of the Adapter dataset)
     */
    static class ThreadViewHolder extends ViewHolder {

        @BindView(R.id.replies_thread_thumbnail) ImageView thumbnail;
        @BindView(R.id.replies_thread_comment)   TextView  comment;
        @BindView(R.id.replies_thread_name)      TextView  name;
        @BindView(R.id.replies_thread_number)    TextView  number;
        @BindView(R.id.replies_thread_replies)   TextView  replies;

        ThreadViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        void onBindViewHolder(@NonNull Reply reply) {
            // Download thumbnail
            KuboUtilities.downloadImageForHolder(thumbnail, reply, mBoard);

            // Bind views
            comment.setText(Html.fromHtml(reply.comment));
            name.setText(reply.name);
            number.setText(String.format(Locale.ENGLISH, "%d", reply.number));
            replies.setText(Html.fromHtml(reply.timeNow));
        }
    }

    /**
     * Post ViewHolder (all the other elements of the Adapter dataset)
     */
    static class PostViewHolder extends ViewHolder {

        @BindView(R.id.replies_post_thumbnail) ImageView thumbnail;
        @BindView(R.id.replies_post_comment)   TextView  comment;
        @BindView(R.id.replies_post_name)      TextView  name;
        @BindView(R.id.replies_post_number)    TextView  number;

        PostViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        void onBindViewHolder(@NonNull Reply reply) {
            // Download thumbnail
            KuboUtilities.downloadImageForHolder(thumbnail, reply, mBoard);

            // Bind views
            if (reply.comment != null) {
                // Sometimes can be null... go figures
                comment.setText(Html.fromHtml(reply.comment));
            }

            name.setText(reply.name);
            number.setText(String.format(Locale.ENGLISH, "%d", reply.number));
        }
    }
}
