package com.example.davee.footyappv2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

public class FeedAdapter extends RecyclerView.Adapter<FeedViewHolder> {
    private RssObject object;
    private Context context;
    private LayoutInflater inflater;    // used to instantiate a layout xml file into its corresponding View objects

    public FeedAdapter(RssObject object, Context context) {
        this.object = object;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    //only creates a new view holder when there are no existing view holders which the RecyclerView can reuse
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row, parent, false);  // the child view is not attached to the parent view yet
        return new FeedViewHolder(view);
    }

    @Override

    // used to replace old data held in the recyclerview with new data.
    public void onBindViewHolder(@NonNull FeedViewHolder holder, int position) {
        String image = String.valueOf(holder.imageView);

        holder.textViewTitle.setText(object.getItems().get(position).getTitle());
        holder.textViewDate.setText(object.getItems().get(position).getPubDate());
        holder.textViewContent.setText(object.getItems().get(position).getContent());

        try {
            if (image.isEmpty()){
                //default image in case image is missing
                holder.imageView.setImageResource(R.drawable.splash_screen);
            }
            else{
                //picasso is a library used to display images in an app easily
                Picasso.get().load(object.getItems().get(position).getThumbnail()).resize(900,300).into(holder.imageView);
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        holder.setItemClickListener(new ItemClickListener() {
            //Display information about the news item based on its position on the recyclerView
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                // when the RecyclerView item is not long clicked
                if (!isLongClick) {
                    //action view is the action that provides rich functionality within the app bar
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(object.getItems().get(position).getLink()));
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    //returns the number of items on the RecyclerView, by default 10
    public int getItemCount() {
        return object.items.size();
    }
}
