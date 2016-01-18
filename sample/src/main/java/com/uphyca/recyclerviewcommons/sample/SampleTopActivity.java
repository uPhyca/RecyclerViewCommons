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

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.uphyca.recyclerviewcommons.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple sample of usage of RecyclerView
 */
public class SampleTopActivity extends AppCompatActivity {

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
        int dividerMargin = (int) (8 * getResources().getDisplayMetrics().density);
        recyclerView.addItemDecoration(new DividerItemDecoration(Color.GRAY, dividerHeight, dividerMargin));

        List<Item> data = new ArrayList<>();
        data.add(new Item("Linear", new Intent(this, LinearActivity.class)));
        data.add(new Item("Linear Reverse", new Intent(this, LinearReverseActivity.class)));
        data.add(new Item("Linear StackFromEnd", new Intent(this, LinearStackFromEndActivity.class)));
        data.add(new Item("Grid", new Intent(this, GridActivity.class)));
        data.add(new Item("Grid Reverse", new Intent(this, GridReverseActivity.class)));
        data.add(new Item("Grid Span", new Intent(this, GridSpanActivity.class)));
        data.add(new Item("StaggeredGrid", new Intent(this, StaggeredGridActivity.class)));
        data.add(new Item("HeaderFooter", new Intent(this, HeaderFooterActivity.class)));
        data.add(new Item("Paging", new Intent(this, PagingActivity.class)));
        data.add(new Item("ExpandableList", new Intent(this, ExpandableListActivity.class)));

        // set adapter
        final SimpleAdapter<Item> adapter = new SimpleAdapter<Item>(this, data) {
            @Override
            protected void onItemClick(Item item, View view, int position) {
                super.onItemClick(item, view, position);
                startActivity(item.intent);
            }
        };
        recyclerView.setAdapter(adapter);
    }

    private static class Item {
        final String title;
        final Intent intent;

        private Item(String title, Intent intent) {
            this.title = title;
            this.intent = intent;
        }

        @Override
        public String toString() {
            return title;
        }
    }
}
