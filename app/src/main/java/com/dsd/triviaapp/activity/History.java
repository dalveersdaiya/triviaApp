package com.dsd.triviaapp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dsd.triviaapp.R;
import com.dsd.triviaapp.adapter.HistoryAdapter;
import com.dsd.triviaapp.model.Game;
import com.dsd.triviaapp.model.modelAccessLayer.GameLayer;

import java.util.List;

public class History extends BaseActivity {

    private RecyclerView recyclerView;
    private HistoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        findViews();
        setUI();
    }

    public static void initActivity(Context context) {
        Intent intent = new Intent(context, History.class);
        context.startActivity(intent);
    }

    private void findViews() {
        recyclerView = findViewById(R.id.recycler_view);
    }

    private void setUI() {
        setupToolBar(getString(R.string.history), false);
        setRecycler();
    }

    /**
     * Set Games list from DB
     */
    private void setRecycler() {
        recyclerView.setHasFixedSize(true);
        List<Game> arrayList = GameLayer.getAll();
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        if (arrayList.size() == 0) {
            recyclerView.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            adapter = new HistoryAdapter(context, arrayList);
            recyclerView.setAdapter(adapter);
            recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        }
    }

}