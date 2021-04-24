package com.dsd.triviaapp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.dsd.triviaapp.R;
import com.dsd.triviaapp.helper.StringHelper;
import com.dsd.triviaapp.model.modelAccessLayer.DataLayerHelper;
import com.dsd.triviaapp.model.modelAccessLayer.GameLayer;

public class SelectCricketer extends BaseActivity {

    private Button buttonNext;
    private String gameId = "";
    private RadioGroup radioGroup;

    private String bestCricketer = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_cricketer);
        setupToolBar(getString(R.string.add_details), true);
        getIntentValues();
        findViews();
        applyClickListeners();
    }

    public static void initActivity(Context context, String gameId) {
        Intent intent = new Intent(context, SelectCricketer.class);
        intent.putExtra("gameId", gameId);
        context.startActivity(intent);
    }

    private void getIntentValues() {
        gameId = getIntent().getExtras().getString("gameId", gameId);
    }

    private void applyClickListeners() {
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkForValidations();
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                int id = radioGroup.getCheckedRadioButtonId();
                RadioButton selectedButton = findViewById(id);
                bestCricketer = selectedButton.getText().toString();
            }
        });
    }

    private void findViews() {
        buttonNext = findViewById(R.id.button_next);
        radioGroup = findViewById(R.id.radioGroup);
    }

    private void checkForValidations() {
        if (StringHelper.isEmpty(bestCricketer)) {
            showAlertMessage(getString(R.string.empty_cricketer_name_alert));
            return;
        }
        updateGameInDb(bestCricketer);
    }

    private void moveToNextScreen() {
        SelectFlagColor.initActivity(context, gameId);
        enterAnimation();
    }

    /**
     * Update best cricketer in Game
     * @param bestCricketer
     */
    private void updateGameInDb(String bestCricketer) {
        GameLayer.updateGameBestCricketer(gameId, bestCricketer, new DataLayerHelper.OnRealmTransactionCompleted() {
            @Override
            public void onRealmTransactionCompleted(String result) {
                moveToNextScreen();
            }
        });
    }
}