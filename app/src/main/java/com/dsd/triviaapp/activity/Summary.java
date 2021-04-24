package com.dsd.triviaapp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dsd.triviaapp.R;
import com.dsd.triviaapp.model.Game;
import com.dsd.triviaapp.model.modelAccessLayer.GameLayer;

public class Summary extends BaseActivity implements View.OnClickListener {
    private TextView tvHelloUser;
    private Button buttonHistory;
    private Button buttonFinish;
    private TextView tvAnsColor;
    private TextView tvAnsCricketer;
    private String gameId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        setupToolBar(getString(R.string.summary), false);
        getIntentValues();
        findViews();
        applyClickListeners();
        setUI();
    }

    public static void initActivity(Context context, String gameId) {
        Intent intent = new Intent(context, Summary.class);
        intent.putExtra("gameId", gameId);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private void getIntentValues() {
        gameId = getIntent().getExtras().getString("gameId", gameId);
    }

    private void findViews() {
        tvHelloUser = findViewById(R.id.tv_hello_user);
        tvAnsColor = findViewById(R.id.tv_ans_color);
        tvAnsCricketer = findViewById(R.id.tv_ans_cricketer);
        buttonHistory = findViewById(R.id.button_history);
        buttonFinish = findViewById(R.id.button_finish);
    }

    private void applyClickListeners() {
        buttonFinish.setOnClickListener(this);
        buttonHistory.setOnClickListener(this);
    }

    /**
     * Fetch Game Model using gameId and update UI accordingly
     */
    private void setUI() {
        Game game = GameLayer.getById(gameId);
        if (game != null) {// Check for null values
            tvHelloUser.setText(getString(R.string.hello_user, game.getName()));
            tvAnsCricketer.setText(getString(R.string.ans_cricketer, game.getBestCricketer()));
            tvAnsColor.setText(getString(R.string.ans_cricketer, game.getFlagColors()));
        }
    }

    private void moveToStartScreen() {
        intentPass(Summary.this, MainActivity.class);
    }

    private void moveToHistoryScreen() {
        History.initActivity(context);
        enterAnimation();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_finish:
                moveToStartScreen();
                break;
            case R.id.button_history:
                moveToHistoryScreen();
                break;
            default:
                //Do nothing here
                break;
        }
    }
}