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
//        holder.tvRS.setText(entity.getReadstatus() == null ? "" : entity.getReadstatus());
//        holder.tvBN.setText(entity.getBillno() == null ? "" : entity.getBillno());
//        holder.tvLN.setText(String.valueOf(entity.getLinenum()));
//        holder.tvTT.setText(entity.getTradetype() == null ? "" : entity.getTradetype());
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    class DetailViewHolder extends RecyclerView.ViewHolder {
        private TextView tvBN;
        private TextView tvCG;
        private TextView tvQQ;
        private TextView tvLN;
        private ConstraintLayout cl;
        public DetailViewHolder(View itemView) {
            super(itemView);
            cl = itemView.findViewById(R.id.cl);
            tvBN = itemView.findViewById(R.id.tvBN);
            tvCG = itemView.findViewById(R.id.tvCG);
            tvQQ = itemView.findViewById(R.id.tvQQ);
            tvLN = itemView.findViewById(R.id.tvLN);
            cl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ScheTimeActivity scheTimeActivity = (ScheTimeActivity) mContext;
                    if (scheTimeActivity != null){
                        scheTimeActivity.refreshSubmitPage();
                    }
                }
            });
        }
    }
}
