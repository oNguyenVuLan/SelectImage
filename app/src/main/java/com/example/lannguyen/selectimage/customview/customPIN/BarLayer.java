package com.example.lannguyen.selectimage.customview.customPIN;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.lannguyen.selectimage.R;

import java.util.List;

public class BarLayer extends LinearLayout {
    LayoutInflater mLayoutInflater;
    ViewGroup mViewGroup;

    public BarLayer(Context context) {
        super(context);
        init(context);
    }

    public BarLayer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        ViewGroup v = (ViewGroup) mLayoutInflater.inflate(R.layout.item_bar_layer, this, true);
        mViewGroup = v.findViewById(R.id.content_layout);

    }

    public void addItems(List<ImageLayer> imageLayers) {
        if (mViewGroup != null) {
            for (ImageLayer imageLayer : imageLayers) {
                LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT);
                layoutParams.weight = 1;
                layoutParams.bottomMargin = 5;
                layoutParams.topMargin = 5;
                layoutParams.leftMargin = 5;
                layoutParams.rightMargin = 5;

                imageLayer.setLayoutParams(layoutParams);
                mViewGroup.addView(imageLayer);
            }
        }

    }
}
