package com.dsd.triviaapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dsd.triviaapp.R;
import com.dsd.triviaapp.application.AppConfigs;
import com.dsd.triviaapp.helper.DateTimeHelper;
import com.dsd.triviaapp.model.Game;

import java.util.List;
import java.util.TimeZone;


public class HistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Game> arrayList;

    public HistoryAdapter(Context context, List<Game> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setItem(arrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvGameNumber;
        private TextView tvDateTime;
        private TextView tvName;
        private View itemView;
        private TextView tvAnsCricketer;
        private TextView tvAnsColor;


        public ViewHolder(View itemView) {
            super(itemView);
            this.tvGameNumber = itemView.findViewById(R.id.tv_game_number);
            this.tvDateTime = itemView.findViewById(R.id.tv_date_time);
            this.tvName = itemView.findViewById(R.id.tv_name);
            this.tvAnsCricketer = itemView.findViewById(R.id.tv_ans_cricketer);
            this.tvAnsColor = itemView.findViewById(R.id.tv_ans_color);
            this.itemView = itemView;
        }

        public void setItem(Game game) {
            int gameNum = getAdapterPosition() + 1;
            tvGameNumber.setText(context.getString(R.string.game_num, String.valueOf(gameNum)));
            String date = DateTimeHelper.convertUnixTimeStampIntoDateStr(game.getTimeStamp(), AppConfigs.DATE_TIME_FORMAT, TimeZone.getDefault());
            tvDateTime.setText(date);
            tvName.setText(game.getName());
            tvAnsCricketer.setText(game.getBestCricketer());
            tvAnsColor.setText(game.getFlagColors());
        }
    }
}
