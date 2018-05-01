package com.example.lannguyen.selectimage;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.lannguyen.selectimage.customview.BarLayer;
import com.example.lannguyen.selectimage.customview.ImageLayer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ImageLayer.OnImageListener {

    private static final int SELECT_IMAGE = 1;
    ImageView mImageView;
    BarLayer mBarLayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = findViewById(R.id.image_selection);
        mBarLayer = findViewById(R.id.bar_layer);
        addBar();
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select picture"), SELECT_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            Bitmap bitmap;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                if (!isBiggerThan512Kb(data.getData())) {
                    mImageView.setImageBitmap(bitmap);
                } else {
                    Toast.makeText(this, "image size should be smaller than 512 Kb", Toast.LENGTH_SHORT).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private boolean isBiggerThan512Kb(Uri uri) {
        InputStream fileInputStream = null;
        try {
            fileInputStream = getApplicationContext().getContentResolver().openInputStream(uri);
            double dataSize = 0;
            if (fileInputStream != null) {
                dataSize = fileInputStream.available();
            }
            Toast.makeText(this, "size : " + dataSize, Toast.LENGTH_SHORT).show();
            return dataSize > 512 * 1024;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void addBar() {
        List<ImageLayer> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            ImageLayer imageLayer = new ImageLayer(this);
            imageLayer.position = i;
            imageLayer.setOnImageListener(this);
            list.add(imageLayer);
        }
        mBarLayer.addItems(list);
    }

    @Override
    public void onImageItemClick(int position) {
        Toast.makeText(this, ""+position, Toast.LENGTH_SHORT).show();
    }
}
