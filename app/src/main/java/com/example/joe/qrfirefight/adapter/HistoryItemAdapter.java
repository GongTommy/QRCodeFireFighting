package com.example.joe.qrfirefight.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.joe.qrfirefight.R;
import com.example.joe.qrfirefight.model.HistoryModel;

import java.util.List;

/**
 * Created by Joe on 2019-05-23.
 */

public class HistoryItemAdapter extends RecyclerView.Adapter<HistoryItemAdapter.HistoryViewHolder> {
    private List<HistoryModel> historyModels;
    private OnItemDelClickListener onItemDelClickListener;

    public HistoryItemAdapter(List<HistoryModel> historyModels){
        this.historyModels = historyModels;
    }

    public interface OnItemDelClickListener {
        void onItemDelClickListener(int position);
    }

    public void setOnItemDelClickListener(OnItemDelClickListener onItemDelClickListener){
        this.onItemDelClickListener = onItemDelClickListener;
    }

    @Override
    public HistoryItemAdapter.HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vhView = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item_view, parent, false);
        return new HistoryViewHolder(vhView);
    }

    @Override
    public void onBindViewHolder(HistoryItemAdapter.HistoryViewHolder holder, final int position) {
        if (historyModels == null || historyModels.size() == 0){
            return;
        }
        HistoryModel historyModel = historyModels.get(position);
        holder.tvDate.setText(historyModel.getCreatedate());
        holder.tvWorkNum.setText(historyModel.getCreateuserid());
        holder.tvEquipNum.setText(historyModel.getEquipmentid());
        holder.btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemDelClickListener.onItemDelClickListener(position);
            }
        });
        if (historyModel.getQualifiedstate() == 1){
            holder.tvNormal.setText("合格");
        }else {
            holder.tvNormal.setText("不合格");
        }
    }

    @Override
    public int getItemCount() {
        if (historyModels == null || historyModels.size() == 0){
            return 0;
        }
        return historyModels.size();
    }

    class HistoryViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDate;
        private TextView tvWorkNum;
        private TextView tvEquipNum;
        private TextView tvNormal;//0表示不合格，1表示合格。
        private Button btnDel;

        public HistoryViewHolder(View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvWorkNum = itemView.findViewById(R.id.tvWorkNum);
            tvEquipNum = itemView.findViewById(R.id.tvEquipNum);
            tvNormal = itemView.findViewById(R.id.tvNormal);
            btnDel = itemView.findViewById(R.id.btnDel);
        }
    }
}
