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
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Simple sample of usage of RecyclerView
 */
public class StaggeredGridActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RecyclerView recyclerView = new RecyclerView(this);
        setContentView(recyclerView);

        // for performance
        recyclerView.setHasFixedSize(true);

        // set layout manager
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        final Random random = new Random();
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            int lines = random.nextInt(5);
            StringBuilder sb = new StringBuilder("Item : " + i);
            for (int j = 0; j < lines; j++) {
                sb.append("\nItem : ").append(i);
            }
            data.add(sb.toString());
        }

        // set adapter
        final SimpleAdapter<String> adapter = new SimpleAdapter<String>(this, data) {
            @Override
            public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                SimpleViewHolder holder = super.onCreateViewHolder(parent, viewType);
                int color = Color.rgb(random.nextInt(128) + 128, random.nextInt(128) + 128, random.nextInt(128) + 128);
                holder.itemView.setBackgroundColor(color);
                return holder;
            }
        };
        recyclerView.setAdapter(adapter);
    }
}
