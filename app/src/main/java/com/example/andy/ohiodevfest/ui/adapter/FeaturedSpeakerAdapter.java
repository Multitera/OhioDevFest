package com.example.andy.ohiodevfest.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.andy.ohiodevfest.R;
import com.example.andy.ohiodevfest.model.Speaker;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by andy on 10/28/16.
 */

public class FeaturedSpeakerAdapter extends RecyclerView.Adapter<FeaturedSpeakerAdapter.ViewHolder> {

    private List<Speaker> featuredSpeakers = null;
    private Context context;

    @Override
    public FeaturedSpeakerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.view_featured_speaker, parent, false);
        return new FeaturedSpeakerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FeaturedSpeakerAdapter.ViewHolder holder, int position) {

        Speaker speaker = featuredSpeakers.get(position);

        if (speaker != null) {
            holder.name.setText(speaker.getName());
            holder.company.setText(speaker.getCompany());
            Picasso.with(context)
                    .load("https://ohiodevfest.com"+speaker.getPhotoUrl())
                    .into(holder.photo);
        }

    }

    @Override
    public int getItemCount() {
        if (featuredSpeakers.size() >= 4)
            return 4;
        else
            return 0;
    }

    public void setFeaturedSpeakers(List<Speaker> featuredSpeakers) {
        this.featuredSpeakers = featuredSpeakers;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView company;
        ImageView photo;
        public ViewHolder(View itemView) {

            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            company = (TextView) itemView.findViewById(R.id.company);
            photo = (ImageView) itemView.findViewById(R.id.photo);
        }
    }
}