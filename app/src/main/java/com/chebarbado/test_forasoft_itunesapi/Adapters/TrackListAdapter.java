package com.chebarbado.test_forasoft_itunesapi.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chebarbado.test_forasoft_itunesapi.R;
import com.chebarbado.test_forasoft_itunesapi.model.Tracks;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TrackListAdapter extends ArrayAdapter<Tracks> {

    ArrayList<Tracks> tracks;
    Context context;
    int resource;


    public TrackListAdapter(Context context, int resource, ArrayList<Tracks> tracks) {
        super(context, resource, tracks);
        this.tracks = tracks;
        this.context = context;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext()
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item_layout, null, false);

        }
        Tracks tracks = getItem(position);

//        ImageView imageView = (ImageView) convertView.findViewById(R.id.image_view_artwork);
//        imageView.setImageDrawable(R.drawable.);

        TextView collectionName = (TextView) convertView.findViewById(R.id.collection_name);
        collectionName.setText(tracks.getTrackName());

        TextView artistName = (TextView) convertView.findViewById(R.id.artist_name_text_view);
        artistName.setText(tracks.getAlbum());

        TextView trackCount = (TextView) convertView.findViewById(R.id.track_count);
        trackCount.setText(String.valueOf(tracks.getTrackNumber()));

        return convertView;
    }
}