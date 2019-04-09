package com.example.ikpmd_eindopdracht.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ikpmd_eindopdracht.R;
import com.example.ikpmd_eindopdracht.model.Track;

import java.io.File;
import java.util.Date;
import java.util.List;

public class TrackListAdapter extends ArrayAdapter<Track> {

    public TrackListAdapter(Context context, int resource, List<Track> objects){
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;

        if (convertView == null ) {
            vh = new ViewHolder();
            LayoutInflater li = LayoutInflater.from(getContext());
            convertView = li.inflate(R.layout.view_content_row, parent, false);
            vh.title = (TextView) convertView.findViewById(R.id.subject_title);
            vh.artist = (TextView) convertView.findViewById(R.id.subject_artist);
            vh.genre = (TextView) convertView.findViewById(R.id.subject_genre);
            vh.duration = (TextView) convertView.findViewById(R.id.subject_duration);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        Track track = getItem(position);


        vh.title.setText((CharSequence) track.getTitle());
        vh.artist.setText((CharSequence) track.getArtist());
        vh.genre.setText((CharSequence) track.getGenre());
//        vh.duration.setText((CharSequence) track.getDuration());
//        vh.track.setText((CharSequence) track.getTrack());
        return convertView;
    }

    private static class ViewHolder {
        TextView title;
        TextView artist;
        TextView genre;
        TextView duration;
        TextView track;
    }
}
