package com.example.joe.qrfirefight.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.joe.qrfirefight.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 18145288 on 2019/6/20.
 */

public class SelectorAdapter extends RecyclerView.Adapter<SelectorAdapter.SelectorViewHolder> {
    private List<String> datas;
    private List<Button> buttons = new ArrayList<>();
    private boolean isSel;
    private final String TAG = "SelectorAdapter";
    private Context mContext;
    public SelectorAdapter(List<String> datas, Context mContext){
        this.datas = datas;
        this.mContext = mContext;
    }

    @Override
    public SelectorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.selector_item, parent, false);
        SelectorViewHolder selectorViewHolder = new SelectorViewHolder(itemView);
        return selectorViewHolder;
    }

    @Override
    public void onBindViewHolder(SelectorViewHolder holder, int position) {
        holder.tvSel.setText(datas.get(position));
        buttons.add(holder.tvSel);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public boolean isSelected(){
        return isSel;
    }

    class SelectorViewHolder extends RecyclerView.ViewHolder {
        private Button tvSel;
        public SelectorViewHolder(View itemView) {
            super(itemView);
            tvSel = itemView.findViewById(R.id.tvSel);
            tvSel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index = getAdapterPosition();
                    for(int i = 0; i < buttons.size(); i++){
                        Button targetBtn = buttons.get(i);
                        if (i == index){
                            isSel = true;
                            targetBtn.setSelected(true);
                        } else {
                            targetBtn.setSelected(false);
                        }
                    }
                }
            });
        }
    }
}
