package com.dsd.triviaapp.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dsd.triviaapp.R;
import com.dsd.triviaapp.helper.StringHelper;
import com.dsd.triviaapp.interfaces.OnColorSelected;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SelectColorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private OnColorSelected onColorSelected;
    private Context context;
    private List<String> arrayList;
    private ArrayList<String> selectedIds;

    public SelectColorAdapter(Context context, List<String> arrayList, String selectedIds, OnColorSelected onColorSelected) {
        this.context = context;
        this.arrayList = arrayList;
        this.onColorSelected = onColorSelected;

        if (StringHelper.isEmpty(selectedIds)) {
            this.selectedIds = new ArrayList<>();
        } else {
            this.selectedIds = new ArrayList<>(Arrays.asList(selectedIds.split(", ")));
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_select_color, parent, false);
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

    public String getSelectedIds() {
        if (selectedIds.size() > 0)
            return TextUtils.join(", ", selectedIds);
        else
            return "";
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvColor;
        private ImageView ivCheck;
        private View itemView;


        public ViewHolder(View itemView) {
            super(itemView);
            this.tvColor = itemView.findViewById(R.id.tv_color);
            this.ivCheck = itemView.findViewById(R.id.iv_check);
            this.itemView = itemView;
        }

        public void setItem(String data) {
            tvColor.setText(data);

            if (selectedIds.contains(data))
                ivCheck.setImageResource(R.drawable.right_select);
            else
                ivCheck.setImageResource(R.drawable.right_deselect);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onColorSelected != null) {
                        if (selectedIds.contains(data)) {
                            selectedIds.remove(data);
                            onColorSelected.onSelected(false);
                        } else {
                            selectedIds.add(data);
                            onColorSelected.onSelected(true);
                        }
                        notifyItemChanged(getAdapterPosition());
                    }

                }
            });
        }
    }
}
