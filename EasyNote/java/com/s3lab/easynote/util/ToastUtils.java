package com.s3lab.easynote.util;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils{
    private Toast mToast;

    public void showShort(Context context, String  string) {
        mToast = Toast.makeText(context, null, Toast.LENGTH_SHORT);
        mToast.setText(string);
        mToast.show();
    }
}
