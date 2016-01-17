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

package com.uphyca.recyclerviewcommons;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter with single header, footer
 */
public abstract class HeaderFooterAdapter<T, VH extends RecyclerView.ViewHolder> extends ArrayAdapter<T, RecyclerView.ViewHolder> {

    protected abstract VH onCreateItemViewHolder(ViewGroup parent);

    protected abstract void onBindItemViewHolder(VH holder, int position);

    public static final int ITEM_VIEW_TYPE_ITEM = 0;
    public static final int ITEM_VIEW_TYPE_HEADER = 1;
    public static final int ITEM_VIEW_TYPE_FOOTER = 2;

    @Nullable
    private final View headerView;
    @Nullable
    private final View footerView;

    private boolean showFooter;

    public HeaderFooterAdapter(View headerView, View footerView) {
        this(new ArrayList<T>(), headerView, footerView);
    }

    public HeaderFooterAdapter(@NonNull List<T> objects, @Nullable View headerView, @Nullable View footerView) {
        super(objects);
        this.headerView = headerView;
        this.footerView = footerView;
        showFooter = footerView != null;
    }

    @Override
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ITEM_VIEW_TYPE_HEADER:
                return new HeaderFooterHolder(headerView);
            case ITEM_VIEW_TYPE_FOOTER:
                return new HeaderFooterHolder(footerView);
            case ITEM_VIEW_TYPE_ITEM:
                return onCreateItemViewHolder(parent);
        }
        return null;
    }

    @Override
    public final void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == ITEM_VIEW_TYPE_ITEM) {
            //noinspection unchecked
            onBindItemViewHolder((VH) holder, position);
        }
    }

    public void showFooter() {
        if (!showFooter) {
            showFooter = footerView != null;
            notifyItemInserted(getHeadersCount() + getAdapterItemCount());
        }
    }

    public void hideFooter() {
        if (showFooter) {
            showFooter = false;
            notifyItemRemoved(getHeadersCount() + getAdapterItemCount());
        }
    }

    private int getHeadersCount() {
        return headerView != null ? 1 : 0;
    }

    private int getFootersCount() {
        return showFooter ? 1 : 0;
    }

    private int getAdapterItemCount() {
        return super.getItemCount();
    }

    @Override
    public final int getItemCount() {
        return getHeadersCount() + getFootersCount() + getAdapterItemCount();
    }

    @Override
    public final int getItemViewType(int position) {
        int numHeaders = getHeadersCount();
        if (position < numHeaders) {
            return ITEM_VIEW_TYPE_HEADER;
        }
        int itemPosition = position - numHeaders;
        if (itemPosition < getAdapterItemCount()) {
            return ITEM_VIEW_TYPE_ITEM;
        }
        return ITEM_VIEW_TYPE_FOOTER;
    }

    @Override
    protected int getPositionOffset() {
        return getHeadersCount();
    }

    private static class HeaderFooterHolder extends RecyclerView.ViewHolder {

        public HeaderFooterHolder(View itemView) {
            super(itemView);
        }
    }
}
