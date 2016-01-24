/*
 * Copyright (C) 2016 uPhyca Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.uphyca.recyclerviewcommons.sample;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.uphyca.recyclerviewcommons.ArrayAdapter;

import java.util.List;

public class DragAndDropWithHandleAdapter<T> extends ArrayAdapter<T, ReorderViewHolder> {

    private final LayoutInflater inflater;
    private final int backgroundResId;

    public DragAndDropWithHandleAdapter(@NonNull Context context, @NonNull List<T> data) {
        super(data);
        this.inflater = LayoutInflater.from(context);

        TypedValue outValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, outValue, true);
        backgroundResId = outValue.resourceId;
    }

    protected void onTouchReorder(RecyclerView.ViewHolder holder) {
    }

    @Override
    public ReorderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View v = inflater.inflate(ReorderViewHolder.LAYOUT_ID, parent, false);
        final ReorderViewHolder holder = new ReorderViewHolder(v);
        holder.itemView.setBackgroundResource(backgroundResId);
        holder.reorderView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    onTouchReorder(holder);
                }
                return false;
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ReorderViewHolder holder, int position) {
        T item = getItem(position);
        holder.textView.setText(item.toString());
    }

    public void onDragStart(RecyclerView.ViewHolder viewHolder) {
        viewHolder.itemView.setBackgroundColor(Color.LTGRAY);
    }

    public void onDragStop(RecyclerView.ViewHolder viewHolder) {
        viewHolder.itemView.setBackgroundResource(backgroundResId);
    }
}
