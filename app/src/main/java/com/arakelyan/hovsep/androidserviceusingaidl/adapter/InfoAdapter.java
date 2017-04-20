/*
 * Copyright (c) 2017. Truiton (http://www.truiton.com/).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 * Mohit Gupt (https://github.com/mohitgupt)
 *
 */

package com.arakelyan.hovsep.androidserviceusingaidl.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.arakelyan.hovsep.androidserviceusingaidl.FileInfo;
import com.arakelyan.hovsep.androidserviceusingaidl.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jrara_000 on 18.04.2017.
 */

public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.ViewHolder> {

    private final List<FileInfo> mValues;
    private final OnItemClickListener mListener;
    Context mContext;

    public InfoAdapter(Context context, OnItemClickListener listener) {
        mValues = new ArrayList<>();
        mContext = context;
        mListener = listener;
    }

    public void swapData(List<FileInfo> items) {
        mValues.clear();
        mValues.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.info_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // mListener.onclickItem(position,mValues.get(position));
               /* DBHelper dbHelper = new DBHelper(mContext);
                int newClickCount = 1 + Integer.parseInt(mValues.get(position).getClickedCount());
                dbHelper.updateClickCount(mValues.get(position).getId(), newClickCount);
                mValues.get(position).setClickedCount(String.valueOf(newClickCount));
                notifyDataSetChanged();*/

                mListener.onclickItem(position,mValues.get(position));
                int newClickCount = 1 + Integer.parseInt(mValues.get(position).getClickedCount());
                mValues.get(position).setClickedCount(String.valueOf(newClickCount));


            }
        });
        holder.name.setText("name: " + mValues.get(position).getName());
        holder.path.setText("path: " + mValues.get(position).getPath());
        holder.date.setText("date: " + mValues.get(position).getCreatedDate());
        holder.clickCount.setText("click count: " + mValues.get(position).getClickedCount());
    }


    @Override
    public int getItemCount() {
        return mValues.size();
    }


    private void deleteByPosition(int position) {
        mValues.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mValues.size());


    }

    public interface OnItemClickListener {
        void onclickItem(int position, FileInfo fileInfo);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView path;
        public TextView date;
        public TextView clickCount;
        public ImageView deleteItem;


        public ViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            path = (TextView) view.findViewById(R.id.path);
            date = (TextView) view.findViewById(R.id.date);
            clickCount = (TextView) view.findViewById(R.id.clickCount);
        }


    }
}