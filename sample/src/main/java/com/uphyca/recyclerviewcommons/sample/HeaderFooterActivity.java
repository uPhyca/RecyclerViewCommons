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

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uphyca.recyclerviewcommons.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple sample of usage of RecyclerView
 */
public class HeaderFooterActivity extends AppCompatActivity {

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

        List<String> data = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            data.add("Item : " + i);
        }

        // set adapter
        int height = (int) (100 * getResources().getDisplayMetrics().density);
        TextView header = new TextView(this);
        header.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
        header.setGravity(Gravity.CENTER);
        header.setText("header");
        header.setBackgroundColor(Color.LTGRAY);

        TextView footer = new TextView(this);
        footer.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
        footer.setGravity(Gravity.CENTER);
        footer.setText("footer");
        footer.setBackgroundColor(Color.LTGRAY);

        final SimpleHeaderFooterAdapter<String> adapter = new SimpleHeaderFooterAdapter<>(this, data, header, footer);
        recyclerView.setAdapter(adapter);
    }
}
