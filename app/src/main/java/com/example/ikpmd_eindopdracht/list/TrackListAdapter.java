package com.example.ikpmd_eindopdracht.list;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ikpmd_eindopdracht.R;
import com.example.ikpmd_eindopdracht.model.Track;

import java.util.ArrayList;
import java.util.List;

import com.example.ikpmd_eindopdracht.service.MediaPlayerService;

public class TrackListAdapter extends ArrayAdapter<Track> {

    private MediaPlayer mediaPlayer = new MediaPlayer();
    private ArrayList<MediaPlayerService> list = new ArrayList<MediaPlayerService>();

    private class ViewHolder {
        AppCompatImageView image;
        TextView title;
        TextView artist;
        AppCompatImageView playpause;
//        TextView genre;
//        TextView duration;
    }

    public TrackListAdapter(Context context, int resource, List<Track> objects){
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;

        if (convertView == null ) {
            vh = new ViewHolder();
            LayoutInflater li = LayoutInflater.from(getContext());
            convertView = li.inflate(R.layout.view_content_row, parent, false);
            vh.image = convertView.findViewById(R.id.subject_image);
            vh.title = convertView.findViewById(R.id.subject_title);
            vh.artist = convertView.findViewById(R.id.subject_artist);
            vh.playpause = convertView.findViewById(R.id.playpause);
//            vh.genre = convertView.findViewById(R.id.subject_genre);
//            vh.duration = convertView.findViewById(R.id.subject_duration);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        final Track track = getItem(position);

//        Image img = track.getImageURL();
//        vh.image.setImageResource(track.getImageURL());
        vh.title.setText(track.getTitle());
        vh.artist.setText(track.getArtist());
//        vh.genre.setText(track.getGenre());


        convertView.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {

                final MediaPlayerService mediaPlayerService = new MediaPlayerService(track.getTrackURL());

                if(!list.isEmpty()) {
                    System.out.println(list.get(0));
                    MediaPlayerService thread = list.get(0);
                    thread.stopTrack();
                    list.clear();
                    list.add(mediaPlayerService);
                }
                else {
                    list.add(mediaPlayerService);
                }

                mediaPlayerService.start();

                vh.playpause.setImageResource(R.drawable.ic_pause_circle);
                vh.title.setTextColor(Color.parseColor("#ff4081"));
                vh.artist.setTextColor(Color.parseColor("#c60055"));

                vh.playpause.setOnClickListener(new AdapterView.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        mediaPlayerService.pauseTrack();
                        vh.playpause.setImageResource(R.drawable.ic_play);

                        vh.title.setTextColor(Color.parseColor("#fafafa"));
                        vh.artist.setTextColor(Color.parseColor("#c7c7c7"));

                    }
                });

            }
        });

        return convertView;
    }
}
