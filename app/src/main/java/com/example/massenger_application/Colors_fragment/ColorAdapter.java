package com.example.massenger_application.Colors_fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.massenger_application.R;

import java.util.List;

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.ViewHolder> {

    private List<Integer> colorList;
    private OnColorItemClickListener onColorItemClickListener;

    public ColorAdapter(List<Integer> colorList, OnColorItemClickListener onColorItemClickListener) {
        this.colorList = colorList;
        this.onColorItemClickListener = onColorItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.color_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int color = colorList.get(position);
        holder.colorView.setBackgroundColor(color);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onColorItemClickListener != null) {
                    onColorItemClickListener.onColorItemClick(color);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return colorList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        View colorView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            colorView = itemView.findViewById(R.id.linearColor1);
        }
    }

    public interface OnColorItemClickListener {
        void onColorItemClick(int color);
    }

}
