package com.limerobotsoftware.ohiodevfest.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.limerobotsoftware.ohiodevfest.R;
import com.limerobotsoftware.ohiodevfest.model.Session;
import com.limerobotsoftware.ohiodevfest.model.Speaker;
import com.limerobotsoftware.ohiodevfest.model.Timeslot;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by andy on 11/8/16.
 */

public class TimeslotAdapter extends RecyclerView.Adapter<TimeslotAdapter.ViewHolder> {

    private List<Timeslot> timeslots = null;
    private Context context;

    @Override
    public TimeslotAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.view_schedule, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TimeslotAdapter.ViewHolder holder, int position) {

        Timeslot timeslot = timeslots.get(position);

        if (timeslot != null) {
            holder.startTime.setText(timeslot.getStartTime());
            holder.cardHolder.removeAllViews();
            int trackNumber = 0;
            for(Session session: timeslot.getSessionList()){
                View view = LayoutInflater.from(context).inflate(R.layout.card_schedule, null, false);

                DisplayMetrics metrics = new DisplayMetrics();

                CardView sessionCard = (CardView) view.findViewById(R.id.session_card);
                TextView title = (TextView) view.findViewById(R.id.title);
                TextView endTime = (TextView) view.findViewById(R.id.end_time);
                TextView track = (TextView) view.findViewById(R.id.track);
                CardView photoCircle = (CardView) view.findViewById(R.id.photo_circle);
                TextView attending = (TextView) view.findViewById(R.id.attending);
                ImageView icAttending = (ImageView) view.findViewById(R.id.ic_attending);

                attending.setTag(session);
                icAttending.setTag(session);
                if (session.getAttending() != null && session.getAttending()) {
                    icAttending.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_event_attending));
                    DrawableCompat.setTint(icAttending.getDrawable(), ContextCompat.getColor(context, R.color.colorAccent));
                    attending.setText(context.getResources().getText(R.string.attending));
                    attending.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
                }

                if (session.getSpeakerList().size() > 0) {
                    Speaker speaker = session.getSpeakerList().get(0);
                    sessionCard.setTag(speaker);
                    sessionCard.setClickable(true);
                    ImageView photo = (ImageView) view.findViewById(R.id.photo);
                    TextView name = (TextView) view.findViewById(R.id.name);
                    TextView company = (TextView) view.findViewById(R.id.company);
                    name.setText(speaker.getName());
                    company.setText(speaker.getCompany());
                    Picasso.with(context)
                            .load("https://ohiodevfest.com"+ speaker.getPhotoUrl())
                            .into(photo);
                } else {
                    photoCircle.setVisibility(View.GONE);
                    sessionCard.setClickable(false);
                }

                title.setText(session.getTitle());
                endTime.setText(timeslot.getEndTime());
                trackNumber++;
                track.setText("Track "+ trackNumber);
                holder.cardHolder.addView(view);
            }
        }

    }

    @Override
    public int getItemCount() {
        if (timeslots == null)
            return 0;
        else
            return timeslots.size();
    }

    public void setTimeslots(List<Timeslot> timeslots) {
        this.timeslots = timeslots;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout cardHolder;
        TextView startTime;

        public ViewHolder(View itemView) {

            super(itemView);
            cardHolder = (LinearLayout) itemView.findViewById(R.id.card_holder);
            startTime = (TextView) itemView.findViewById(R.id.start_time);
        }
    }
}