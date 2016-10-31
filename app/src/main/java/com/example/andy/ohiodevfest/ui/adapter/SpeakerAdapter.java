package com.example.andy.ohiodevfest.ui.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.andy.ohiodevfest.R;
import com.example.andy.ohiodevfest.model.Social;
import com.example.andy.ohiodevfest.model.Speaker;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by andy on 10/30/16.
 */

public class SpeakerAdapter extends RecyclerView.Adapter<SpeakerAdapter.ViewHolder> {

    private List<Speaker> speakers = null;
    private Context context;

    @Override
    public SpeakerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.card_speaker, parent, false);
        return new SpeakerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SpeakerAdapter.ViewHolder holder, int position) {

        Speaker speaker = speakers.get(position);

        if (speaker != null) {
            Resources resources = context.getResources();
            holder.name.setText(speaker.getName());
            holder.company.setText(speaker.getCompany());

            if (speaker.getBio().length() > resources.getInteger(R.integer.bioCutOff))
                holder.bio.setText(speaker.getBio().substring(0, resources.getInteger(R.integer.bioCutOff))+"...");
            else
                holder.bio.setText(speaker.getBio());

            Picasso.with(context)
                    .load("https://ohiodevfest.com"+speaker.getPhotoUrl())
                    .into(holder.photo);

            holder.gPlus.setImageDrawable(null);
            holder.twitter.setImageDrawable(null);
            for (Social social : speaker.getSocials()) {
                if (social.getName().equals("Twitter")) {
                    holder.twitter.setTag(social.getLink().substring(resources.getInteger(R.integer.twitterStart), social.getLink().length()-1));
                    holder.twitter.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_twitter_box, null));
                } else if (social.getName().equals("Google+")) {
                    holder.gPlus.setTag(social.getLink().substring(resources.getInteger(R.integer.gPlusStart)));
                    holder.gPlus.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_google_plus_box, null));
                }
            }
        }

    }

    @Override
    public int getItemCount() {
        return speakers.size();
    }

    public void setSpeakers(List<Speaker> featuredSpeakers) {
        this.speakers = featuredSpeakers;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView company;
        TextView bio;
        ImageView photo;
        ImageView twitter;
        ImageView gPlus;

        public ViewHolder(View itemView) {

            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            company = (TextView) itemView.findViewById(R.id.company);
            photo = (ImageView) itemView.findViewById(R.id.photo);
            bio = (TextView) itemView.findViewById(R.id.bio);
            twitter = (ImageView) itemView.findViewById(R.id.twitter);
            gPlus = (ImageView) itemView.findViewById(R.id.g_plus);
        }
    }
}