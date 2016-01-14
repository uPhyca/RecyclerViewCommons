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
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.uphyca.recyclerviewcommons.EndlessScrollListener;
import com.uphyca.recyclerviewcommons.SimpleDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple sample of usage of RecyclerView
 */
public class PagingActivity extends AppCompatActivity {

    private SimpleHeaderFooterAdapter<String> adapter;
    private boolean hasNextPage = false;

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
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(Color.GRAY, dividerHeight));

        recyclerView.addOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onScrollToLast() {
                if (hasNextPage) {
                    hasNextPage = false;
                    mockLoadData();
                }
            }
        });

        // set adapter
        int height = (int) (72 * getResources().getDisplayMetrics().density);
        FrameLayout footer = new FrameLayout(this);
        footer.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));

        ProgressBar progressBar = new ProgressBar(this);
        final FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        progressBar.setLayoutParams(params);
        footer.addView(progressBar);

        adapter = new SimpleHeaderFooterAdapter<>(this, new ArrayList<String>(), null, footer);
        recyclerView.setAdapter(adapter);

        mockLoadData();
    }

    private void mockLoadData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.hideFooter();
                List<String> data = new ArrayList<>();
                for (int i = 0; i < 15; i++) {
                    data.add("Item : " + i);
                }
                adapter.addAll(data);

                hasNextPage = true;
                adapter.showFooter();
            }
        }, 1000);
    }
}
