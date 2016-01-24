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
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uphyca.recyclerviewcommons.ArrayAdapter;
import com.uphyca.recyclerviewcommons.DividerItemDecoration;
import com.uphyca.recyclerviewcommons.ExpandableItemStates;

import java.util.ArrayList;
import java.util.List;

/**
 * Usage sample of {@link ExpandableItemStates}
 */
public class ExpandableListActivity extends AppCompatActivity {

    private static final String KEY_EXPANDED_STATE = "expanded_state";

    private ExpandableAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RecyclerView recyclerView = new RecyclerView(this);
        setContentView(recyclerView);

        // for performance
        recyclerView.setHasFixedSize(true);

        // set layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // set divider
        int dividerHeight = (int) (1 * getResources().getDisplayMetrics().density);
        recyclerView.addItemDecoration(new DividerItemDecoration(Color.GRAY, dividerHeight));

        final String lorem = getString(R.string.lorem);
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            data.add(lorem);
        }

        // set adapter
        adapter = new ExpandableAdapter(this, data);
        if (savedInstanceState != null) {
            ArrayList<Integer> positions = savedInstanceState.getIntegerArrayList(KEY_EXPANDED_STATE);
            if (positions != null) {
                adapter.setExpandedPositions(positions);
            }
        }
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putIntegerArrayList(KEY_EXPANDED_STATE, adapter.getExpandedPositions());
    }

    public static class ExpandableAdapter extends ArrayAdapter<String, ViewHolder> {

        private final int p;
        private final int backgroundResId;
        private final ExpandableItemStates expandableItemStates;

        public ExpandableAdapter(@NonNull Context context, @NonNull List<String> data) {
            super(data);
            p = (int) (16 * context.getResources().getDisplayMetrics().density);

            TypedValue outValue = new TypedValue();
            context.getTheme().resolveAttribute(R.attr.selectableItemBackground, outValue, true);
            backgroundResId = outValue.resourceId;

            expandableItemStates = new ExpandableItemStates(this);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            TextView textView = new TextView(parent.getContext());
            textView.setPadding(p, p, p, p);
            textView.setBackgroundResource(backgroundResId);
            textView.setEllipsize(TextUtils.TruncateAt.END);

            final ViewHolder holder = new ViewHolder(textView);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int position = holder.getAdapterPosition();
                    expandableItemStates.toggle(position);
                }
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.textView.setMaxLines(expandableItemStates.isExpanded(position)
                    ? Integer.MAX_VALUE : 3);
            holder.textView.setText(getItem(position));
        }

        @Override
        public void clear() {
            expandableItemStates.clear();
            super.clear();
        }

        @NonNull
        public ArrayList<Integer> getExpandedPositions() {
            return expandableItemStates.getExpandedPositions();
        }

        public void setExpandedPositions(@NonNull ArrayList<Integer> positions) {
            expandableItemStates.setExpandedPositions(positions);
        }
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView;
        }
    }
}
