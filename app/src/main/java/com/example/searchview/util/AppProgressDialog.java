package com.example.searchview.util;

import android.app.ProgressDialog;
import android.content.Context;

public class AppProgressDialog {
    private static ProgressDialog progress;
    public static void show(Context context) {
        if (progress == null) {
            progress = new ProgressDialog(context);
            progress.setMessage("Please wait...");
            progress.setCanceledOnTouchOutside(false);
            progress.setCancelable(false);
            progress.show();
        }
}
    public static void dismiss() {
        if (progress != null && progress.isShowing()) {
            progress.dismiss();
        }
        progress = null;
    }
}
