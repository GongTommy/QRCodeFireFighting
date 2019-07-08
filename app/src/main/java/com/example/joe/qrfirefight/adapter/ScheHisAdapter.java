package com.example.joe.qrfirefight.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.example.joe.qrfirefight.R;
import java.util.List;

/**
 * Created by 18145288 on 2019/6/26.
 */

public class ScheHisAdapter extends RecyclerView.Adapter<ScheHisAdapter.ScheHisViewHolder>{
    private List<String> historyModels;
    private ScheHisAdapter.OnItemDelClickListener onItemDelClickListener;

    public ScheHisAdapter(List<String> historyModels){
        this.historyModels = historyModels;
    }

    public interface OnItemDelClickListener {
        void onItemDelClickListener(int position);
    }

    public void setOnItemDelClickListener(ScheHisAdapter.OnItemDelClickListener onItemDelClickListener){
        this.onItemDelClickListener = onItemDelClickListener;
    }

    @Override
    public ScheHisAdapter.ScheHisViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vhView = LayoutInflater.from(parent.getContext()).inflate(R.layout.qrcode_history_item, parent, false);
        return new ScheHisAdapter.ScheHisViewHolder(vhView);
    }

    @Override
    public void onBindViewHolder(ScheHisAdapter.ScheHisViewHolder holder, final int position) {
        if (historyModels == null || historyModels.size() == 0){
            return;
        }
        holder.tvScheNum.setText(historyModels.get(position));
        holder.btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemDelClickListener.onItemDelClickListener(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (historyModels == null || historyModels.size() == 0){
            return 0;
        }
        return historyModels.size();
    }

    class ScheHisViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDate;
        private TextView tvEquipNum;
        private TextView tvScheNum;
        private Button btnDel;

        public ScheHisViewHolder(View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvEquipNum = itemView.findViewById(R.id.tvEquipNum);
            btnDel = itemView.findViewById(R.id.btnDel);
            tvScheNum = itemView.findViewById(R.id.tvScheNum);
        }
    }
}
