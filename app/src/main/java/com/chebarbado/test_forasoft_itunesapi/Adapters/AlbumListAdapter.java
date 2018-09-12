package com.chebarbado.test_forasoft_itunesapi.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.chebarbado.test_forasoft_itunesapi.R;
import com.chebarbado.test_forasoft_itunesapi.model.Albums;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AlbumListAdapter extends ArrayAdapter<Albums> implements Filterable {

    ArrayList<Albums> albums;
    ArrayList<Albums> resReq;
    Context context;
    int resource;


    public AlbumListAdapter(Context context, int resource, ArrayList<Albums> albums) {
        super(context, resource, albums);
        this.albums = albums;
        this.context = context;
        this.resource = resource;
        resReq = new ArrayList<>();
        resReq.addAll(albums);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext()
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item_layout, null, false);

        }
        Albums albums = getItem(position);

        ImageView imageView = (ImageView) convertView.findViewById(R.id.image_view_artwork);
        Picasso.get().load(albums.getArtwork()).into(imageView);

        TextView collectionName = (TextView) convertView.findViewById(R.id.collection_name);
        collectionName.setText(albums.getCollectionName());

        TextView artistName = (TextView) convertView.findViewById(R.id.artist_name_text_view);
        artistName.setText(albums.getArtistName());

        TextView trackCount = (TextView) convertView.findViewById(R.id.track_count);
        trackCount.setText(String.valueOf(albums.getTrackCount()));

        return convertView;
    }


    // Фильтр по альбомам
    @NonNull
    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {

            // Фильтруем в соответствии с вводом в EditText
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults result = new FilterResults();
                if (charSequence == null || charSequence.length() == 0) {
                    result.values = resReq; // здесь контролируем чтобы при пустой строке было отображение всего списка.
                    result.count = resReq.size();
                } else {
                    ArrayList<Albums> filteredList = new ArrayList<>();
                    charSequence = charSequence.toString().toLowerCase();
                    for (Albums p : resReq) {
                        if (p.getCollectionName().toLowerCase().startsWith(charSequence.toString())) //если начинается с введенной строки добавляем в результат
                            filteredList.add(p);
                    }

                    result.values = filteredList;
                    result.count = filteredList.size();
                }
                return result;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                refreshData();
                albums.clear();
                albums.addAll((ArrayList<Albums>) filterResults.values);

                Log.d("ATTENTION", "performFiltering: " + getCount());

                notifyDataSetChanged();

            }
        };
        return filter;
    }

    // Сбрасываем массив альбомов до начального
    public void refreshData() {
        albums.clear();
        albums.addAll(resReq);
    }
}