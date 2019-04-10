package com.example.ikpmd_eindopdracht.list;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ikpmd_eindopdracht.R;
import com.example.ikpmd_eindopdracht.model.Track;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.function.ToDoubleBiFunction;

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
//            vh.image = (AppCompatImageView) convertView.findViewById(R.id.subject_image);
            vh.title = convertView.findViewById(R.id.subject_title);
            vh.artist = convertView.findViewById(R.id.subject_artist);
            vh.genre = convertView.findViewById(R.id.subject_genre);
            vh.duration = convertView.findViewById(R.id.subject_duration);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        Track track = getItem(position);

//        Image img = track.getImage();
        // TODO: 10/04/2019 image wordt als image opgeslagen in firebase, dus niet als string 
//        vh.image.setImageDrawable(track.getImage());
        vh.title.setText(track.getTitle());
        vh.artist.setText(track.getArtist());
        vh.genre.setText(track.getGenre());
//        vh.duration.setText(track.getDuration());
//        vh.track.setText(track.getTrack());
        return convertView;
    }

    private static class ViewHolder {
//        AppCompatImageView image;
        TextView title;
        TextView artist;
        TextView genre;
        TextView duration;
        TextView track;
    }
}
