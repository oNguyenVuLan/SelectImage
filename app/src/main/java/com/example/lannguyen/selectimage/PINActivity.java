package com.example.lannguyen.selectimage;

import android.app.Service;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class PINActivity extends AppCompatActivity implements View.OnKeyListener, View.OnFocusChangeListener {

    EditText textInput1, textInput2, textInput3, textInput4, textInput5, textInput6, textHidden;
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
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
                hideSoftKeyboard(textInput6);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);
        findView();
        setEditTextListener();
        textHidden.addTextChangedListener(textWatcher);
    }

    private void setFocus(EditText editText) {
        if (editText == null) {
            return;
        }
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
    }

    private void showSoftKeyboard(EditText editText) {
        if (editText == null) {
            return;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Service.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.showSoftInput(editText, 0);
        }
    }

    private void hideSoftKeyboard(EditText editText) {
        if (editText == null) {
            return;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Service.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        }
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

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        switch (view.getId()) {
            case R.id.input_1:
            case R.id.input_2:
            case R.id.input_3:
            case R.id.input_4:
            case R.id.input_5:
            case R.id.input_6:
                if (hasFocus) {
                    setFocus(textHidden);
                    showSoftKeyboard(textHidden);
                }
                break;
        }
    }

    @Override
    public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
        if (keyCode == KeyEvent.KEYCODE_DEL) {
            if (textHidden.getText().length() == 6) {
                textInput6.setText("");
            } else if (textHidden.getText().length() == 5) {
                textInput5.setText("");
            } else if (textHidden.getText().length() == 4) {
                textInput4.setText("");
            } else if (textHidden.getText().length() == 3) {
                textInput3.setText("");
            } else if (textHidden.getText().length() == 2) {
                textInput2.setText("");
            } else if (textHidden.getText().length() == 1) {
                textInput1.setText("");
            }

            if (textHidden.length() > 0) {
                textHidden.setText(textHidden.getText().subSequence(0, textHidden.length() - 1));
                textHidden.setSelection(textHidden.getText().length());
            }
            return true;
        }
        return false;
    }

    private void setEditTextListener() {

        textHidden.addTextChangedListener(textWatcher);
        textInput1.setOnFocusChangeListener(this);
        textInput2.setOnFocusChangeListener(this);
        textInput3.setOnFocusChangeListener(this);
        textInput4.setOnFocusChangeListener(this);
        textInput5.setOnFocusChangeListener(this);
        textInput6.setOnFocusChangeListener(this);

        textInput1.setOnKeyListener(this);
        textInput2.setOnKeyListener(this);
        textInput3.setOnKeyListener(this);
        textInput4.setOnKeyListener(this);
        textInput5.setOnKeyListener(this);
        textInput6.setOnKeyListener(this);
    }

}
