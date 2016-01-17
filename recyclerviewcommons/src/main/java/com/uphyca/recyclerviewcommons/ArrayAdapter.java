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
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Common Adapter for RecyclerView.
 * This is thread safe for modify data list.
 */
public abstract class ArrayAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    private final Object lock = new Object();
    private final List<T> objects;

    public ArrayAdapter() {
        this(new ArrayList<T>());
    }

    public ArrayAdapter(@NonNull List<T> objects) {
        this.objects = objects;
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    public T getItem(int position) {
        int positionOffset = getPositionOffset();
        if (position < positionOffset) {
            return null;
        }
        position -= positionOffset;
        synchronized (lock) {
            if (position < objects.size()) {
                return objects.get(position);
            }
        }
        return null;
    }

    /**
     * return position offset for data like header counts.
     */
    protected int getPositionOffset() {
        return 0;
    }

    /**
     * Adds the specified object at the end of the array.
     *
     * @param object The object to add at the end of the array.
     */
    public void add(@NonNull T object) {
        final int position;
        synchronized (lock) {
            position = objects.size() + getPositionOffset();
            objects.add(object);
        }
        notifyItemInserted(position);
    }

    /**
     * Adds the specified Collection at the end of the array.
     *
     * @param collection The Collection to add at the end of the array.
     */
    public void addAll(@NonNull Collection<? extends T> collection) {
        final int itemCount = collection.size();
        final int position;
        synchronized (lock) {
            position = objects.size() + getPositionOffset();
            objects.addAll(collection);
        }
        notifyItemRangeInserted(position, itemCount);
    }

    /**
     * Adds the specified items at the end of the array.
     *
     * @param items The items to add at the end of the array.
     */
    public void addAll(T... items) {
        final int itemCount = items.length;
        final int position;
        synchronized (lock) {
            position = objects.size() + getPositionOffset();
            Collections.addAll(objects, items);
        }
        notifyItemRangeInserted(position, itemCount);
    }

    /**
     * Inserts the specified object at the specified index in the array.
     *
     * @param object   The object to insert into the array.
     * @param position The index at which the object must be inserted.
     */
    public void insert(@NonNull T object, int position) {
        synchronized (lock) {
            objects.add(position - getPositionOffset(), object);
        }
        notifyItemInserted(position);
    }

    /**
     * Removes the specified object from the array.
     *
     * @param object The object to remove.
     */
    public void remove(@NonNull T object) {
        final int position;
        final boolean isRemoved;
        synchronized (lock) {
            position = objects.indexOf(object) + getPositionOffset();
            isRemoved = objects.remove(object);
        }
        if (isRemoved) {
            notifyItemRemoved(position);
        }
    }

    /**
     * Removes the specified object at the specified index from the array.
     *
     * @param position The index at which the object must be removed.
     */
    public T remove(int position) {
        T prev;
        synchronized (lock) {
            prev = objects.remove(position - getPositionOffset());
        }
        notifyItemRemoved(position);
        return prev;
    }

    /**
     * Moves the specified object
     *
     * @param from The index at which the object must be moved.
     * @param to   The destination index.
     */
    public void move(int from, int to) {
        synchronized (lock) {
            T prev = objects.remove(from - getPositionOffset());
            objects.add(to - getPositionOffset(), prev);
        }
        notifyItemMoved(from, to);
    }

    /**
     * Remove all elements from the list.
     */
    public void clear() {
        final int itemCount;
        synchronized (lock) {
            itemCount = objects.size();
            objects.clear();
        }
        notifyItemRangeRemoved(getPositionOffset(), itemCount);
    }

    public boolean isEmpty() {
        return objects.isEmpty();
    }
}
