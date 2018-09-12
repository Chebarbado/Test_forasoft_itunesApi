package com.chebarbado.test_forasoft_itunesapi.model;

public class Albums {
    private String artwork;
    private String collectionName;
    private String artistName;
    private int trackCount;

    public Albums(String artwork, String collectionName, String artistName, int trackCount) {
        this.artwork = artwork;
        this.collectionName = collectionName;
        this.artistName = artistName;
        this.trackCount = trackCount;
    }

    public String getArtwork() {
        return artwork;
    }

    public void setArtwork(String artwork) {
        this.artwork = artwork;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }


    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }


    public int getTrackCount() {
        return trackCount;
    }

    public void setTrackCount(int trackCount) {
        this.trackCount = trackCount;
    }
}
