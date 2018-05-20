package com.example.lannguyen.selectimage;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lannguyen.selectimage.customview.customImage.BarLayer;
import com.example.lannguyen.selectimage.customview.customImage.ImageLayer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements ImageLayer.OnImageListener {

    private static final int SELECT_IMAGE = 1;
    ImageView mImageView;
    BarLayer mBarLayer;
    EditText textHere;


    public InputFilter getEditTextFilter() {
        return new InputFilter() {

            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

                boolean keepOriginal = true;
                StringBuilder sb = new StringBuilder(end - start);
                for (int i = start; i < end; i++) {
                    char c = source.charAt(i);
                    if (isCharAllowed(c)) // put your condition here
                        sb.append(c);
                    else
                        keepOriginal = false;
                }
                if (keepOriginal)
                    return null;
                else {
                    if (source instanceof Spanned) {
                        SpannableString sp = new SpannableString(sb);
                        TextUtils.copySpansFrom((Spanned) source, start, sb.length(), null, sp, 0);
                        return sp;
                    } else {
                        return sb;
                    }
                }
            }

            private boolean isCharAllowed(char c) {
                Pattern ps = Pattern.compile("^[a-zA-Z ]+$");
                Matcher ms = ps.matcher(String.valueOf(c));
                return ms.matches();
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = findViewById(R.id.image_selection);
        mBarLayer = findViewById(R.id.bar_layer);
        textHere = findViewById(R.id.account_owner);
        textHere.setFilters(new InputFilter[]{getEditTextFilter()});
        // addBar();
//        mImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openGallery();
//            }
//        });
    }

    private void filterText() {
        Pattern ps = Pattern.compile("^[a-zA-Z ]+$");
        Matcher ms = ps.matcher(textHere.getText().toString());
        boolean bs = ms.matches();
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
        Toast.makeText(this, "" + position, Toast.LENGTH_SHORT).show();
    }


}
