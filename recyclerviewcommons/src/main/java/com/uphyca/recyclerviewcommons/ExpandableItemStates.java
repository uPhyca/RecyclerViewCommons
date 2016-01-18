package com.uphyca.recyclerviewcommons;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;

import java.util.ArrayList;

/**
 * Managing expanded state of RecyclerView.Adapter items.
 * This class is intended to use inside of your RecyclerView.Adapter implementation.
 */
public class ExpandableItemStates {

    @NonNull
    private final SparseBooleanArray expandedStates = new SparseBooleanArray();

    @NonNull
    private final RecyclerView.Adapter adapter;

    private final boolean allowMultiExpand;

    public ExpandableItemStates(@NonNull RecyclerView.Adapter adapter) {
        this(adapter, false);
    }

    public ExpandableItemStates(@NonNull RecyclerView.Adapter adapter, boolean allowMultiExpand) {
        this.adapter = adapter;
        this.allowMultiExpand = allowMultiExpand;
    }

    public boolean isExpanded(int position) {
        return expandedStates.get(position, false);
    }

    public void toggle(int position) {
        boolean isExpanded = expandedStates.get(position, false);
        isExpanded = !isExpanded;
        if (isExpanded && !allowMultiExpand) {
            collapseAll();
        }
        expandedStates.put(position, isExpanded);
        adapter.notifyItemChanged(position);
    }

    public void expand(int position) {
        boolean isExpanded = expandedStates.get(position, false);
        if (!isExpanded) {
            if (!allowMultiExpand) {
                collapseAll();
            }
            expandedStates.put(position, true);
            adapter.notifyItemChanged(position);
        }
    }

    public void collapse(int position) {
        boolean isExpanded = expandedStates.get(position, false);
        if (isExpanded) {
            expandedStates.put(position, false);
            adapter.notifyItemChanged(position);
        }
    }

    public void collapseAll() {
        final int size = expandedStates.size();
        for (int i = 0; i < size; i++) {
            if (expandedStates.valueAt(i)) {
                int p = expandedStates.keyAt(i);
                expandedStates.put(p, false);
                adapter.notifyItemChanged(p);
            }
        }
        expandedStates.clear();
    }

    /**
     * Should call when adapter's item be cleared.
     */
    public void clear() {
        expandedStates.clear();
    }

    @NonNull
    public ArrayList<Integer> getExpandedPositions() {
        ArrayList<Integer> positions = new ArrayList<>();
        final int size = expandedStates.size();
        for (int i = 0; i < size; i++) {
            if (expandedStates.valueAt(i)) {
                positions.add(expandedStates.keyAt(i));
            }
        }
        return positions;
    }

    public void setExpandedPositions(@NonNull ArrayList<Integer> positions) {
        expandedStates.clear();
        for (int position : positions) {
            expandedStates.put(position, true);
        }
        adapter.notifyDataSetChanged();
    }
}
