package com.dsd.triviaapp.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.dsd.triviaapp.R;
import com.dsd.triviaapp.helper.LogHelper;


public abstract class BaseActivity extends AppCompatActivity {

    Context context = this;
    public TextView toolbarTitleTxtView;

    /**
     * Animating the activity transitions
     */
    public void enterAnimation() {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    /**
     * Animating the activity transitions
     */
    public void exitAnimation() {
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        exitAnimation();
    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    /**
     * Clear the activity stack and start from beginning
     * @param activity
     * @param classname
     */
    void intentPass(Activity activity, Class classname) {
        Intent i = new Intent(activity, classname);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        enterAnimation();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * Pass the string to show alert message
     * @param message
     */
    public void showAlertMessage(String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage(message).setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertdialog = alertDialogBuilder.create();
        alertdialog.show();
    }

    public void hideKeyboard() {
        try {
            if (getCurrentFocus() != null) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        } catch (Exception e) {
            LogHelper.printStackTrace(e);
        }
    }

    public void showKeyBoard(final EditText editText) {
        editText.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager keyboard = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                keyboard.showSoftInput(editText, 0);
            }
        }, 300);
    }

    public void showToast(String msgStr) {
        Toast.makeText(context, msgStr, Toast.LENGTH_SHORT).show();
    }

    /**
     * Custom Toolbaar with title
     * @param title
     */
    void setupToolBar(String title) {
        setupToolBar(title, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    /**
     * Custom Toolbar with title and navigation requirement option
     * @param title
     * @param showNavigationBtn
     */
    void setupToolBar(String title, boolean showNavigationBtn) {
        setupToolBar(title, showNavigationBtn, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                finish();
                onBackPressed();
            }
        });
    }

    /**
     * Custom Toolbar with title and click listener
     * @param title
     * @param onClickListener
     */
    void setupToolBar(String title, View.OnClickListener onClickListener) {
        setupToolBar(title, true, onClickListener);
        showToolbar();
    }

    void setToolBarTitle(String title) {
        if (toolbarTitleTxtView != null) {
            if (title != null) {
                toolbarTitleTxtView.setText(title);
                toolbarTitleTxtView.setVisibility(View.VISIBLE);
            } else {
                toolbarTitleTxtView.setVisibility(View.GONE);
            }
            showToolbar();
        }
    }

    void hideToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setVisibility(View.GONE);
    }

    void showToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);
    }

    /**
     * Custom Toolbar with title and navigation requirement option and click listener
     * @param title
     * @param showNavigationBtn
     * @param onClickListener
     */
    private void setupToolBar(String title, boolean showNavigationBtn, View.OnClickListener onClickListener) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        toolbarTitleTxtView = findViewById(R.id.toolbar_title);
        ImageView ivBackButton = findViewById(R.id.iv_back_button);
        if (title != null) {
            toolbarTitleTxtView.setText(title);
            toolbarTitleTxtView.setVisibility(View.VISIBLE);
        } else {
            toolbarTitleTxtView.setVisibility(View.GONE);
        }
        ivBackButton.setOnClickListener(onClickListener);
        if (!showNavigationBtn) {
            ivBackButton.setVisibility(View.GONE);
        }
    }

    /**
     *    Enable-Disable View groups
     * @param vg
     * @param enable
     */
    public void disableEnableControls(ViewGroup vg, boolean enable) {
        for (int i = 0; i < vg.getChildCount(); i++) {
            View child = vg.getChildAt(i);
            child.setEnabled(enable);
            if (child instanceof ViewGroup) {
                disableEnableControls((ViewGroup) child, enable);
            }
        }
    }

    /**
     *    Enable-Disable Views
     * @param view
     * @param enable
     */
    public void enableDisableView(View view, boolean enable) {
        view.setEnabled(enable);
        if (enable) {
            view.setAlpha(0.9f);
        } else {
            view.setAlpha(0.3f);
        }
    }

}