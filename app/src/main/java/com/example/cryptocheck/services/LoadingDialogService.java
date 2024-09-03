package com.example.cryptocheck.services;

import android.app.Dialog;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.cryptocheck.R;

public class LoadingDialogService {

    private class LoadingDialog extends Dialog {
        private LoadingDialog(Context context) {
            super(context, R.style.loading_dialog);
            this.setCancelable(false);
            this.addContentView(new ProgressBar(context), new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
    }

    private static LoadingDialogService instance = null;

    private LoadingDialog loadingDialog;

    public static LoadingDialogService getInstance() {
        if (instance == null) {
            instance = new LoadingDialogService();
        }
        return instance;
    }

    public void createLoadingDialog(Context context) {
        loadingDialog = new LoadingDialog(context);
    }

    public void show() {
        if (!loadingDialog.isShowing()) {
            loadingDialog.show();
        }
    }

    public void dismiss() {
        loadingDialog.dismiss();
    }

}
