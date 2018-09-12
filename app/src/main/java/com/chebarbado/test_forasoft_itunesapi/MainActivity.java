package com.chebarbado.test_forasoft_itunesapi;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.chebarbado.test_forasoft_itunesapi.Adapters.AlbumListAdapter;
import com.chebarbado.test_forasoft_itunesapi.model.Albums;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "ALBUM ACTIVITY";
    private static final String RES = "results";
    private static final String ALBUM_NAME = "collectionName";
    private static final String BAND = "artistName";
    private static final String IMAGE = "artworkUrl100";
    private static final String TRACKS_COUNT = "trackCount";
    private static final String TARGET_REQUEST = "https://itunes.apple.com/search?term=Metallica&entity=album";
    private static final String NEW_LINE = "\n";
    private ArrayList<Albums> arrayOfAlbumsList;
    private ListView albumslv;
    private EditText searchET;
    private JSONObject albumsObject;
    private JSONArray jsonArray;
    private JSONObject jsonObject;
    private AlbumListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        arrayOfAlbumsList = new ArrayList<>();
        albumslv = (ListView) findViewById(R.id.albums_list);
        searchET = (EditText) findViewById(R.id.search_edit_text);


        //В потоке ищем данные
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlbumsLoader().execute(TARGET_REQUEST);
            }
        });

        //Устанавливаем слушателя на элемент списка альбомов
        albumslv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {

                viewTracksFromAlbum(i);

            }
        });

        // Следим за вводом текста
        searchET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                MainActivity.this.adapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }


    class AlbumsLoader extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            return readURL(params[0]);
        }

        @Override
        protected void onPostExecute(String content) {
            try {
                jsonObject = new JSONObject(content);

                jsonArray = jsonObject.getJSONArray(RES);


                for (int i = 0; i < jsonArray.length(); i++) {
                    albumsObject = jsonArray.getJSONObject(i);

                    arrayOfAlbumsList.add(new Albums(
                            albumsObject.getString(IMAGE),
                            albumsObject.getString(ALBUM_NAME),
                            albumsObject.getString(BAND),
                            albumsObject.getInt(TRACKS_COUNT)
                    ));
                }
            } catch (JSONException e) {
                e.printStackTrace();

            }
            sortArray(arrayOfAlbumsList);
            //Связываем данные массива с элементом ListView:

            adapter = new AlbumListAdapter(
                    getApplicationContext(), R.layout.list_item_layout, arrayOfAlbumsList
            );
            adapter.setNotifyOnChange(true);
            albumslv.setAdapter(adapter);
        }
    }

    //Читаем URL
    private static String readURL(String theUrl) {
        StringBuilder content = new StringBuilder();
        try {
            URL url = new URL(theUrl);
            URLConnection connection = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                content.append(line).append(NEW_LINE);

            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    //Сортировка по названию альбома
    private void sortArray(ArrayList<Albums> unsortedArray) {
        Albums buff;
        for (int i = 0; i < unsortedArray.size(); i++) {
            for (int j = 0; j < unsortedArray.size() - 1; j++) {
                if (unsortedArray.get(j).getCollectionName().compareTo(unsortedArray.get(j + 1).getCollectionName()) > 0) {
                    buff = unsortedArray.get(j);
                    unsortedArray.set(j, unsortedArray.get(j + 1));
                    unsortedArray.set(j + 1, buff);
                }
            }

        }
    }

    //Отправляем в интент название группы и альбом и количество композиций
    private void viewTracksFromAlbum(int position) {

        String albumName = arrayOfAlbumsList.get(position).getCollectionName();
        String artistName = arrayOfAlbumsList.get(position).getArtistName();
        int trackCount = arrayOfAlbumsList.get(position).getTrackCount();
        Intent intent = new Intent(this, TrackViewActivity.class);
        intent.putExtra(ALBUM_NAME, albumName);
        intent.putExtra(BAND, artistName);
        intent.putExtra(TRACKS_COUNT, trackCount);
        Log.d(TAG, "viewTracksFromAlbum: " + albumName + NEW_LINE + artistName + NEW_LINE + trackCount);
        startActivity(intent);


    }


}