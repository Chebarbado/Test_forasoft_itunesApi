package com.chebarbado.test_forasoft_itunesapi.model;

public class Tracks {
    private String trackName;
    private int trackNumber;
    private String band;
    private String album;


    public String getBand() {
        return band;
    }

    public void setBand(String band) {
        this.band = band;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }


    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public int getTrackNumber() {
        return trackNumber;
    }

    public void setTrackNumber(int trackNumber) {
        this.trackNumber = trackNumber;
    }

    public Tracks(String trackName, int trackNumber, String album, String band) {
        this.trackName = trackName;
        this.trackNumber = trackNumber;
        this.album = album;
        this.band = band;

    }
}
