package com.example.davee.footyappv2;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class FeedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    public TextView textViewTitle, textViewDate, textViewContent;
    private ItemClickListener itemClickListener;
    public ImageView imageView;

    public FeedViewHolder(View itemView) {
        super(itemView);

        textViewTitle = itemView.findViewById(R.id.title);
        textViewDate = itemView.findViewById(R.id.date);
        textViewContent = itemView.findViewById(R.id.content);

        imageView = itemView.findViewById(R.id.imageview);

        // set events
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }

    @Override
    public boolean onLongClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), true);
        return true;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
