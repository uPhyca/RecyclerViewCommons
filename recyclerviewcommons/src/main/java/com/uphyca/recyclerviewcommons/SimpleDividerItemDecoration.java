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

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Draw divider for vertical non-reverse LinearLayoutManager.
 */
public class SimpleDividerItemDecoration extends RecyclerView.ItemDecoration {

    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final int dividerHeight;
    private final int dividerMargin;
    private int dividerStartPosition = 0;

    public SimpleDividerItemDecoration(int color, int height) {
        this(color, height, 0);
    }

    public SimpleDividerItemDecoration(int color, int height, int margin) {
        this.dividerHeight = height;
        this.dividerMargin = margin;
        paint.setColor(color);
    }

    public void setDividerStartPosition(int position) {
        dividerStartPosition = position;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);

        final RecyclerView.LayoutManager manager = parent.getLayoutManager();

        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();

        final int childCount = parent.getChildCount();
        if (childCount < 2) {
            return;
        }

        for (int i = 1; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            if (params.getViewLayoutPosition() == 0) {
                continue;
            }
            // ViewCompat.getTranslationY() needs for add/delete animation
            final int top = manager.getDecoratedTop(child)
                    - params.topMargin
                    + Math.round(ViewCompat.getTranslationY(child));
            final int bottom = top + dividerHeight;
            c.drawRect(left + dividerMargin, top, right - dividerMargin, bottom, paint);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) view.getLayoutParams();
        int position = params.getViewLayoutPosition();
        int top = position > dividerStartPosition ? dividerHeight : 0;
        // offset for divider
        if (outRect.top < top) {
            outRect.top = top;
        }
    }
}
