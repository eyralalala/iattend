package com.example.fyp.utils;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.fyp.R;

public class ProgressDialog {
    private final Dialog dialog;
    private final View dialogView;
    private TextView message;

    public ProgressDialog(@NonNull Context context) {
        dialog = new Dialog(context);
        dialogView = LayoutInflater.from(context).inflate(R.layout.layout_progress_dialog, null);
        initDialog();
    }

    private void initDialog() {
        dialog.requestWindowFeature(android.view.Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        message = dialogView.findViewById(R.id.progress_dialog_message);
        dialog.setContentView(dialogView);
        dialog.setCancelable(false);
    }

    public void show(String message) {
        this.message.setText(message);
        dialog.show();
    }

    public void setMessage(String message) {
        if (dialog.isShowing()) {
            this.message.setText(message);
        }
    }

    public void dismiss() {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}