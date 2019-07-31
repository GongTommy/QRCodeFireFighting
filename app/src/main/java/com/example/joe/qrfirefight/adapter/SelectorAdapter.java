package com.example.joe.qrfirefight.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.joe.qrfirefight.R;
import com.example.joe.qrfirefight.activity.ScheTimeActivity;
import com.example.joe.qrfirefight.model.ScheTimeEntity;
import java.util.List;

/**
 * Created by 18145288 on 2019/6/20.
 */

public class SelectorAdapter extends RecyclerView.Adapter<SelectorAdapter.SelectorViewHolder> {
    private List<ScheTimeEntity> datas;
    private final String TAG = "SelectorAdapter";
    private Context mContext;
    public SelectorAdapter(List<ScheTimeEntity> datas, Context mContext){
        this.datas = datas;
        this.mContext = mContext;
    }

    @Override
    public SelectorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i(TAG, "onCreateViewHolder");
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.selector_item, parent, false);
        SelectorViewHolder selectorViewHolder = new SelectorViewHolder(itemView);
        return selectorViewHolder;
    }

    @Override
    public void onBindViewHolder(SelectorViewHolder holder, final int position) {
        Log.i(TAG, "onBindViewHolder" + position);
        if (datas == null || datas.size() == 0){
            return;
        }
        ScheTimeEntity entity = datas.get(position);
        if (entity != null){
            holder.tvBN.setText(entity.getBillno() == null ? "": entity.getBillno());
            holder.tvBD.setText(entity.getBilldate() == null ? "" : entity.getBilldate());
            holder.tvBC.setText(entity.getClientno() == null ? "" : entity.getClientno());
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class SelectorViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout cl;
        TextView tvBN;
        TextView tvBD;
        TextView tvBC;
        public SelectorViewHolder(View itemView) {
            super(itemView);
            tvBC = itemView.findViewById(R.id.tvBC);
            tvBD = itemView.findViewById(R.id.tvBD);
            tvBN = itemView.findViewById(R.id.tvBN);
            cl = itemView.findViewById(R.id.cl);
            cl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ScheTimeEntity scheTimeEntity = datas.get(getAdapterPosition());
                    if (scheTimeEntity != null && scheTimeEntity.getBillno() != null){
                        ScheTimeActivity scheTimeActivity = (ScheTimeActivity) mContext;
                        scheTimeActivity.getScheTimeDataDetail(scheTimeEntity.getBillno());
                    }
                }
            });
        }
    }
}
