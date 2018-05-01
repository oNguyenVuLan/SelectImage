package com.example.lannguyen.selectimage.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.lannguyen.selectimage.R;

public class ImageLayer extends RelativeLayout {

    LayoutInflater mLayoutInflater;
    public int position;
    public OnImageListener mOnImageListener;

    public ImageLayer(Context context) {
        super(context);
        init(context);
    }

    public ImageLayer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void setOnImageListener(OnImageListener onImageListener) {
        mOnImageListener = onImageListener;
    }

    private void init(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        View v = mLayoutInflater.inflate(R.layout.item_image_layer, this, true);
        ImageView imageView = v.findViewById(R.id.image_view_adding);
        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnImageListener != null) {
                    mOnImageListener.onImageItemClick(position);
                }
            }
        });

    }

    public interface OnImageListener {
        void onImageItemClick(int position);
    }
}
