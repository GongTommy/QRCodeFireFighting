package com.example.joe.qrfirefight.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.joe.qrfirefight.R;
import com.example.joe.qrfirefight.activity.ScheTimeActivity;
import com.example.joe.qrfirefight.model.ScheTimeDetailEntity;

import java.util.List;


public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.DetailViewHolder> {
    private List<ScheTimeDetailEntity> list;
    private Context mContext;

    public DetailAdapter(List<ScheTimeDetailEntity> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    @Override
    public DetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_item, parent, false);
        DetailViewHolder detailViewHolder = new DetailViewHolder(itemView);
        return detailViewHolder;
    }

    @Override
    public void onBindViewHolder(DetailViewHolder holder, int position) {
        if (list == null || list.size() == 0){
            return;
        }
        ScheTimeDetailEntity entity = list.get(position);
        if (entity != null){
            holder.tvCG.setText(entity.getCopgno() != null ? entity.getCopgno() : "");
            holder.tvEQ.setText(String.valueOf(entity.getErpqty()));
            holder.tvLN.setText(String.valueOf(entity.getLinenum()));
            holder.tvIndex.setText(String.valueOf(position + 1));
        }
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    class DetailViewHolder extends RecyclerView.ViewHolder {
        private TextView tvCG;
        private TextView tvEQ;
        private TextView tvLN;
        private TextView tvIndex;
        public DetailViewHolder(View itemView) {
            super(itemView);
            tvIndex = itemView.findViewById(R.id.tvIndex);
            tvCG = itemView.findViewById(R.id.tvCG);
            tvEQ = itemView.findViewById(R.id.tvEQ);
            tvLN = itemView.findViewById(R.id.tvLN);
        }
    }
}
