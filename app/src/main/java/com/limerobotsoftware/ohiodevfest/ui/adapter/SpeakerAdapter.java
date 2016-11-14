package com.limerobotsoftware.ohiodevfest.ui.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.limerobotsoftware.ohiodevfest.R;
import com.limerobotsoftware.ohiodevfest.model.Social;
import com.limerobotsoftware.ohiodevfest.model.Speaker;
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

            holder.card.setTag(speaker);

            Picasso.with(context)
                    .load("https://ohiodevfest.com"+speaker.getPhotoUrl())
                    .into(holder.photo);

            holder.twitter.setVisibility(View.GONE);
            holder.gPlus.setVisibility(View.GONE);
            for (Social social : speaker.getSocials()) {
                if (social.getName().equals("Twitter")) {
                    holder.twitter.setTag(social.getLink().substring(resources.getInteger(R.integer.twitterStart), social.getLink().length()-1));
                    holder.twitter.setVisibility(View.VISIBLE);
                    holder.divider.setVisibility(View.VISIBLE);

                } else if (social.getName().equals("Google+")) {
                    holder.gPlus.setTag(social.getLink().substring(resources.getInteger(R.integer.gPlusStart)));
                    holder.gPlus.setVisibility(View.VISIBLE);
                    holder.divider.setVisibility(View.VISIBLE);
                }
            }
        }

    }

    @Override
    public int getItemCount() {
        if (speakers == null)
            return 0;
        else
            return speakers.size();
    }

    public void setSpeakers(List<Speaker> featuredSpeakers) {
        this.speakers = featuredSpeakers;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView card;
        TextView name;
        TextView company;
        TextView bio;
        ImageView photo;
        ImageButton twitter;
        ImageButton gPlus;
        View divider;

        public ViewHolder(View itemView) {

            super(itemView);
            card = (CardView) itemView.findViewById(R.id.speaker_card);
            name = (TextView) itemView.findViewById(R.id.name);
            company = (TextView) itemView.findViewById(R.id.company);
            photo = (ImageView) itemView.findViewById(R.id.photo);
            bio = (TextView) itemView.findViewById(R.id.bio);
            twitter = (ImageButton) itemView.findViewById(R.id.twitter);
            gPlus = (ImageButton) itemView.findViewById(R.id.g_plus);
            divider = itemView.findViewById(R.id.divider);
        }
    }
}