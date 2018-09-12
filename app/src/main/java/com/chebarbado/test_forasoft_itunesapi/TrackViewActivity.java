package com.chebarbado.test_forasoft_itunesapi;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.chebarbado.test_forasoft_itunesapi.Adapters.AlbumListAdapter;
import com.chebarbado.test_forasoft_itunesapi.Adapters.TrackListAdapter;
import com.chebarbado.test_forasoft_itunesapi.model.Tracks;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class TrackViewActivity extends AppCompatActivity {

    private static final String TAG = "TRACK VIEW";
    private static final String ALBUM_NAME = "collectionName";
    private static final String BAND = "artistName";
    private static final String TRACK_NAME = "trackName";
    private static final String TRACK_NUMBER = "trackNumber";
    private static final String TRACKS_COUNT = "trackCount";
    private static final String TARGET_REQUEST = "https://itunes.apple.com/search?term=Metallica+%s&entity=song&explicit=No";
    private static final String NEW_LINE = "\n";
    private static final String RES = "results";
    private final Handler handler = new Handler();
    private ArrayList<Tracks> arrayOfTrackList;
    private ListView trackslv;
    private String band;
    private String album;
    private int trackCount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tracks_view_activity);
        arrayOfTrackList = new ArrayList<>();
        trackslv = (ListView) findViewById(R.id.track_list);
        getInfo();
        getTracks(album);
    }

    public void getInfo() {
        band = getIntent().getExtras().getString(BAND, BAND);
        album = getIntent().getExtras().getString(ALBUM_NAME, ALBUM_NAME);
        trackCount = getIntent().getExtras().getInt(TRACKS_COUNT, 1);
    }


    private void getTracks(final String album) {
        new Thread() {
            public void run() {
                final JSONObject json = getJSONdata(getApplicationContext(), album);
                if (json == null) {
                    handler.post(new Runnable() {
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "FALSE",
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    handler.post(new Runnable() {
                        public void run() {
                            printTracks(json);
                        }
                    });
                }
            }

        }.start();


    }

    public JSONObject getJSONdata(Context context, String album) {
        StringBuilder content = new StringBuilder();
        try {
            URL url = new URL(String.format(TARGET_REQUEST, album.replace(" ", "+")));
            URLConnection connection = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                content.append(line).append(NEW_LINE);
                Log.d(TAG, "getJSONdata: " + line);
            }
            bufferedReader.close();
            return new JSONObject(content.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private void printTracks(JSONObject json) {
        try {

            JSONArray jsonArray = json.getJSONArray(RES);


            for (int i = 0; i < trackCount; i++) {
                JSONObject trackObject = jsonArray.getJSONObject(i);

                arrayOfTrackList.add(new Tracks(
                        trackObject.getString(TRACK_NAME),
                        trackObject.getInt(TRACK_NUMBER)
                        , album
                        , band
                ));
            }
        } catch (JSONException e) {
            e.printStackTrace();

        }

        TrackListAdapter adapter = new TrackListAdapter(
                this, R.layout.list_item_layout, arrayOfTrackList
        );
        trackslv.setAdapter(adapter);
    }


}
