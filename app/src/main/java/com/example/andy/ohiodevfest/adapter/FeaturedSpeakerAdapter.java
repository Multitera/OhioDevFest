package com.example.andy.ohiodevfest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.andy.ohiodevfest.R;
import com.example.andy.ohiodevfest.model.Speaker;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by andy on 10/28/16.
 */

public class FeaturedSpeakerAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<Speaker> featuredSpeakers = null;

    public FeaturedSpeakerAdapter(Context context) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(List<Speaker> speakers) {
        this.featuredSpeakers = speakers;
    }

    @Override
    public int getCount() {
        if (featuredSpeakers == null) {
            return 0;
        }
        return featuredSpeakers.size();
    }

    @Override
    public Object getItem(int i) {
        if (featuredSpeakers == null || featuredSpeakers.get(i) == null) {
            return null;
        }
        return featuredSpeakers.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(R.layout.view_featured_speaker, viewGroup, false);
        }

        Speaker speaker = featuredSpeakers.get(i);

        if (speaker != null) {
            ((TextView) view.findViewById(R.id.name)).setText(speaker.getName());
            ((TextView) view.findViewById(R.id.company)).setText(speaker.getCompany());
            Picasso.with(context)
                    .load("https://ohiodevfest.com"+speaker.getPhotoUrl())
                    .into((ImageView) view.findViewById(R.id.photo));
        }

        return view;
    }
}
