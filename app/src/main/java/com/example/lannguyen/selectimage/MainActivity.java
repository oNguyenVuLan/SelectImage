package com.example.lannguyen.selectimage;

import android.app.Activity;
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
    EditText textInput1, textInput2, textInput3, textInput4, textInput5, textInput6, textHidden;
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            if (s.length() == 0) {
                textInput1.setText("");
                textInput2.setText("");
                textInput3.setText("");
                textInput4.setText("");
                textInput5.setText("");
                textInput6.setText("");
            } else if (s.length() == 1) {
                textInput1.setText(s.charAt(0) + "");
                textInput2.setText("");
                textInput3.setText("");
                textInput4.setText("");
                textInput5.setText("");
                textInput6.setText("");
            } else if (s.length() == 2) {
                textInput2.setText(s.charAt(1) + "");
                textInput3.setText("");
                textInput4.setText("");
                textInput5.setText("");
                textInput6.setText("");
            } else if (s.length() == 3) {
                textInput3.setText(s.charAt(2) + "");
                textInput4.setText("");
                textInput5.setText("");
                textInput6.setText("");
            } else if (s.length() == 4) {
                textInput4.setText(s.charAt(3) + "");
                textInput5.setText("");
                textInput6.setText("");
            } else if (s.length() == 5) {
                textInput5.setText(s.charAt(4) + "");
                textInput6.setText("");
            } else if (s.length() == 6) {
                textInput6.setText(s.charAt(5) + "");
            }
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

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
        findView();
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

    private void findView() {
        textInput1 = findViewById(R.id.input_1);
        textInput2 = findViewById(R.id.input_2);
        textInput3 = findViewById(R.id.input_3);
        textInput4 = findViewById(R.id.input_4);
        textInput5 = findViewById(R.id.input_5);
        textInput6 = findViewById(R.id.input_6);
        textHidden = findViewById(R.id.hidden_input);
    }

    private void setEditTextListener() {
        textHidden.addTextChangedListener(textWatcher);
    }
}
