// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.meggnify.component;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class ImageviewNormal extends ImageView {

    public ImageviewNormal(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        attrs.getAttributeValue(0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        Drawable d = getDrawable();
        if (d != null) {
            // ceil not round - avoid thin vertical gaps along the left/right
            // edges
            try {
                float imgHeight = (float) ((BitmapDrawable) d).getBitmap()
                        .getHeight();
                float imgWidth = (float) ((BitmapDrawable) d).getBitmap()
                        .getWidth();
                float pembagi = 1;
                int width = 1;
                int height = 1;// (int)
                pembagi = imgHeight / imgWidth;
                width = MeasureSpec.getSize(widthMeasureSpec);
                height = (int) ((float) width * pembagi);
                setMeasuredDimension(width, height);
            } catch (Exception e) {
                // TODO: handle exception
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);

            }

        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
