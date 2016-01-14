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
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uphyca.recyclerviewcommons.HeaderFooterAdapter;

import java.util.List;

public class SimpleHeaderFooterAdapter<T> extends HeaderFooterAdapter<T, SimpleViewHolder> {

    private final LayoutInflater inflater;
    private final int backgroundResId;

    public SimpleHeaderFooterAdapter(@NonNull Context context, @NonNull List<T> data, View header, View footer) {
        super(data, header, footer);
        this.inflater = LayoutInflater.from(context);

        TypedValue outValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, outValue, true);
        backgroundResId = outValue.resourceId;
    }

    protected void onItemClick(T item, View view, int position) {
    }

    @Override
    protected SimpleViewHolder onCreateItemViewHolder(ViewGroup parent) {
        final View v = inflater.inflate(SimpleViewHolder.LAYOUT_ID, parent, false);
        final SimpleViewHolder holder = new SimpleViewHolder(v);
        holder.itemView.setBackgroundResource(backgroundResId);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int position = holder.getAdapterPosition();
                final T item = getItem(position);
                onItemClick(item, v, position);
            }
        });
        return holder;
    }

    @Override
    protected void onBindItemViewHolder(SimpleViewHolder holder, int position) {
        T item = getItem(position);
        holder.textView.setText(item.toString());
    }
}
