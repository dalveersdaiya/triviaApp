package com.dsd.triviaapp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dsd.triviaapp.R;
import com.dsd.triviaapp.adapter.SelectColorAdapter;
import com.dsd.triviaapp.helper.LogHelper;
import com.dsd.triviaapp.helper.StringHelper;
import com.dsd.triviaapp.interfaces.OnColorSelected;
import com.dsd.triviaapp.model.modelAccessLayer.DataLayerHelper;
import com.dsd.triviaapp.model.modelAccessLayer.GameLayer;

import java.util.ArrayList;
import java.util.List;

public class SelectFlagColor extends BaseActivity {
    private Button buttonNext;
    private RecyclerView recyclerView;
    private String selectedIds = "";
    private SelectColorAdapter selectColorAdapter;
    private String gameId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_color);
        setupToolBar(getString(R.string.add_details), true);
        getIntentValues();
        findViews();
        applyClickListeners();
        setRecycler();
    }

    private void getIntentValues() {
        gameId = getIntent().getExtras().getString("gameId", gameId);
    }

    public static void initActivity(Context context, String gameId) {
        Intent intent = new Intent(context, SelectFlagColor.class);
        intent.putExtra("gameId", gameId);
        context.startActivity(intent);
    }

    private void findViews() {
        recyclerView = findViewById(R.id.recycler_view);
        buttonNext = findViewById(R.id.button_next);
    }

    private void applyClickListeners() {
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkForValidations();
            }
        });
    }

    private void checkForValidations() {
        if (StringHelper.isEmpty(selectedIds)) {
            showAlertMessage(getString(R.string.empty_flag_color_alert));
            return;
        }
        updateGameInDb();
    }

    private void setRecycler() {
        recyclerView.setHasFixedSize(true);
        List<String> arrayList = new ArrayList<>();
        arrayList.add(getString(R.string.white));
        arrayList.add(getString(R.string.yellow));
        arrayList.add(getString(R.string.orange));
        arrayList.add(getString(R.string.green));

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        if (arrayList.size() == 0) {
            recyclerView.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            selectColorAdapter = new SelectColorAdapter(context, arrayList, selectedIds, new OnColorSelected() {
                @Override
                public void onSelected(boolean ifSelected) {
                    try {
                        if (selectColorAdapter != null) {
                            selectedIds = selectColorAdapter.getSelectedIds();
                            LogHelper.logD("Daiya", "OnColorSelected : " + selectedIds);
                        }
                    } catch (Exception e) {
                        LogHelper.printStackTrace(e);
                    }
                }
            });
            recyclerView.setAdapter(selectColorAdapter);
            recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        }

    }

    private void moveToNextScreen() {
        Summary.initActivity(context, gameId);
        enterAnimation();
    }

    /**
     * Update Game with selected flag colors
     */
    private void updateGameInDb() {
        GameLayer.updateGameFlagColors(gameId, selectedIds, new DataLayerHelper.OnRealmTransactionCompleted() {
            @Override
            public void onRealmTransactionCompleted(String result) {
                moveToNextScreen();
            }
        });
    }
}