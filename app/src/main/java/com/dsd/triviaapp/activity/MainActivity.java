package com.dsd.triviaapp.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dsd.triviaapp.R;
import com.dsd.triviaapp.helper.StringHelper;
import com.dsd.triviaapp.model.modelAccessLayer.DataLayerHelper;
import com.dsd.triviaapp.model.modelAccessLayer.GameLayer;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends BaseActivity {
    private TextInputLayout tilEmail;
    private EditText etName;
    private Button buttonNext;
    private String gameId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupToolBar(getString(R.string.add_name), false);
        findViews();
        applyClickListeners();
        setUi();
    }

    private void findViews() {
        tilEmail = findViewById(R.id.til_email);
        etName = findViewById(R.id.et_name);
        buttonNext = findViewById(R.id.button_next);
    }

    private void applyClickListeners() {
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkForValidations();
            }
        });
        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                etName.setError(null);
                tilEmail.setError(null);
            }
        });
    }

    private void setUi() {
        gameId = StringHelper.uniqueId24();
    }

    /**
     * Check for required values, before navigating to other screens
     */
    private void checkForValidations() {
        etName.setError(null);
        tilEmail.setError(null);
        if (StringHelper.isEmpty(etName.getText().toString())) {
            etName.setSelection(etName.getText().length());
            tilEmail.setError(getString(R.string.empty_name_alert));
            etName.requestFocus();
            return;
        }
        createGameInDb();
    }

    private void moveToNextScreen() {
        SelectCricketer.initActivity(context, gameId);
        enterAnimation();
    }

    /**
     * Create a realm db model for a new Game
     */
    private void createGameInDb() {
        GameLayer.createGame(gameId, new DataLayerHelper.OnRealmTransactionCompleted() {
            @Override
            public void onRealmTransactionCompleted(String result) {
                updateGameInDb();
            }
        });
    }

    /**
     * Update Game user name
     */
    private void updateGameInDb() {
        GameLayer.updateGameName(gameId, etName.getText().toString(), new DataLayerHelper.OnRealmTransactionCompleted() {
            @Override
            public void onRealmTransactionCompleted(String result) {
                moveToNextScreen();
            }
        });
    }
}