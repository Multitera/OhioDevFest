package com.limerobotsoftware.ohiodevfest.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.limerobotsoftware.ohiodevfest.R;
import com.limerobotsoftware.ohiodevfest.model.Session;

import java.util.List;

/**
 * Created by andy on 10/31/16.
 */

public class SessionViewAdapter extends RecyclerView.Adapter<SessionViewAdapter.ViewHolder> {

    private List<Session> sessions = null;
    private Context context;

    @Override
    public SessionViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.view_session, parent, false);
        return new SessionViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SessionViewAdapter.ViewHolder holder, int position) {

        Session session = sessions.get(position);

        if (session != null) {

            holder.title.setText(session.getTitle());
            holder.description.setText(session.getDescription());
        }

    }

    @Override
    public int getItemCount() {
        return sessions.size();
    }

    public void setSessions(List<Session> sessions) {
        this.sessions = sessions;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView description;

        public ViewHolder(View itemView) {

            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            description = (TextView) itemView.findViewById(R.id.description);

        }
    }
}